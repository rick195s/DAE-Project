package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Hibernate;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.UserDontHavePolicyException;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;
import pt.ipleiria.estg.dei.ei.dae.project.utils.FileUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
public class OccurrenceBean {
    @PersistenceContext
    EntityManager entityManager;

    @EJB
    HistoricalBean historicalBean;

    @EJB
    PolicyBean policyBean;

    @EJB
    EmailBean emailBean;

    @EJB
    RepairShopBean repairShopBean;

    @EJB
    RepairShopExpertBean repairShopExpertBean;

    @EJB
    InsurerBean insurerBean;

    @EJB
    InsurerExpertBean insurerExpertBean;

    @EJB
    OccurrenceFileBean occurrenceFileBean;

    @EJB
    ClientBean clientBean;

    public Occurrence create(int policyId, String description, int clientId) throws OccurrenceSmallDescriptionException, UserDontHavePolicyException {
        if (description.length() < 10) {
            throw new OccurrenceSmallDescriptionException(description);
        }

        Client client = clientBean.find(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client dont exists");
        }

        Policy policy = policyBean.find(policyId);
        if (policy == null) {
            throw new EntityNotFoundException("Policy dont exists");
        }

        if (policy.getClientNIFNIPC() != client.getNIF_NIPC()) {
            throw new UserDontHavePolicyException();
        }

        Occurrence occurrence = new Occurrence(policy, new RepairShop(), description, ApprovalType.WAITING_FOR_APPROVAL, Calendar.getInstance(), null, client);

        entityManager.persist(occurrence);
        // flushing to get the generated Id
        entityManager.flush();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        historicalBean.create("Occurrence Created", occurrence.getId(), formatter.format(Calendar.getInstance().getTime()), HistoricalEnum.A_AGUARDAR_APROVACAO_PELA_SEGURADORA);

        return occurrence;
    }


    public void createOccurrenceWithCSV(MultipartFormDataInput input) throws OccurrenceSmallDescriptionException, IOException, UserDontHavePolicyException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        for (InputPart inputPart : inputParts) {
            InputStream inputStream = inputPart.getBody(InputStream.class, null);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] occurrence = line.split(";");

                create(
                        Integer.parseInt(occurrence[0]),
                        occurrence[1],
                        Integer.parseInt(occurrence[2])
                );

            }
        }
    }

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(*) FROM " + Occurrence.class.getSimpleName(), Long.class).getSingleResult();
    }


    public List<Occurrence> getAllOccurrences(User user, int offset, int limit) {
        List<Policy> policies;
        List<Integer> ids = new ArrayList<>();

        Query query = getOccurrencesOfUser(user);

        List<Occurrence> occurrences = query != null ? (List<Occurrence>) query
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList()
                : new ArrayList<>();

        for (Occurrence occurrence : occurrences) {
            ids.add(occurrence.getPolicyId());
        }

        policies = policyBean.findAll(ids);
        for (Occurrence occurrence : occurrences) {
            for (Policy policy : policies) {
                if (occurrence.getPolicyId() == policy.getId()) {
                    occurrence.setPolicy(policy);
                }
            }
        }
        return occurrences;
    }


    public Query getOccurrencesOfUser(User user) {

        switch (user.getRole()) {
            case CLIENT:
                return entityManager.createNamedQuery("getOccurrencesOfClient")
                        .setParameter("client", clientBean.find(user.getId()));

            case REPAIR_SHOP_EXPERT:
                RepairShopExpert repairShopExpert = repairShopExpertBean.find(user.getId());
                if (repairShopExpert == null) {
                    throw new EntityNotFoundException("RepairShopExpert dont exists");
                }
                return entityManager.createNamedQuery("getOccurrencesOfRepairShop")
                        .setParameter("repairShopId", repairShopExpert.getRepairShopId());

            case INSURER_EXPERT:
                InsurerExpert insurerExpert = insurerExpertBean.find(user.getId());
                if (insurerExpert == null) {
                    throw new EntityNotFoundException("InsurerExpert dont exists");
                }
                List<Integer> policiesIds = insurerBean.getPoliciesIds(insurerExpert.getInsurerId());
                return  entityManager.createNamedQuery("getOccurrencesOfInsurerByPolicies")
                        .setParameter("policiesIds", policiesIds);

            case ADMINISTRATOR:
                return entityManager.createNamedQuery("getAllOccurrences");

        }
        return null;
    }

    public Occurrence find(int id) {
        Occurrence occurrence = entityManager.find(Occurrence.class, id);
        if (occurrence == null) {
            return null;
        }

        occurrence.setPolicy(policyBean.find(occurrence.getPolicyId()));

        return occurrence;
    }

    public List<OccurrenceFile> getOccurrenceFiles(int id) {
        Occurrence occurrence = entityManager.find(Occurrence.class, id);
        if (occurrence == null) {
            throw new IllegalArgumentException("Occurrence dont exists");
        }

        Hibernate.initialize(occurrence.getOccurenceFileList());
        return occurrence.getOccurenceFileList();
    }

    public List<Historical> getHistorical(int id) {
        Occurrence occurrence = entityManager.find(Occurrence.class, id);
        if (occurrence == null) {
            throw new IllegalArgumentException("Occurrence dont exists");
        }

        Hibernate.initialize(occurrence.getOccurenceHistoricalList());
        return occurrence.getOccurenceHistoricalList();
    }

    public void approveOccurrence(int id) {
        Occurrence occurrence = find(id);
        if (occurrence == null) {
            throw new EntityNotFoundException("Occurrence dont exists");
        }

        occurrence.setApprovalType(ApprovalType.APPROVED);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentData = formatter.format(Calendar.getInstance().getTime());

        historicalBean.create("Aprovado pela seguradora", occurrence.getId(), currentData, HistoricalEnum.APROVADO_PELA_SEGURADORA);

        entityManager.merge(occurrence);
    }

    public void declineOccurrence(int id) {
        Occurrence occurrence = find(id);
        if (occurrence == null) {
            throw new EntityNotFoundException("Occurrence dont exists");
        }

        occurrence.setApprovalType(ApprovalType.REJECTED);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentData = formatter.format(Calendar.getInstance().getTime());

        historicalBean.create("Rejected by insurer", occurrence.getId(), currentData, HistoricalEnum.NAO_APROVADO_PELA_SEGURADORA);

        entityManager.merge(occurrence);
    }

    public void concludeOccurrence(int id) {
        Occurrence occurrence = find(id);
        if (occurrence == null) {
            throw new EntityNotFoundException("Occurrence dont exists");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentData = formatter.format(Calendar.getInstance().getTime());

        historicalBean.create("Occurrence concluded and finished by repair shop expert", occurrence.getId(), currentData, HistoricalEnum.APROVADO_PELO_EXPERT);

        entityManager.merge(occurrence);
    }

    public void setOccurrenceRepairShop(int id, int repairShopId) throws MessagingException {
        RepairShop repairShop = repairShopBean.find(repairShopId);
        if (repairShop == null) {
            throw new EntityNotFoundException("Repair Shop dont exists");
        }

        Occurrence occurrence = find(id);
        if (occurrence == null) {
            throw new EntityNotFoundException("Occurrence dont exists");
        }

        occurrence.setRepairShop(repairShop);

        entityManager.merge(occurrence);

        String to = repairShop.getEmail();
        for (RepairShopExpert repairShopExpert : repairShopExpertBean.findByRepairShop(repairShop.getId())) {
            to = to.concat("," + repairShopExpert.getEmail());
        }

        emailBean.send(to, "Nova ocorrência", "Foi atribuida uma nova ocorrência a sua oficina\n Link: http://127.0.0.1:3000/occurrences/" + occurrence.getId());
    }

    public void setCustomOccurrenceRepairShop(int id, String name, String email, long phone) {
        Occurrence occurrence = find(id);
        if (occurrence == null) {
            throw new EntityNotFoundException("Occurrence dont exists");
        }

        RepairShop repairShop = repairShopBean.create(name, email, phone);
        if (repairShop == null) {
            throw new EntityNotFoundException("Repair Shop dont exists");
        }

        occurrence.setRepairShop(repairShop);

        entityManager.merge(occurrence);
    }

    public List<OccurrenceFile> uploadFiles(int id, MultipartFormDataInput input) throws IOException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

        List<InputPart> inputParts = uploadForm.get("file");

        List<OccurrenceFile> occurrenceFiles = new LinkedList<OccurrenceFile>();

        FileUtils fileUtils = new FileUtils();

        for (InputPart inputPart : inputParts) {

            String filename = fileUtils.getFilename(inputPart.getHeaders());
            String ext = FilenameUtils.getExtension(filename);
            filename = FilenameUtils.removeExtension(filename) + "_" + System.currentTimeMillis() + "." + ext;

            String filepath = fileUtils.upload("occurrences" + File.separator + id, filename, inputPart);

            var occurrenceFile = occurrenceFileBean.create(id, filename, filepath);
            occurrenceFiles.add(occurrenceFile);
        }

        return occurrenceFiles;
    }


}

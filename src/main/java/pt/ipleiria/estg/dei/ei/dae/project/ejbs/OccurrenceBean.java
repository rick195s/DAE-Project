package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Stateless
public class OccurrenceBean {
    @PersistenceContext
    EntityManager entityManager;

    @EJB
    HistoricalBean historicalBean;

    @EJB
    PolicyBean policyBean;

    @EJB
    RepairShopBean repairShopBean;

    @EJB
    ClientBean clientBean;

    public Occurrence create(int policyId, String description, int id) throws OccurrenceSmallDescriptionException {
        if (description.length() < 10) {
            throw new OccurrenceSmallDescriptionException(description);
        }

        Client client = clientBean.find(id);
        if (client == null) {
            throw new EntityNotFoundException("Client dont exists");
        }

        Policy policy = policyBean.find(policyId);
        if (policy == null) {
            throw new EntityNotFoundException("Policy dont exists");
        }

        Occurrence occurrence = new Occurrence(policy, new RepairShop(), description, ApprovalType.WAITING_FOR_APPROVAL, Calendar.getInstance(), null, client);

        entityManager.persist(occurrence);
        // flushing to get the generated Id
        entityManager.flush();

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        historicalBean.create("Occurrence Created", occurrence.getId(), formatter.format(Calendar.getInstance().getTime()), HistoricalEnum.A_AGUARDAR_APROVACAO_PELA_SEGURADORA);

        return occurrence;
    }

    public List<Occurrence> getAllOccurrences() {
        List<Policy> policies;
        List<Integer> ids = new ArrayList<>();

        List<Occurrence> occurrences = (List<Occurrence>) entityManager.createNamedQuery("getAllOccurrences").getResultList();

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

    public Occurrence find(int id) {
        Occurrence occurrence = entityManager.find(Occurrence.class, id);
        if (occurrence == null){
            return null;
        }

        occurrence.setPolicy(policyBean.find(occurrence.getPolicyId()));

        return  occurrence;
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

    public List<Occurrence> getOccurrencesOfClient(int id) {
        Client client = entityManager.find(Client.class, id);
        if (client == null) {
            throw new IllegalArgumentException("Client dont exists");
        }

        return (List<Occurrence>) entityManager.createNamedQuery("getOccurrencesByClient").setParameter("client", client).getResultList();
    }

    public void approveOccurrence(int id) {
        Occurrence occurrence = find(id);
        if (occurrence == null) {
            throw new EntityNotFoundException("Occurrence dont exists");
        }

        occurrence.setApprovalType(ApprovalType.APPROVED);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentData = formatter.format(Calendar.getInstance().getTime());

        historicalBean.create("Rejeitado pela seguradora", occurrence.getId(), currentData, HistoricalEnum.NAO_APROVADO_PELA_SEGURADORA);

        entityManager.merge(occurrence);
    }

    public void setOccurrenceRepairShop(int id, int repairShopId) {
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

    }
}

package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
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

    public Occurrence create(int policyId, int repairShopId, String description, int clientId) throws OccurrenceSmallDescriptionException, EntityNotFoundException {
        if (description.length() < 10) {
            throw new OccurrenceSmallDescriptionException(description);
        }

        Client client = entityManager.find(Client.class, clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client dont exists");
        }

        Policy policy = policyBean.findPolicy(policyId);
        if (policy == null) {
            throw new EntityNotFoundException("Policy dont exists");
        }

        Occurrence occurrence = new Occurrence(policy, new RepairShop(), description, ApprovalType.WAITING_FOR_APPROVAL, Calendar.getInstance(), null, client);

        entityManager.persist(occurrence);
        // flushing to get the generated Id
        entityManager.flush();

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        historicalBean.create("Occurrence Created", occurrence.getId(), formatter.format(Calendar.getInstance().getTime()));

        return occurrence;
    }

    public List<Occurrence> getAllOccurrences() {
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return (List<Occurrence>) entityManager.createNamedQuery("getAllOccurrences").getResultList();
    }

    public Occurrence findOccurrence(int id) {
        return entityManager.find(Occurrence.class, id);
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
}

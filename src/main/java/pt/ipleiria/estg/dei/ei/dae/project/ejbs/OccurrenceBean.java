package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

@Stateless
public class OccurrenceBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, Policy policy, RepairShop repairShop, String description, int clientId) {
        Occurrence occurrence = findOccurrence(id);
        if (occurrence != null) {
            throw new IllegalArgumentException("Occurrence already exists");
        }

        Client client = entityManager.find(Client.class, clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client dont exists");
        }

        occurrence = new Occurrence(id, policy, repairShop, description, ApprovalType.WAITING_FOR_APPROVAL, Calendar.getInstance(), null, client);
        entityManager.persist(occurrence);
    }

    public List<Occurrence> getAllOccurrences() {
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return (List<Occurrence>) entityManager.createNamedQuery("getAllOccurrences").getResultList();
    }

    public Occurrence findOccurrence(int id) {
        return entityManager.find(Occurrence.class, id);
    }
}

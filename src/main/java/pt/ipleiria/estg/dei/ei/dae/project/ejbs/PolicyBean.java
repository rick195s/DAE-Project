package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.entities.PolicyTypeDetail;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

@Stateless
public class PolicyBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, int clientId, int insurerId, PolicyTypeDetail policyTypeDetail, Calendar startDate, Calendar endDate) {
        Policy policy = findPolicy(id);
        if (policy != null) {
            throw new IllegalArgumentException("Policy already exists");
        }

        Client client = entityManager.find(Client.class, clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client does not exist");
        }

        Insurer insurer = entityManager.find(Insurer.class, insurerId);
        if (insurer == null) {
            throw new IllegalArgumentException("Insurer does not exist");
        }

        policy = new Policy(id, client, insurer, PolicyState.WAITING_FOR_VALIDATION, policyTypeDetail, startDate, endDate);
        entityManager.persist(policy);
    }

    public List<Policy> getAllPolicies() {
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return (List<Policy>) entityManager.createNamedQuery("getAllPolicies").getResultList();
    }

    public Policy findPolicy(int id) {
        return entityManager.find(Policy.class, id);
    }
}

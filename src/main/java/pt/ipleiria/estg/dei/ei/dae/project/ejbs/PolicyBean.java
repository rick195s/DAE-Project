package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PolicyBean {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    Supervisor supervisor;

    public List<Policy> getAllPolicies() {
        return supervisor.getPolicies();
    }

    public Policy findPolicy(int id) {
        for (Policy policy : supervisor.getPolicies()) {
            if (policy.getId() == id) {
                return policy;
            }
        }
        return null;
    }
}

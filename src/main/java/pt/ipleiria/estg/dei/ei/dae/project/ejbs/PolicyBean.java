package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PolicyBean {
    @Inject
    Supervisor supervisor;

    public List<Policy> getAllPolicies() {
        return supervisor.getPolicies();
    }

    public Policy find(int id) {
        for (Policy policy : supervisor.getPolicies()) {
            if (policy.getId() == id) {
                return policy;
            }
        }
        return null;
    }
}

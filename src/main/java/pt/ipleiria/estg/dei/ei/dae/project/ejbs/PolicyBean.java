package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PolicyBean {
    @PersistenceContext
    EntityManager entityManager;

    @EJB
    private ConfigBean configBean;

    public List<Policy> getAllPolicies() {
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return configBean.getPolicies();
    }

    public Policy findPolicy(int id) {
        for (Policy policy : configBean.getPolicies()) {
            if (policy.getId() == id) {
                return policy;
            }
        }
        return null;
    }
}

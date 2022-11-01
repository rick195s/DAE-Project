package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.PolicyObject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PolicyObjectBean {
    @PersistenceContext
    EntityManager entityManager;

    public List<PolicyObject> getAllPolicyObjects() {
        return (List<PolicyObject>) entityManager.createNamedQuery("getAllPolicyObjects").getResultList();
    }

    public PolicyObject findPolicyObject(int id) {
        return entityManager.find(PolicyObject.class, id);
    }


    public void create(int id, String name, String filepath) {
        PolicyObject policyObject = findPolicyObject(id);
        if (policyObject != null) {
            throw new IllegalArgumentException("PolicyObject already exists");
        }

        policyObject= new PolicyObject(id, name, filepath);
        entityManager.persist(policyObject);
    }
}

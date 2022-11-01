package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.PolicyTypeDetail;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PolicyTypeDetailBean {
    @PersistenceContext
    EntityManager entityManager;

    public List<PolicyTypeDetail> getAllPolicyTypeDetails() {
        return (List<PolicyTypeDetail>) entityManager.createNamedQuery("getAllPolicyTypeDetails").getResultList();
    }

    public PolicyTypeDetail findPolicyTypeDetail(int id) {
        return entityManager.find(PolicyTypeDetail.class, id);
    }


    public void create(int id, PolicyType policyType, PolicyObjectType policyObjectType, String description) {
        PolicyTypeDetail policyTypeDetail = findPolicyTypeDetail(id);
        if (policyTypeDetail != null) {
            throw new IllegalArgumentException("PolicyTypeDetail already exists");
        }

        policyTypeDetail= new PolicyTypeDetail(id, policyType, policyObjectType, description);
        entityManager.persist(policyTypeDetail);
    }
}

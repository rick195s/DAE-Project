package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PolicyBean {
    @Inject
    Supervisor supervisor;
    @EJB
    ClientBean clientBean;

    @PersistenceContext
    EntityManager entityManager;

    public List<Policy> getAllPolicies(int NIF)
    {
        return supervisor.getPolicies(NIF);
    }

    public List<Policy> getAllPolicies()
    {
        return getAllPolicies(0);
    }

    public List<Policy> findAll(List<Integer> policiesIds) {
        List<Policy> policies = supervisor.getPolicies();
        policies.removeIf(policy -> !policiesIds.contains(policy.getId()));
        return policies;
    }

    public Policy find(int id) {
        for (Policy policy : supervisor.getPolicies()) {
            if (policy.getId() == id) {
                return policy;
            }
        }
        return null;
    }
    public PolicyTypeDetail getPolicyDetails(int id) {
        return supervisor.getPolicyTypeDetail(id);
    }

}

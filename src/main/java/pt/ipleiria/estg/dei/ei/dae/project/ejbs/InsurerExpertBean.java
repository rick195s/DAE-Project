package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.InsurerExpert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class InsurerExpertBean {
    @PersistenceContext
    EntityManager entityManager;

    public List<InsurerExpert> getAllInsurerExpert() {
        return (List<InsurerExpert>) entityManager.createNamedQuery("getAllInsurerExperts").getResultList();
    }

    public InsurerExpert find(int id) {
        return entityManager.find(InsurerExpert.class, id);
    }

}

package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.InsurerExpert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class InsurerExpertBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String email, String password, Insurer insurer) {
        InsurerExpert insurer_expert = findInsurerExpert(id);
        if (insurer_expert != null) {
            throw new IllegalArgumentException("Insurer_Expert already exists");
        }
        insurer_expert = new InsurerExpert(id, name, email, password, insurer);
        entityManager.persist(insurer_expert);
    }

    public List<InsurerExpert> getAllInsurerExpert() {
        return (List<InsurerExpert>) entityManager.createNamedQuery("getAllInsurerExperts").getResultList();
    }

    public InsurerExpert findInsurerExpert(int id) {
        return entityManager.find(InsurerExpert.class, id);
    }
}

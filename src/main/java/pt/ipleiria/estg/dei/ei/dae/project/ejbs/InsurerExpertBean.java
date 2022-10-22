package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.InsurerExpert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InsurerExpertBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String email, String password, Insurer insurer) {
        InsurerExpert insurer_expert = findInsurer_Expert(id);
        if (insurer_expert != null) {
            throw new IllegalArgumentException("Insurer_Expert already exists");
        }
        insurer_expert = new InsurerExpert(id, name, email, password, insurer);
        entityManager.persist(insurer_expert);
    }

    private InsurerExpert findInsurer_Expert(int id) {
        return entityManager.find(InsurerExpert.class, id);
    }
}

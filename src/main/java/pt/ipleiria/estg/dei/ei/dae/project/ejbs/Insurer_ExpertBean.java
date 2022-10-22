package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.enteties.Insurer_Expert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Insurer_ExpertBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String email, String password, Insurer insurer) {
        Insurer_Expert insurer_expert = findInsurer_Expert(id);
        if (insurer_expert != null) {
            throw new IllegalArgumentException("Insurer_Expert already exists");
        }
        insurer_expert = new Insurer_Expert(id, name, email, password, insurer);
        entityManager.persist(insurer_expert);
    }

    private Insurer_Expert findInsurer_Expert(int id) {
        return entityManager.find(Insurer_Expert.class, id);
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.InsurerExpert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class InsurerBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name) {
        Insurer insurer = findInsurer(id);
        if (insurer != null) {
            throw new IllegalArgumentException("Insurer already exists");
        }
        insurer = new Insurer(id, name);
        entityManager.persist(insurer);
    }

    public List<Insurer> getAllInsurer() {
        return (List<Insurer>) entityManager.createNamedQuery("getAllInsurers").getResultList();
    }

    public Insurer findInsurer(int id) {
        return entityManager.find(Insurer.class, id);
    }
}

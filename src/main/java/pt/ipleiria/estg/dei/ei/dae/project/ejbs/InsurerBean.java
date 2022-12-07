package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;

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

    public void update(int id, String name) {
        Insurer insurer = findInsurer(id);
        if (insurer == null) {
            throw new IllegalArgumentException("Insurer does not exist");
        }
        insurer.setName(name);
        entityManager.merge(insurer);
    }

    public void delete(int id) {
        Insurer insurer = findInsurer(id);
        if (insurer == null) {
            throw new IllegalArgumentException("Insurer does not exist");
        }
        entityManager.remove(insurer);
    }
}

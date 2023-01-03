package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class InsurerBean {

    EntityManager entityManager;

    @Inject
    Supervisor supervisor;


    public void create(int id, String name) {
        Insurer insurer = find(id);
        if (insurer != null) {
            throw new IllegalArgumentException("Insurer already exists");
        }
        insurer = new Insurer(id, name);
        entityManager.persist(insurer);
    }

    public List<Insurer> getAllInsurers() {
        return supervisor.getInsurers();
    }

    public Insurer find(int id) {
        return entityManager.find(Insurer.class, id);
    }

    public void update(int id, String name) {
        Insurer insurer = find(id);
        if (insurer == null) {
            throw new IllegalArgumentException("Insurer does not exist");
        }
        insurer.setName(name);
        entityManager.merge(insurer);
    }

    public void delete(int id) {
        Insurer insurer = find(id);
        if (insurer == null) {
            throw new IllegalArgumentException("Insurer does not exist");
        }
        entityManager.remove(insurer);
    }
}

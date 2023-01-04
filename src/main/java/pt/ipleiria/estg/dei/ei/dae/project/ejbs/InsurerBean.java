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

    public List<Insurer> getAllInsurers() {
        return supervisor.getInsurers();
    }

    public Insurer find(int id) {
        return entityManager.find(Insurer.class, id);
    }
}

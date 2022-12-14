package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.gateways.APIGateway;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class InsurerBean {

    EntityManager entityManager;

    @EJB
    private ConfigBean configBean;


    public void create(int id, String name) {
        Insurer insurer = findInsurer(id);
        if (insurer != null) {
            throw new IllegalArgumentException("Insurer already exists");
        }
        insurer = new Insurer(id, name);
        entityManager.persist(insurer);
    }

    public List<Insurer> getAllInsurers() {
        return configBean.getInsurers();
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

package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.gateways.APIGateway;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class InsurerBean {
    final String URI_INSURERS = "https://634f1183df22c2af7b4a4b38.mockapi.io/insurers";

    final List<Insurer> insurers = new ArrayList<Insurer>();

    EntityManager entityManager;

    @PostConstruct
    private void populateInsurersViaAPI(){
        JsonArray jsonArrayInsurers = APIGateway.getDataFromAPI(URI_INSURERS);
        jsonArrayInsurers.forEach(insurer -> {
            Jsonb jsonb = JsonbBuilder.create();
            Insurer insurerObj = jsonb.fromJson(insurer.toString(), Insurer.class);

            insurers.add(insurerObj);
        });
    }

    public void create(int id, String name) {
        Insurer insurer = findInsurer(id);
        if (insurer != null) {
            throw new IllegalArgumentException("Insurer already exists");
        }
        insurer = new Insurer(id, name);
        entityManager.persist(insurer);
    }

    public List<Insurer> getAllInsurers() {
        return insurers;
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

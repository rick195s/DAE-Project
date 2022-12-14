package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.APIGateway;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;

import javax.ejb.Stateless;
import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Stateless
public class PolicyBean {
    @PersistenceContext
    EntityManager entityManager;

    final List<Policy> policies = new ArrayList<Policy>();

    private void populatePoliciesViaAPI(){
        int occurrenceId = 1;
        entityManager.find(Occurrence.class, occurrenceId);

        Client client =  entityManager.find(Client.class, 1);

        Insurer insurer = new Insurer(1, "Allianz");

        Policy policy1 = new Policy(1,client, insurer, PolicyState.APPROVED, new PolicyTypeDetail(), new PolicyObject(),
                Calendar.getInstance(), Calendar.getInstance());

        policies.add(policy1);

    }

    public List<Policy> getAllPolicies() {
        populatePoliciesViaAPI();
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return policies;
    }

    public Policy findPolicy(int id) {
        for (Policy policy : policies) {
            if (policy.getId() == id) {
                return policy;
            }
        }
        return null;
    }
}

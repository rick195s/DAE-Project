package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PolicyObjectBean {

    @Inject
    Supervisor supervisor;


    public List<PolicyObject> getAllPolicyObjects() {
        return supervisor.getPolicyObjects();
    }
}

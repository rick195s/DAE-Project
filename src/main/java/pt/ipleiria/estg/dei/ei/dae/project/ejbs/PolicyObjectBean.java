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
    public void create(String name, String filePath) {
        PolicyObject policyObject= supervisor.getPolicyObject(name);
        if(policyObject!=null){
            throw new IllegalArgumentException("Policy object with name " + name + " already exists");
        }
        policyObject = new PolicyObject(name,filePath);
        supervisor.addPolicyObject(policyObject);
    }

    public PolicyObject findPolicyObject(String name) {
        return supervisor.getPolicyObject(name);
    }
    public List<PolicyObject> getAllPolicyObjects() {
        return supervisor.getPolicyObjects();
    }
}

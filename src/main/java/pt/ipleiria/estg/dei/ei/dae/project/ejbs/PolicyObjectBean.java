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
        for(PolicyObject policyObject : supervisor.getPolicyObjects()){
            if(policyObject.getName().equals(name)){
                throw new IllegalArgumentException("Policy object with name " + name + " already exists");
            }
        }
        PolicyObject policyObject = new PolicyObject(name,filePath);
        supervisor.addPolicyObject(policyObject);
    }

    public PolicyObject findPolicyObject(String name) {
        for (PolicyObject policyObject : supervisor.getPolicyObjects()) {
            if (policyObject.getName().equals(name)) {
                return policyObject;
            }
        }
        return null;
    }
    public List<PolicyObject> getAllPolicyObjects() {
        return supervisor.getPolicyObjects();
    }
}

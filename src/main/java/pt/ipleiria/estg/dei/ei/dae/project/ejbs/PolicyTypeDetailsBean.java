package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.Supervisor;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PolicyTypeDetailsBean {

    @Inject
    Supervisor supervisor;

    public PolicyTypeDetail find(int id) {
        return supervisor.getPolicyTypeDetail(id);
    }
    public List<PolicyTypeDetail> getAllPolicyTypeDetails() {
        return supervisor.getPolicyTypeDetails();
    }
}

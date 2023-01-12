package pt.ipleiria.estg.dei.ei.dae.project.dtos.detailed;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;

import java.io.Serializable;
import java.util.List;

public class DetailedPolicyDTO extends PolicyDTO implements Serializable {
    PolicyTypeDetail policyTypeDetails;

    public DetailedPolicyDTO() {
    }

    public DetailedPolicyDTO(int id, int clientId, int insurerId, int policyTypeDetailId, int policyObjectId, PolicyState state, String startDate, String endDate, PolicyTypeDetail policyTypeDetails) {
        super(id, clientId, insurerId, policyTypeDetailId, policyObjectId, state, startDate, endDate);
        this.policyTypeDetails = policyTypeDetails;
    }

    public void setPolicyTypeDetails(PolicyTypeDetail policyTypeDetails) {
        this.policyTypeDetails = policyTypeDetails;
    }

    public PolicyTypeDetail getPolicyTypeDetails() {
        return policyTypeDetails;
    }
}


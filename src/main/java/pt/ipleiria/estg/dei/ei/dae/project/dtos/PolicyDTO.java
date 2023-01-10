package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PolicyDTO {
    int id;

    int clientId;

    int insurerId;

    int  policyTypeDetailId;

    int policyObjectId;

    PolicyState state;

    String startDate;

    String endDate;

    public PolicyDTO() {}

    public PolicyDTO(int id, int clientId, int insurerId, int policyTypeDetailId, int policyObjectId, PolicyState state, String startDate, String endDate) {
        this.id = id;
        this.clientId = clientId ;
        this.insurerId = insurerId;
        this.policyTypeDetailId = policyTypeDetailId;
        this.policyObjectId = policyObjectId;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getInsurerId() {
        return insurerId;
    }

    public void setInsurerId(int insurerId) {
        this.insurerId = insurerId;
    }

    public int getPolicyTypeDetailId() {
        return policyTypeDetailId;
    }

    public void setPolicyTypeDetailId(int policyTypeDetailId) {
        this.policyTypeDetailId = policyTypeDetailId;
    }

    public int getPolicyObjectId() {
        return policyObjectId;
    }

    public void setPolicyObjectId(int policyObjectId) {
        this.policyObjectId = policyObjectId;
    }

    public PolicyState getState() {
        return state;
    }

    public void setState(PolicyState state) {
        this.state = state;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public static PolicyDTO from(Policy policy) {
        return new PolicyDTO(
                policy.getId(),
                policy.getClientId(),
                policy.getInsurerId(),
                policy.getPolicyTypeDetailId(),
                policy.getPolicyObjectId(),
                policy.getState(),
                policy.getStartDate(),
                policy.getEndDate()

        );
    }

    public static List<PolicyDTO> from(List<Policy> policies) {
        return policies.stream().map(PolicyDTO::from).collect(Collectors.toList());
    }
}



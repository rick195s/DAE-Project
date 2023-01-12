package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyType;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PolicyTypeDetailsDTO implements Serializable {
    int id;

    PolicyType type;

    PolicyObjectType objectType;

    String description;

    List<Policy> policies;


    public PolicyTypeDetailsDTO(int id, PolicyType type, PolicyObjectType objectType, String description) {
        this.id = id;
        this.type = type;
        this.objectType = objectType;
        this.description = description;
        policies = new LinkedList<>();
    }

    public PolicyTypeDetailsDTO() {
        policies = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PolicyType getType() {
        return type;
    }

    public void setType(PolicyType type) {
        this.type = type;
    }

    public PolicyObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(PolicyObjectType objectType) {
        this.objectType = objectType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    public void addPolicy(Policy policy) {
        policies.add(policy);
    }

    public void removePolicy(Policy policy) {
        policies.remove(policy);
    }

    public static PolicyTypeDetailsDTO from(PolicyTypeDetail policyTypeDetail) {
        return new PolicyTypeDetailsDTO(
                policyTypeDetail.getId(),
                policyTypeDetail.getType(),
                policyTypeDetail.getObjectType(),
                policyTypeDetail.getDescription()
        );
    }

    public static List<PolicyTypeDetailsDTO> from(List<PolicyTypeDetail> policyTypeDetails) {
        return policyTypeDetails.stream().map(PolicyTypeDetailsDTO::from).collect(Collectors.toList());
    }
}

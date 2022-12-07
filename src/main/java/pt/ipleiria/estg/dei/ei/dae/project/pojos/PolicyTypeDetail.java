package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class PolicyTypeDetail implements Serializable {
    int id;

    PolicyType type;

    PolicyObjectType objectType;

    String description;

    List<Policy> policies;


    public PolicyTypeDetail(int id, PolicyType type, PolicyObjectType objectType, String description) {
        this.id = id;
        this.type = type;
        this.objectType = objectType;
        this.description = description;
        policies = new LinkedList<>();
    }

    public PolicyTypeDetail() {
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

}

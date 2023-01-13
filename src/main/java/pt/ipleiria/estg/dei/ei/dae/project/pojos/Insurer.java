package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.InsurerExpert;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Insurer implements Serializable {
    int id;

    String name;

    List<Policy> policies;

    List<InsurerExpert> insurer_experts;

    List<RepairShop> repairShops;


    public Insurer(int id, String name) {
        this.id = id;
        this.name = name;
        policies = new LinkedList<>();
        insurer_experts = new LinkedList<>();
        repairShops = new LinkedList<>();
    }

    public Insurer() {
        policies = new LinkedList<>();
        insurer_experts = new LinkedList<>();
        repairShops = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    public List<InsurerExpert> getInsurer_experts() {
        return insurer_experts;
    }

    public void setInsurer_experts(List<InsurerExpert> insurer_experts) {
        this.insurer_experts = insurer_experts;
    }

    public List<RepairShop> getRepairShops() {
        return repairShops;
    }

    public void setRepairShops(List<RepairShop> repairShops) {
        this.repairShops = repairShops;
    }

    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    public void removePolicy(Policy policy) {
        this.policies.remove(policy);
    }

    public void addInsurerExpert(InsurerExpert insurerExpert) {
        this.insurer_experts.add(insurerExpert);
    }

    public void removeInsurerExpert(InsurerExpert insurerExpert) {
        this.insurer_experts.remove(insurerExpert);
    }

    public void addRepairShop(RepairShop repairShop) {
        this.repairShops.add(repairShop);
    }

    public void removeRepairShop(RepairShop repairShop) {
        this.repairShops.remove(repairShop);
    }

}

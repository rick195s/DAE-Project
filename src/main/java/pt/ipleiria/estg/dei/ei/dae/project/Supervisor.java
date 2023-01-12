package pt.ipleiria.estg.dei.ei.dae.project;

import com.github.javafaker.Faker;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyType;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.*;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.*;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class Supervisor {
    @EJB
    ClientBean clientBean;

    private final Faker faker = new Faker(new Locale("en"));

    private List<Policy> policies = new ArrayList<>();
    private List<RepairShop> repairShops = new ArrayList<>();
    private List<Insurer> insurers = new ArrayList<>();
    private List<PolicyTypeDetail> policyTypeDetails = new ArrayList<>();
    private List<PolicyObject> policyObjects = new ArrayList<>();

    public Supervisor() {
        populatePolicyTypeDetails();
        populatePolicyObejcts();
    }

    public List<Policy> getPolicies() {
        refreshPoliciesViaAPI();
        return policies;
    }

    private void refreshPoliciesViaAPI() {
        PolicyGateway gateway = new PolicyGateway();
        policies = gateway.getFromMockAPI();

        for (Policy policy : policies) {
            policy.setClient(clientBean.find(policy.getClientId()));
        }
    }

    public List<RepairShop> getRepairShops() {
        refreshRepairShopsViaAPI();
        return repairShops;
    }

    public List<RepairShop> getRepairShops(int insurerId) {
        List<RepairShop> repairShopsOfInsurer = new ArrayList<>();

        InsurerGateway insurerGateway = new InsurerGateway();
        List<Integer> repairShopsIds = new ArrayList<>(insurerGateway.getInsurersRepairShopsFromMockAPI(insurerId));

        for (RepairShop repairShop : getRepairShops()) {
            if (repairShopsIds.contains(repairShop.getId())) {
                repairShopsOfInsurer.add(repairShop);
            }
        }
        return repairShopsOfInsurer;
    }

    public List<PolicyObject> getPolicyObjects() {
       populatePolicyObejcts();
        return policyObjects;
    }

    public PolicyObject getPolicyObject(String name) {
        for(PolicyObject policyObject :policyObjects){
            if(policyObject.getName().equals(name)){
               return policyObject;
            }
        }
        return null;
    }

    public void addRepairShop(RepairShop repairShop) {
        RepairShopGateway repairShopGateway = new RepairShopGateway();
        repairShopGateway.postToMockAPI(repairShop);
        repairShops.add(repairShop);
    }
    public void addPolicyObject(PolicyObject policyObject) {
        PolicyObjectGateway policyObjectGateway = new PolicyObjectGateway();
        policyObjectGateway.postToMockAPI(policyObject);
        policyObjects.add(policyObject);
    }


    private void refreshRepairShopsViaAPI() {
        RepairShopGateway repairShopGateway = new RepairShopGateway();
        repairShops = repairShopGateway.getFromMockAPI();
    }

    public List<Insurer> getInsurers() {
        refreshInsurersViaAPI();
        return insurers;
    }

    private void populatePolicyTypeDetails() {

        policyTypeDetails.add(new PolicyTypeDetail(1, PolicyType.ACCIDENT, PolicyObjectType.VEHICLE,
                "Seguro que abrange o seu proprio carro e os danos causados aos outros carros"));

        policyTypeDetails.add(new PolicyTypeDetail(2, PolicyType.FULL, PolicyObjectType.HOUSE,
                "Seguro que abrange a sua casa em qualquer sobre qualquer dano ou roubo"));


        policyTypeDetails.add(new PolicyTypeDetail(3, PolicyType.BASIC, PolicyObjectType.HOUSE,
                "Seguro que abrange a sua casa em danos menores" +
                        ", vidros e portas"));


        policyTypeDetails.add(new PolicyTypeDetail(4, PolicyType.FULL, PolicyObjectType.PHONE,
                "Seguro que abrange os danos totais causados ao seu aparelho com opcao de reparacao ou substituicao"));


        policyTypeDetails.add(new PolicyTypeDetail(5, PolicyType.BASIC, PolicyObjectType.PHONE,
                "Seguro que abrange os danos parciais causados ao seu aparelho com opcao de reparacao"));

    }

    private void populatePolicyObejcts() {
        PolicyObjectGateway gateway = new PolicyObjectGateway();
        policyObjects = gateway.getFromMockAPI();
    }

    private void refreshInsurersViaAPI() {
        InsurerGateway gateway = new InsurerGateway();
        insurers = gateway.getFromMockAPI();
    }

}

package pt.ipleiria.estg.dei.ei.dae.project;

import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyType;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.*;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.*;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Supervisor {
    @EJB
    ClientBean clientBean;

    final String URI_INSURERS = "https://63af23e6649c73f572b64917.mockapi.io/insurers";

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
        PolicyTypeDetailsGateway gateway = new PolicyTypeDetailsGateway();
        policyTypeDetails = gateway.getFromMockAPI();
    }

    private void populatePolicyObejcts() {
        PolicyObjectGateway gateway = new PolicyObjectGateway();
        policyObjects = gateway.getFromMockAPI();
        //policyObjects.add(new PolicyObject(1, "Carro Ze Manel", "C:\\Users\\joaop\\Desktop\\carro.jpg"));
    }

    private void refreshInsurersViaAPI() {
        insurers = new ArrayList<>();
        JsonArray jsonArrayInsurers = APIGateway.getDataFromAPI(URI_INSURERS);
        jsonArrayInsurers.forEach(insurer -> {
            Jsonb jsonb = JsonbBuilder.create();
            Insurer insurerObj = jsonb.fromJson(insurer.toString(), Insurer.class);

            insurers.add(insurerObj);
        });
    }

    public List<PolicyTypeDetail> getPolicyTypeDetails() {
        populatePolicyTypeDetails();
        return policyTypeDetails;
    }


    public PolicyTypeDetail getPolicyTypeDetail(int id) {
        for(PolicyTypeDetail policyObject :policyTypeDetails){
            if(policyObject.getId()==id){
                return policyObject;
            }
        }
        return null;
    }



}

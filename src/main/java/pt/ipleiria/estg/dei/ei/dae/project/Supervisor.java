package pt.ipleiria.estg.dei.ei.dae.project;

import com.github.javafaker.Faker;
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
import java.util.Locale;

@ApplicationScoped
public class Supervisor {
    @EJB
    ClientBean clientBean;

    final String URI_INSURERS = "https://63af23e6649c73f572b64917.mockapi.io/insurers";

    private final Faker faker = new Faker(new Locale("en"));

    private List<Policy> policies = new ArrayList<>();
    private List<RepairShop> repairShops = new ArrayList<>();
    private List<Insurer> insurers = new ArrayList<>();
    private List<PolicyTypeDetail> policyTypeDetails = new ArrayList<>();
    private List<PolicyObject> policyObjects = new ArrayList<>();
    private List<Administrator> administrators = new ArrayList<>();

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

    private void populateRepairShopExpertsInAPI() {
        refreshRepairShopsViaAPI();
        RepairShopExpertGateway repairShopExpertGateway = new RepairShopExpertGateway();
        for (int i = 0; i < 20; i++) {
            RepairShopExpert repairShopExpert = new RepairShopExpert(faker.company().name(), faker.internet().emailAddress(), "123", "repairShopExpert", repairShops.get(1));
            repairShopExpertGateway.postToMockAPI(repairShopExpert);
        }
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



}

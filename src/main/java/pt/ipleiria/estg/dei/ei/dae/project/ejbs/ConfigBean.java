package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.PolicyGateway;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.RepairShopGateway;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.*;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyType;
import pt.ipleiria.estg.dei.ei.dae.project.gateways.APIGateway;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.text.SimpleDateFormat;
import java.util.*;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    ClientBean clientBean;

    @EJB
    RepairShopBean repairShopBean;

    @EJB
    PolicyObjectBean policyObjectBean;

    final String URI_REPAIR_SHOPS = "https://634f1183df22c2af7b4a4b38.mockapi.io/repair_shops";
    final String URI_INSURERS = "https://63af23e6649c73f572b64917.mockapi.io/insurers";


     private List<Policy> policies = new ArrayList<>();
     private List<Insurer> insurers = new ArrayList<>();
     private List<PolicyTypeDetail> policyTypeDetails = new ArrayList<>();
     private List<PolicyObject> policyObjects = new ArrayList<>();

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        clientBean.create(1, "João", "sdwqdwq@dwqdwq.cqwd", "dwqdwq", 213123);

        populatePolicyTypeDetails();
        populatePolicyObejcts();
        refreshInsurersViaAPI();
        refreshPoliciesViaAPI();

        //testar mockAPI
        populateMockAPI();

        //get all repair shops from mockAPI
        RepairShopGateway repairShopGateway = new RepairShopGateway();
        List<RepairShop> repairShops = repairShopGateway.getFromMockAPI();
        for (RepairShop repairShop : repairShops) {
            System.out.println(repairShop);
        }

    }

    public  List<Policy> getPolicies(){
        refreshPoliciesViaAPI();
        return policies;
    }

    public Policy getPolicy(int id){
        for (Policy policy : policies) {
            if(policy.getId() == id){
                return policy;
            }
        }
        return null;
    }

    public  void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    public  List<Insurer> getInsurers() {
        refreshInsurersViaAPI();
        return insurers;
    }

    public  void setInsurers(List<Insurer> insurers) {
        this.insurers = insurers;
    }

    private void populatePolicyTypeDetails(){

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
        policyObjects.add(new PolicyObject(1, "Carro Ze Manel", "C:\\Users\\joaop\\Desktop\\carro.jpg"));
    }

    private void refreshRepairShopsViaAPI() {
        JsonArray jsonArrayRepairShops = APIGateway.getDataFromAPI(URI_REPAIR_SHOPS);
        jsonArrayRepairShops.forEach(repairShop -> {
            Jsonb jsonb = JsonbBuilder.create();
            RepairShop repairShopObj = jsonb.fromJson(repairShop.toString(), RepairShop.class);
            repairShopBean.create(repairShopObj.getId(), repairShopObj.getName(), repairShopObj.getEmail(), repairShopObj.getPhone());
        });
    }

    private void refreshInsurersViaAPI(){
        insurers = new ArrayList<>();
        JsonArray jsonArrayInsurers = APIGateway.getDataFromAPI(URI_INSURERS);
        jsonArrayInsurers.forEach(insurer -> {
            Jsonb jsonb = JsonbBuilder.create();
            Insurer insurerObj = jsonb.fromJson(insurer.toString(), Insurer.class);

            insurers.add(insurerObj);
        });
    }

    private void refreshPoliciesViaAPI(){
        PolicyGateway gateway = new PolicyGateway();
        policies = gateway.getFromMockAPI();

        for (Policy policy : policies) {
            policy.setClient(clientBean.findClient(policy.getClientId()));
        }
    }

    private void populateMockAPI(){
        //populateRepairShopsInAPI();
        //populateInsurersInAPI();
        //populatePoliciesInAPI();
        //populateRepairShopsInAPI();
    }

    private void populateRepairShopsInAPI() {
        RepairShop repairShop1 = new RepairShop(1, "Oficina do Ze Manel", "zemanel99@gmail.com", 912345678);

        RepairShopGateway gateway = new RepairShopGateway();
        gateway.postToMockAPI(repairShop1);
    }

    private void populatePoliciesInAPI() {
        Client client = clientBean.findClient(1);


        Calendar calendar = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));

        calendar.set(2021, Calendar.JULY, 1);
        Calendar calendar2 = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));
        calendar2.set(2021, Calendar.DECEMBER, 2);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        policies.add(
                new Policy(
                        1,
                        client,
                        2,
                        PolicyState.APPROVED,
                        policyTypeDetails.get(0).getId(),
                        policyObjects.get(0).getId(),
                        formatter.format(calendar.getTime()),
                        formatter.format(calendar2.getTime())
                )
        );

        PolicyGateway gateway = new PolicyGateway();
        gateway.postToMockAPI(policies);
    }
}

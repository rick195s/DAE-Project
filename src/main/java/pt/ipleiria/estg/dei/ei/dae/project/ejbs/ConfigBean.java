package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
    final String URI_INSURERS = "https://634f1183df22c2af7b4a4b38.mockapi.io/insurers";


     private List<Policy> policies = new ArrayList<>();
     private List<Insurer> insurers = new ArrayList<>();
     private List<PolicyTypeDetail> policyTypeDetails = new ArrayList<>();
     private List<PolicyObject> policyObjects = new ArrayList<>();

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        populatePolicyTypeDetails();
        populatePolicyObejcts();
        populateInsurersViaAPI();

        clientBean.create(1, "João", "sdwqdwq@dwqdwq.cqwd", "dwqdwq", 213123);

        populatePolicies();

        // occurrenceBean.create(1, new Policy(), new RepairShop(), "example", 1);

    }

    public  List<Policy> getPolicies(){

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

    private void populateRepairShopsViaAPI() {
        JsonArray jsonArrayRepairShops = APIGateway.getDataFromAPI(URI_REPAIR_SHOPS);
        jsonArrayRepairShops.forEach(repairShop -> {
            Jsonb jsonb = JsonbBuilder.create();
            RepairShop repairShopObj = jsonb.fromJson(repairShop.toString(), RepairShop.class);
            repairShopBean.create(repairShopObj.getId(), repairShopObj.getName(), repairShopObj.getEmail(), repairShopObj.getPhone());
        });
    }

    private void populateInsurersViaAPI(){
        JsonArray jsonArrayInsurers = APIGateway.getDataFromAPI(URI_INSURERS);
        jsonArrayInsurers.forEach(insurer -> {
            Jsonb jsonb = JsonbBuilder.create();
            Insurer insurerObj = jsonb.fromJson(insurer.toString(), Insurer.class);

            insurers.add(insurerObj);
        });
    }

    private void populatePolicies(){

        Client client = clientBean.findClient(1);


        Calendar calendar = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));

        calendar.set(2021, Calendar.JULY, 1);
        Calendar calendar2 = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));
        calendar2.set(2021, Calendar.DECEMBER, 2);


        policies.add(
                new Policy(
                        1,
                        client,
                        insurers.get(0),
                        PolicyState.APPROVED,
                        policyTypeDetails.get(0),
                        policyObjects.get(0),
                        calendar,
                        calendar2
                )
        );
    }


}

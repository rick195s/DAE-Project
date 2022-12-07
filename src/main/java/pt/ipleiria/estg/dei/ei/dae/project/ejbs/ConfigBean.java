package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;
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
import java.util.Calendar;
import java.util.TimeZone;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    ClientBean clientBean;

    @EJB
    InsurerBean insurerBean;

    @EJB
    InsurerExpertBean insurerExpertBean;

    @EJB
    HistoricalBean historicalBean;

    @EJB
    RepairShopBean repairShopBean;

    @EJB
    PolicyBean policyBean;

    @EJB
    PolicyObjectBean policyObjectBean;

    @EJB
    PolicyTypeDetailBean policyTypeDetailBean;

    final String URI_INSURERS = "https://634f1183df22c2af7b4a4b38.mockapi.io/insurers";
    final String URI_REPAIR_SHOPS = "https://634f1183df22c2af7b4a4b38.mockapi.io/repair_shops";

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");
/*
        populateInsurersViaAPI();
        populateRepairShopsViaAPI();
        populatePolicyTypeDetails();
*/
        clientBean.create(1, "João", "sdwqdwq@dwqdwq.cqwd", "dwqdwq", 213123);



        // Funciona mas não é melhor opcao passar o objeto Insurer por parametro
       //  insurerExpertBean.create(5, "Jose", "jose@asdsda.com", "123123", insurerBean.findInsurer(10));
     //   insurerExpertBean.create(5, "Jose", "jose@asdsda.com", "123123", 10);

/*
        Calendar calendar = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));

        calendar.set(2021, Calendar.JULY, 1);


        Calendar calendar2 = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));
        calendar2.set(2021, Calendar.DECEMBER, 2);

        policyObjectBean.create(1, "Carro Ze Manel", "C:\\Users\\joaop\\Desktop\\carro.jpg");

        policyBean.create(1, 1,1, 1, 1, (Calendar) calendar.clone(), (Calendar) calendar2.clone());

 */
    }


    private void populatePolicyTypeDetails(){

        policyTypeDetailBean.create(1, PolicyType.ACCIDENT, PolicyObjectType.VEHICLE,
                "Seguro que abrange o seu proprio carro e os danos causados aos outros carros");

        policyTypeDetailBean.create(2, PolicyType.FULL, PolicyObjectType.HOUSE,
                "Seguro que abrange a sua casa em qualquer sobre qualquer dano ou roubo");


        policyTypeDetailBean.create(3, PolicyType.BASIC, PolicyObjectType.HOUSE,
                "Seguro que abrange a sua casa em danos menores" +
                        ", vidros e portas");


        policyTypeDetailBean.create(4, PolicyType.FULL, PolicyObjectType.PHONE,
                "Seguro que abrange os danos totais causados ao seu aparelho com opcao de reparacao ou substituicao");


        policyTypeDetailBean.create(5, PolicyType.BASIC, PolicyObjectType.PHONE,
                "Seguro que abrange os danos parciais causados ao seu aparelho com opcao de reparacao");

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
            insurerBean.create(insurerObj.getId(), insurerObj.getName());
        });
    }
}

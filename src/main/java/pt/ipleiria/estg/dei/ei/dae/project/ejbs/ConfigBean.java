package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShop;
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

    final String URI_INSURERS = "https://634f1183df22c2af7b4a4b38.mockapi.io/insurers";
    final String URI_REPAIR_SHOPS = "https://634f1183df22c2af7b4a4b38.mockapi.io/repair_shops";

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        clientBean.create(1, "João", "sdwqdwq@dwqdwq.cqwd", "dwqdwq", 213123);


        JsonArray jsonArrayInsurers = APIGateway.getDataFromAPI(URI_INSURERS);
        jsonArrayInsurers.forEach(insurer -> {
            Jsonb jsonb = JsonbBuilder.create();
            Insurer insurerObj = jsonb.fromJson(insurer.toString(), Insurer.class);
            insurerBean.create(insurerObj.getId(), insurerObj.getName());
        });

        JsonArray jsonArrayRepairShops = APIGateway.getDataFromAPI(URI_REPAIR_SHOPS);
        jsonArrayRepairShops.forEach(repairShop -> {
            Jsonb jsonb = JsonbBuilder.create();
            RepairShop repairShopObj = jsonb.fromJson(repairShop.toString(), RepairShop.class);
            repairShopBean.create(repairShopObj.getId(), repairShopObj.getName(), repairShopObj.getEmail(), repairShopObj.getPhone());
        });


        // Funciona mas não é melhor opcao passar o objeto Insurer por parametro
        // insurerExpertBean.create(5, "Jose", "jose@asdsda.com", "123123", insurerBean.findInsurer(10));
        insurerExpertBean.create(5, "Jose", "jose@asdsda.com", "123123", 10);



        Calendar calendar = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));

        calendar.set(2021, Calendar.JULY, 1);

        historicalBean.create(1, "teste", "Teste 123", (Calendar) calendar.clone());

        Calendar calendar2 = Calendar.getInstance(
                TimeZone.getTimeZone("UTC"));
        calendar2.set(2021, Calendar.DECEMBER, 2);
        //policyBean.create(1, 1,1, PolicyType.ACCIDENT, (Calendar) calendar.clone(), (Calendar) calendar2.clone());
    }

}

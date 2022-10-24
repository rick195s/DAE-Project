package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShop;
import pt.ipleiria.estg.dei.ei.dae.project.utils.APIConsumer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.Calendar;
import java.util.Date;


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

    //@EJB
    //RepairShopBean repairShopBean;

    String urlInsurers = "https://634f1183df22c2af7b4a4b38.mockapi.io/insurers";
    String urlRepairShops = "https://634f1183df22c2af7b4a4b38.mockapi.io/repair_shops";

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        clientBean.create(1, "João", "sdwqdwq@dwqdwq.cqwd", "dwqdwq", 213123);

        //Populate Insurers Table
        APIConsumer apiConsumerInsurers = new APIConsumer();
        JsonArray jsonArrayInsurers = apiConsumerInsurers.getDataFromAPI(urlInsurers);

        if (jsonArrayInsurers != null) {
            Jsonb jsonb = JsonbBuilder.create();

            for (JsonValue sticker : jsonArrayInsurers) {
                Insurer insurer = jsonb.fromJson(sticker.toString(), Insurer.class);
                //System.out.println(insurer.getName());
                insurerBean.create(insurer.getId(), insurer.getName());
            }
        }

        // Funciona mas não é melhor opcao passar o objeto Insurer por parametro
        // insurerExpertBean.create(5, "Jose", "jose@asdsda.com", "123123", insurerBean.findInsurer(10));
        insurerExpertBean.create(5, "Jose", "jose@asdsda.com", "123123", 10);

        historicalBean.create(1, "teste", "Teste 123", new Date(2021, Calendar.JULY, 1));

        //Populate RepairShops Table
        APIConsumer apiConsumerRepairShops = new APIConsumer();
        JsonArray jsonArrayRepairShops = apiConsumerRepairShops.getDataFromAPI(urlRepairShops);

        if (jsonArrayRepairShops != null) {
            Jsonb jsonb = JsonbBuilder.create();

            for (JsonValue sticker : jsonArrayRepairShops) {
                RepairShop repairShop = jsonb.fromJson(sticker.toString(), RepairShop.class);
                //System.out.println(repairShop.getName());
                //repairShopsBean.create(insurer.getId(), insurer.getName());
            }
        }
    }
}

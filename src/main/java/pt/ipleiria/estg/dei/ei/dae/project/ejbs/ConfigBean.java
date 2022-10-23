package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.utils.APIConsumer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;


@Startup
@Singleton
public class ConfigBean {
    @EJB
    ClientBean clientBean;

    @EJB
    InsurerBean insurerBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        clientBean.create(1, "João", "sdwqdwq@dwqdwq.cqwd", "dwqdwq", 213123);


        String url = "https://634f1183df22c2af7b4a4b38.mockapi.io/insurers";

        APIConsumer apiConsumer = new APIConsumer();

        JsonArray jsonArray = apiConsumer.getDataFromAPI(url);

        if (jsonArray != null){
            Jsonb jsonb = JsonbBuilder.create();


            for (JsonValue sticker : jsonArray) {
                Insurer insurer = jsonb.fromJson(sticker.toString(), Insurer.class);
                System.out.println(insurer.getName());
                insurerBean.create(insurer.getId(), insurer.getName());
            }


        }

    }
}

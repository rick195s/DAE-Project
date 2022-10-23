package pt.ipleiria.estg.dei.ei.dae.project.utils;

import pt.ipleiria.estg.dei.ei.dae.project.ejbs.InsurerBean;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.BufferedReader;

public class JsonParse {
    API api = null;
    int id;
    String name;

    public void parse(String urlAPI, InsurerBean insurerBean) {
        api = new API();
        BufferedReader reader = api.getDataFromAPI(urlAPI);
        JsonParser parser = Json.createParser(reader);

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            if (event == JsonParser.Event.KEY_NAME) {
                switch (parser.getString()) {
                    case "id":
                        parser.next();
                        this.id = Integer.parseInt(parser.getString());
                        break;
                    case "name":
                        parser.next();
                        this.name = parser.getString();
                        break;
                }
                System.out.println("ID: " + id + " Name: " + name);
                //insurerBean.create(id, name);
            }
        }
    }


}

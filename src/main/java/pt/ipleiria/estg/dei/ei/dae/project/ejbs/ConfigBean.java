package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.InsurerDTO;
import pt.ipleiria.estg.dei.ei.dae.project.enteties.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.utils.API;
import pt.ipleiria.estg.dei.ei.dae.project.utils.JsonParse;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    UserBean userBean;

    @EJB
    InsurerBean insurerBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        userBean.create(1, "João", "sdwqdwq@dwqdwq.cqwd", "dwqdwq");
        String url = "https://634f1183df22c2af7b4a4b38.mockapi.io/insurers";
        JsonParse parse = new JsonParse();
        parse.parse(url, insurerBean);


        /*API api = new API();
        BufferedReader reader = api.getDataFromAPI("https://634f1183df22c2af7b4a4b38.mockapi.io/insurers");
        JsonParser parser = Json.createParser(reader);

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            if (event == JsonParser.Event.KEY_NAME) {
                switch (parser.getString()) {
                    case "id":
                        parser.next();
                        //this.id = parser.getInt();
                        System.out.println(Integer.parseInt(parser.getString()));
                        break;
                    case "name":
                        parser.next();
                        //this.name = parser.getString();
                        System.out.println(parser.getString());
                        break;
                }
            }
        }*/


        /*try {
            URL url = new URL("https://634f1183df22c2af7b4a4b38.mockapi.io/insurers");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());


            BufferedReader responseReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JsonParser parser = Json.createParser(responseReader);

            while (parser.hasNext()) {
                JsonParser.Event event = parser.next();
                if (event == JsonParser.Event.KEY_NAME) {
                    switch (parser.getString()) {
                        case "id":
                            parser.next();
                            //this.id = parser.getInt();
                            System.out.println(Integer.parseInt(parser.getString()));
                            break;
                        case "name":
                            parser.next();
                            //this.name = parser.getString();
                            System.out.println(parser.getString());
                            break;
                    }
                }
            }
            responseReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/



       /* try {
            URL url = new URL("https://634f1183df22c2af7b4a4b38.mockapi.io/Students");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());

            BufferedReader responseReader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String responseLine;
            while ((responseLine = responseReader.readLine()) != null) {
                System.out.println(responseLine);

            }
            responseReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }
}

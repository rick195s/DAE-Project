package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    CourseBean courseBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        try {
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


    }
}

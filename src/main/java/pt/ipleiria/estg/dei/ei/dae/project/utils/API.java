package pt.ipleiria.estg.dei.ei.dae.project.utils;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    BufferedReader responseReader = null;

    public BufferedReader getDataFromAPI(String urlAPI) {

        try {
            URL url = new URL(urlAPI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());

            responseReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            return responseReader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseReader;
    }
}

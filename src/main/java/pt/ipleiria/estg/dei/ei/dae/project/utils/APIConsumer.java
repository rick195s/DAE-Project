package pt.ipleiria.estg.dei.ei.dae.project.utils;


import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class APIConsumer {

    public JsonArray getDataFromAPI(String urlAPI) {

        Client apiClient = ClientBuilder.newClient();
        Response response = null;
        JsonArray jsonArray = null;

        try {
            // don't mismatch this client with the insurer client
            WebTarget target = apiClient.target(urlAPI);
            response = target.request(MediaType.APPLICATION_JSON).get();
            jsonArray = response.readEntity(JsonArray.class);

        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            apiClient.close();

            if(response != null) {
                response.close();
            }
        }

        return jsonArray;
    }
}

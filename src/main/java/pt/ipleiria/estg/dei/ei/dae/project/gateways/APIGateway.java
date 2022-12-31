package pt.ipleiria.estg.dei.ei.dae.project.gateways;


import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class APIGateway {


    public static JsonArray getDataFromAPI(String urlAPI) {

        // don't mismatch this client with the insurer client
        Client apiClient = ClientBuilder.newClient();
        Response response = null;
        JsonArray jsonArray = null;

        try {
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

    public static Response postDataToAPI(String urlAPI, String jsonBody) {
        // don't mismatch this client with the insurer client
        Client apiClient = ClientBuilder.newClient();

        try {
            WebTarget target = apiClient.target(urlAPI);
            return target.request().post(Entity.entity(jsonBody, MediaType.APPLICATION_JSON));
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            apiClient.close();
        }

        return null;
    }
}

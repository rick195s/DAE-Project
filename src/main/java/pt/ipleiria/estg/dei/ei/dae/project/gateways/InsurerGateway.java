package pt.ipleiria.estg.dei.ei.dae.project.gateways;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.InsurerDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InsurerGateway {
    final String URI_INSURERS = "https://63af23e6649c73f572b64917.mockapi.io/insurers";
    final String URI_INSURERS_REPAIR_SHOPS_PIVOT = "https://63af23e6649c73f572b64917.mockapi.io/insurers_repair_shops";

    public void postToMockAPI(Insurer insurer) {
        InsurerDTO insurerDTO = InsurerDTO.from(insurer);

        Jsonb jsonb = JsonbBuilder.create();
        Response response = null;

        try{
            response = APIGateway.postDataToAPI(URI_INSURERS, jsonb.toJson(insurerDTO));
            jsonb.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (response != null) {
                response.close();
            }
        }
    }


    public List<Insurer> getFromMockAPI(){
        ArrayList<Insurer> insurers = new ArrayList<>();

        JsonArray jsonArrayPolicies = APIGateway.getDataFromAPI(URI_INSURERS);
        jsonArrayPolicies.forEach(insurer -> {
            Jsonb jsonb = JsonbBuilder.create();
            InsurerDTO insurerDTO = jsonb.fromJson(insurer.toString(), InsurerDTO.class);

            Insurer insurerObj = new Insurer(
                    insurerDTO.getId(),
                    insurerDTO.getName()
                );

            insurers.add(insurerObj);
        });

        return insurers;
    }

}

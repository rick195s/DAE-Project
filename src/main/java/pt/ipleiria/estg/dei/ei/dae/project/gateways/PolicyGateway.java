package pt.ipleiria.estg.dei.ei.dae.project.gateways;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.*;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;

public class PolicyGateway {
    final String URI_POLICIES = "https://63af23e6649c73f572b64917.mockapi.io/policies";

    public void postToMockAPI(Policy policy) {
        PolicyDTO policyDTO = PolicyDTO.from(policy);

        Jsonb jsonb = JsonbBuilder.create();
        Response response = null;

        try{
            response = APIGateway.postDataToAPI(URI_POLICIES, jsonb.toJson(policyDTO));
            jsonb.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (response != null) {
                response.close();
            }
        }
    }


    public List<Policy> getFromMockAPI(int insurerId){
        ArrayList<Policy> policies = new ArrayList<>();

        String uri = URI_POLICIES;
        if (insurerId != 0) {
            uri += "?insurerId=" + insurerId;
        }

        JsonArray jsonArrayPolicies = APIGateway.getDataFromAPI(uri);
        jsonArrayPolicies.forEach(policy -> {
            Jsonb jsonb = JsonbBuilder.create();
            PolicyDTO policyDTOObj = jsonb.fromJson(policy.toString(), PolicyDTO.class);

            Policy policyObj = new Policy(
                    policyDTOObj.getId(),
                    new Client(policyDTOObj.getClientNIFNIPC()),
                    policyDTOObj.getInsurerId(),
                    policyDTOObj.getState(),
                    policyDTOObj.getPolicyTypeDetailId(),
                    policyDTOObj.getPolicyObjectId(),
                    policyDTOObj.getStartDate(),
                    policyDTOObj.getEndDate()
                );

            policies.add(policyObj);
        });

        return policies;
    }
}

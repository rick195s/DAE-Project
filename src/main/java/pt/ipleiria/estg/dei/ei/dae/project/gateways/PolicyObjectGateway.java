package pt.ipleiria.estg.dei.ei.dae.project.gateways;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyObjectDTO;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class PolicyObjectGateway {
final String URI_POLICY_OBJECTS = "https://63af1ea3649c73f572b5d24b.mockapi.io/policy_objects/Policy_Objects";



public void postToMockAPI(PolicyObject policyObject) {
    PolicyObjectDTO policyObjectDTO = PolicyObjectDTO.from(policyObject);
    Jsonb jsonb = JsonbBuilder.create();
    Response response = null;
    try {
        response = APIGateway.postDataToAPI(URI_POLICY_OBJECTS, jsonb.toJson(policyObjectDTO));
        jsonb.close();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (response != null) {
            response.close();
        }
    }
}

    public List<PolicyObject> getFromMockAPI() {
        ArrayList<PolicyObject> policyObjects = new ArrayList<>();
        JsonArray jsonArrayPolicyObjects = APIGateway.getDataFromAPI(URI_POLICY_OBJECTS);
        if (jsonArrayPolicyObjects != null) {
            jsonArrayPolicyObjects.forEach(policyObject -> {
                Jsonb jsonb = JsonbBuilder.create();
                PolicyObjectDTO policyObjectDTOObj =jsonb.fromJson(policyObject.toString(), PolicyObjectDTO.class);
                PolicyObject policyObjectObj = toEntity(policyObjectDTOObj);
                policyObjects.add(policyObjectObj);
            });
        }
        return policyObjects;
    }

    public PolicyObject toEntity(PolicyObjectDTO policyObjectDTO) {
        return new PolicyObject(
                policyObjectDTO.getId(),
                policyObjectDTO.getName(),
                policyObjectDTO.getFilePath()
        );
    }
}

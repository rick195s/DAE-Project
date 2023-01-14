package pt.ipleiria.estg.dei.ei.dae.project.gateways;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyTypeDetailsDTO;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class PolicyTypeDetailsGateway {


    final String URI_POLICY_TYPE_DETAILS = "https://63af23b8649c73f572b643b1.mockapi.io/Policy_Type_Details";

    public void postToMockAPI(PolicyTypeDetail policyTypeDetail) {

        PolicyTypeDetailsDTO policyObjectDTO = toDTO(policyTypeDetail);
        Jsonb jsonb = JsonbBuilder.create();
        Response response = null;
        try {
            response = APIGateway.postDataToAPI(URI_POLICY_TYPE_DETAILS, jsonb.toJson(policyObjectDTO));
            jsonb.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public List<PolicyTypeDetail> getFromMockAPI() {
        ArrayList<PolicyTypeDetail> policyTypeDetails = new ArrayList<>();
        JsonArray jsonArrayPolicyObjects = APIGateway.getDataFromAPI(URI_POLICY_TYPE_DETAILS);
        if (jsonArrayPolicyObjects != null) {
            jsonArrayPolicyObjects.forEach(policyTypeDetail -> {
                Jsonb jsonb = JsonbBuilder.create();
                PolicyTypeDetailsDTO policyObjectDTOObj =jsonb.fromJson(policyTypeDetail.toString(), PolicyTypeDetailsDTO.class);
                PolicyTypeDetail policyObjectObj = toEntity(policyObjectDTOObj);
                policyTypeDetails.add(policyObjectObj);
            });
        }
        return policyTypeDetails;
    }

    public PolicyTypeDetail toEntity(PolicyTypeDetailsDTO policyTypeDetailsDTO) {
        return new PolicyTypeDetail(
                policyTypeDetailsDTO.getId(),
                policyTypeDetailsDTO.getType(),
                policyTypeDetailsDTO.getObjectType(),
                policyTypeDetailsDTO.getDescription()
        );
    }
    private PolicyTypeDetailsDTO toDTO(PolicyTypeDetail policyTypeDetail) {
        return new PolicyTypeDetailsDTO(
                policyTypeDetail.getId(),
                policyTypeDetail.getType(),
                policyTypeDetail.getObjectType(),
                policyTypeDetail.getDescription()
        );
    }
}

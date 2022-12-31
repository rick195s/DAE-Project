package pt.ipleiria.estg.dei.ei.dae.project.gateways;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import javax.ejb.EJB;

public class PolicyGateway {
    final String URI_POLICIES = "https://63af23e6649c73f572b64917.mockapi.io/policies";

    @EJB
    ClientBean clientBean;


    public void postToMockAPI( List<Policy> policies) {
        List<PolicyDTO> policyDTOS = toDTOs(policies);

        Jsonb jsonb = JsonbBuilder.create();
        Response response = null;

        try{
            for (PolicyDTO policyDTO:policyDTOS){
                response = APIGateway.postDataToAPI(URI_POLICIES, jsonb.toJson(policyDTO));
            }
            jsonb.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (response != null) {
                response.close();
            }
        }
    }


    public List<Policy> getFromMockAPI(){
        ArrayList<Policy> policies = new ArrayList<>();

        JsonArray jsonArrayPolicies = APIGateway.getDataFromAPI(URI_POLICIES);
        jsonArrayPolicies.forEach(policy -> {
            Jsonb jsonb = JsonbBuilder.create();
            PolicyDTO policyDTOObj = jsonb.fromJson(policy.toString(), PolicyDTO.class);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = dateFormat.parse(policyDTOObj.getStartDate());
                endDate = dateFormat.parse(policyDTOObj.getEndDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Calendar startDateCalendar = Calendar.getInstance();
            startDateCalendar.setTime(startDate);
            Calendar endDateCalendar = Calendar.getInstance();
            startDateCalendar.setTime(endDate);


            Policy policyObj = new Policy(
                    policyDTOObj.getId(),
                    new Client(),
                    policyDTOObj.getInsurerId(),
                    policyDTOObj.getState(),
                    policyDTOObj.getPolicyTypeDetailId(),
                    policyDTOObj.getPolicyObjectId(),
                    startDateCalendar,
                    endDateCalendar
                );

            policies.add(policyObj);
        });

        return policies;
    }

    // Converts an entity Student to a DTO Student class
    private PolicyDTO toDTO(Policy policy) {
        return new PolicyDTO(
                policy.getId(),
                policy.getClientId(),
                policy.getInsurerId(),
                policy.getPolicyTypeDetailId(),
                new LinkedList<>(),
                policy.getPolicyObjectId(),
                policy.getState(),
                policy.getStartDate(),
                policy.getEndDate()

        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<PolicyDTO> toDTOs(List<Policy> policies) {
        return policies.stream().map(this::toDTO).collect(Collectors.toList());
    }

}

package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyObjectDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PolicyObjectBean;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("policy_objects")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PolicyObjectService {
    @EJB
    private PolicyObjectBean policyObjectBean;

    @GET
    @Path("/")
    public List<PolicyObjectDTO> getAllPolicyObjectsWS() {
        return toDTOs(policyObjectBean.getAllPolicyObjects());
    }

    private PolicyObjectDTO toDTO(PolicyObject policyObject) {
        return new PolicyObjectDTO(
                policyObject.getId(),
                policyObject.getName(),
                policyObject.getFilePath()
        );
    }
    private List<PolicyObjectDTO> toDTOs(List<PolicyObject> policyObjects) {
        return policyObjects.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

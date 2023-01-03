package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyObjectDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PolicyObjectBean;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        return PolicyObjectDTO.from(policyObjectBean.getAllPolicyObjects());
    }

    @POST
    @Path("/")
    public Response createPolicyObject(PolicyObjectDTO policyObjectDTO){
        policyObjectBean.create(
            policyObjectDTO.getName(),
            policyObjectDTO.getFilePath()
        );
        PolicyObject newPolicyObject = policyObjectBean.findPolicyObject(policyObjectDTO.getName());
        if (newPolicyObject == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(PolicyObjectDTO.from(newPolicyObject)).build();
    }
}

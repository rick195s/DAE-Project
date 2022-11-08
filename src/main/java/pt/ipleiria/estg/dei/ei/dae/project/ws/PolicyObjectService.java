package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.InsurerDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyObjectDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PolicyObjectBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.entities.PolicyObject;

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


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all") // means: the relative url path is “/api/insurers/all”
    public List<PolicyObjectDTO>getAllPolicyObjects() {
        return toDTOs(policyObjectBean.getAllPolicyObjects());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("{id}") // means: the relative url path is “/api/insurers/all”
    public Response getPolicyObjectDetails(@PathParam("id") int id) {
        PolicyObject policyObject = policyObjectBean.findPolicyObject(id);
        if (policyObject != null) {
            return Response.ok(toDTO(policyObject)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewPolicyObject(PolicyObjectDTO policyObjectDTO) {
        policyObjectBean.create(
                policyObjectDTO.getId(),
                policyObjectDTO.getName(),
                policyObjectDTO.getFilePath()
        );
        PolicyObject newPolicyObject = policyObjectBean.findPolicyObject(policyObjectDTO.getId());
        if (newPolicyObject == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(newPolicyObject)).build();
    }
    @PUT
    @Path("{id}")
    public Response updatePolicyObject(@PathParam("id") int id, PolicyObjectDTO policyObjectDTO) {
        PolicyObject policyObject = policyObjectBean.findPolicyObject(id);
        if (policyObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        policyObjectBean.update(
                policyObjectDTO.getId(),
                policyObjectDTO.getName(),
                policyObjectDTO.getFilePath()
        );
        return Response.status(Response.Status.OK).entity(toDTO(policyObject)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePolicyObject(@PathParam("id") int id) {
        PolicyObject policyObject = policyObjectBean.findPolicyObject(id);
        if (policyObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        policyObjectBean.delete(id);
        return Response.status(Response.Status.OK).build();
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

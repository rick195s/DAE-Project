package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ErrorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.detailed.DetailedPolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("policies") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PolicyService {
    @EJB
    private PolicyBean policyBean;

    @EJB
    private ClientBean clientBean;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "CLIENT"})
    @Path("/")
    public Response getAllPoliciesWS() {
        List<Policy> policies = null;

        User user =  userBean.findUserByEmail(securityContext.getUserPrincipal().getName());

        if (user.getRole().equals(Role.ADMINISTRATOR)) {
            policies = policyBean.getAllPolicies(0);

        } else if (user.getRole().equals(Role.CLIENT)){
            Client client = clientBean.find(user.getId());
            policies = policyBean.getAllPolicies(client.getNIF_NIPC());
        }

        return policies == null
                ? Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorDTO("No Policies found."))
                .build()

                : Response.ok(PolicyDTO.from(policies)).build();

    }

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "CLIENT"})
    @Path("/{id}")
    public PolicyDTO getPolicyDetails(@PathParam("id") int id) {
        Policy policy = policyBean.find(id);
        PolicyTypeDetail policyTypeDetail = this.policyBean.getPolicyDetails(policy.getPolicyTypeDetailId());

        if (policyTypeDetail != null) {
            return DetailedPolicyDTO.from(policy, policyTypeDetail);
        }else {
            return PolicyDTO.from(policy);
        }
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ErrorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PaginatedDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.detailed.DetailedPolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;
import pt.ipleiria.estg.dei.ei.dae.project.requests.PageRequest;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.Valid;
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
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "CLIENT"})
    @Path("/")
    public Response getAllPoliciesWS(@BeanParam @Valid PageRequest pageRequest) {
        List<Policy> policies;

        Long count = policyBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTO<>(count)).build();
        }

        User user =  userBean.findUserByEmail(securityContext.getUserPrincipal().getName());
        policies = policyBean.getAll(user, pageRequest.getOffset(), pageRequest.getLimit());
        if (policies == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorDTO("No occurrences found."))
                    .build();
        }

        if (policies.isEmpty()) {
            count = 0L;
        }

        var paginatedDTO = new PaginatedDTO<>(PolicyDTO.from(policies), count, pageRequest.getOffset());

        return Response.ok(paginatedDTO).build();

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

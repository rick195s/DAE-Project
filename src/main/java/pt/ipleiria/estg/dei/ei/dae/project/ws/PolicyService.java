package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.detailed.DetailedPolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("policies") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PolicyService {
    @EJB
    private PolicyBean policyBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/all”
    public List<PolicyDTO> getAllPoliciesWS() {
        return PolicyDTO.from(policyBean.getAllPolicies());
    }

    @GET
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

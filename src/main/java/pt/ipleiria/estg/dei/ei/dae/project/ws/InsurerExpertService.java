package pt.ipleiria.estg.dei.ei.dae.project.ws;


import pt.ipleiria.estg.dei.ei.dae.project.dtos.InsurerExpertDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.InsurerExpertBean;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.InsurerExpert;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("insurer_experts") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class InsurerExpertService {
        @EJB
        private InsurerExpertBean insurerExpertBean;

        @GET // means: to call this endpoint, we need to use the HTTP GET method
        @Path("/") // means: the relative url path is “/api/insurerExperts/all”
        public List<InsurerExpertDTO> getAllInsurerExpertsWS() {
            return toDTOs(insurerExpertBean.getAllInsurerExpert());
        }

        @GET
        @Path("{id}")
        public Response getInsurerExpertDetails(@PathParam("id") int id) {
            InsurerExpert insurerExpert = insurerExpertBean.findInsurerExpert(id);
            if (insurerExpert != null) {
                return Response.ok(toDTO(insurerExpert)).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_STUDENT")
                    .build();
        }

        //TODO: change the password (Acho que é devido a isso o erro)
        @POST
        @Path("/")
        public Response createNewInsurerExpert(InsurerExpertDTO insurerExpertDTO) {
            insurerExpertBean.create(
                    insurerExpertDTO.getName(),
                    insurerExpertDTO.getEmail(),
                    "adsd",
                    insurerExpertDTO.getInsurerId()

            );
            InsurerExpert newInsurerExpert = insurerExpertBean.findInsurerExpert(insurerExpertDTO.getId());
            if (newInsurerExpert == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.CREATED).entity(toDTO(newInsurerExpert)).build();
        }

    //TODO: change the password (Acho que é devido a isso o erro)
    @PUT
    @Path("{id}")
    public Response updateInsurerExpert(@PathParam("id") int id, InsurerExpertDTO insurerExpertDTO) {
        InsurerExpert insurerExpert = insurerExpertBean.findInsurerExpert(id);
        if (insurerExpert == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        insurerExpertBean.update(
                insurerExpertDTO.getId(),
                insurerExpertDTO.getName(),
                insurerExpertDTO.getEmail(),
                "asd",
                insurerExpertDTO.getInsurerId()
        );
        return Response.ok(toDTO(insurerExpert)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteInsurerExpert(@PathParam("id") int id) {
        InsurerExpert insurerExpert = insurerExpertBean.findInsurerExpert(id);
        if (insurerExpert == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        insurerExpertBean.delete(id);
        return Response.ok().build();
    }

        // Converts an entity Student to a DTO Student class
        private InsurerExpertDTO toDTO(InsurerExpert insurerExpert) {
            return new InsurerExpertDTO(
                    insurerExpert.getId(),
                    insurerExpert.getName(),
                    insurerExpert.getEmail()
            );
        }

        // converts an entire list of entities into a list of DTOs
        private List<InsurerExpertDTO> toDTOs(List<InsurerExpert> insurerExperts) {
            return insurerExperts.stream().map(this::toDTO).collect(Collectors.toList());
        }
}

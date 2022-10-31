package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.InsurerDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.InsurerBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Insurer;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("insurers") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class InsurerService {
    @EJB
    private InsurerBean insurerBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all") // means: the relative url path is “/api/insurers/all”
    public List<InsurerDTO> getAllInsurersWS() {
        return toDTOs(insurerBean.getAllInsurer());
    }

    @GET
    @Path("{id}")
    public Response getInsurerDetails(@PathParam("id") int id) {
        Insurer insurer = insurerBean.findInsurer(id);
        if (insurer != null) {
            return Response.ok(toDTO(insurer)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewInsurer(InsurerDTO insurerDTO) {
        insurerBean.create(
                insurerDTO.getId(),
                insurerDTO.getName()
        );
        Insurer newInsurer = insurerBean.findInsurer(insurerDTO.getId());
        if (newInsurer == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(newInsurer)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateInsurer(@PathParam("id") int id, InsurerDTO insurerDTO) {
        insurerBean.update(
                id,
                insurerDTO.getName()
        );
        Insurer updatedInsurer = insurerBean.findInsurer(id);
        if (updatedInsurer == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(toDTO(updatedInsurer)).build();
    }

    // Converts an entity Student to a DTO Student class
    private InsurerDTO toDTO(Insurer insurer) {
        return new InsurerDTO(
               insurer.getId(),
               insurer.getName()
       );
    }

    // converts an entire list of entities into a list of DTOs
    private List<InsurerDTO> toDTOs(List<Insurer> insurers) {
        return insurers.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

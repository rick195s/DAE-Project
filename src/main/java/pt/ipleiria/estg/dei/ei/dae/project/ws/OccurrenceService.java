package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("occurrences") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class OccurrenceService {
    @EJB
    private OccurrenceBean occurrenceBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/all”
    public List<OccurrenceDTO> getAllOccurrencesWS() {
        return toDTOs(occurrenceBean.getAllOccurrences());
    }

    @POST
    @Path("/")
    public Response createOccurrence(OccurrenceDTO occurrenceDTO) {
        Occurrence occurrence = occurrenceBean.create(
                occurrenceDTO.getPolicyId(),
                occurrenceDTO.getRepairShopId(),
                occurrenceDTO.getDescription(),
                occurrenceDTO.getClientId(),
                null
        );

        return Response.status(Response.Status.CREATED).entity(toDTO(occurrence)).build();
    }

    // Converts an entity Student to a DTO Student class
    private OccurrenceDTO toDTO(Occurrence occurrence) {
        return new OccurrenceDTO(
                occurrence.getId(),
                occurrence.getDescription(),
                occurrence.getApprovalType(),
                occurrence.getStartDate(),
                occurrence.getEndDate(),
                occurrence.getPolicyId(),
                occurrence.getRepairShopId(),
                occurrence.getClient().getId()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<OccurrenceDTO> toDTOs(List<Occurrence> occurrences) {
        return occurrences.stream().map(this::toDTO).collect(Collectors.toList());
    }

}

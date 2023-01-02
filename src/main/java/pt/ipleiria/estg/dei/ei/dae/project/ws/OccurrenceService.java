package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.HistoricalDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceFileDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.detailed.DetailedOccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;


import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
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

    @GET
    @Path("/")
    public List<OccurrenceDTO> getAllOccurrencesWS() {
        return toDTOs(occurrenceBean.getAllOccurrences());
    }

    @GET
    @Path("/{id}")
    public Response getOccurrence(@PathParam("id") int id) {
        Occurrence occurrence = occurrenceBean.findOccurrence(id);
        if (occurrence != null) {
            return Response.ok(toDetailedDTO(occurrence)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_OCCURRENCE")
                .build();
    }

    @POST
    @Path("/")
    public Response createOccurrence(OccurrenceDTO occurrenceDTO) throws OccurrenceSmallDescriptionException, EntityNotFoundException {
        Occurrence occurrence = occurrenceBean.create(
                occurrenceDTO.getPolicyId(),
                occurrenceDTO.getRepairShopId(),
                occurrenceDTO.getDescription(),
                occurrenceDTO.getClientId()
        );

        return Response.status(Response.Status.CREATED).entity(toDTO(occurrence)).build();
    }

    private DetailedOccurrenceDTO toDetailedDTO(Occurrence occurrence) {
        DetailedOccurrenceDTO detailedOccurrenceDTO = new DetailedOccurrenceDTO(occurrence);

        occurrenceBean.getOccurrenceFiles(occurrence.getId()).forEach(occurrenceFile -> {
            detailedOccurrenceDTO.getFiles().add(new OccurrenceFileDTO(occurrenceFile));
        });

        occurrenceBean.getHistorical(occurrence.getId()).forEach(historical -> {
            detailedOccurrenceDTO.getHistoric().add(new HistoricalDTO(historical));
        });

        return detailedOccurrenceDTO;
    }

    private OccurrenceDTO toDTO(Occurrence occurrence) {
        return new OccurrenceDTO(occurrence);
    }

    // converts an entire list of entities into a list of DTOs
    private List<OccurrenceDTO> toDTOs(List<Occurrence> occurrences) {
        return occurrences.stream().map(this::toDTO).collect(Collectors.toList());
    }

}

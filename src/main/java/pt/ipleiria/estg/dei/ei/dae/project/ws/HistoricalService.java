package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.HistoricalDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.HistoricalBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("historical") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class HistoricalService {
    @EJB
    private HistoricalBean historicalBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/all") // means: the relative url path is “/api/insurerExperts/all”
    public List<HistoricalDTO> getAllHistoricalsWS() {
        return toDTOs(historicalBean.getAllHistorical());
    }

    @GET
    @Path("{id}")
    public Response getHistoricalDetails(@PathParam("id") int id) {
        Historical historical = historicalBean.findHistorical(id);
        if (historical != null) {
            return Response.ok(toDTO(historical)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateHistorical(@PathParam("id") int id, HistoricalDTO historicalDTO) {
        Historical historical = historicalBean.findHistorical(id);
        if (historical != null) {
            historicalBean.update(historicalDTO.getId(), historicalDTO.getState(), historicalDTO.getDescription(), historicalDTO.getDate());
            return Response.ok(toDTO(historical)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteHistorical(@PathParam("id") int id) {
        Historical historical = historicalBean.findHistorical(id);
        if (historical != null) {
            historicalBean.delete(historical);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    // Converts an entity Student to a DTO Student class
    private HistoricalDTO toDTO(Historical historical) {
        return new HistoricalDTO(
                historical.getId(),
                historical.getState(),
                historical.getDescription(),
                historical.getDate()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<HistoricalDTO> toDTOs(List<Historical> historicals) {
        return historicals.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

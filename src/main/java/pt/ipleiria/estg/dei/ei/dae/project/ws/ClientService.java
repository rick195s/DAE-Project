package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("clients") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ClientService {
    @EJB
    private ClientBean clientBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/all”
    public List<ClientDTO> getAllUsersWS() {
        return toDTOs(clientBean.getAllClients());
    }
    // Converts an entity Student to a DTO Student class
    private ClientDTO toDTO(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getRole(),
                client.getNIF_NIPC()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<ClientDTO> toDTOs(List<Client> clients) {
        return clients.stream().map(this::toDTO).collect(Collectors.toList());
    }

}

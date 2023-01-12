package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("clients") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed({"ADMINISTRATOR", "CLIENT"})
public class ClientService {
    @EJB
    private ClientBean clientBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/") // means: the relative url path is “/api/students/all”
    public List<ClientDTO> getAllUsersWS() {
        return ClientDTO.clientFrom(clientBean.getAllClients());
    }
}

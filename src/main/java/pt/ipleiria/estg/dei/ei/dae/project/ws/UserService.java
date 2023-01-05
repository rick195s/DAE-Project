package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("users") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class UserService {

    @EJB
    private UserBean userBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/all”
    public List<UserDTO> getAllUsersWS() {
        return toDTOs(userBean.getAllUsers());
    }

    @GET
    @Path("/administrators")
    public List<UserDTO> getAllAdministratorsWS() {
        return toDTOs(userBean.getAllAdministrators());
    }

    @GET
    @Path("/repairshopexperts")
    public List<UserDTO> getAllRepairShopExpertsWS() {
        return toDTOs(userBean.getAllRepairShopExperts());
    }


    // Converts an entity Student to a DTO Student class
    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<UserDTO> toDTOs(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

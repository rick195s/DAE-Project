package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserCreateDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("users") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
/*@Authenticated
@RolesAllowed({"ADMINISTRATOR"})*/
public class UserService {

    @EJB
    private UserBean userBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/all”
    public List<UserDTO> getAllUsersWS() {
        return toDTOs(userBean.getAllUsers());
    }

    @GET
    @Path("/{id}")
    public UserDTO getUserWS(@PathParam("id") int id) {
        return toDTO(userBean.find(id));
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

    @POST
    @Path("/")
    public Response createNewUserWS(UserCreateDTO userDTO) {
        userBean.create(
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole()
        );

        User user = userBean.findUserByEmail(userDTO.getEmail());

        return Response.status(Response.Status.CREATED).entity(userDTO.from(user)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUserWS(@PathParam("id") int id, UserDTO userDTO) {
        return userBean.update(id, userDTO);
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

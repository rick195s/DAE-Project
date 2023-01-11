package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserCreateDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.ejb.EJB;
import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        return UserDTO.from(userBean.getAllUsers());
    }

    @GET
    @Path("/{id}")
    public UserDTO getUserWS(@PathParam("id") int id) {
        return UserDTO.from(userBean.find(id));
    }

    @GET
    @Path("/administrators")
    public List<UserDTO> getAllAdministratorsWS() {
        return UserDTO.from(userBean.getAllAdministrators());
    }

    @GET
    @Path("/repairshopexperts")
    public List<UserDTO> getAllRepairShopExpertsWS() {
        return UserDTO.from(userBean.getAllRepairShopExperts());
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

        return Response.status(Response.Status.CREATED).entity(UserDTO.from(user)).build();
    }

    @POST
    @Path("/register")
    public Response registerUserWS(UserCreateDTO userDTO) {
        userBean.create(
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                Role.CLIENT.toString()
        );

        User user = userBean.findUserByEmail(userDTO.getEmail());

        return Response.status(Response.Status.CREATED).entity(UserDTO.from(user)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUserWS(@PathParam("id") int id, UserDTO userDTO) {
        userBean.update(id, userDTO);

        User user = userBean.find(id);
        return Response.status(Response.Status.OK).entity(UserDTO.from(user)).build();
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserCreateDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.annotation.security.RolesAllowed;
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
public class UserService {

    @EJB
    private UserBean userBean;

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/")
    public List<UserDTO> getAllUsersWS() {
        return UserDTO.from(userBean.getAllUsers());
    }

    @GET
    @Authenticated
    @Path("/{id}")
    public UserDTO getUserWS(@PathParam("id") int id) {
        return UserDTO.from(userBean.find(id));
    }

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/administrators")
    public List<UserDTO> getAllAdministratorsWS() {
        return UserDTO.from(userBean.getAllAdministrators());
    }

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/repairshopexperts")
    public List<UserDTO> getAllRepairShopExpertsWS() {
        return UserDTO.from(userBean.getAllRepairShopExperts());
    }

    @POST
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
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


    @PUT
    @Authenticated
    @Path("/{id}")
    public Response updateUserWS(@PathParam("id") int id, UserDTO userDTO) {
        userBean.update(id, userDTO);

        User user = userBean.find(id);
        return Response.status(Response.Status.OK).entity(UserDTO.from(user)).build();
    }
}

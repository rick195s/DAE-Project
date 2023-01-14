package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PaginatedDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserCreateDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.RepairShopExpertBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.requests.PageRequest;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class UserService {

    @EJB
    private UserBean userBean;

    @EJB
    private RepairShopExpertBean repairShopExpertBean;

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/")
    public Response getAllUsersWS(@BeanParam @Valid PageRequest pageRequest) {
        Long count = userBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTO<>(count)).build();
        }


        var paginatedDTO = new PaginatedDTO<>(UserDTO.from(userBean.getAllUsers()), count, pageRequest.getOffset());

        return Response.ok(paginatedDTO).build();

    }

    @GET
    @Authenticated
    @Path("/{id}")
    public Response getUserWS(@PathParam("id") int id) {
        return Response.ok(UserDTO.from(userBean.find(id))).build();
    }

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/administrators")
    public Response getAllAdministratorsWS() {
        return Response.ok(UserDTO.from(userBean.getAllAdministrators())).build();
    }

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/repairshopexperts")
    public Response getAllRepairShopExpertsWS() {
        return Response.ok(UserDTO.from(userBean.getAllRepairShopExperts())).build();
    }

    @POST
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/")
    public Response createNewUserWS(UserCreateDTO userDTO) {
        if (userDTO.getRole().equals("REPAIR_SHOP_EXPERT")) {
            repairShopExpertBean.create(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getRepairShopId());
        }else{
            userBean.create(
                    userDTO.getName(),
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    userDTO.getRole()
            );
        }

        User user = userBean.findUserByEmail(userDTO.getEmail());

        return Response.status(Response.Status.CREATED).entity(UserDTO.from(user)).build();
    }


    @PUT
    @Authenticated
    @Path("/{id}")
    public Response updateUserWS(@PathParam("id") int id, UserDTO userDTO) {
        userBean.update(id, userDTO.getName());

        User user = userBean.find(id);
        return Response.status(Response.Status.OK).entity(UserDTO.from(user)).build();
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.Auth;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UpdatePasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.ClientCreateDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.PasswordInvalidException;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.project.security.TokenIssuer;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;

    @EJB
    private UserBean userBean;

    @EJB
    private ClientBean clientBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/login")
    public Response authenticate(@Valid Auth auth) {
        if (userBean.canLogin(auth.getEmail(), auth.getPassword())) {
            String token = issuer.issue(auth.getEmail());
            return Response.ok(token).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/register")
    public Response registerUserWS(ClientCreateDTO clientDTO) {
        clientBean.create(clientDTO.getName(), clientDTO.getEmail(), clientDTO.getPassword(), clientDTO.getNIF_NIPC());

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        String userEmail = securityContext.getUserPrincipal().getName();
        User user = userBean.findUserByEmail(userEmail);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(UserDTO.from(user)).build();
    }

    @PUT
    @Authenticated
    @Path("/updatePassword")
    public Response updatePassword(@Valid UpdatePasswordDTO updatePasswordDTO) throws PasswordInvalidException {
        String userEmail = securityContext.getUserPrincipal().getName();
        userBean.updatePassword(userEmail, updatePasswordDTO);
        return Response.ok().build();
    }
}

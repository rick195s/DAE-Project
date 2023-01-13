package pt.ipleiria.estg.dei.ei.dae.project.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ErrorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.UserDontHavePolicyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserDontHavePolicyExceptionMapper implements ExceptionMapper<UserDontHavePolicyException> {

    @Override
    public Response toResponse(UserDontHavePolicyException e) {
        var error = new ErrorDTO(e.getMessage());

        return Response.status(Response.Status.FORBIDDEN).entity(error).build();
    }

}

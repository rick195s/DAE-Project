package pt.ipleiria.estg.dei.ei.dae.project.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ErrorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.PasswordInvalidException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class PasswordInvalidExceptionMapper implements ExceptionMapper<PasswordInvalidException> {
    protected static Response getResponse(PasswordInvalidException e) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorDTO(e.getMessage())).build();
    }

    @Override
    public Response toResponse(PasswordInvalidException e) {
        return null;
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.exceptions.mappers;


import pt.ipleiria.estg.dei.ei.dae.project.dtos.ErrorDTO;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException e) {
        return getResponse(e);
    }

    protected static Response getResponse(EntityNotFoundException e) {
        var cause = e.getMessage();
        var entity = cause.substring(cause.lastIndexOf('.') + 1, cause.indexOf(" with id"));

        var msg = entity + " not found: " + cause.substring(cause.lastIndexOf(' ') + 1);
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDTO(msg)).build();
    }
}

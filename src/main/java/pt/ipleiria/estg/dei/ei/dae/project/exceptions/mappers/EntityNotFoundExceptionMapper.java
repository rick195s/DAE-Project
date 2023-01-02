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
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDTO(e.getMessage())).build();
    }
}

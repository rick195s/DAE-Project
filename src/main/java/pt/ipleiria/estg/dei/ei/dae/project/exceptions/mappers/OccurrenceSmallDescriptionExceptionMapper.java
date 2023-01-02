package pt.ipleiria.estg.dei.ei.dae.project.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ErrorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ExceptionMapper;

@Provider
public class OccurrenceSmallDescriptionExceptionMapper implements ExceptionMapper<OccurrenceSmallDescriptionException> {

    @Override
    public Response toResponse(OccurrenceSmallDescriptionException e) {
        var error = new ErrorDTO(e.getMessage());

        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }

}

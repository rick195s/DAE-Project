package pt.ipleiria.estg.dei.ei.dae.project.ws;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.HistoricalDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceFileDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.detailed.DetailedOccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.OccurrenceFileBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.utils.FileUtils;


import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Path("occurrences") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class OccurrenceService {
    @EJB
    private OccurrenceBean occurrenceBean;

    @EJB
    private OccurrenceFileBean occurrenceFileBean;

    @GET
    @Path("/")
    public List<OccurrenceDTO> getAllOccurrencesWS() {
        return OccurrenceDTO.from(occurrenceBean.getAllOccurrences());
    }

    @GET
    @Path("/{id}")
    public Response getOccurrence(@PathParam("id") int id) {
        Occurrence occurrence = occurrenceBean.findOccurrence(id);
        if (occurrence != null) {
            return Response.ok(toDetailedDTO(occurrence)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_OCCURRENCE")
                .build();
    }

    @POST
    @Path("/")
    public Response createOccurrence(OccurrenceDTO occurrenceDTO) throws OccurrenceSmallDescriptionException, EntityNotFoundException {
        Occurrence occurrence = occurrenceBean.create(
                occurrenceDTO.getPolicyId(),
                occurrenceDTO.getRepairShopId(),
                occurrenceDTO.getDescription(),
                occurrenceDTO.getClientId()
        );

        return Response.status(Response.Status.CREATED).entity(OccurrenceDTO.from(occurrence)).build();
    }

    @POST
    @Path("{id}/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@PathParam("id") int id, MultipartFormDataInput input) throws IOException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

        List<InputPart> inputParts = uploadForm.get("file");

        var occurrenceFiles = new LinkedList<OccurrenceFile>();

        FileUtils fileUtils = new FileUtils();
        String homedir = System.getProperty("user.home");
        String dirpath = homedir + File.separator + "uploads" + File.separator + "occurrences";

        for (InputPart inputPart : inputParts) {
            String filepath = fileUtils.upload(dirpath, inputPart);

            String filename = fileUtils.getFilename(inputPart.getHeaders());

            var occurrenceFile = occurrenceFileBean.create(id, filename, filepath);
            occurrenceFiles.add(occurrenceFile);
        }

        return Response.ok(OccurrenceFileDTO.from(occurrenceFiles)).build();
    }

    private DetailedOccurrenceDTO toDetailedDTO(Occurrence occurrence) {
        DetailedOccurrenceDTO detailedOccurrenceDTO = DetailedOccurrenceDTO.from(occurrence);

        occurrenceBean.getOccurrenceFiles(occurrence.getId()).forEach(occurrenceFile -> {
            detailedOccurrenceDTO.getFiles().add(OccurrenceFileDTO.from(occurrenceFile));
        });

        occurrenceBean.getHistorical(occurrence.getId()).forEach(historical -> {
            detailedOccurrenceDTO.getHistoric().add(HistoricalDTO.from(historical));
        });

        return detailedOccurrenceDTO;
    }

}

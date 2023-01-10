package pt.ipleiria.estg.dei.ei.dae.project.ws;

import org.apache.commons.io.FilenameUtils;
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
    public Response getOccurrencesWS() {
        int clientId = 0;
        List<Occurrence> occurrences;

        if (clientId == 1) {
            occurrences = occurrenceBean.getOccurrencesOfClient(clientId);
            if (occurrences != null) {
                return Response.ok(OccurrenceDTO.from(occurrences)).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_OCCURRENCE")
                    .build();
        }

        occurrences = occurrenceBean.getAllOccurrences();
        if (occurrences != null) {
            return Response.ok(OccurrenceDTO.from(occurrences)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_OCCURRENCE")
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getOccurrence(@PathParam("id") int id) {
        Occurrence occurrence = occurrenceBean.find(id);
        if (occurrence != null) {
            return Response.ok(toDetailedDTO(occurrence)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_OCCURRENCE")
                .build();
    }

    @GET
    @Path("{id}/files")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOccurrenceFiles(@PathParam("id") int id) {
        var documents = occurrenceBean.getOccurrenceFiles(id);
        return Response.ok(OccurrenceFileDTO.from(documents)).build();
    }

    @GET
    @Path("{occurrenceId}/files/download/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("occurrenceId") int occurrenceId, @PathParam("fileId") int fileId) {
        OccurrenceFile occurrenceFile = occurrenceFileBean.find(fileId);
        var response = Response.ok(new File(occurrenceFile.getPath()));

        response.header("Content-Disposition", "attachment;filename=" + occurrenceFile.getName());

        return response.build();
    }


    @POST
    @Path("/")
    public Response createOccurrence(OccurrenceDTO occurrenceDTO) throws OccurrenceSmallDescriptionException, EntityNotFoundException {
        Occurrence occurrence = occurrenceBean.create(
                occurrenceDTO.getPolicyId(),
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

        for (InputPart inputPart : inputParts) {

            String filename = fileUtils.getFilename(inputPart.getHeaders());
            String ext = FilenameUtils.getExtension(filename);
            filename = FilenameUtils.removeExtension(filename) + "_" + System.currentTimeMillis() + "." + ext;

            String filepath = fileUtils.upload("occurrences" + File.separator + id, filename, inputPart);

            var occurrenceFile = occurrenceFileBean.create(id, filename, filepath);
            occurrenceFiles.add(occurrenceFile);
        }

        return Response.ok(OccurrenceFileDTO.from(occurrenceFiles)).build();
    }

    @PATCH
    @Path("/{id}/approved")
    public Response approveOccurrence(@PathParam("id") int id) {
        Occurrence occurrence = occurrenceBean.find(id);
        if (occurrence != null) {
            occurrenceBean.approveOccurrence(occurrence);
            return Response.ok(toDetailedDTO(occurrence)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_OCCURRENCE")
                .build();
    }

    @PATCH
    @Path("/{id}/declined")
    public Response declineOccurrence(@PathParam("id") int id) {
        Occurrence occurrence = occurrenceBean.find(id);
        if (occurrence != null) {
            occurrenceBean.declineOccurrence(occurrence);
            return Response.ok(toDetailedDTO(occurrence)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_OCCURRENCE")
                .build();
    }


    @PATCH
    @Path("/{id}/repair-shop/{repairShopId}")
    public Response declineOccurrence(@PathParam("id") int id, @PathParam("repairShopId") int repairShopId) {
        occurrenceBean.setOccurrenceRepairShop(id, repairShopId);
        return Response.ok(toDetailedDTO(occurrenceBean.find(id))).build();
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

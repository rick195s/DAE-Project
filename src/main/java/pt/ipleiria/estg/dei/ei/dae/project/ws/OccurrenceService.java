package pt.ipleiria.estg.dei.ei.dae.project.ws;

import jakarta.persistence.EntityNotFoundException;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.detailed.DetailedOccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.project.entities.*;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.OccurrenceSmallDescriptionException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.UserDontHavePolicyException;
import pt.ipleiria.estg.dei.ei.dae.project.requests.PageRequest;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;


import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.*;
import java.util.List;

@Path("occurrences") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class OccurrenceService {
    @EJB
    private OccurrenceBean occurrenceBean;

    @EJB
    private OccurrenceFileBean occurrenceFileBean;

    @EJB
    private UserBean userBean;

    @EJB
    private RepairShopExpertBean repairShopExpertBean;

    @EJB
    private InsurerExpertBean insurerExpertBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Authenticated
    @Path("/")
    public Response getOccurrencesWS(@BeanParam @Valid PageRequest pageRequest) {
        List<Occurrence> occurrences;

        Long count = occurrenceBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTO<>(count)).build();
        }

        User user =  userBean.findUserByEmail(securityContext.getUserPrincipal().getName());

        occurrences = occurrenceBean.getAllOccurrences(user, pageRequest.getOffset(), pageRequest.getLimit());
        if (occurrences == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorDTO("No occurrences found."))
                    .build();
        }

        if (occurrences.isEmpty()) {
            count = 0L;
        }

        var paginatedDTO = new PaginatedDTO<>(OccurrenceDTO.from(occurrences), count, pageRequest.getOffset());

        return Response.ok(paginatedDTO).build();
    }

    @GET
    @Authenticated
    @Path("/{id}")
    public Response getOccurrence(@PathParam("id") int id) {

        if (!canUserSeeOccurrence(id)){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorDTO("You dont have permission to see this occurrence"))
                    .build();
        }

        Occurrence occurrence = occurrenceBean.find(id);
        if (occurrence != null) {
            return Response.ok(toDetailedDTO(occurrence)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorDTO("Occurrence not found"))
                .build();
    }

    @GET
    @Authenticated
    @Path("{id}/files")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOccurrenceFiles(@PathParam("id") int id) {
        var documents = occurrenceBean.getOccurrenceFiles(id);
        return Response.ok(OccurrenceFileDTO.from(documents)).build();
    }

    @GET
    @Authenticated
    @Path("{occurrenceId}/files/download/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("occurrenceId") int occurrenceId, @PathParam("fileId") int fileId) {
        OccurrenceFile occurrenceFile = occurrenceFileBean.find(fileId);
        var response = Response.ok(new File(occurrenceFile.getPath()));

        response.header("Content-Disposition", "attachment;filename=" + occurrenceFile.getName());

        return response.build();
    }


    @POST
    @Authenticated
    @RolesAllowed({"CLIENT"})
    @Path("/")
    public Response createOccurrence(OccurrenceDTO occurrenceDTO) throws OccurrenceSmallDescriptionException, UserDontHavePolicyException {
        Occurrence occurrence = occurrenceBean.create(
                occurrenceDTO.getPolicyId(),
                occurrenceDTO.getDescription(),
                occurrenceDTO.getClientId()
        );

        return Response.status(Response.Status.CREATED).entity(OccurrenceDTO.from(occurrence)).build();
    }

   //Create a occurrence with a file.csv
    @POST
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "CLIENT"})
    @Path("/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createOccurrenceWithCSV(MultipartFormDataInput input) throws IOException, OccurrenceSmallDescriptionException, UserDontHavePolicyException {
        occurrenceBean.createOccurrenceWithCSV(input);
        return Response.status(Response.Status.CREATED).build();
    }


    @POST
    @Authenticated
    @Path("{id}/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@PathParam("id") int id, MultipartFormDataInput input) throws IOException {
        List<OccurrenceFile> occurrenceFiles = occurrenceBean.uploadFiles(id, input);
        return Response.ok(OccurrenceFileDTO.from(occurrenceFiles)).build();
    }

    @PATCH
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "INSURER_EXPERT"})
    @Path("/{id}/approved")
    public Response approveOccurrence(@PathParam("id") int id) {
        occurrenceBean.approveOccurrence(id);
        return Response.ok(toDetailedDTO(occurrenceBean.find(id))).build();
    }

    @PATCH
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "INSURER_EXPERT"})
    @Path("/{id}/declined")
    public Response declineOccurrence(@PathParam("id") int id) {
        occurrenceBean.declineOccurrence(id);
        return Response.ok(toDetailedDTO(occurrenceBean.find(id))).build();
    }

    @PATCH
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "REPAIR_SHOP_EXPERT"})
    @Path("/{id}/concluded")
    public Response concludeOccurrence(@PathParam("id") int id) {
        System.out.println("start conlude");
        occurrenceBean.concludeOccurrence(id);
        return Response.ok(toDetailedDTO(occurrenceBean.find(id))).build();
    }


    @PATCH
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "CLIENT"})
    @Path("/{id}/repair-shop/{repairShopId}")
    public Response setOccurrenceRepairShop(@PathParam("id") int id, @PathParam("repairShopId") int repairShopId) throws MessagingException {
        occurrenceBean.setOccurrenceRepairShop(id, repairShopId);
        return Response.ok(toDetailedDTO(occurrenceBean.find(id))).build();
    }

    @POST
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR", "CLIENT"})
    @Path("/{id}/repair-shop")
    public Response setCustomOccurrenceRepairShop(@PathParam("id") int id, RepairShopDTO repairShopDTO) {
        occurrenceBean.setCustomOccurrenceRepairShop(id, repairShopDTO.getName(), repairShopDTO.getEmail(), repairShopDTO.getPhone());
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


    private boolean canUserSeeOccurrence(int occurrenceId) {
        Occurrence occurrence = occurrenceBean.find(occurrenceId);
        if (occurrence == null) {
            throw new EntityNotFoundException("Occurrence dont exists");
        }

        User user =  userBean.findUserByEmail(securityContext.getUserPrincipal().getName());

        switch (user.getRole()) {
            case CLIENT:
                return occurrence.getClient().getId() == user.getId();

            case REPAIR_SHOP_EXPERT:
                RepairShopExpert repairShopExpert = repairShopExpertBean.find(user.getId());
                return occurrence.getRepairShopId() == repairShopExpert.getRepairShopId();

            case INSURER_EXPERT:
                InsurerExpert insurerExpert = insurerExpertBean.find(user.getId());
                return occurrence.getInsurerId() == insurerExpert.getInsurerId();


            case ADMINISTRATOR:
                return true;

            default:
                return false;
        }
    }


}

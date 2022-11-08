package pt.ipleiria.estg.dei.ei.dae.project.ws;


import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyObjectDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.RepairShopBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.PolicyObject;
import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShop;
import pt.ipleiria.estg.dei.ei.dae.project.entities.RepairShopExpert;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("repair_shops")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class RepairShopService {
    @EJB
    private RepairShopBean repairShopBean;

    @GET
    @Path("/all")
    public List<RepairShopDTO> getAllRepairShops() {
        return toDTOs(repairShopBean.getAllRepairShops());
    }
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("{id}") // means: the relative url path is “/api/insurers/all”
    public Response getPolicyObjectDetails(@PathParam("id") int id) {
        RepairShop repairShop = repairShopBean.findRepairShop(id);
        if (repairShop != null) {
            return Response.ok(toDTO(repairShop)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewPolicyObject(RepairShopDTO repairShopDTO) {
        repairShopBean.create(
                repairShopDTO.getId(),
                repairShopDTO.getName(),
                repairShopDTO.getEmail(),
                repairShopDTO.getPhone()
        );
        RepairShop newRepairShop = repairShopBean.findRepairShop(repairShopDTO.getId());
        if (newRepairShop == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(newRepairShop)).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePolicyObject(@PathParam("id") int id, RepairShopDTO repairShopDTO) {
        RepairShop repairShop = repairShopBean.findRepairShop(id);
        if (repairShop == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        repairShopBean.update(
                repairShopDTO.getId(),
                repairShopDTO.getName(),
                repairShopDTO.getEmail(),
                repairShopDTO.getPhone()
        );
        return Response.status(Response.Status.OK).entity(toDTO(repairShop)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePolicyObject(@PathParam("id") int id) {
        RepairShop repairShop = repairShopBean.findRepairShop(id);
        if (repairShop == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        repairShopBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }


    private RepairShopDTO toDTO(RepairShop repairShop) {
        return new RepairShopDTO(
                repairShop.getId(),
                repairShop.getName(),
                repairShop.getEmail(),
                repairShop.getPhone()
        );
    }

    private List<RepairShopDTO> toDTOs(List<RepairShop> reairShops) {
        return reairShops.stream().map(this::toDTO).collect(Collectors.toList());
    }

}

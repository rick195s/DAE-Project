package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.InsurerBean;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("insurers") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class InsurerService {
    @EJB
    private InsurerBean insurerBean;

    @GET
    @Authenticated
    @Path("{id}/repair-shops")
    public Response getRepairShops(@PathParam("id") int id) {
        List<RepairShopDTO> repairShops = RepairShopDTO.from(insurerBean.getRepairShops(id));

        if (repairShops != null) {
            return Response.ok(repairShops).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_REPAIR_SHOPS")
                .build();
    }
}

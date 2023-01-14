package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.RepairShopBean;
import pt.ipleiria.estg.dei.ei.dae.project.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("repairshops") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class RepairShopService {
    @EJB
    private RepairShopBean repairShopBean;

    @GET
    @Authenticated
    @RolesAllowed({"ADMINISTRATOR"})
    @Path("/")
    public List<RepairShopDTO> getAllRepairShopsWS() {
        return RepairShopDTO.from(repairShopBean.getAllRepairShops());
    }

}

package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.RepairShopBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("repairshops") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class RepairShopService {
    @EJB
    private RepairShopBean repairShopBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/repairshops”
    public List<RepairShopDTO> getAllRepairShopsWS() {
        return toDTOs(repairShopBean.getAllRepairShops());
    }

    private RepairShopDTO toDTO(RepairShop repairShop) {
        return new RepairShopDTO(
                repairShop.getId(),
                repairShop.getName(),
                repairShop.getEmail(),
                repairShop.getPhone()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<RepairShopDTO> toDTOs(List<RepairShop> repairShops) {
        return repairShops.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

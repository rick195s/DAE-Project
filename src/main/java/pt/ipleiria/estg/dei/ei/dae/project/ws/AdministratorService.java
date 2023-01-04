package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.AdministratorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.AdministratorBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.RepairShopBean;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Administrator;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("administrators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministratorService {

    @EJB
    private AdministratorBean administratorBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/repairshops”
    public List<AdministratorDTO> getAllAdministratorBeanWS() {
        return toDTOs(administratorBean.getAllAdministrators());
    }

    private AdministratorDTO toDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getId(),
                administrator.getName(),
                administrator.getEmail(),
                administrator.getRole()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<AdministratorDTO> toDTOs(List<Administrator> administrators) {
        return administrators.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

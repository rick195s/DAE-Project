package pt.ipleiria.estg.dei.ei.dae.project.gateways;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopDTO;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RepairShopGateway {
    final String URI_REPAIR_SHOPS = "https://63af1f07cb0f90e5146dbd21.mockapi.io/api/insurers/Repair_Shops";

    public RepairShop postToMockAPI(RepairShop repairShop) {
        RepairShopDTO repairShopDTO = RepairShopDTO.from(repairShop);
        RepairShop repairShopCreated = null;

        Jsonb jsonb = JsonbBuilder.create();
        Response response = null;


        try {
            response = APIGateway.postDataToAPI(URI_REPAIR_SHOPS, jsonb.toJson(repairShopDTO));
            if (response != null) {
                repairShopCreated = jsonb.fromJson(response.readEntity(String.class), RepairShop.class);
            }
            jsonb.close();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (response != null) {
                response.close();
            }
        }

        return repairShopCreated;
    }

    public List<RepairShop> getFromMockAPI() {
        ArrayList<RepairShop> repairShops = new ArrayList<>();

        JsonArray jsonArrayRepairShops = APIGateway.getDataFromAPI(URI_REPAIR_SHOPS);
        if (jsonArrayRepairShops != null) {
            jsonArrayRepairShops.forEach(repairShop -> {
                Jsonb jsonb = JsonbBuilder.create();
                RepairShopDTO repairShopDTOObj = jsonb.fromJson(repairShop.toString(), RepairShopDTO.class);

                RepairShop repairShopObj = toEntity(repairShopDTOObj);

                repairShops.add(repairShopObj);
            });
        }

        return repairShops;
    }

    //convert one DTO into an entity
    public RepairShop toEntity(RepairShopDTO repairShopDTO) {
        return new RepairShop(
                repairShopDTO.getId(),
                repairShopDTO.getName(),
                repairShopDTO.getEmail(),
                repairShopDTO.getPhone()
        );
    }
}

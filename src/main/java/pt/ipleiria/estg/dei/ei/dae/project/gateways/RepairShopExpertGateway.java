package pt.ipleiria.estg.dei.ei.dae.project.gateways;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.RepairShopExpertDTO;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShopExpert;

import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RepairShopExpertGateway {

    final String URI_REPAIR_SHOP_EXPERTS = "https://63af1f07cb0f90e5146dbd21.mockapi.io/api/insurers/Repair_Shops_Experts";

    public void postToMockAPI(RepairShopExpert repairShopExpert) {
        RepairShopExpertDTO repairShopExpertDTO = toDTO(repairShopExpert);

        Jsonb jsonb = JsonbBuilder.create();
        Response response = null;

        try {
            response = APIGateway.postDataToAPI(URI_REPAIR_SHOP_EXPERTS, jsonb.toJson(repairShopExpertDTO));
            jsonb.close();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public List<RepairShopExpert> getFromMockAPI() {
        ArrayList<RepairShopExpert> repairShopExperts = new ArrayList<>();

        JsonArray jsonArrayRepairShopExperts = APIGateway.getDataFromAPI(URI_REPAIR_SHOP_EXPERTS);
        if (jsonArrayRepairShopExperts != null) {
            jsonArrayRepairShopExperts.forEach(repairShopExpert -> {
                Jsonb jsonb = JsonbBuilder.create();
                RepairShopExpertDTO repairShopExpertDTOObj = jsonb.fromJson(repairShopExpert.toString(), RepairShopExpertDTO.class);

                RepairShopExpert repairShopExpertObj = toEntity(repairShopExpertDTOObj);

                repairShopExperts.add(repairShopExpertObj);
            });
        }

        return repairShopExperts;
    }

    //convert one entity into a DTO
    private RepairShopExpertDTO toDTO(RepairShopExpert repairShopExpert) {
        return new RepairShopExpertDTO(
                repairShopExpert.getId(),
                repairShopExpert.getName(),
                repairShopExpert.getEmail(),
                repairShopExpert.getRole(),
                repairShopExpert.getRepairShop()
        );
    }

    //convert one DTO into an entity
    public RepairShopExpert toEntity(RepairShopExpertDTO repairShopExpertDTO) {
        return new RepairShopExpert(
                repairShopExpertDTO.getName(),
                repairShopExpertDTO.getEmail(),
                "123", //TODO: grab password
                repairShopExpertDTO.getRole(),
                repairShopExpertDTO.getRepairShop()
        );
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import java.io.Serializable;

public class RepairShopExpertDTO extends UserDTO implements Serializable {
    RepairShop repairShop;

    public RepairShopExpertDTO(int id, String name, String email, String role, RepairShop repairShop) {
        super(id, name, email, role);
        this.repairShop = repairShop;

    }

    public RepairShopExpertDTO() {
    }

    public RepairShop getRepairShop() {
        return repairShop;
    }

    public void setRepairShop(RepairShop repairShop) {
        this.repairShop = repairShop;
    }
}


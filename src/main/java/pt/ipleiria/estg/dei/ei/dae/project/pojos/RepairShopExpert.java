package pt.ipleiria.estg.dei.ei.dae.project.pojos;


import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RepairShopExpert extends User implements Serializable {

    RepairShop repairShop;

    public RepairShopExpert(int id, String name, String email, String password, String role, RepairShop repairShop) {
        super(name, email, password, role);
        this.repairShop = repairShop;
    }

    public RepairShopExpert() {
    }

    public RepairShop getRepairShop() {
        return repairShop;
    }

    public void setRepairShop(RepairShop repairShop) {
        this.repairShop = repairShop;
    }
}

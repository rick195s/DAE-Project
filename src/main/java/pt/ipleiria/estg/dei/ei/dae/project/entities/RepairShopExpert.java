package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllRepairShopExperts",
                query = "SELECT c FROM RepairShopExpert c ORDER BY c.name" // JPQL
        ),
        @NamedQuery(
                name = "getAllRepairShopExpertsByRepairShop",
                query = "SELECT c FROM RepairShopExpert c WHERE c.repairShopId IN :repairShopId ORDER BY c.name" // JPQL
        ),
})
/*@Table(
        name = "repair_shop_experts"
)
@PrimaryKeyJoinColumn(name = "user_id")*/
public class RepairShopExpert extends User {
    @Column(name = "repair_shop_id")
    private int repairShopId;

    public RepairShopExpert(String name, String email, String password, int repairShopId) {
        super(name, email, password, Role.REPAIR_SHOP_EXPERT);
        this.repairShopId = repairShopId;
    }

    public RepairShopExpert() {
    }

    public int getRepairShopId() {
        return repairShopId;
    }

    public void setRepairShopId(int repairShopId) {
        this.repairShopId = repairShopId;
    }
}

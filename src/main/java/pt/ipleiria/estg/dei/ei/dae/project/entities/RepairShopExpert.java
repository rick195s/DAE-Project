package pt.ipleiria.estg.dei.ei.dae.project.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "repair_shops_experts")
@PrimaryKeyJoinColumn(name = "user_id")
@NamedQueries({
        @NamedQuery(
                name = "getAllRepairShopsExperts",
                query = "SELECT r FROM RepairShopExpert r ORDER BY r.id" // JPQL
        )
})
public class RepairShopExpert extends User implements Serializable {
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "repair_shop_id")
    RepairShop repairShop;

    public RepairShopExpert(int id, String name, String email, String password, RepairShop repairShop) {
        super(id, name, email, password);
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

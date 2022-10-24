package pt.ipleiria.estg.dei.ei.dae.project.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "repair_shops_experts")
@NamedQueries({
        @NamedQuery(
                name = "getAllRepairShopsExperts",
                query = "SELECT r FROM RepairShopExpert r ORDER BY r.id" // JPQL
        )
})
public class RepairShopExpert extends User implements Serializable {
    @Id
    int id;
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "repair_shop")
    RepairShop repairShop;

    public RepairShopExpert(int id, RepairShop repairShop) {
        super(id, repairShop.getName(), repairShop.getEmail(), repairShop.getPassword());
        this.repairShop = repairShop;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

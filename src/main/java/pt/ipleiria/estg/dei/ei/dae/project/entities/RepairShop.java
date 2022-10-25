package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "repair_shops")
@NamedQueries({
        @NamedQuery(
                name = "getAllRepairShops",
                query = "SELECT r FROM RepairShop r ORDER BY r.name" // JPQL
        )
})
public class RepairShop implements Serializable {
    @Id
    int id;
    @NotNull
    String name;
    @Email
    @NotNull
    String email;
    @NotNull
    String phone;
    @NotNull
    @OneToMany(mappedBy = "repairShop")
    List<RepairShopExpert> repairShopExperts;
    @ManyToMany
    @JoinTable(
            name = "insurers_repair_shop",
            joinColumns = @JoinColumn(name = "repair_shop_id"),
            inverseJoinColumns = @JoinColumn(name = "insurer_id")
    )
    @NotNull
    List<Insurer> insurers;

    public RepairShop(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        insurers= new LinkedList<>();
        repairShopExperts = new LinkedList<>();

    }

    public RepairShop() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String password) {
        this.phone = password;
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllInsurers",
                query = "SELECT u FROM Insurer u ORDER BY u.name" // JPQL
        )
})
@Table(name = "Insurers")
public class Insurer implements Serializable {
    @Id
    int id;

    // if the name of the property in the JSON is different
    // from the name of the attribute in the class, we can do mapping:
    // @JsonbProperty("first-name")
    @NotNull
    String name;

    @NotNull
    @OneToMany(mappedBy = "insurer")
    List<InsurerExpert> insurer_experts;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "insurers")
    List<RepairShop> repairShops;


    public Insurer() {
        insurer_experts = new LinkedList<>();
    }

    public Insurer(int id, String name) {
        this.id = id;
        this.name = name;
        insurer_experts = new LinkedList<>();
        repairShops = new LinkedList<>();
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

    public List<InsurerExpert> getInsurer_experts() {
        return insurer_experts;
    }

    public void setInsurer_experts(List<InsurerExpert> insurer_experts) {
        this.insurer_experts = new LinkedList<>(insurer_experts);
    }
}

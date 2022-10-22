package pt.ipleiria.estg.dei.ei.dae.project.enteties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllInsurerExperts",
                query = "SELECT u FROM InsurerExpert u ORDER BY u.name" // JPQL
        )
})
@Table(name = "insurer_experts")
@PrimaryKeyJoinColumn(name = "user_id")
public class InsurerExpert extends User implements Serializable {
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insurer_id")
    Insurer insurer;

    public InsurerExpert() {
    }

    public InsurerExpert(int id, String name, String email, String password, Insurer insurer) {
        super(id, name, email, password);
        this.insurer = insurer;
    }

    public Insurer getInsurer() {
        return insurer;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
    }


}

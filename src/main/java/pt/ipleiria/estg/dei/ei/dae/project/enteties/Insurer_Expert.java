package pt.ipleiria.estg.dei.ei.dae.project.enteties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllInsurer_Experts",
                query = "SELECT u FROM Insurer_Expert u ORDER BY u.name" // JPQL
        )
})
@Table(name = "Insurer_Experts")
@PrimaryKeyJoinColumn(name = "userId")
public class Insurer_Expert extends User implements Serializable {
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insurer_id")
    Insurer insurer;

    public Insurer_Expert() {
    }

    public Insurer_Expert(int id, String name, String email, String password, Insurer insurer) {
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

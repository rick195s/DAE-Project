package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllInsurerExperts",
                query = "SELECT c FROM InsurerExpert c ORDER BY c.name" // JPQL
        )
})
@Table(
        name = "insurer_experts"
)
@PrimaryKeyJoinColumn(name = "user_id")
public class InsurerExpert extends User {
    @Column(name = "insurer_id")
    private int insurerId;

    public InsurerExpert(String name, String email, String password, int insurerId) {
        super(name, email, password, Role.INSURER_EXPERT);
        this.insurerId = insurerId;
    }

    public InsurerExpert() {
    }

    public int getInsurerId() {
        return insurerId;
    }

    public void setInsurerId(int insurerId) {
        this.insurerId = insurerId;
    }
}

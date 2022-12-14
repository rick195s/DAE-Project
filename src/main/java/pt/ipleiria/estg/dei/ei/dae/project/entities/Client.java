package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT c FROM Client c ORDER BY c.name" // JPQL
        )
})
@Table(
        name = "clients"
)
@PrimaryKeyJoinColumn(name = "user_id")
public class Client extends User implements Serializable {
    int NIF_NIPC;

    @Transient
    List<Policy> policies;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "client")
    private List<Occurrence> occurrences;


    public Client(int id, String name, String email, String password, int NIF_NIPC) {
        super(id, name, email, password);
        this.NIF_NIPC = NIF_NIPC;
        this.policies = new ArrayList<>();
        this.occurrences = new ArrayList<>();
    }

    public Client() {
        this.policies = new ArrayList<>();
        this.occurrences = new ArrayList<>();
    }

    public int getNIF_NIPC() {
        return NIF_NIPC;
    }

    public void setNIF_NIPC(int NIF_NIPC) {
        this.NIF_NIPC = NIF_NIPC;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    public void removePolicy(Policy policy) {
        this.policies.remove(policy);
    }
}

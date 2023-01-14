package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

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
        ),
        @NamedQuery(
                name = "getClientByNIFNIPC",
                query = "SELECT c FROM Client c WHERE c.NIF_NIPC = :nif_nipc ORDER BY c.name" // JPQL
        )

})
/*@Table(
        name = "clients"
)
@PrimaryKeyJoinColumn(name = "user_id")*/
public class Client extends User {
    int NIF_NIPC;

    @Transient
    List<Policy> policies;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "client")
    private List<Occurrence> occurrences;


    public Client(String name, String email, String password, Role role, int NIF_NIPC) {
        super(name, email, password, role);
        this.NIF_NIPC = NIF_NIPC;
        this.policies = new ArrayList<>();
        this.occurrences = new ArrayList<>();
    }

    public Client(int NIF_NIPC) {
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

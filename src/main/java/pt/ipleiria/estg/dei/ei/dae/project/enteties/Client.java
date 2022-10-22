package pt.ipleiria.estg.dei.ei.dae.project.enteties;

import javax.persistence.*;
import java.io.Serializable;

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

    public Client(int id, String name, String email, String password, int NIF_NIPC) {
        super(id, name, email, password);
        this.NIF_NIPC = NIF_NIPC;
    }

    public Client() {
    }

    public int getNIF_NIPC() {
        return NIF_NIPC;
    }

    public void setNIF_NIPC(int NIF_NIPC) {
        this.NIF_NIPC = NIF_NIPC;
    }
}

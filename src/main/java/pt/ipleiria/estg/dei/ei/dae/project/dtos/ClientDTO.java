package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class ClientDTO extends UserDTO implements Serializable {

    int NIF_NIPC;

    public ClientDTO(int id, String name, String email, int NIF_NIPC) {
        super(id, name, email);
        this.NIF_NIPC = NIF_NIPC;
    }

    public ClientDTO() {
    }

    public int getNIF_NIPC() {
        return NIF_NIPC;
    }

    public void setNIF_NIPC(int NIF_NIPC) {
        this.NIF_NIPC = NIF_NIPC;
    }
}

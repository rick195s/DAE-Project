package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class AdministratorDTO extends UserDTO implements Serializable {

    public AdministratorDTO(int id, String name, String email, String role) {
        super(id, name, email, role);
    }

    public AdministratorDTO() {

    }
}

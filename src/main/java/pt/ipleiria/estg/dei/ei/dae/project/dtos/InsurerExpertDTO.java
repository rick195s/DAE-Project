package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class InsurerExpertDTO extends UserDTO implements Serializable {

    public InsurerExpertDTO() {
    }

    public InsurerExpertDTO(int id, String name, String email) {
        super(id, name, email);
    }

}

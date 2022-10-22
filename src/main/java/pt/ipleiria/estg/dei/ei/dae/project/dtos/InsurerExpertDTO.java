package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.Insurer;

import java.io.Serializable;

public class InsurerExpertDTO extends UserDTO implements Serializable {
    Insurer insurer;

    public InsurerExpertDTO() {
    }

    public InsurerExpertDTO(Insurer insurer) {
        this.insurer = insurer;
    }

    public InsurerExpertDTO(int id, String name, String email, Insurer insurer) {
        super(id, name, email);
        this.insurer = insurer;
    }

    public Insurer getInsurer() {
        return insurer;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
    }
}

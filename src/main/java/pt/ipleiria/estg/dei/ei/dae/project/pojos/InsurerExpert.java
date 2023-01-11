package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class InsurerExpert extends User implements Serializable {

    Insurer insurer;

    public InsurerExpert() {
    }

    public InsurerExpert( String name, String email, String password, Role role, Insurer insurer) {
        super( name, email, password, role);
        this.insurer = insurer;
    }

    public Insurer getInsurer() {
        return insurer;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
    }


}

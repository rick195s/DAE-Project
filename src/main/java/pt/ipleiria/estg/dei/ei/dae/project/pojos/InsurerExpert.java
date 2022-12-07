package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class InsurerExpert extends User implements Serializable {

    Insurer insurer;

    public InsurerExpert() {
    }

    public InsurerExpert(int id, String name, String email, String password, Insurer insurer) {
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

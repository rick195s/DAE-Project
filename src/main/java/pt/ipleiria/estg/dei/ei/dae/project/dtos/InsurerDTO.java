package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.Insurer_Expert;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class InsurerDTO implements Serializable {
    int id;

    String name;

    List<Insurer_Expert> insurer_experts;

    public InsurerDTO() {
        insurer_experts = new LinkedList<>();
    }

    public InsurerDTO(int id, String name) {
        this.id = id;
        this.name = name;
        insurer_experts = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Insurer_Expert> getInsurer_experts() {
        return insurer_experts;
    }

    public void setInsurer_experts(List<Insurer_Expert> insurer_experts) {
        this.insurer_experts = new LinkedList<>(insurer_experts);
    }
}

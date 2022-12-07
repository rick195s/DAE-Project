package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class InsurerDTO implements Serializable {
    int id;

    String name;


    public InsurerDTO() {
    }

    public InsurerDTO(int id, String name) {
        this.id = id;
        this.name = name;
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

}

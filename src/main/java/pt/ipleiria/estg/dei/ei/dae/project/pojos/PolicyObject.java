package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PolicyObject implements Serializable {
    int id;

    String name;

    String filePath;

    public PolicyObject(int id, String name, String filePath) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
    }
    public PolicyObject(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public PolicyObject() {
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

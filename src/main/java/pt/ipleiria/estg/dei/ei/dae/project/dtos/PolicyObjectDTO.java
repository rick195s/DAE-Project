package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PolicyObjectDTO implements Serializable {

    int id;
    String name;
    String filePath;
    PolicyObjectType type;
    //@NotNull
    //Policy policy;


    public PolicyObjectDTO(int id, String name, String filePath, PolicyObjectType type) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.type = type;
    }

    public PolicyObjectDTO() {
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

    public PolicyObjectType getType() {
        return type;
    }

    public void setType(PolicyObjectType type) {
        this.type = type;
    }
}
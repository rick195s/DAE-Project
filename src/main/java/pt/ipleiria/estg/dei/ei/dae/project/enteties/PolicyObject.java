package pt.ipleiria.estg.dei.ei.dae.project.enteties;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.enums.PolicyObjectType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class PolicyObject implements Serializable {
    @Id
    int id;
    @NotNull
    String name;
    @NotNull
    String filePath;
    @Enumerated(EnumType.STRING)
    @NotNull
    private PolicyObjectType type;
    //@NotNull
    //Policy policy;

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

    public PolicyObjectType getType() {
        return type;
    }

    public void setType(PolicyObjectType type) {
        this.type = type;
    }
}
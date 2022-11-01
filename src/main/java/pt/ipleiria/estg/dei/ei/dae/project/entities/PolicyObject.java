package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPolicyObjects",
                query = "SELECT p FROM PolicyObject p ORDER BY p.name" // JPQL
        )
})
@Table(name = "policy_objects")
public class PolicyObject implements Serializable {
    @Id
    int id;

    @NotNull
    String name;

    @NotNull
    String filePath;

    public PolicyObject(int id, String name, String filePath) {
        this.id = id;
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

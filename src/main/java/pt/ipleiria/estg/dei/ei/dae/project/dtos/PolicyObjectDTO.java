package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyObjectType;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PolicyObjectDTO implements Serializable {

    int id;
    String name;
    String filePath;

    //@NotNull
    //Policy policy;


    public PolicyObjectDTO(int id, String name, String filePath) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
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

    public static PolicyObjectDTO from(PolicyObject policyObject) {
        return new PolicyObjectDTO(
                policyObject.getId(),
                policyObject.getName(),
                policyObject.getFilePath()
        );
    }

    public static List<PolicyObjectDTO> from(List<PolicyObject> policyObjects) {
        return policyObjects.stream().map(PolicyObjectDTO::from).collect(Collectors.toList());
    }
}

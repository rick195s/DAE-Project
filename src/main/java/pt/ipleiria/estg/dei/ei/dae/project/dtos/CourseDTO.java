package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import javax.persistence.Id;
import java.io.Serializable;

public class CourseDTO implements Serializable {
    @Id
    long code;
    String name;

    public CourseDTO(long code, String name) {
        this.name = name;
        this.code = code;
    }

    public CourseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}

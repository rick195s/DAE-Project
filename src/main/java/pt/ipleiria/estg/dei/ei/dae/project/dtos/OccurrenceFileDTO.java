package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;

import java.io.Serializable;

public class OccurrenceFileDTO implements Serializable {

    String name;
    String path;

    public OccurrenceFileDTO(OccurrenceFile occurrenceFile) {
        this.name = occurrenceFile.getName();
        this.path = occurrenceFile.getPath();
    }


    public OccurrenceFileDTO(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public OccurrenceFileDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

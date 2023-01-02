package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrenceFileDTO implements Serializable {

    String name;
    String path;

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

    public static OccurrenceFileDTO from(OccurrenceFile document) {
        return new OccurrenceFileDTO(document.getName(), document.getPath());
    }

    public static List<OccurrenceFileDTO> from(List<OccurrenceFile> documents) {
        return documents.stream().map(OccurrenceFileDTO::from).collect(Collectors.toList());
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrenceFileDTO implements Serializable {

    int id;
    String name;

    public OccurrenceFileDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public OccurrenceFileDTO() {
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

    public static OccurrenceFileDTO from(OccurrenceFile occurrenceFile) {
        return new OccurrenceFileDTO(occurrenceFile.getId(), occurrenceFile.getName());
    }

    public static List<OccurrenceFileDTO> from(List<OccurrenceFile> occurrenceFiles) {
        return occurrenceFiles.stream().map(OccurrenceFileDTO::from).collect(Collectors.toList());
    }
}

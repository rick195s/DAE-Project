package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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

    public static InsurerDTO from(Insurer insurer) {
        return new InsurerDTO(
                insurer.getId(),
                insurer.getName()
        );
    }

    public static List<InsurerDTO> from(List<Insurer> insurers) {
        return insurers.stream().map(InsurerDTO::from).collect(Collectors.toList());
    }
}

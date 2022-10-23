package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class UserDTO implements Serializable {
    int id;

    String name, email;

    public UserDTO(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

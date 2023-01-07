package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import javax.validation.constraints.NotNull;

public class UserCreateDTO extends UserDTO {

    @NotNull
    private String password;

    public UserCreateDTO() {
    }

    public UserCreateDTO(int id, String name, String email, String role, String password) {
        super(id, name, email, role);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

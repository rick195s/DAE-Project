package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import javax.validation.constraints.NotBlank;

public class Auth {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Auth() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

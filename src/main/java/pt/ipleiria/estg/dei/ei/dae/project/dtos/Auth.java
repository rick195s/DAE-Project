package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import javax.validation.constraints.NotBlank;

public class Auth {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public Auth() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

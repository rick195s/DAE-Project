package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import javax.validation.constraints.NotNull;

public class ClientCreateDTO extends ClientDTO {

    @NotNull
    private String password;

    public ClientCreateDTO() {
    }

    public ClientCreateDTO(int id, String name, String email, String role, int NIF_NIPC, String password) {
        super(id, name, email, role, NIF_NIPC);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

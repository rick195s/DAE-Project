package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import javax.validation.constraints.NotNull;

public class UserCreateDTO extends UserDTO {

    @NotNull
    private String password;

    private int repairShopId;

    public UserCreateDTO() {
    }

    public UserCreateDTO(int id, String name, String email, String role, String password, int repairShopId) {
        super(id, name, email, role);
        this.password = password;
        this.repairShopId = repairShopId;
    }

    public int getRepairShopId() {
        return repairShopId;
    }

    public void setRepairShopId(int repairShopId) {
        this.repairShopId = repairShopId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

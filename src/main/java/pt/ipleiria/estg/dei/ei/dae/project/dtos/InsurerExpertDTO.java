package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class InsurerExpertDTO extends UserDTO implements Serializable {

    InsurerDTO insurerId;

    public InsurerExpertDTO() {
    }

    public InsurerExpertDTO(int id, String name, String email, String role) {
        super(id, name, email, role);
    }

    public InsurerDTO getInsurerDTO() {
        return insurerId;
    }

    public void setInsurerId(InsurerDTO insurerId) {
        this.insurerId = insurerId;
    }

    public int getInsurerId() {
        return insurerId.getId();
    }

    public void setInsurerId(int id) {
        this.insurerId.setId(id);
    }
}

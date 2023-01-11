package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.pojos.InsurerExpert;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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

    public static InsurerExpertDTO from(InsurerExpert insurerExpert) {
        return new InsurerExpertDTO(
                insurerExpert.getId(),
                insurerExpert.getName(),
                insurerExpert.getEmail(),
                insurerExpert.getRole()
        );
    }

    public static List<InsurerExpertDTO> insurerExpertFrom(List<InsurerExpert> insurerExperts) {
        return insurerExperts.stream().map(InsurerExpertDTO::from).collect(Collectors.toList());
    }
}

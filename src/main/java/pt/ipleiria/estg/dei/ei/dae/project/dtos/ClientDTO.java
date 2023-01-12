package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO extends UserDTO implements Serializable {

    @JsonProperty("nif_nipc")
    int NIF_NIPC;

    public ClientDTO(int id, String name, String email, String role, int NIF_NIPC) {
        super(id, name, email, role);
        this.NIF_NIPC = NIF_NIPC;
    }

    public ClientDTO() {

    }

    public int getNIF_NIPC() {
        return NIF_NIPC;
    }

    public void setNIF_NIPC(int NIF_NIPC) {
        this.NIF_NIPC = NIF_NIPC;
    }

    // Converts an entity Student to a DTO Student class
    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getRole().toString(),
                client.getNIF_NIPC()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<ClientDTO> clientFrom(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }
}

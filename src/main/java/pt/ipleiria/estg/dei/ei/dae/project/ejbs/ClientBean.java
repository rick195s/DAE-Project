package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void create(String name, String email, String password, int NIF_NIPC) {
        if (NIF_NIPC < 100000000 || NIF_NIPC > 999999999) {
            throw new IllegalArgumentException("NIF/NIPC must have 9 digits");
        }

        Client client = new Client(name, email, hasher.hash(password), Role.CLIENT, NIF_NIPC);
        entityManager.persist(client);
    }

    public List<Client> getAllClients() {
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return (List<Client>) entityManager.createNamedQuery("getAllClients").getResultList();
    }

    public Client find(int id) {
        return entityManager.find(Client.class, id);
    }
}

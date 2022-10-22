package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.Client;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ClientBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String email, String password, int NIF_NIPC) {
        Client client = new Client(id, name, email, password, NIF_NIPC);
        entityManager.persist(client);
    }

    public List<Client> getAllClients() {
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return (List<Client>) entityManager.createNamedQuery("getAllClients").getResultList();
    }

    public Client findClient(long id) {
        return entityManager.find(Client.class, id);
    }
}

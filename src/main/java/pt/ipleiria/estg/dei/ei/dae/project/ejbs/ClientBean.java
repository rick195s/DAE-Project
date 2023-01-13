package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
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
        return (List<Client>) entityManager.createNamedQuery("getAllClients").getResultList();
    }

    public Client find(int id) {
        return entityManager.find(Client.class, id);
    }

    public Client findByNIFNIPC(int nif_nipc) {

        try {
            return (Client) entityManager.createNamedQuery("getClientByNIFNIPC")
                    .setParameter("nif_nipc", nif_nipc)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}

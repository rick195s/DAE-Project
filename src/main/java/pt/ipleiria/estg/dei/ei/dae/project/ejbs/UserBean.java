package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(int id, String name, String email, String password ) {
        User user = new User(id, name, email, password);
        entityManager.persist(user);
    }

    public List<User> getAllUsers() {
        // remember, maps to: “SELECT c FROM Courses c ORDER BY c.name”
        return (List<User>) entityManager.createNamedQuery("getAllUsers").getResultList();
    }

    public User findUser(long id) {
        return entityManager.find(User.class, id);
    }
}

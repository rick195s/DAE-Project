package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    public User find(String email) {
        return em.find(User.class, email);
    }

    public User findOrFail(String username) {
        User user = em.getReference(User.class, username);
        Hibernate.initialize(user);

        return user;
    }

    public boolean canLogin(String email, String password) {
        User user = find(email);

        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}

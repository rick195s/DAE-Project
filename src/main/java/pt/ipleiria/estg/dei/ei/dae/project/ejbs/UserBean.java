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
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public User find(int id) {
        return entityManager.find(User.class, id);
    }

    public User findUserByEmail(String email) {
        return (User) entityManager.createNamedQuery("getUserByEmail")
                .setParameter("email", email)
                .getSingleResult();
    }

    public User findOrFail(String email) {
        //var user = entityManager.getReference(User.class, email);
        var user = findUserByEmail(email);
        Hibernate.initialize(user);

        return user;
    }

    public boolean canLogin(String email, String password) {
        User user = findUserByEmail(email);

        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}

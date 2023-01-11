package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void create(String name, String email, String password, String role) {
        User user = new User(name, email, hasher.hash(password), Role.valueOf(role));
        entityManager.persist(user);
    }

    public User find(int id) {
        return entityManager.find(User.class, id);
    }

    public List<User> getAllUsers() {
        return (List<User>) entityManager.createNamedQuery("getAllUsers").getResultList();
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

    public List<User> getAllAdministrators() {
        return (List<User>) entityManager.createNamedQuery("getAllAdministrators").getResultList();
    }

    public List<User> getAllRepairShopExperts() {
        return (List<User>) entityManager.createNamedQuery("getAllRepairShopExperts").getResultList();
    }


    public void update(int id, UserDTO userDTO) {
        User user = find(id);
        if (user == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }


        if (userDTO.getRole() != null && !userDTO.getRole().equals("ADMINISTRATOR")) {
            try {
                user.setRole(Role.valueOf(userDTO.getRole()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Role " + userDTO.getRole() + " not found");
            }
        }


        entityManager.merge(user);
    }
}

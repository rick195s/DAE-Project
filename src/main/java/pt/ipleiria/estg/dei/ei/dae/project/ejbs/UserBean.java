package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UpdatePasswordDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.PasswordInvalidException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.mappers.PasswordInvalidExceptionMapper;
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

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(*) FROM " + User.class.getSimpleName(), Long.class).getSingleResult();
    }

    public User find(int id) {
        return entityManager.find(User.class, id);
    }

    public List<User> getAllUsers() {
        return (List<User>) entityManager.createNamedQuery("getAllUsers").getResultList();
    }

    public User findUserByEmail(String email) {
        try {
            return (User) entityManager.createNamedQuery("getUserByEmail")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
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


    public void update(int id, String newName) {
        User user = find(id);
        if (user == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        if (newName != null) {
            user.setName(newName);
        }

        entityManager.merge(user);
    }

    public void updatePassword(String userEmail, UpdatePasswordDTO updatePasswordDTO) throws PasswordInvalidException {
        User user = findUserByEmail(userEmail);
        if (user == null) {
            throw new EntityNotFoundException("User don't exists");
        }

        //verify if user password is not equal to old password
        if (!user.getPassword().equals(hasher.hash(updatePasswordDTO.getOldPassword()))) {
            throw new PasswordInvalidException("Old password is not correct");
        }
        //verify if new password is not equal to confirm password
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getConfirmNewPassword())) {
            throw new PasswordInvalidException("New password and confirm password are not equal");
        }

        //verify if old password is  equal to new password
        if (updatePasswordDTO.getOldPassword().equals(updatePasswordDTO.getNewPassword())) {
            throw new PasswordInvalidException("Old password and new password are equal");
        }

        user.setPassword(hasher.hash(updatePasswordDTO.getNewPassword()));
        entityManager.merge(user);
    }

    public void delete(int id) {
        User user = find(id);
        if (user == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        user.setDeleted(true);

        entityManager.merge(user);
    }

}

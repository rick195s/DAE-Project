package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.security.Hasher;
import pt.ipleiria.estg.dei.ei.dae.project.security.enums.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void create(String name, String email, String password, String role) {
        User user = new User(name, email, hasher.hash(password), role);
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


    public Response update(int id, UserDTO userDTO) {
        User user = find(id);
        String email = findUserByEmail(userDTO.getEmail()).getEmail();

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }

        if (userDTO.getEmail() != null) {
            if (findUserByEmail(userDTO.getEmail()) == null || userDTO.getEmail().equals(user.getEmail())) {
                user.setEmail(userDTO.getEmail());
            } else {
                return Response.status(Response.Status.CONFLICT).build();
            }
        }


        List<Role> roles = new ArrayList<>(Arrays.asList(Role.values()));

        if (userDTO.getRole() != null) {
            if (!userDTO.getRole().equals("ADMINISTRATOR")) {
                //TODO: falta implementar uma resposta má caso o if dentro deste ciclo for falhe
                for (Role role : roles) {
                    if (role.name().equals(userDTO.getRole())) {
                        user.setRole(String.valueOf(role));
                    }
                }

            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }

        }

        entityManager.merge(user);
        return Response.status(Response.Status.OK).entity(userDTO.from(user)).build();
    }
}

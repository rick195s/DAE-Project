package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllUsers",
                query = "SELECT u FROM User u ORDER BY u.id" // JPQL
        ),
        @NamedQuery(
                name = "getUserByEmail",
                query = "SELECT u FROM User u WHERE u.email = :email" // JPQL
        ),
        @NamedQuery(
                name = "getAllAdministrators",
                query = "SELECT u FROM User u WHERE u.role = 'admin' ORDER BY u.id" // JPQL
        ),
        @NamedQuery(
                name = "getAllRepairShopExperts",
                query = "SELECT u FROM User u WHERE u.role = 'repairShopExpert' ORDER BY u.id" // JPQL
        )
})
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"})
)

@Inheritance(strategy=InheritanceType.JOINED)
public class User {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @NotNull
    protected String name, password;

    @NotNull
    @Email
    protected String email;

    @NotNull
    protected String role;

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}

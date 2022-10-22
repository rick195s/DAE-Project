package pt.ipleiria.estg.dei.ei.dae.project.enteties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllHistoricals",
                query = "SELECT u FROM Historical u ORDER BY u.date" // JPQL
        )
})
@Table(name = "Historicals")
public class Historical implements Serializable {
    @Id
    int id;

    @Enumerated(EnumType.STRING)
    Historical_Enum state;

    @NotNull
    String description;

    @NotNull
    Date date;

    public Historical() {
    }

    public Historical(int id, Historical_Enum state, String description, Date date) {
        this.id = id;
        this.state = state;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Historical_Enum getState() {
        return state;
    }

    public void setState(Historical_Enum state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

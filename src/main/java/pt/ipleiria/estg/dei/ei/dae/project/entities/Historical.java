package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

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
    HistoricalEnum state;

    @NotNull
    String description;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    Calendar date;

    public Historical() {
    }

    public Historical(int id, HistoricalEnum state, String description, Calendar date) {
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

    public HistoricalEnum getState() {
        return state;
    }

    public void setState(HistoricalEnum state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCalendar() {
        return date;
    }

    public void setCalendar(Calendar date) {
        this.date = date;
    }
}

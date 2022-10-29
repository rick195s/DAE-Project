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
                query = "SELECT u FROM Historical u ORDER BY u.calendar" // JPQL
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

    @Column(name = "date")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    Calendar calendar;

    public Historical() {
    }

    public Historical(int id, HistoricalEnum state, String description, Calendar calendar) {
        this.id = id;
        this.state = state;
        this.description = description;
        this.calendar = calendar;
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
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}

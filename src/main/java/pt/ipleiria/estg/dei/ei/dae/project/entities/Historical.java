package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    HistoricalEnum state;

    @NotNull
    String description;

    @NotNull
    // @Temporal(TemporalType.TIMESTAMP)
    String date;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "occurrence_id")
    Occurrence occurrence;

    public Historical(HistoricalEnum state, String description, Occurrence occurrence, String date) {
        this.state = state;
        this.description = description;
        this.occurrence = occurrence;
        this.date = date;
    }

    public Historical() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.enums.HistoricalEnum;

import java.io.Serializable;
import java.util.Date;

public class HistoricalDTO implements Serializable {
    int id;

    HistoricalEnum state;

    String description;

    Date date;

    public HistoricalDTO() {
    }

    public HistoricalDTO(int id, HistoricalEnum state, String description, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;

import java.io.Serializable;
import java.util.Calendar;

public class HistoricalDTO implements Serializable {
    int id;

    HistoricalEnum state;

    String description;

    String date;

    public HistoricalDTO() {
    }

    public HistoricalDTO(Historical historical) {
        this.id = historical.getId();
        this.state = historical.getState();
        this.description = historical.getDescription();
        this.date = historical.getDate();
    }

    public HistoricalDTO(int id, HistoricalEnum state, String description, String date) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

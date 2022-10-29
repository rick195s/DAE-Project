package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class HistoricalDTO implements Serializable {
    int id;

    HistoricalEnum state;

    String description;

    Calendar calendar;

    public HistoricalDTO() {
    }

    public HistoricalDTO(int id, HistoricalEnum state, String description, Calendar calendar) {
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

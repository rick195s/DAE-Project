package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.Historical_Enum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class HistoricalDTO implements Serializable {
    int id;

    Historical_Enum state;

    String description;

    Date date;

    public HistoricalDTO() {
    }

    public HistoricalDTO(int id, Historical_Enum state, String description, Date date) {
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

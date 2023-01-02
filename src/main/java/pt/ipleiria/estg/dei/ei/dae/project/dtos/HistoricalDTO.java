package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Historical;
import pt.ipleiria.estg.dei.ei.dae.project.entities.OccurrenceFile;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.HistoricalEnum;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricalDTO implements Serializable {
    int id;

    HistoricalEnum state;

    String description;

    String date;

    public HistoricalDTO() {
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

    public static HistoricalDTO from(Historical historical) {
        return new HistoricalDTO(historical.getId(), historical.getState(), historical.getDescription(), historical.getDate());
    }

    public static List<HistoricalDTO> from(List<Historical> historicals) {
        return historicals.stream().map(HistoricalDTO::from).collect(Collectors.toList());
    }
}

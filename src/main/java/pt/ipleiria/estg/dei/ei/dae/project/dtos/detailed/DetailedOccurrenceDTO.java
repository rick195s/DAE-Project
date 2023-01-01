package pt.ipleiria.estg.dei.ei.dae.project.dtos.detailed;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.HistoricalDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.OccurrenceFileDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailedOccurrenceDTO extends OccurrenceDTO implements Serializable {
    private List<HistoricalDTO> historic;

    private List<OccurrenceFileDTO> files;

    public DetailedOccurrenceDTO(int id, String description, ApprovalType approvalType, Calendar startDate, Calendar endDate, int policyId, int repairShopId, int clientId) {
        super(id, description, approvalType, startDate, endDate, policyId, repairShopId, clientId);
        this.files = new ArrayList<>();
        this.historic = new ArrayList<>();
    }
    public DetailedOccurrenceDTO(Occurrence occurrence) {
        super(occurrence);
        this.files = new ArrayList<>();
        this.historic = new ArrayList<>();
    }

    public DetailedOccurrenceDTO() {
        this.files = new ArrayList<>();
        this.historic = new ArrayList<>();
    }

    public List<HistoricalDTO> getHistoric() {
        return historic;
    }

    public void setHistoric(List<HistoricalDTO> historic) {
        this.historic = historic;
    }

    public List<OccurrenceFileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<OccurrenceFileDTO> files) {
        this.files = files;
    }
}

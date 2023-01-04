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
import java.util.stream.Collectors;

public class DetailedOccurrenceDTO extends OccurrenceDTO implements Serializable {
    private List<HistoricalDTO> historic;

    private List<OccurrenceFileDTO> files;

    public DetailedOccurrenceDTO(int id, String description, ApprovalType approvalType, Calendar startDate, Calendar endDate, int policyId, int repairShopId, String clientEmail) {
        super(id, description, approvalType, startDate, endDate, policyId, repairShopId, clientEmail);
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

    public static DetailedOccurrenceDTO from(Occurrence occurrence) {
        return new DetailedOccurrenceDTO(
                occurrence.getId(),
                occurrence.getDescription(),
                occurrence.getApprovalType(),
                occurrence.getStartDate(),
                occurrence.getEndDate(),
                occurrence.getPolicyId(),
                occurrence.getRepairShopId(),
                occurrence.getClient().getEmail());
    }

    public static List<DetailedOccurrenceDTO> detailedFrom(List<Occurrence> occurrences) {
        return occurrences.stream().map(DetailedOccurrenceDTO::from).collect(Collectors.toList());
    }
}

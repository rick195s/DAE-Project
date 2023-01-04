package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PolicyDTO {
    int id;

    String clientEmail;

    int insurerId;

    int  policyTypeDetailId;

    List<OccurrenceDTO> occurrences;

    int policyObjectId;

    PolicyState state;

    String startDate;

    String endDate;

    public PolicyDTO() {
        occurrences = new ArrayList<>();
    }

    public PolicyDTO(int id, String clientEmail, int insurerId, int policyTypeDetailId, List<OccurrenceDTO> occurrences, int policyObjectId, PolicyState state, String startDate, String endDate) {
        this.id = id;
        this.clientEmail = clientEmail;
        this.insurerId = insurerId;
        this.policyTypeDetailId = policyTypeDetailId;
        this.occurrences = occurrences;
        this.policyObjectId = policyObjectId;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public int getInsurerId() {
        return insurerId;
    }

    public void setInsurerId(int insurerId) {
        this.insurerId = insurerId;
    }

    public int getPolicyTypeDetailId() {
        return policyTypeDetailId;
    }

    public void setPolicyTypeDetailId(int policyTypeDetailId) {
        this.policyTypeDetailId = policyTypeDetailId;
    }

    public List<OccurrenceDTO> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(List<OccurrenceDTO> occurrences) {
        this.occurrences = occurrences;
    }

    public int getPolicyObjectId() {
        return policyObjectId;
    }

    public void setPolicyObjectId(int policyObjectId) {
        this.policyObjectId = policyObjectId;
    }

    public PolicyState getState() {
        return state;
    }

    public void setState(PolicyState state) {
        this.state = state;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}



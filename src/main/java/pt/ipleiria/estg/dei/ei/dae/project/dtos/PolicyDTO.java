package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Insurer;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyObject;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.PolicyTypeDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PolicyDTO {
    int id;

    int clientId;

    int insurerId;

    int  policyTypeDetailId;

    List<OccurrenceDTO> occurrences;

    int policyObjectId;

    PolicyState state;

    Calendar startDate;

    Calendar endDate;

    public PolicyDTO() {
        occurrences = new ArrayList<>();
    }

    public PolicyDTO(int id, int clientId, int insurerId, int policyTypeDetailId, List<OccurrenceDTO> occurrences, int policyObjectId, PolicyState state, Calendar startDate, Calendar endDate) {
        this.id = id;
        this.clientId = clientId;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
}



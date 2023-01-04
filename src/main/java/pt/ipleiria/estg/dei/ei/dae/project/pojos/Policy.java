package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Policy implements Serializable {
    int id;

    Client client;

    int insurerId;

    int policyTypeDetailId;

    List<Occurrence> occurrences;

    int policyObjectId;

    PolicyState state;

    String startDate;

    String endDate;

    public Policy(int id, Client client, int insurerId, PolicyState state, int policyTypeDetailId, int policyObjectId, String startDate, String endDate) {
        this.id = id;
        this.client = client;
        this.insurerId = insurerId;
        this.state = state;
        this.policyTypeDetailId = policyTypeDetailId;
        this.policyObjectId = policyObjectId;
        this.startDate = startDate;
        this.endDate = endDate;
        occurrences = new LinkedList<>();
    }

    public Policy() {
        occurrences = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientEmail() {
        if (client == null){
            return null;
        }
        return client.getEmail();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getInsurerId() {
        return insurerId;
    }

    public void setInsurerId(int insurerId) {
        this.insurerId = insurerId;
    }

    public PolicyState getState() {
        return state;
    }

    public void setState(PolicyState state) {
        this.state = state;
    }

    public int getPolicyTypeDetailId() {
        return policyTypeDetailId;
    }

    public void setPolicyTypeDetailId(int policyTypeDetailId) {
        this.policyTypeDetailId = policyTypeDetailId;
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

    public int getPolicyObjectId() {
        return policyObjectId;
    }

    public void setPolicyObjectId(int policyObjectId) {
        this.policyObjectId = policyObjectId;
    }

    public List<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(List<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public void addOccurrence(Occurrence occurrence) {
        occurrences.add(occurrence);
    }
}

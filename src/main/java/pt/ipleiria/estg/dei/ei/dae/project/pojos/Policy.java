package pt.ipleiria.estg.dei.ei.dae.project.pojos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Policy implements Serializable {
    int id;

    Client client;

    Insurer insurer;

    PolicyTypeDetail policyTypeDetail;

    List<Occurrence> occurrences;

    PolicyObject policyObject;

    PolicyState state;

    Calendar startDate;

    Calendar endDate;

    public Policy(int id, Client client, Insurer insurer, PolicyState state, PolicyTypeDetail policyTypeDetail, PolicyObject policyObject, Calendar startDate, Calendar endDate) {
        this.id = id;
        this.client = client;
        this.insurer = insurer;
        this.state = state;
        this.policyTypeDetail = policyTypeDetail;
        this.policyObject = policyObject;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Insurer getInsurer() {
        return insurer;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
    }

    public PolicyState getState() {
        return state;
    }

    public void setState(PolicyState state) {
        this.state = state;
    }

    public PolicyTypeDetail getPolicyTypeDetail() {
        return policyTypeDetail;
    }

    public void setPolicyTypeDetail(PolicyTypeDetail policyTypeDetail) {
        this.policyTypeDetail = policyTypeDetail;
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

    public PolicyObject getPolicyObject() {
        return policyObject;
    }

    public void setPolicyObject(PolicyObject policyObject) {
        this.policyObject = policyObject;
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

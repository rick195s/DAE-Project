package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyState;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.PolicyType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPolicies",
                query = "SELECT p FROM Policy p ORDER BY p.startDate" // JPQL
        )
})
@Table(
        name = "policies"
)

public class Policy implements Serializable {
    @Id
    int id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    Client client;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insurer_id")
    Insurer insurer;


    @NotNull
    @Enumerated(EnumType.STRING)
    PolicyState state;

    @NotNull
    @Enumerated(EnumType.STRING)
    PolicyType type;

    @NotNull
    Calendar startDate;

    @NotNull
    Calendar endDate;

    public Policy(int id, Client client, Insurer insurer, PolicyState state, PolicyType type, Calendar startDate, Calendar endDate) {
        this.id = id;
        this.client = client;
        this.insurer = insurer;
        this.state = state;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Policy() {
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

    public PolicyType getType() {
        return type;
    }

    public void setType(PolicyType type) {
        this.type = type;
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

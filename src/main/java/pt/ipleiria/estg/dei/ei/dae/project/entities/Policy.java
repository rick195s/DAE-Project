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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_type_details_id")
    PolicyTypeDetail policyTypeDetail;

    @NotNull
    @OneToOne
    @JoinColumn(name = "policy_object_id")
    PolicyObject policyObject;

    @NotNull
    @Enumerated(EnumType.STRING)
    PolicyState state;

    @NotNull
    @Column(name = "start_date")
    Calendar startDate;

    @NotNull
    @Column(name = "end_date")
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
}

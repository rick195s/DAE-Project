package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOccurrences",
                query = "SELECT o FROM Occurrence o ORDER BY o.id" // JPQL
        )
})
@Table(
        name = "occurrences"
)
public class Occurrence implements Serializable {
    @Id
    private int id;

    @Column(name = "policy_id")
    private int policyID;

    @Column(name = "repair_id")
    private int repairID;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_type")
    private ApprovalType approvalType;

    @Column(name = "start_date")
    private Calendar startDate;

    @Column(name = "end_date")
    private Calendar endDate;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "occurrence")
    private List<OccurrenceFile> occurenceFileList;

    public Occurrence() {
    }

    public Occurrence(int id, int policyID, int repairID, String description, ApprovalType approvalType, Calendar startDate, Calendar endDate, List<OccurrenceFile> occurenceFileList) {
        this.id = id;
        this.policyID = policyID;
        this.repairID = repairID;
        this.description = description;
        this.approvalType = approvalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.occurenceFileList = occurenceFileList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPolicyID() {
        return policyID;
    }

    public void setPolicyID(int policyID) {
        this.policyID = policyID;
    }

    public int getRepairID() {
        return repairID;
    }

    public void setRepairID(int repairID) {
        this.repairID = repairID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalType getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(ApprovalType approvalType) {
        this.approvalType = approvalType;
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

package pt.ipleiria.estg.dei.ei.dae.project.enteties;

import pt.ipleiria.estg.dei.ei.dae.project.enteties.enums.ApprovalType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOccurrences",
                query = "SELECT u FROM Occurence u ORDER BY u.id" // JPQL
        )
})
@Table(
        name = "occurrences"
)
public class Occurence implements Serializable {
    @Id
    private int id;
    private int policyID;
    private int repairID;
    private String description;

    @Enumerated(EnumType.STRING)
    private ApprovalType approvalType;
    private Date startDate;
    private Date endDate;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "occurrence")
    private List<OccurenceFile> occurenceFileList;

    public Occurence() {
    }

    public Occurence(int id, int policyID, int repairID, String description, ApprovalType approvalType, Date startDate, Date endDate, List<OccurenceFile> occurenceFileList) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

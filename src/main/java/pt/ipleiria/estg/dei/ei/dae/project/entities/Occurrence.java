package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
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

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_type")
    private ApprovalType approvalType;

    @Column(name = "start_date")
    private Calendar startDate;

    @Column(name = "end_date")
    private Calendar endDate;

    @NotNull
    private int policy;

    @NotNull
    private String repairShop;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "occurrence")
    private List<OccurrenceFile> occurenceFileList;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "occurrence")
    private List<Historical> occurenceHistoricalList;

    public Occurrence() {
        this.occurenceFileList = new LinkedList<>();
        this.occurenceHistoricalList = new LinkedList<>();
    }

    public Occurrence(int id, int policy, String repairShop, String description, ApprovalType approvalType, Calendar startDate, Calendar endDate) {
        this.id = id;
        this.repairShop = repairShop;
        this.description = description;
        this.approvalType = approvalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.policy = policy;
        this.occurenceFileList = new LinkedList<>();
        this.occurenceHistoricalList = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPolicy() {
        return policy;
    }

    public void setPolicy(int policy) {
        this.policy = policy;
    }

    public String getRepairShop() {
        return repairShop;
    }

    public void setRepairShop(String repairShop) {
        this.repairShop = repairShop;
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

    public List<OccurrenceFile> getOccurenceFileList() {
        return occurenceFileList;
    }

    public void setOccurenceFileList(List<OccurrenceFile> occurenceFileList) {
        this.occurenceFileList = occurenceFileList;
    }

    public List<Historical> getOccurenceHistoricalList() {
        return occurenceHistoricalList;
    }

    public void setOccurenceHistoricalList(List<Historical> occurenceHistoricalList) {
        this.occurenceHistoricalList = occurenceHistoricalList;
    }

    public void addOccurenceFile(OccurrenceFile occurrenceFile) {
        this.occurenceFileList.add(occurrenceFile);
    }

    public void addOccurenceHistorical(Historical historical) {
        this.occurenceHistoricalList.add(historical);
    }

    public void removeOccurenceFile(OccurrenceFile occurrenceFile) {
        this.occurenceFileList.remove(occurrenceFile);
    }
}

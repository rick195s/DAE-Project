package pt.ipleiria.estg.dei.ei.dae.project.entities;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.Policy;
import pt.ipleiria.estg.dei.ei.dae.project.pojos.RepairShop;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOccurrences",
                query = "SELECT o FROM Occurrence o ORDER BY o.id" // JPQL
        ),
        @NamedQuery(
                name = "getOccurrencesOfClient",
                query = "SELECT o FROM Occurrence o WHERE o.client = :client ORDER BY o.id" // JPQL
        )
})
@Table(
        name = "occurrences"
)
public class Occurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_type")
    private ApprovalType approvalType;

    @Column(name = "start_date")
    private Calendar startDate;

    @Column(name = "end_date")
    private Calendar endDate;

    // Policy in memory
    @Transient
    private Policy policy;

    // Persist just policy id to maintain relation
    // to external resource
    @Column(name = "policy_id")
    private int policyId;

    @Transient
    private RepairShop repairShop;

    @Column(name = "repair_shop_id")
    private int repairShopId;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "occurrence")
    private List<OccurrenceFile> occurenceFileList;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "occurrence")
    private List<Historical> occurenceHistoricalList;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "client_id")
    Client client;


    public Occurrence() {
        this.occurenceFileList = new LinkedList<>();
        this.occurenceHistoricalList = new LinkedList<>();
    }

    public Occurrence( Policy policy,  RepairShop repairShop, String description, ApprovalType approvalType, Calendar startDate, Calendar endDate, Client client) {

        this.repairShop = repairShop;
        this.repairShopId = repairShop.getId();
        this.description = description;
        this.approvalType = approvalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.policy = policy;
        this.policyId = policy.getId();
        this.client = client;
        this.occurenceFileList = new LinkedList<>();
        this.occurenceHistoricalList = new LinkedList<>();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public int getRepairShopId() {
        return repairShopId;
    }

    public void setRepairShopId(int repairShopId) {
        this.repairShopId = repairShopId;
    }

    public int getId() {
        return id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
        this.policyId = policy.getId();
    }

    public RepairShop getRepairShop() {
        return repairShop;
    }

    public void setRepairShop(RepairShop repairShop) {
        this.repairShop = repairShop;
        this.repairShopId = repairShop.getId();
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

    public int getInsurerId() {
        return policy.getInsurerId();
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

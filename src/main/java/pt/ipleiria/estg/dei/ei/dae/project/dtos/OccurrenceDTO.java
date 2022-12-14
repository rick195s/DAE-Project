package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;

import java.io.Serializable;
import java.util.Calendar;

public class OccurrenceDTO implements Serializable {
     int id;

     String description;

     ApprovalType approvalType;

     Calendar startDate;

     Calendar endDate;

     int policyId;

     int repairShopId;

    int clientId;

    public OccurrenceDTO(int id, String description, ApprovalType approvalType, Calendar startDate, Calendar endDate, int policyId, int repairShopId, int clientId) {
        this.id = id;
        this.description = description;
        this.approvalType = approvalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.policyId = policyId;
        this.repairShopId = repairShopId;
        this.clientId = clientId;
    }

    public OccurrenceDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}

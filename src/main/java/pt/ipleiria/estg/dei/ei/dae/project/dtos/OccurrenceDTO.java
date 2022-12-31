package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.ApprovalType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OccurrenceDTO implements Serializable {
     int id;

     String description;

     ApprovalType approvalType;

     String startDate;

     String endDate;

     int policyId;

     int repairShopId;

    int clientId;

    public OccurrenceDTO(int id, String description, ApprovalType approvalType, Calendar startDate, Calendar endDate, int policyId, int repairShopId, int clientId) {
        this.id = id;
        this.description = description;
        this.approvalType = approvalType;
        this.policyId = policyId;
        this.repairShopId = repairShopId;
        this.clientId = clientId;

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.startDate = formatter.format(startDate.getTime());

        if (endDate != null) {
            this.endDate = formatter.format(endDate.getTime());
        }
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

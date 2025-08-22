/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;
import java.util.Objects;

/**
 * StockRequest entity for managing medicine restock requests
 * @author shim
 */
public class StockRequest {
    private String requestID;
    private String medicineID;
    private int requestedQuantity;
    private Date requestDate;
    private String status; // PENDING, APPROVED, REJECTED, COMPLETED
    private String medicineName; // For display purposes
    
    public StockRequest() {}
    
    public StockRequest(String requestID, String medicineID, String medicineName, 
                       int requestedQuantity, Date requestDate, String status) {
        this.requestID = requestID;
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.requestedQuantity = requestedQuantity;
        this.requestDate = requestDate;
        this.status = status;
    }
    
    // Getters
    public String getRequestID() {
        return requestID;
    }
    
    public String getMedicineID() {
        return medicineID;
    }
    
    public String getMedicineName() {
        return medicineName;
    }
    
    public int getRequestedQuantity() {
        return requestedQuantity;
    }
    
    public Date getRequestDate() {
        return requestDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    // Setters
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
    
    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }
    
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    
    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
    
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockRequest)) return false;
        StockRequest that = (StockRequest) o;
        return Objects.equals(requestID, that.requestID);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(requestID);
    }
    
    @Override
    public String toString() {
        String dateStr = requestDate == null ? "-" : new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(requestDate);
        return String.format("%-8s | %-8s | %-20s | %8d | %-16s | %-10s",
                requestID, medicineID, medicineName, requestedQuantity, dateStr, status);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Lee Wei Hao
 */
public class QueueEntry {
    
    private static int queueCounter = 1001; // starting queue number
    
    private int queueNumber;
    private String status;
    private Date checkInTime;
    private String patientId;

    public QueueEntry(String patientId) {
        this.queueNumber = queueCounter++;
        this.status = "Waiting"; // default status
        this.checkInTime = new Date(); // current date and time
        this.patientId = patientId;
    }
    
    public int getQueueNumber() {
        return queueNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        String patientName = Control.PatientManagement.getPatientNameById(this.getPatientId());
        return String.format("Queue #%d | Name: %s | Status: %s", queueNumber, patientName, status);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDate;

/**
 *
 * @author ACER
 */
public class DoctorLeave {
    
    private String leaveID;        // Unique ID for this schedule
    private String doctorID;           // Which doctor this schedule belongs to
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String reason; // optional

    public boolean coversDate(LocalDate date) {
        return !date.isBefore(dateFrom) && !date.isAfter(dateTo);
    }

    public DoctorLeave(String leaveID, String doctorID, LocalDate dateFrom, LocalDate dateTo, String reason) {
        this.leaveID = leaveID;
        this.doctorID = doctorID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.reason = reason;
    }

    public String getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(String leaveID) {
        this.leaveID = leaveID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "DoctorLeave{" + "leaveID=" + leaveID + ", doctorID=" + doctorID + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", reason=" + reason + '}';
    }
    
    
}

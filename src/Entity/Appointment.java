/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author leekeezhan
 */
public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentTime;
    private String reason; 

    public Appointment(String appointmentId, String patientId, String doctorId,
                       LocalDateTime appointmentTime, String reason) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    // ✅ 统一格式化方法（避免 null）
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "-";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTime.format(formatter);
    }

    @Override
    public String toString() {
        return appointmentId + " - Patient: " + patientId
               + " - Doctor: " + doctorId
               + " - Time: " + formatDateTime(appointmentTime)
               + " - Reason: " + (reason == null ? "-" : reason);
    }
}
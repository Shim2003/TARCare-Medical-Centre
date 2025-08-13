/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDateTime;
/**
 *
 * @author leekeezhan
 */
public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentTime;
    private String symptoms;

    public Appointment(String appointmentId, String patientId, String doctorId,
                       LocalDateTime appointmentTime, String symptoms) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.symptoms = symptoms;
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

    public String getSymptoms() {
        return symptoms;
    }

    @Override
    public String toString() {
        return appointmentId + " - Patient: " + patientId 
               + " - Doctor: " + doctorId 
               + " - Time: " + appointmentTime 
               + " - Symptoms: " + symptoms;
    }
}

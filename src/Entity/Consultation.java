/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author leekeezhan
 */

import java.time.LocalDateTime;

public class Consultation {
    private String consultationId;
    private String patientId;
    private String doctorId;
    private LocalDateTime consultationDate;
    private String symptoms;
    private String diagnosis;

    public Consultation(String consultationId, String patientId, String doctorId,
                        LocalDateTime consultationDate, String symptoms, String diagnosis) {
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.consultationDate = consultationDate;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
    }

    public String getConsultationId() { return consultationId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getConsultationDate() { return consultationDate; }
    public String getSymptoms() { return symptoms; }
    public String getDiagnosis() { return diagnosis; }

    @Override
    public String toString() {
        return "Consultation ID: " + consultationId +
               "\nPatient ID: " + patientId +
               "\nDoctor ID: " + doctorId +
               "\nDate: " + consultationDate +
               "\nSymptoms: " + symptoms +
               "\nDiagnosis: " + diagnosis + "\n";
    }
}

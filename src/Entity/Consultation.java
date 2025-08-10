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
    private static Consultation currentConsultation;

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
    public static Consultation getCurrentConsultation() { return currentConsultation; }
    
    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setConsultationDate(LocalDateTime consultationDate) {
        this.consultationDate = consultationDate;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public static void setCurrentConsultation(Consultation consultation) {
        currentConsultation = consultation;
    }

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

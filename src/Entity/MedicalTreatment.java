package Entity;

import java.util.Date;

public class MedicalTreatment {
    private String treatmentId;
    private String diagnosisId;
    private String patientId;
    private String doctorId;
    private String treatmentType;
    private String treatmentDescription;
    private final Date treatmentDate;
    private String treatmentStatus;
    private String treatmentDuration;
    private String medicalTreatmentAdvise;

    public MedicalTreatment(String treatmentId, String diagnosisId, String patientId, String doctorId, String treatmentType, 
    String treatmentDescription, Date treatmentDate, String treatmentStatus, String treatmentDuration, String medicalTreatmentAdvise) {
        this.treatmentId = treatmentId;
        this.diagnosisId = diagnosisId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentDescription = treatmentDescription;
        this.treatmentDate = treatmentDate;
        this.treatmentStatus = treatmentStatus;
        this.treatmentType = treatmentType;
        this.treatmentDuration = treatmentDuration;
        this.medicalTreatmentAdvise = medicalTreatmentAdvise;
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public String getTreatmentStatus() {
        return treatmentStatus;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public String getTreatmentDuration() {
        return treatmentDuration;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }
    
    public void setTreatmentStatus(String treatmentStatus) {
        this.treatmentStatus = treatmentStatus;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public void setTreatmentDuration(String treatmentDuration) {
        this.treatmentDuration = treatmentDuration;
    }

    public void setMedicalTreatmentAdvise(String medicalTreatmentAdvise) {
        this.medicalTreatmentAdvise = medicalTreatmentAdvise;
    }

    public String toString() {
        return 
        "Treatment ID: " + treatmentId + 
        ", Diagnosis ID: " + diagnosisId +
        ", Treatment Description: " + treatmentDescription + 
        ", Treatment Date: " + treatmentDate +
        ", Treatment Status: " + treatmentStatus + 
        ", Treatment Type: " + treatmentType + 
        ", Treatment Duration: " + treatmentDuration +
        ", Medical Treatment Advise: " + medicalTreatmentAdvise;
    }
}
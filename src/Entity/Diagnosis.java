package Entity;

import java.util.Date;
import ADT.DynamicList;

public class Diagnosis {
    private String diagnosisId;
    private String patientId;
    private String doctorId;
    private Date diagnosisDate;
    private DynamicList<String> symptoms;
    private String diagnosisDescription;
    private String severityLevel; // Low, Medium, High, Critical
    private String recommendations;
    private String notes;

    public Diagnosis(String diagnosisId, String patientId, String doctorId, Date diagnosisDate, 
                    DynamicList<String> symptoms, String diagnosisDescription, String severityLevel, 
                    String recommendations, String notes) {
        this.diagnosisId = diagnosisId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosisDate = diagnosisDate;
        this.symptoms = symptoms;
        this.diagnosisDescription = diagnosisDescription;
        this.severityLevel = severityLevel;
        this.recommendations = recommendations;
        this.notes = notes;
    }

    // Getters
    public String getDiagnosisId() { return diagnosisId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public Date getDiagnosisDate() { return diagnosisDate; }
    public DynamicList<String> getSymptoms() { return symptoms; }
    public String getDiagnosisDescription() { return diagnosisDescription; }
    public String getSeverityLevel() { return severityLevel; }
    public String getRecommendations() { return recommendations; }
    public String getNotes() { return notes; }

    // Setters
    public void setDiagnosisId(String diagnosisId) { this.diagnosisId = diagnosisId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setDiagnosisDate(Date diagnosisDate) { this.diagnosisDate = diagnosisDate; }
    public void setSymptoms(DynamicList<String> symptoms) { this.symptoms = symptoms; }
    public void setDiagnosisDescription(String diagnosisDescription) { this.diagnosisDescription = diagnosisDescription; }
    public void setSeverityLevel(String severityLevel) { this.severityLevel = severityLevel; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "diagnosisId='" + diagnosisId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", diagnosisDate=" + diagnosisDate +
                ", symptoms='" + symptoms + '\'' +
                ", diagnosisDescription='" + diagnosisDescription + '\'' +
                ", severityLevel='" + severityLevel + '\'' +
                ", recommendations='" + recommendations + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
} 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import ADT.DynamicList;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author User
 */
public class Diagnosis {

    // ID counter
    private static int diagnosisIdCounter = 1001;
    
    private String diagnosisId;
    private String patientId;
    private String doctorId;
    private Date diagnosisDate;
    private DynamicList<String> symptoms;
    private String diagnosisDescription;
    private String severityLevel; // Low, Medium, High, Critical
    private String recommendations;
    private String notes;

    // Constructor
    public Diagnosis() {
        this.diagnosisId = "DIAG" + diagnosisIdCounter++;
    }

    // Constructor for updating existing diagnosis
    public Diagnosis(String diagnosisId, String diagnosisDescription, String severityLevel, 
                    String recommendations, String notes) {
        this.diagnosisId = diagnosisId;
        this.symptoms = symptoms;
        this.diagnosisDescription = diagnosisDescription;
        this.severityLevel = severityLevel;
        this.recommendations = recommendations;
        this.notes = notes;
    }
    
    public Diagnosis(String patientId, String doctorId, Date diagnosisDate, 
                    DynamicList<String> symptoms, String diagnosisDescription, String severityLevel, 
                    String recommendations, String notes) {
        this.diagnosisId = "DIAG" + diagnosisIdCounter++;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Diagnosis{" +
                "diagnosisId='" + diagnosisId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", diagnosisDate=" + (diagnosisDate != null ? sdf.format(diagnosisDate) : "N/A") +
                ", symptoms=" + symptoms +
                ", diagnosisDescription='" + diagnosisDescription + '\'' +
                ", severityLevel='" + severityLevel + '\'' +
                ", recommendations='" + recommendations + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}

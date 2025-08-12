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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        
        sb.append("=================================================================\n");
        sb.append(">                        MEDICAL DIAGNOSIS                      <\n");
        sb.append("=================================================================\n");
        sb.append(String.format("> Diagnosis ID      : %-41s <\n", diagnosisId != null ? diagnosisId : "N/A"));
        sb.append(String.format("> Patient ID        : %-41s <\n", patientId != null ? patientId : "N/A"));
        sb.append(String.format("> Doctor ID         : %-41s <\n", doctorId != null ? doctorId : "N/A"));
        sb.append(String.format("> Diagnosis Date    : %-41s <\n", diagnosisDate != null ? dateFormat.format(diagnosisDate) : "N/A"));
        sb.append("=================================================================\n");
        
        // Severity Level with visual indicator
        String severityDisplay = getSeverityDisplay();
        sb.append(String.format("> Severity Level    : %-41s <\n", severityDisplay));
        
        sb.append("=================================================================\n");
        
        // Symptoms
        sb.append("> Symptoms:                                                     <\n");
        if (symptoms != null && symptoms.size() > 0) {
            for (int i = 0; i < symptoms.size(); i++) {
                String symptom = symptoms.get(i);
                if (symptom.length() > 55) {
                    // Handle long symptoms by wrapping text
                    String[] words = symptom.split(" ");
                    StringBuilder line = new StringBuilder(">   - ");
                    for (String word : words) {
                        if (line.length() + word.length() + 1 > 63) {
                            sb.append(String.format("%-63s <\n", line.toString()));
                            line = new StringBuilder(">     ");
                        }
                        line.append(word).append(" ");
                    }
                    if (line.length() > 6) {
                        sb.append(String.format("%-63s <\n", line.toString().trim()));
                    }
                } else {
                    sb.append(String.format(">   - %-57s <\n", symptom));
                }
            }
        } else {
            sb.append(">   No symptoms recorded                                        <\n");
        }
        
        sb.append("=================================================================\n");
        
        // Diagnosis Description
        sb.append("> Diagnosis:                                                    <\n");
        appendWrappedText(sb, diagnosisDescription != null ? diagnosisDescription : "No diagnosis description provided");
        
        sb.append("=================================================================\n");
        
        // Recommendations
        sb.append("> Recommendations:                                              <\n");
        appendWrappedText(sb, recommendations != null ? recommendations : "No recommendations provided");
        
        // Notes (if available)
        if (notes != null && !notes.trim().isEmpty()) {
            sb.append("=================================================================\n");
            sb.append("> Additional Notes:                                             <\n");
            appendWrappedText(sb, notes);
        }
        
        sb.append("=================================================================");
        // UtilityClass.pressEnterToContinue();
        return sb.toString();
    }
    
    private String getSeverityDisplay() {
        if (severityLevel == null) return "N/A";
        
        switch (severityLevel.toUpperCase()) {
            case "LOW":
                return severityLevel + " !";
            case "MEDIUM":
                return severityLevel + " !!";
            case "HIGH":
                return severityLevel + " !!!";
            case "CRITICAL":
                return severityLevel + " !!!!";
            default:
                return severityLevel;
        }
    }
    
    private void appendWrappedText(StringBuilder sb, String text) {
        if (text == null || text.trim().isEmpty()) {
            sb.append(">   N/A                                                         <\n");
            return;
        }
        
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder(">   ");
        
        for (String word : words) {
            if (line.length() + word.length() + 1 > 63) {
                sb.append(String.format("%-63s <\n", line.toString()));
                line = new StringBuilder(">   ");
            }
            line.append(word).append(" ");
        }
        
        if (line.length() > 4) {
            sb.append(String.format("%-63s <\n", line.toString().trim()));
        }
    }
}

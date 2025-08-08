package Entity;

import java.util.Date;

public class TreatmentHistory {
    private String historyId;
    private String treatmentId;
    private String diagnosisId;
    private String patientId;
    private String doctorId;
    private Date treatmentDate;
    private Date followUpDate;
    private String treatmentOutcome; // Successful, Partial, Failed, Ongoing
    private String status; // Active, Completed, Cancelled
    private String notes;

    public TreatmentHistory(String historyId, String treatmentId, String diagnosisId, String patientId, 
                          String doctorId, Date treatmentDate, Date followUpDate, String treatmentOutcome, 
                          String status, String notes) {
        this.historyId = historyId;
        this.treatmentId = treatmentId;
        this.diagnosisId = diagnosisId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentDate = treatmentDate;
        this.followUpDate = followUpDate;
        this.treatmentOutcome = treatmentOutcome;
        this.status = status;
        this.notes = notes;
    }

    public TreatmentHistory(String historyId, String treatmentId, String diagnosisId, String patientId, 
                          String doctorId, Date treatmentDate, Date followUpDate, String treatmentOutcome, 
                          String status) {
        this.historyId = historyId;
        this.treatmentId = treatmentId;
        this.diagnosisId = diagnosisId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentDate = treatmentDate;
        this.followUpDate = followUpDate;
        this.treatmentOutcome = treatmentOutcome;
        this.status = status;
        this.notes = "";
    }

    // Getters
    public String getHistoryId() { return historyId; }
    public String getTreatmentId() { return treatmentId; }
    public String getDiagnosisId() { return diagnosisId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public Date getTreatmentDate() { return treatmentDate; }
    public Date getFollowUpDate() { return followUpDate; }
    public String getTreatmentOutcome() { return treatmentOutcome; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }

    // Setters
    public void setHistoryId(String historyId) { this.historyId = historyId; }
    public void setTreatmentId(String treatmentId) { this.treatmentId = treatmentId; }
    public void setDiagnosisId(String diagnosisId) { this.diagnosisId = diagnosisId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setTreatmentDate(Date treatmentDate) { this.treatmentDate = treatmentDate; }
    public void setFollowUpDate(Date followUpDate) { this.followUpDate = followUpDate; }
    public void setTreatmentOutcome(String treatmentOutcome) { this.treatmentOutcome = treatmentOutcome; }
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "TreatmentHistory{" +
                "historyId='" + historyId + '\'' +
                ", treatmentId='" + treatmentId + '\'' +
                ", diagnosisId='" + diagnosisId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", treatmentDate=" + treatmentDate +
                ", followUpDate=" + followUpDate +
                ", treatmentOutcome='" + treatmentOutcome + '\'' +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
} 
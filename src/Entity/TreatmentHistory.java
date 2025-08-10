package Entity;

import ADT.DynamicList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TreatmentHistory {

    // ID counter
    private static int historyIdCounter = 1001;

    private String historyId;
    private String treatmentId;
    private String consultationId;
    private String patientId;
    private String doctorId;
    private Date treatmentDate;
    private Date followUpDate;
    private String treatmentOutcome; // Successful, Partial, Failed, Ongoing
    private String status; // Active, Completed, Cancelled
    private String treatmentAdvice; // Advice given to the patient
    private String notes; // Additional notes
    private DynamicList<MedicalTreatmentItem> medicineList;

    public TreatmentHistory(String treatmentId, String consultationId, String patientId, String doctorId,
                            Date treatmentDate, Date followUpDate, String treatmentOutcome, 
                          String status, String treatmentAdvice,String notes, DynamicList<MedicalTreatmentItem> medicineList) {
        this.historyId = "HIST" + historyIdCounter++;
        this.treatmentId = treatmentId;
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentDate = treatmentDate;
        this.followUpDate = followUpDate;
        this.treatmentOutcome = treatmentOutcome;
        this.status = status;
        this.treatmentAdvice = treatmentAdvice;
        this.notes = notes;
        this.medicineList = medicineList;
    }

    public TreatmentHistory(String treatmentId, String consultationId, String patientId, String doctorId,
                          Date treatmentDate, Date followUpDate, String treatmentOutcome, 
                          String status, String treatmentAdvice, DynamicList<MedicalTreatmentItem> medicineList) {
        this.historyId = "HIST" + historyIdCounter++;
        this.treatmentId = treatmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentDate = treatmentDate;
        this.followUpDate = followUpDate;
        this.treatmentOutcome = treatmentOutcome;
        this.status = status;
        this.treatmentAdvice = treatmentAdvice;
        this.notes = "";
        this.medicineList = new DynamicList<>();
    }

    // Getters
    public String getHistoryId() { return historyId; }
    public String getTreatmentId() { return treatmentId; }
    public String getConsultationId() { return consultationId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public Date getTreatmentDate() { return treatmentDate; }
    public Date getFollowUpDate() { return followUpDate; }
    public String getTreatmentOutcome() { return treatmentOutcome; }
    public String getStatus() { return status; }
    public String getTreatmentAdvice() { return treatmentAdvice; }
    public String getNotes() { return notes; }
    public DynamicList<MedicalTreatmentItem> getMedicineList() { return medicineList; }

    // Setters
    public void setHistoryId(String historyId) { this.historyId = historyId; }
    public void setTreatmentId(String treatmentId) { this.treatmentId = treatmentId; }
    public void setConsultationId(String consultationId) { this.consultationId = consultationId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setTreatmentDate(Date treatmentDate) { this.treatmentDate = treatmentDate; }
    public void setFollowUpDate(Date followUpDate) { this.followUpDate = followUpDate; }
    public void setTreatmentOutcome(String treatmentOutcome) { this.treatmentOutcome = treatmentOutcome; }
    public void setStatus(String status) { this.status = status; }
    public void setTreatmentAdvice(String treatmentAdvice) { this.treatmentAdvice = treatmentAdvice; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setMedicineList(DynamicList<MedicalTreatmentItem> medicineList) { this.medicineList = medicineList; }

@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    Scanner scanner = new Scanner(System.in);
    
    sb.append("==================================================================\n");
    sb.append(">                    TREATMENT HISTORY RECORD                    <\n");
    sb.append("==================================================================\n");
    sb.append(String.format("> History ID      : %-44s <\n", historyId));
    sb.append(String.format("> Treatment ID    : %-44s <\n", treatmentId));
    sb.append(String.format("> Consultation ID : %-44s <\n", consultationId));
    sb.append(String.format("> Patient ID      : %-44s <\n", patientId));
    sb.append(String.format("> Doctor ID       : %-44s <\n", doctorId));
    sb.append("==================================================================\n");
    sb.append(String.format("> Treatment Date  : %-44s <\n", 
        treatmentDate != null ? dateFormatter.format(treatmentDate) : "Not specified"));
    sb.append(String.format("> Follow-up Date  : %-44s <\n", 
        followUpDate != null ? dateFormatter.format(followUpDate) : "Not scheduled"));
    sb.append("==================================================================\n");
    sb.append(String.format("> Status          : %-44s <\n", status));
    sb.append(String.format("> Outcome         : %-44s <\n", treatmentOutcome));
    sb.append("==================================================================\n");
    
    // Treatment Advice
    sb.append("> Treatment Advice:                                              <\n");
    if (treatmentAdvice != null && !treatmentAdvice.trim().isEmpty()) {
        String[] adviceLines = wrapText(treatmentAdvice, 60);
        for (String line : adviceLines) {
            sb.append(String.format("> %-62s <\n", line));
        }
    } else {
        sb.append("> No advice provided                                             <\n");
    }
    
    // Notes
    if (notes != null && !notes.trim().isEmpty()) {
        sb.append("==================================================================\n");
        sb.append("> Additional Notes:                                              <\n");
        String[] notesLines = wrapText(notes, 60);
        for (String line : notesLines) {
            sb.append(String.format("> %-62s <\n", line));
        }
    }
    
    // Medicine List
    sb.append("==================================================================\n");
    sb.append("> PRESCRIBED MEDICATIONS:                                        <\n");
    sb.append("==================================================================\n");
    
    if (medicineList != null && !medicineList.isEmpty()) {
        for (int i = 0; i < medicineList.size(); i++) {
            MedicalTreatmentItem medicine = medicineList.get(i);
            sb.append(String.format("> %d. %-59s <\n", (i + 1), medicine.getMedicineName()));
            sb.append(String.format(">    Dosage    : %-48s <\n", medicine.getDosage()));
            sb.append(String.format(">    Frequency : %-48s <\n", medicine.getFrequency()));
            sb.append(String.format(">    Duration  : %-48s <\n", medicine.getDuration()));
            sb.append(String.format(">    Method    : %-48s <\n", medicine.getMethod()));
            if (i < medicineList.size() - 1) {
                sb.append(">                                                                <\n");
            }
        }
    } else {
        sb.append("> No medications prescribed                                      <\n");
    }
    
    sb.append("==================================================================");
    
    return sb.toString();
}
// Helper method to wrap long text into multiple lines
private String[] wrapText(String text, int maxWidth) {
    if (text == null || text.trim().isEmpty()) {
        return new String[]{"No information provided"};
    }
    
    String[] words = text.trim().split("\\s+");
    java.util.List<String> lines = new java.util.ArrayList<>();
    StringBuilder currentLine = new StringBuilder();
    
    for (String word : words) {
        if (currentLine.length() + word.length() + 1 <= maxWidth) {
            if (currentLine.length() > 0) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        } else {
            if (currentLine.length() > 0) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                // Word is too long, truncate it
                lines.add(word.substring(0, Math.min(word.length(), maxWidth)));
            }
        }
    }
    
    if (currentLine.length() > 0) {
        lines.add(currentLine.toString());
    }
    
    return lines.toArray(new String[0]);
}
} 
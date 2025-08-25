package Entity;

import ADT.DynamicList;
import ADT.MyList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalTreatment {

    // ID counter
    private static int treatmentIdCounter = 1001;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    private String diagnosisId;
    private String treatmentId;
    private String patientId;
    private String doctorId;
    private final Date treatmentDate;
    private Date followUpDate;
    private String treatmentStatus; // e.g., "Active", "Completed"
    private String treatmentOutcome; // e.g., "Successful", "Needs Follow-up", "Failed", "Ongoing"
    private String medicalTreatmentAdvise;
    private String notes;
    private MyList<MedicalTreatmentItem> medicineList;

    // Constructor
    public MedicalTreatment() {
        this.treatmentId = "TRMT" + treatmentIdCounter++;
        this.treatmentDate = new Date();
        this.medicineList = new DynamicList<>();
    }

    public MedicalTreatment(String diagnosisId, String patientId, String doctorId, Date treatmentDate,
    Date followUpDate, String treatmentStatus, String treatmentOutcome, String medicalTreatmentAdvise, String notes, 
    MyList<MedicalTreatmentItem> medicineList) {
        this.treatmentId = "TRMT" + treatmentIdCounter++;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentDate = treatmentDate;
        this.followUpDate = followUpDate;
        this.treatmentStatus = treatmentStatus;
        this.treatmentOutcome = treatmentOutcome;
        this.medicalTreatmentAdvise = medicalTreatmentAdvise;
        this.notes = notes;
        this.medicineList = medicineList;
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

    public String getTreatmentStatus() {
        return treatmentStatus;
    }

    public String getTreatmentOutcome() {
        return treatmentOutcome;
    }

    public String getMedicalTreatmentAdvise() {
        return medicalTreatmentAdvise;
    }

    public MyList<MedicalTreatmentItem> getMedicineList() {
        return medicineList;
    }

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public String getNotes() {
        return notes;
    }

    public String getMonth() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM");
        return dateFormatter.format(treatmentDate);
    }
    public String getYear() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
        return dateFormatter.format(treatmentDate);
    }

    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
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

    public void setTreatmentStatus(String treatmentStatus) {
        this.treatmentStatus = treatmentStatus;
    }

    public void setTreatmentOutcome(String treatmentOutcome) {
        this.treatmentOutcome = treatmentOutcome;
    }

    public void setMedicalTreatmentAdvise(String medicalTreatmentAdvise) {
        this.medicalTreatmentAdvise = medicalTreatmentAdvise;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setMedicineList(MyList<MedicalTreatmentItem> medicineList) {
        this.medicineList = medicineList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
            "%-20s %-20s %-20s %-20s %-20s %-15s %-30s\n",
            "Treatment ID: " + treatmentId,
            "Diagnosis ID: " + diagnosisId,
            "Patient ID: " + patientId,
            "Doctor ID: " + doctorId,
            "Treatment Date: " + treatmentDate,
            "Follow Up Date: " + followUpDate,
            "Status: " + treatmentStatus,
            "Outcome: " + treatmentOutcome,
            "Advice: " + medicalTreatmentAdvise,
            "Notes: " + notes
        ));
        sb.append("Medicines:\n");
        if (medicineList != null && medicineList.size() > 0) {
            sb.append(String.format("%-25s %-15s %-20s %-15s %-20s\n", 
                "Medicine Name", "Dosage", "Frequency", "Duration", "Method"));
            for (int i = 0; i < medicineList.size(); i++) {
                MedicalTreatmentItem item = medicineList.get(i);
                sb.append(String.format("%-25s %-15s %-20s %-15s %-20s\n",
                    item.getMedicineName(),
                    item.getDosage(),
                    item.getFrequency(),
                    item.getDuration(),
                    item.getMethod()
                ));
            }
        } else {
            sb.append("None\n");
        }
        return sb.toString();
    }
}
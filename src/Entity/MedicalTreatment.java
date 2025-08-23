package Entity;

import ADT.DynamicList;
import ADT.MyList;
import java.util.Date;

public class MedicalTreatment {

    // ID counter
    private static int treatmentIdCounter = 1001;

    private String treatmentId;
    private String consultationId;
    private String patientId;
    private String doctorId;
    private final Date treatmentDate;
    private String treatmentStatus;
    private String medicalTreatmentAdvise;
    private MyList<MedicalTreatmentItem> medicineList;

    // Constructor
    public MedicalTreatment() {
        this.treatmentId = "TRMT" + treatmentIdCounter++;
        this.treatmentDate = new Date();
        this.medicineList = new DynamicList<>();
    }

    public MedicalTreatment(String consultationId, String patientId,
    String doctorId, Date treatmentDate, String treatmentStatus, String medicalTreatmentAdvise,
    MyList<MedicalTreatmentItem> medicineList) {
        this.treatmentId = "TRMT" + treatmentIdCounter++;
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentDate = treatmentDate;
        this.treatmentStatus = treatmentStatus;
        this.medicalTreatmentAdvise = medicalTreatmentAdvise;
        this.medicineList = medicineList;
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public String getConsultationId() {
        return consultationId;
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

    public String getMedicalTreatmentAdvise() {
        return medicalTreatmentAdvise;
    }

    public MyList<MedicalTreatmentItem> getMedicineList() {
        return medicineList;
    }

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
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

    public void setMedicalTreatmentAdvise(String medicalTreatmentAdvise) {
        this.medicalTreatmentAdvise = medicalTreatmentAdvise;
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
            "Consultation ID: " + consultationId,
            "Patient ID: " + patientId,
            "Doctor ID: " + doctorId,
            "Treatment Date: " + treatmentDate,
            "Status: " + treatmentStatus,
            "Advice: " + medicalTreatmentAdvise
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
package Entity;

import ADT.DynamicList;
import java.util.Date;

public class MedicalTreatment {
    private String treatmentId;
    private String diagnosisId;
    private String patientId;
    private String doctorId;
    private final Date treatmentDate;
    private String treatmentStatus;
    private String medicalTreatmentAdvise;
    private DynamicList<MedicalTreatmentItem> medicineList;

    public MedicalTreatment(String treatmentId, String diagnosisId, String patientId, 
    String doctorId, Date treatmentDate, String treatmentStatus, String medicalTreatmentAdvise,
    DynamicList<MedicalTreatmentItem> medicineList) {
        this.treatmentId = treatmentId;
        this.diagnosisId = diagnosisId;
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

    public String getMedicalTreatmentAdvise() {
        return medicalTreatmentAdvise;
    }

    public DynamicList<MedicalTreatmentItem> getMedicineList() {
        return medicineList;
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

    public void setMedicalTreatmentAdvise(String medicalTreatmentAdvise) {
        this.medicalTreatmentAdvise = medicalTreatmentAdvise;
    }

    public void setMedicineList(DynamicList<MedicalTreatmentItem> medicineList) {
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
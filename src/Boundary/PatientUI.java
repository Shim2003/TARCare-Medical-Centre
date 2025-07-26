package Entity;
public class MedicalTreatment {
    private String treatmentId;
    private String patientId;
    private String doctorId;
    private String treatmentName;
    private String treatmentDescription;
    private String treatmentStatus;
    private String treatmentType;
    private String treatmentDuration;
    private String medicalTreatmentAdvise;

    public MedicalTreatment(String treatmentId, String patientId, String doctorId, String treatmentName, 
    String treatmentDescription, String treatmentStatus, String treatmentType, String treatmentDuration, String medicalTreatmentAdvise) {
        this.treatmentId = treatmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentName = treatmentName;
        this.treatmentDescription = treatmentDescription;
        this.treatmentStatus = treatmentStatus;
        this.treatmentType = treatmentType;
        this.treatmentDuration = treatmentDuration;
        this.medicalTreatmentAdvise = medicalTreatmentAdvise;
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getTreatmentName() {
        return treatmentName;
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

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
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
        return "Treatment ID: " + treatmentId + ", Patient ID: " + patientId + ", Doctor ID: " + doctorId + 
        ", Treatment Name: " + treatmentName + ", Treatment Description: " + treatmentDescription + ", Treatment Status: " + 
        treatmentStatus + ", Treatment Type: " + treatmentType + ", Treatment Duration: " + 
        treatmentDuration + ", Medical Treatment Advise: " + medicalTreatmentAdvise;
    }
}
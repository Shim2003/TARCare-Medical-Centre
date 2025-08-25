/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;
import ADT.DynamicList;
import ADT.MyList;

public class Prescription {

    private String prescriptionID;
    private String patientID;  // Changed from Patient object to String ID
    private MyList<MedicalTreatmentItem> medicineItems;
    private String status; // PENDING, PROCESSING, COMPLETED
    private String doctorId;
    
    // Default constructor
    public Prescription() {
        this.medicineItems = new DynamicList<>();
        this.status = "PENDING";
    }
    
    // Basic constructor - most common use case
    public Prescription(String prescriptionID, String patientID, String doctorId) {
        this.prescriptionID = prescriptionID; // ✅ use passed ID
        this.patientID = patientID;
        this.doctorId = doctorId;
        this.medicineItems = new DynamicList<>();
        this.status = "PENDING";
    }
    
    // Full constructor - allows setting medicine items and status
    public Prescription(String prescriptionID, String patientID, String doctorId, 
                       MyList<MedicalTreatmentItem> medicineItems, String status) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.doctorId = doctorId;
        this.medicineItems = medicineItems != null ? medicineItems : new DynamicList<>();
        this.status = status != null ? status : "PENDING";
    }
    
    // Convenience constructor for creating with initial medicine items
    public Prescription(String prescriptionID, String patientID, String doctorId, 
                       MyList<MedicalTreatmentItem> medicineItems) {
        this(prescriptionID, patientID, doctorId, medicineItems, "PENDING");
    }
    
    // Convenience method to add medicine items
    public void addMedicineItem(String medicineName, String dosage, String frequency, 
                               String duration, String method) {
        MedicalTreatmentItem item = new MedicalTreatmentItem(medicineName, dosage, frequency, 
                                                           duration, method);
        medicineItems.add(item);
    }
    
    // Convenience method to add pre-created medicine item
    public void addMedicineItem(MedicalTreatmentItem item) {
        if (item != null) {
            medicineItems.add(item);
        }
    }
    
    // Getters and Setters
    public String getPrescriptionID() { return prescriptionID; }
    public void setPrescriptionID(String prescriptionID) { this.prescriptionID = prescriptionID; }
    
    // Changed to work with patient ID instead of Patient object
    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }
    
    public MyList<MedicalTreatmentItem> getMedicineItems() { return medicineItems; }
    public void setMedicineItems(MyList<MedicalTreatmentItem> medicineItems) { 
        this.medicineItems = medicineItems != null ? medicineItems : new DynamicList<>(); 
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    
    // Utility methods
    public boolean hasMedicineItems() {
        return medicineItems != null && medicineItems.size() > 0;
    }
    
    public int getMedicineItemCount() {
        return medicineItems != null ? medicineItems.size() : 0;
    }
    
    public boolean isPending() {
        return "PENDING".equalsIgnoreCase(status);
    }
    
    public boolean isCompleted() {
        return "COMPLETED".equalsIgnoreCase(status);
    }
    
    @Override
    public String toString() {
        return String.format("Prescription[ID=%s, PatientID=%s, DoctorID=%s, Status=%s, Items=%d]",
                prescriptionID, patientID, doctorId, status, getMedicineItemCount());
    }
}
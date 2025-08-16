/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;
import ADT.DynamicList;
import ADT.MyList;

public class Prescription {
    private String prescriptionID;
    private Patient patient;
    private MyList<MedicalTreatmentItem> medicineItems;
    private String status; // PENDING, PROCESSING, COMPLETED
    private String doctorId;
    
    public Prescription() {
        this.medicineItems = new DynamicList<>();
        this.status = "PENDING";
    }
    
    public Prescription(String prescriptionID, Patient patient, String doctorId) {
        this.prescriptionID = prescriptionID;
        this.patient = patient;
        this.doctorId = doctorId;
        this.medicineItems = new DynamicList<>();
        this.status = "PENDING";
    }
    
    public void addMedicineItem(String medicineName, String dosage, String frequency, 
                               String duration, String method) {
        MedicalTreatmentItem item = new MedicalTreatmentItem(medicineName, dosage, frequency, 
                                                           duration, method);
        medicineItems.add(item);
    }
    
    // Getters and Setters
    public String getPrescriptionID() { return prescriptionID; }
    public void setPrescriptionID(String prescriptionID) { this.prescriptionID = prescriptionID; }
    
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public MyList<MedicalTreatmentItem> getMedicineItems() { return medicineItems; }
    public void setMedicineItems(MyList<MedicalTreatmentItem> medicineItems) { this.medicineItems = medicineItems; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import Entity.Medicine;
import Entity.MedicalTreatmentItem;
import Entity.Prescription;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

public class PharmacyService {
    private final MyList<Medicine> medicines;
    private final MyList<Prescription> prescriptionQueue;
    private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    public PharmacyService() {
        this.medicines = new DynamicList<>();
        this.prescriptionQueue = new DynamicList<>();
    }
    
    // Medicine management methods
    public boolean addMedicine(Medicine m) {
        if (findById(m.getMedicineID()) != null) {
            return false; // duplicate id
        }
        medicines.add(m);
        return true;
    }
    
    public boolean updateMedicine(String id, Medicine newData) {
        int idx = indexOfId(id);
        if (idx == -1) return false;
        medicines.remove(idx);
        medicines.add(idx, newData);
        return true;
    }
    
    public boolean deleteMedicine(String id) {
        int idx = indexOfId(id);
        if (idx == -1) return false;
        medicines.remove(idx);
        return true;
    }
    
    public Medicine findById(String id) {
        return medicines.findFirst(m -> m.getMedicineID().equalsIgnoreCase(id));
    }
    
    public Medicine findByName(String name) {
        return medicines.findFirst(m -> m.getMedicineName().equalsIgnoreCase(name));
    }
    
    public MyList<Medicine> getAll() {
        return medicines;
    }
    
    public Date parseDate(String s) throws ParseException {
        return df.parse(s);
    }
    
    private int indexOfId(String id) {
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getMedicineID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }
    
    // Prescription queue management
    public void addToQueue(Prescription prescription) {
        prescriptionQueue.add(prescription);
    }
    
    public Prescription getNextInQueue() {
        if (prescriptionQueue.size() > 0) {
            return prescriptionQueue.get(0);
        }
        return null;
    }
    
    public Prescription getQueueAt(int index) {
        if (index >= 0 && index < prescriptionQueue.size()) {
            return prescriptionQueue.get(index);
        }
        return null;
    }
    
    public int getQueueSize() {
        return prescriptionQueue.size();
    }
    
    // Updated process prescription method using calculated quantities
    public boolean processPrescription() {
        if (prescriptionQueue.size() == 0) {
            return false;
        }
        
        Prescription prescription = prescriptionQueue.get(0);
        
        // Check if all medicines are available in required quantities
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            int quantityNeeded = item.calculateQuantityNeeded();
            
            if (medicine == null || medicine.getQuantity() < quantityNeeded) {
                return false; // Cannot process due to insufficient stock
            }
        }
        
        // Distribute medicines and update stock
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            int quantityNeeded = item.calculateQuantityNeeded();
            
            // Update stock using calculated quantity
            int newQuantity = medicine.getQuantity() - quantityNeeded;
            medicine.setQuantity(newQuantity);
        }
        
        // Mark prescription as completed and remove from queue
        prescription.setStatus("COMPLETED");
        prescriptionQueue.remove(0);
        
        return true;
    }
    
    // Method to check stock availability for a prescription
    public boolean checkStockAvailability(Prescription prescription) {
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            int quantityNeeded = item.calculateQuantityNeeded();
            
            if (medicine == null || medicine.getQuantity() < quantityNeeded) {
                return false;
            }
        }
        return true;
    }
    
    // Method to get total cost for a prescription
    public double calculatePrescriptionCost(Prescription prescription) {
        double totalCost = 0.0;
        
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            
            if (medicine != null) {
                int quantityNeeded = item.calculateQuantityNeeded();
                totalCost += medicine.getPrice() * quantityNeeded;
            }
        }
        
        return totalCost;
    }
    
    // Search functionality
    public Medicine findFirst(Predicate<Medicine> predicate) {
        return medicines.findFirst(predicate);
    }
}
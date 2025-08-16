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
import java.util.Comparator;
import java.util.function.Predicate;

public class PharmacyService {
    private final MyList<Medicine> medicines;
    private final MyList<Prescription> prescriptionQueue;
    private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    public PharmacyService() {
        this.medicines = new DynamicList<>();
        this.prescriptionQueue = new DynamicList<>();
    }
    
    // ===== BASIC MEDICINE MANAGEMENT METHODS =====
    public boolean addMedicine(Medicine m) {
        if (findById(m.getMedicineID()) != null) {
            return false; // duplicate id
        }
        medicines.add(m);
        return true;
    }
    
    public boolean updateMedicine(String id, Medicine newData) {
        int idx = medicines.findIndex(m -> m.getMedicineID().equalsIgnoreCase(id));
        if (idx == -1) return false;
        medicines.replace(idx, newData);
        return true;
    }
    
    public boolean deleteMedicine(String id) {
        return medicines.removeIf(m -> m.getMedicineID().equalsIgnoreCase(id));
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
    
    // ===== PRESCRIPTION PROCESSING =====
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
    
    // UPDATED: Use ADT's anyMatch method for cleaner code
    public boolean checkStockAvailability(Prescription prescription) {
        return !prescription.getMedicineItems().anyMatch(item -> {
            Medicine medicine = findByName(item.getMedicineName());
            int quantityNeeded = item.calculateQuantityNeeded();
            return medicine == null || medicine.getQuantity() < quantityNeeded;
        });
    }
    
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
    
    // Medicine inventory statistics
    public void printMedicineUsageStats() {
        if (medicines.isEmpty()) {
            System.out.println("No medicines in inventory.");
            return;
        }
        
        // Cast to DynamicList to access getStatistics method
        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;
        var stats = medicineList.getStatistics(Medicine::getQuantity);
        
        System.out.println("=== Medicine Inventory Statistics ===");
        System.out.println("Total medicines: " + stats.count);
        System.out.printf("Average quantity: %.2f%n", stats.average);
        System.out.println("Minimum stock: " + (int)stats.min);
        System.out.println("Maximum stock: " + (int)stats.max);
        System.out.printf("Standard deviation: %.2f%n", stats.standardDeviation);
        System.out.printf("Total inventory value: $%.2f%n", calculateTotalInventoryValue());
        System.out.println("==========================================");
    }
    
    // Sort medicines by name
    public void sortMedicinesByName() {
        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;
        medicineList.quickSort(Comparator.comparing(Medicine::getMedicineName));
        System.out.println("Medicines sorted by name.");
    }
    
    // Sort medicines by quantity (useful for stock management)
    public void sortMedicinesByQuantity() {
        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;
        medicineList.quickSort(Comparator.comparing(Medicine::getQuantity));
        System.out.println("Medicines sorted by quantity (ascending).");
    }
    
    // Sort medicines by price
    public void sortMedicinesByPrice() {
        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;
        medicineList.quickSort(Comparator.comparing(Medicine::getPrice));
        System.out.println("Medicines sorted by price (ascending).");
    }
    
    // Find medicines with low stock
    public MyList<Medicine> getLowStockMedicines(int threshold) {
        return medicines.findAll(m -> m.getQuantity() <= threshold);
    }
    
    // Get medicines by price range
    public MyList<Medicine> getMedicinesByPriceRange(double minPrice, double maxPrice) {
        return medicines.findAll(m -> m.getPrice() >= minPrice && m.getPrice() <= maxPrice);
    }
    
    // Get medicines by name pattern (contains search)
    public MyList<Medicine> searchMedicinesByName(String namePattern) {
        return medicines.findAll(m -> m.getMedicineName().toLowerCase()
                                    .contains(namePattern.toLowerCase()));
    }
    
    // Calculate total inventory value
    public double calculateTotalInventoryValue() {
        double total = 0.0;
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            total += med.getPrice() * med.getQuantity();
        }
        return total;
    }
    
    // Print low stock alert
    public void printLowStockAlert(int threshold) {
        MyList<Medicine> lowStock = getLowStockMedicines(threshold);
        if (lowStock.isEmpty()) {
            System.out.println("✓ All medicines are well-stocked!");
        } else {
            System.out.println("⚠️  === LOW STOCK ALERT ===");
            System.out.println("Medicines with stock <= " + threshold + ":");
            for (int i = 0; i < lowStock.size(); i++) {
                Medicine med = lowStock.get(i);
                System.out.printf("- %s (ID: %s): %d units remaining%n", 
                    med.getMedicineName(), med.getMedicineID(), med.getQuantity());
            }
            System.out.println("==========================");
        }
    }
    
    // Get medicines that are out of stock
    public MyList<Medicine> getOutOfStockMedicines() {
        return medicines.findAll(m -> m.getQuantity() == 0);
    }
    
    // Print detailed inventory report
    public void printDetailedInventoryReport() {
        if (medicines.isEmpty()) {
            System.out.println("No medicines in inventory.");
            return;
        }
        
        System.out.println("=== DETAILED INVENTORY REPORT ===");
        System.out.printf("%-15s %-25s %-10s %-10s %-15s%n", 
                         "ID", "Name", "Quantity", "Price", "Total Value");
        System.out.println("-".repeat(75));
        
        double grandTotal = 0.0;
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            double totalValue = med.getPrice() * med.getQuantity();
            grandTotal += totalValue;
            
            System.out.printf("%-15s %-25s %-10d $%-9.2f $%-14.2f%n",
                             med.getMedicineID(),
                             med.getMedicineName(),
                             med.getQuantity(),
                             med.getPrice(),
                             totalValue);
        }
        
        System.out.println("-".repeat(75));
        System.out.printf("Total Inventory Value: $%.2f%n", grandTotal);
        System.out.println("=================================");
    }
    
    // Check if medicine exists by name (case-insensitive)
    public boolean medicineExists(String medicineName) {
        return medicines.anyMatch(m -> m.getMedicineName().equalsIgnoreCase(medicineName));
    }
    
    // Get medicine count
    public int getMedicineCount() {
        return medicines.size();
    }
    
    // Clear all prescriptions from queue
    public void clearPrescriptionQueue() {
        prescriptionQueue.clear();
        System.out.println("Prescription queue cleared.");
    }
    
    // Get all pending prescriptions
    public MyList<Prescription> getPendingPrescriptions() {
        return prescriptionQueue.findAll(p -> "PENDING".equals(p.getStatus()));
    }
    
    // ===== SEARCH FUNCTIONALITY =====
    public Medicine findFirst(Predicate<Medicine> predicate) {
        return medicines.findFirst(predicate);
    }
    
    public MyList<Medicine> findAll(Predicate<Medicine> predicate) {
        return medicines.findAll(predicate);
    }
}
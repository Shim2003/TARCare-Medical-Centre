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
import Entity.StockRequest;
import Utility.UtilityClass;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * 
 * @author jecsh
 */
public class PharmacyManagement {
    private static MyList<Medicine> medicines;
    private static MyList<Prescription> prescriptionQueue;
    private static MyList<StockRequest> stockRequests;
    SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
    private int requestCounter = 1;
    
    public PharmacyManagement() {
        this.medicines = new DynamicList<>();
        this.prescriptionQueue = new DynamicList<>();
        this.stockRequests = new DynamicList<>();
    }
    
    // ===== BASIC MEDICINE MANAGEMENT METHODS =====
    public static boolean addMedicine(Medicine m) {
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
    
    public static Medicine findById(String id) {
        return medicines.findFirst(m -> m.getMedicineID().equalsIgnoreCase(id));
    }
    
    public Medicine findByName(String name) {
        return medicines.findFirst(m -> m.getMedicineName().equalsIgnoreCase(name));
    }
    
    public MyList<Medicine> getAll() {
        return medicines;
    }
    
    /**
     * Get detailed header with dosage information
     */
    public String getDetailedHeader() {
        return String.format("%-8s | %-20s | %5s | %-12s | %8s | %-15s | %s",
                "ID", "Name", "Qty", "Category", "Price", "Manufacturer", "Expiry");
    }
    
    // ===== EXISTING METHODS (keeping all original functionality) =====
    
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
            int quantityNeeded = PrescriptionCalculator.calculateQuantityNeeded(item);
            
            if (medicine == null || medicine.getQuantity() < quantityNeeded) {
                return false; // Cannot process due to insufficient stock
            }
        }
        
        // Distribute medicines and update stock
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            int quantityNeeded = PrescriptionCalculator.calculateQuantityNeeded(item); // FIX: Use static call
            
            // Update stock using calculated quantity
            int newQuantity = medicine.getQuantity() - quantityNeeded;
            medicine.setQuantity(newQuantity);
        }
        
        // Mark prescription as completed and remove from queue
        prescription.setStatus("COMPLETED");
        prescriptionQueue.remove(0);
        
        return true;
    }
    
    public boolean checkStockAvailability(Prescription prescription) {
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            int quantityNeeded = PrescriptionCalculator.calculateQuantityNeeded(item); // FIX: Use static call
            
            if (medicine == null || medicine.getQuantity() < quantityNeeded) {
                return false;
            }
        }
        return true;
    }
    
    public double calculatePrescriptionCost(Prescription prescription) {
        double totalCost = 0.0;
        
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            
            if (medicine != null) {
                int quantityNeeded = PrescriptionCalculator.calculateQuantityNeeded(item); // FIX: Use static call
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
        UtilityClass.quickSort(medicineList, Comparator.comparing(Medicine::getMedicineName));
        System.out.println("Medicines sorted by name.");
    }
    
    // Sort medicines by quantity (useful for stock management)
    public void sortMedicinesByQuantity() {
        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;
        UtilityClass.quickSort(medicineList, Comparator.comparing(Medicine::getQuantity));
        System.out.println("Medicines sorted by quantity (ascending).");
    }
    
    // Sort medicines by price
    public void sortMedicinesByPrice() {
        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;
        UtilityClass.quickSort(medicineList, Comparator.comparing(Medicine::getPrice));
        System.out.println("Medicines sorted by price (ascending).");
    }
    
    // Sort medicines by dosage form
    public void sortMedicinesByDosageForm() {
        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;
        UtilityClass.quickSort(medicineList, Comparator.comparing(Medicine::getDosageForm));
        System.out.println("Medicines sorted by dosage form.");
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
                System.out.printf("- %s (ID: %s): %d %s remaining%n", 
                    med.getMedicineName(), med.getMedicineID(), 
                    med.getQuantity(), med.getDosageForm());
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
        System.out.printf("%-15s %-25s %-10s %-20s %-10s %-15s%n", 
                         "ID", "Name", "Quantity", "Dosage", "Price", "Total Value");
        System.out.println("-".repeat(95));
        
        double grandTotal = 0.0;
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            double totalValue = med.getPrice() * med.getQuantity();
            grandTotal += totalValue;
            
            System.out.printf("%-15s %-25s %-10d %-20s $%-9.2f $%-14.2f%n",
                             med.getMedicineID(),
                             med.getMedicineName(),
                             med.getQuantity(),
                             med.getCompleteDosage(),
                             med.getPrice(),
                             totalValue);
        }
        
        System.out.println("-".repeat(95));
        System.out.printf("Total Inventory Value: $%.2f%n", grandTotal);
        System.out.println("=================================");
    }
    
    public String createStockRequest(String medicineID, int requestedQuantity) {
        Medicine medicine = findById(medicineID);
        if (medicine == null) {
            return null; // Medicine not found
        }

        String requestID = generateRequestID();
        StockRequest request = new StockRequest(
            requestID,
            medicineID,
            medicine.getMedicineName(),
            requestedQuantity,
            new Date(),
            "PENDING"
        );

        stockRequests.add(request);
        return requestID;
    }
    
    private String generateRequestID() {
        return "REQ" + String.format("%03d", requestCounter++);
    }
    
    public MyList<StockRequest> getAllStockRequests() {
        return stockRequests;
    }
    
    public MyList<StockRequest> getPendingStockRequests() {
        return stockRequests.findAll(r -> "PENDING".equals(r.getStatus()));
    }
    
    public MyList<StockRequest> getCompletedStockRequests() {
        return stockRequests.findAll(r -> "COMPLETED".equals(r.getStatus()) || 
                                         "APPROVED".equals(r.getStatus()) || 
                                         "REJECTED".equals(r.getStatus()));
    }
    
    public StockRequest findStockRequestById(String requestID) {
        return stockRequests.findFirst(r -> r.getRequestID().equalsIgnoreCase(requestID));
    }
    
    public boolean updateStockRequestStatus(String requestID, String newStatus) {
        int index = stockRequests.findIndex(r -> r.getRequestID().equalsIgnoreCase(requestID));
        if (index == -1) return false;

        StockRequest request = stockRequests.get(index);
        request.setStatus(newStatus);
        return true;
    }
    
    public boolean approveStockRequest(String requestID) {
        StockRequest request = findStockRequestById(requestID);
        if (request == null || !"PENDING".equals(request.getStatus())) {
            return false;
        }

        Medicine medicine = findById(request.getMedicineID());
        if (medicine == null) return false;

        // Update medicine stock
        medicine.setQuantity(medicine.getQuantity() + request.getRequestedQuantity());

        // Update request status
        request.setStatus("COMPLETED");

        return true;
    }
    
    public boolean hasPendingRequestForMedicine(String medicineID) {
        return stockRequests.anyMatch(r -> r.getMedicineID().equalsIgnoreCase(medicineID) && 
                                          "PENDING".equals(r.getStatus()));
    }

    public int getTotalPendingQuantityForMedicine(String medicineID) {
        int total = 0;
        for (int i = 0; i < stockRequests.size(); i++) {
            StockRequest request = stockRequests.get(i);
            if (request.getMedicineID().equalsIgnoreCase(medicineID) && 
                "PENDING".equals(request.getStatus())) {
                total += request.getRequestedQuantity();
            }
        }
        return total;
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
    
    public boolean isMedicineExpired(Medicine medicine) {
        if (medicine == null || medicine.getExpiryDate() == null) {
            return false;
        }
        return medicine.getExpiryDate().before(new Date());
    }
    
    public boolean isMedicineNearExpiry(Medicine medicine, int days) {
        if (medicine == null || medicine.getExpiryDate() == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        long expiryTime = medicine.getExpiryDate().getTime();
        long daysInMillis = days * 24L * 60L * 60L * 1000L;
        
        return (expiryTime - currentTime) <= daysInMillis && expiryTime > currentTime;
    }
    
    public MyList<Medicine> getExpiredMedicines() {
        return medicines.findAll(medicine -> isMedicineExpired(medicine));
    }
    
    public MyList<Medicine> getMedicinesExpiringInMonths(int months) {
        int days = UtilityClass.convertMonthsToDays(months);
        return getMedicinesNearExpiry(days);
    }
    
    public MyList<Medicine> getMedicinesNearExpiry(int days) {
        Date futureDate = UtilityClass.addDaysToDate(new Date(), days);
        return medicines.filter(m -> m.getExpiryDate().before(futureDate) && 
                                m.getExpiryDate().after(new Date()));
    }
    
    public int removeExpiredMedicines() {
        MyList<Medicine> expiredMedicines = getExpiredMedicines();
        int removedCount = 0;
        
        for (int i = 0; i < expiredMedicines.size(); i++) {
            Medicine expired = expiredMedicines.get(i);
            if (deleteMedicine(expired.getMedicineID())) {
                removedCount++;
            }
        }
        
        return removedCount;
    }
    
    public void printExpiryAlert(int daysAhead) {
        MyList<Medicine> expired = getExpiredMedicines();
        MyList<Medicine> nearExpiry = getMedicinesNearExpiry(daysAhead);
        
        if (!expired.isEmpty()) {
            System.out.println("❌ === EXPIRED MEDICINES ===");
            for (int i = 0; i < expired.size(); i++) {
                Medicine med = expired.get(i);
                System.out.printf("- %s (ID: %s): Expired on %s%n", 
                    med.getMedicineName(), 
                    med.getMedicineID(),
                    sdf.format(med.getExpiryDate()));
            }
            System.out.println("============================");
        }
        
        if (!nearExpiry.isEmpty()) {
            System.out.println("⚠️  === MEDICINES EXPIRING WITHIN " + daysAhead + " DAYS ===");
            for (int i = 0; i < nearExpiry.size(); i++) {
                Medicine med = nearExpiry.get(i);
                long daysUntilExpiry = (med.getExpiryDate().getTime() - System.currentTimeMillis()) 
                                     / (24 * 60 * 60 * 1000);
                System.out.printf("- %s (ID: %s): Expires in %d days (%s)%n", 
                    med.getMedicineName(), 
                    med.getMedicineID(),
                    daysUntilExpiry,
                    sdf.format(med.getExpiryDate()));
            }
            System.out.println("================================================");
        }
        
        if (expired.isEmpty() && nearExpiry.isEmpty()) {
            System.out.println("✅ No expired or near-expiry medicines found!");
        }
    }
    
    public double calculatePrescriptionCostForUI(Prescription prescription) {
        return calculatePrescriptionCost(prescription);
    }

    public MyList<Medicine> getLowStockMedicines() {
        return getLowStockMedicines(UtilityClass.LOW_STOCK_THRESHOLD);
    }

    public MyList<Medicine> searchMedicinesByPattern(String pattern) {
        return medicines.filter(m -> 
            m.getMedicineName().toLowerCase().contains(pattern.toLowerCase()));
    }

    public MyList<Medicine> filterByPriceRange(double minPrice, double maxPrice) {
        return medicines.filter(m -> 
            m.getPrice() >= minPrice && m.getPrice() <= maxPrice);
    }

    public MyList<Medicine> filterByMultipleCriteria(String namePattern, 
        String category, String manufacturer, Double minPrice, Double maxPrice) {
        return medicines.filter(m -> {
            boolean matches = true;
            if (!namePattern.isEmpty()) {
                matches = matches && m.getMedicineName().toLowerCase()
                    .contains(namePattern.toLowerCase());
            }
            if (!category.isEmpty()) {
                matches = matches && m.getCategory().equalsIgnoreCase(category);
            }
            // ... rest of criteria logic
            return matches;
        });
    }

    public MyList<Medicine> getMedicinesSortedByName() {
        MyList<Medicine> sorted = medicines.clone();
        sorted.quickSort(java.util.Comparator.comparing(Medicine::getMedicineName));
        return sorted;
    }

    public MyList<Medicine> getMedicinesSortedByQuantity() {
        MyList<Medicine> sorted = medicines.clone();
        sorted.quickSort(java.util.Comparator.comparing(Medicine::getQuantity));
        return sorted;
    }

    public MyList<Medicine> getMedicinesSortedByPrice(boolean descending) {
        MyList<Medicine> sorted = medicines.clone();
        if (descending) {
            sorted.quickSort(java.util.Comparator.comparing(Medicine::getPrice).reversed());
        } else {
            sorted.quickSort(java.util.Comparator.comparing(Medicine::getPrice));
        }
        return sorted;
    }

    public MyList<Medicine> getAvailableMedicinesForRequest() {
        MyList<Medicine> lowStock = getLowStockMedicines();
        return lowStock.filter(med -> 
            !hasPendingRequestForMedicine(med.getMedicineID()));
    }
}
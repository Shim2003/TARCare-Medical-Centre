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
import java.util.Date;
/**
 * 
 * @author Shim
 */
public class PharmacyManagement {
    private static MyList<Medicine> medicines = new DynamicList<>();
    public static MyList<Prescription> prescriptionQueue = new DynamicList<>();
    private static MyList<StockRequest> stockRequests = new DynamicList<>();
    private int requestCounter = 1;
    
    public PharmacyManagement() {    }
    
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
    
    public static void addToQueue(Prescription prescription) {
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
            int quantityNeeded = UtilityClass.calculateQuantityNeeded(item);
            
            if (medicine == null || medicine.getQuantity() < quantityNeeded) {
                return false; // Cannot process due to insufficient stock
            }
        }
        
        // Distribute medicines and update stock
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            int quantityNeeded = UtilityClass.calculateQuantityNeeded(item); // FIX: Use static call
            
            // Update stock using calculated quantity
            int newQuantity = medicine.getQuantity() - quantityNeeded;
            medicine.setQuantity(newQuantity);
        }
        
        // Mark prescription as completed and remove from queue
        prescription.setStatus("COMPLETED");
        prescriptionQueue.remove(0);
        
        return true;
    }
    
    public double calculatePrescriptionCost(Prescription prescription) {
        double totalCost = 0.0;
        
        for (int i = 0; i < prescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = prescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            
            if (medicine != null) {
                int quantityNeeded = UtilityClass.calculateQuantityNeeded(item); // FIX: Use static call
                totalCost += medicine.getPrice() * quantityNeeded;
            }
        }
        
        return totalCost;
    }
    
    // Find medicines with low stock
    public MyList<Medicine> getLowStockMedicines(int threshold) {
        return medicines.findAll(m -> m.getQuantity() <= threshold);
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
    
    // Get medicines that are out of stock
    public MyList<Medicine> getOutOfStockMedicines() {
        return medicines.findAll(m -> m.getQuantity() == 0);
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
    
    public static MyList<StockRequest> getAllStockRequests() {
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
    
    // Get medicine count
    public int getMedicineCount() {
        return medicines.size();
    }
    
    // ===== SEARCH FUNCTIONALITY =====
    public boolean isMedicineExpired(Medicine medicine) {
        if (medicine == null || medicine.getExpiryDate() == null) {
            return false;
        }
        return medicine.getExpiryDate().before(new Date());
    }

    public MyList<Medicine> getExpiredMedicines() {
        return medicines.findAll(medicine -> isMedicineExpired(medicine));
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
    
    public double calculatePrescriptionCostForUI(Prescription prescription) {
        return calculatePrescriptionCost(prescription);
    }

    public MyList<Medicine> searchMedicinesByPattern(String pattern) {
        return medicines.filter(m -> 
            m.getMedicineName().toLowerCase().contains(pattern.toLowerCase()));
    }

    public MyList<Medicine> filterByPriceRange(double minPrice, double maxPrice) {
        return medicines.filter(m -> 
            m.getPrice() >= minPrice && m.getPrice() <= maxPrice);
    }
    
    public MyList<Medicine> filterByCategory(String category) {
        return medicines.filter(m
                -> m.getCategory().equalsIgnoreCase(category));
    }

    public MyList<Medicine> filterByDosageForm(String dosageForm) {
        return medicines.filter(m
                -> m.getDosageForm().equalsIgnoreCase(dosageForm));
    }

    public MyList<Medicine> filterByManufacturer(String manufacturer) {
        return medicines.filter(m
                -> m.getManufacturer().equalsIgnoreCase(manufacturer));
    }

    public MyList<Medicine> filterByMultipleCriteria(
            String namePattern,
            String category,
            String manufacturer,
            Double minPrice,
            Double maxPrice) {

        MyList<Medicine> results = medicines;

        if (namePattern != null && !namePattern.trim().isEmpty()) {
            results = searchMedicinesByPattern(namePattern);
        }
        if (category != null && !category.trim().isEmpty()) {
            results = filterByCategory(category);
        }
        if (manufacturer != null && !manufacturer.trim().isEmpty()) {
            results = filterByManufacturer(manufacturer);
        }
        if (minPrice != null || maxPrice != null) {
            double min = (minPrice == null) ? Double.MIN_VALUE : minPrice;
            double max = (maxPrice == null) ? Double.MAX_VALUE : maxPrice;
            results = filterByPriceRange(min, max);
        }

        return results;
    }
    
    public static String generateNewPrescriptionId() {
        if (prescriptionQueue.isEmpty()) {
            return "RX001";
        } else {
            String lastId = prescriptionQueue.get(prescriptionQueue.size() - 1).getPrescriptionID();
            int numericPart = Integer.parseInt(lastId.substring(2));
            numericPart++;
            return String.format("RX%03d", numericPart);
        }
    }

    public MyList<Medicine> createInventorySnapshot() { return medicines.clone(); }
    
    public double calculateSnapshotTotalValue(MyList<Medicine> snapshot) {
        double totalValue = 0;
        for (int i = 0; i < snapshot.size(); i++) {
            Medicine med = snapshot.get(i);
            totalValue += med.getPrice() * med.getQuantity();
        }
        return totalValue;
    }

    public double calculateSnapshotAverageStock(MyList<Medicine> snapshot) {
        if (snapshot.isEmpty()) return 0;

        var stats = medicines.getStatistics(Medicine::getQuantity);
        return stats.average;
    }
}
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
import java.text.SimpleDateFormat;

/**
 *
 *
 * @author Shim
 */
public class PharmacyManagement {
    private static MyList<Medicine> medicines = new DynamicList<>();
    private static MyList<Prescription> prescriptionQueue = new DynamicList<>();
    private static MyList<StockRequest> stockRequests = new DynamicList<>();
    private int requestCounter = 1;
    
    public PharmacyManagement() { }
    
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
    
    // ===== UI-SPECIFIC BUSINESS LOGIC METHODS =====
    
    /**
     * Add medicine with validation and user feedback
     */
    public String addMedicineWithValidation(Medicine medicine) {
        if (addMedicine(medicine)) {
            return "Medicine added successfully!";
        } else {
            return "Medicine ID already exists!";
        }
    }
    
    /**
     * Update medicine with validation and user feedback
     */
    public String updateMedicineWithValidation(String id, Medicine updated) {
        if (updateMedicine(id, updated)) {
            return "Updated successfully!";
        } else {
            return "Failed to update.";
        }
    }
    
    /**
     * Delete medicine with validation and user feedback
     */
    public String deleteMedicineWithValidation(String id) {
        if (deleteMedicine(id)) {
            return "Medicine deleted successfully.";
        } else {
            return "Failed to delete medicine.";
        }
    }
    
    /**
     * Get all medicines for display purposes
     */
    public MyList<Medicine> getAllMedicinesForDisplay() {
        return medicines;
    }
    
    /**
     * Get formatted medicine inventory display for UI
     */
    public String getMedicineInventoryDisplay() {
        if (medicines.size() == 0) {
            return "No medicines in inventory.";
        }

        StringBuilder display = new StringBuilder();
        
        // Add header
        display.append("ID   | Name                           | Quantity(Form)   | Category             | Price (RM)     | Manufacturer           | Expiry\n");
        display.append("=".repeat(135)).append("\n");
        
        // Add medicine data
        for (int i = 0; i < medicines.size(); i++) {
            Medicine medicine = medicines.get(i);
            display.append(formatMedicineDisplay(medicine)).append("\n");
        }
        
        return display.toString();
    }
    
    // ===== PRESCRIPTION QUEUE MANAGEMENT =====
    
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
    
    /**
     * Get prescription queue display for UI
     */
    public String getPrescriptionQueueDisplay() {
        if (getQueueSize() == 0) {
            return "No prescriptions in queue.";
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < getQueueSize(); i++) {
            Prescription p = getQueueAt(i);
            display.append("=== Queue Position ").append(i + 1).append(" ===\n");
            display.append("Prescription ID: ").append(p.getPrescriptionID()).append("\n");
            display.append("Patient ID: ").append(p.getPatientID()).append("\n");
            display.append("Doctor ID: ").append(p.getDoctorId()).append("\n");
            display.append("Status: ").append(p.getStatus()).append("\n");
            display.append("Medicine Items:\n");
            
            for (int j = 0; j < p.getMedicineItems().size(); j++) {
                MedicalTreatmentItem item = p.getMedicineItems().get(j);
                Medicine medicine = findByName(item.getMedicineName());
                String dosageForm = medicine != null ? medicine.getDosageForm() : "unit";

                display.append("  - ").append(item.getMedicineName())
                       .append(" | Dosage: ").append(UtilityClass.getCompleteDosageDescription(item, dosageForm))
                       .append(" | Frequency: ").append(item.getFrequency())
                       .append(" | Duration: ").append(item.getDuration())
                       .append(" | Method: ").append(item.getMethod())
                       .append(" | Calculated Quantity: ").append(UtilityClass.calculateQuantityNeeded(item))
                       .append("\n");
            }
            display.append("\n");
        }
        return display.toString();
    }
    
    // ===== PRESCRIPTION PROCESSING =====
    
    /**
     * Information holder for prescription processing
     */
    public static class PrescriptionProcessingInfo {
        private String patientId;
        private String prescriptionId;
        private String medicineAvailabilityDisplay;
        private double totalCost;
        private boolean canProcess;
        private MyList<String> insufficientMedicines;
        
        public PrescriptionProcessingInfo(String patientId, String prescriptionId, 
                String medicineAvailabilityDisplay, double totalCost, boolean canProcess,
                MyList<String> insufficientMedicines) {
            this.patientId = patientId;
            this.prescriptionId = prescriptionId;
            this.medicineAvailabilityDisplay = medicineAvailabilityDisplay;
            this.totalCost = totalCost;
            this.canProcess = canProcess;
            this.insufficientMedicines = insufficientMedicines;
        }
        
        // Getters
        public String getPatientId() { return patientId; }
        public String getPrescriptionId() { return prescriptionId; }
        public String getMedicineAvailabilityDisplay() { return medicineAvailabilityDisplay; }
        public double getTotalCost() { return totalCost; }
        public boolean canProcess() { return canProcess; }
        public boolean hasInsufficientMedicines() { return insufficientMedicines.size() > 0; }
        public MyList<String> getInsufficientMedicines() { return insufficientMedicines; }
    }
    
    /**
     * Prepare prescription processing information
     */
    public PrescriptionProcessingInfo preparePrescriptionProcessing() {
        if (getQueueSize() == 0) {
            return null;
        }

        Prescription nextPrescription = getNextInQueue();
        String patientId = nextPrescription.getPatientID();
        String prescriptionId = nextPrescription.getPrescriptionID();
        
        boolean canProcess = true;
        double totalCost = 0.0;
        MyList<String> insufficientMedicines = new DynamicList<>();
        StringBuilder availabilityDisplay = new StringBuilder();

        // Check stock availability using calculated quantities
        for (int i = 0; i < nextPrescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = nextPrescription.getMedicineItems().get(i);
            Medicine medicine = findByName(item.getMedicineName());
            int quantityNeeded = UtilityClass.calculateQuantityNeeded(item);

            if (medicine == null) {
                availabilityDisplay.append("[X] ").append(item.getMedicineName()).append(" - MEDICINE NOT FOUND\n");
                canProcess = false;
            } else if (medicine.getQuantity() < quantityNeeded) {
                availabilityDisplay.append("[X] ").append(item.getMedicineName())
                        .append(" - INSUFFICIENT STOCK (Available: ").append(medicine.getQuantity())
                        .append(", Calculated Need: ").append(quantityNeeded).append(")\n");
                insufficientMedicines.add(medicine.getMedicineID() + ":" + medicine.getMedicineName());
                canProcess = false;
            } else {
                double itemCost = medicine.getPrice() * quantityNeeded;
                totalCost += itemCost;
                availabilityDisplay.append("[OK] ").append(item.getMedicineName())
                        .append(" - Available (Calculated Need: ").append(quantityNeeded)
                        .append(", Cost: $").append(String.format("%.2f", itemCost)).append(")\n");
                availabilityDisplay.append("     Prescription: ").append(item.getDosage()).append(", ")
                        .append(item.getFrequency()).append(" for ").append(item.getDuration()).append("\n");
                availabilityDisplay.append("     Medicine Form: ").append(medicine.getCompleteDosage()).append("\n");
            }
        }

        return new PrescriptionProcessingInfo(patientId, prescriptionId, 
                availabilityDisplay.toString(), totalCost, canProcess, insufficientMedicines);
    }
    
    /**
     * Process prescription with user feedback
     */
    public String processPrescriptionWithFeedback() {
        // Distribute medicines and update stock
        boolean success = processPrescription();

        if (success) {
            Prescription lastProcessed = prescriptionQueue.size() > 0 ? 
                prescriptionQueue.get(prescriptionQueue.size() - 1) : null;
            double totalCost = lastProcessed != null ? calculatePrescriptionCost(lastProcessed) : 0.0;
            
            StringBuilder result = new StringBuilder();
            result.append("[SUCCESS] Prescription processed successfully!\n");
            result.append("[$] Total amount charged: $").append(String.format("%.2f", totalCost)).append("\n");
            result.append("[INFO] Medicine stock has been updated based on calculated quantities.\n");
            
            if (lastProcessed != null) {
                result.append("[PATIENT] Patient ").append(lastProcessed.getPatientID())
                      .append(" can collect medicines.");
            }
            
            return result.toString();
        } else {
            return "[ERROR] Failed to process prescription.";
        }
    }
    
    /**
     * Create restock requests for insufficient medicines
     */
    public String createRestockRequestsForInsufficientMedicines(PrescriptionProcessingInfo processingInfo) {
        StringBuilder result = new StringBuilder();
        MyList<String> insufficientMedicines = processingInfo.getInsufficientMedicines();
        
        for (int i = 0; i < insufficientMedicines.size(); i++) {
            String[] parts = insufficientMedicines.get(i).split(":");
            String medicineID = parts[0];
            String medicineName = parts[1];

            result.append("\nCreating restock request for: ").append(medicineName);
            // For automatic creation, use a default quantity (could be configurable)
            String requestID = createStockRequest(medicineID, UtilityClass.DEFAULT_RESTOCK_QUANTITY);
            if (requestID != null) {
                result.append("\nRequest created: ").append(requestID);
            } else {
                result.append("\nFailed to create request for ").append(medicineName);
            }
        }
        
        return result.toString();
    }
    
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
            int quantityNeeded = UtilityClass.calculateQuantityNeeded(item);
            
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
                int quantityNeeded = UtilityClass.calculateQuantityNeeded(item);
                totalCost += medicine.getPrice() * quantityNeeded;
            }
        }
        
        return totalCost;
    }
    
    // ===== LOW STOCK ALERT MANAGEMENT =====
    
    /**
     * Information holder for low stock alerts
     */
    public static class LowStockAlertInfo {
        private MyList<Medicine> outOfStockMedicines;
        private MyList<Medicine> lowStockMedicines;
        private MyList<Medicine> availableForRequest;
        private String outOfStockDisplay;
        private String lowStockDisplay;
        private String requestInfo;
        private String availableForRequestDisplay;
        
        public LowStockAlertInfo(MyList<Medicine> outOfStock, MyList<Medicine> lowStock,
                MyList<Medicine> availableForRequest, String outOfStockDisplay,
                String lowStockDisplay, String requestInfo, String availableForRequestDisplay) {
            this.outOfStockMedicines = outOfStock;
            this.lowStockMedicines = lowStock;
            this.availableForRequest = availableForRequest;
            this.outOfStockDisplay = outOfStockDisplay;
            this.lowStockDisplay = lowStockDisplay;
            this.requestInfo = requestInfo;
            this.availableForRequestDisplay = availableForRequestDisplay;
        }
        
        public boolean hasNoAlerts() { 
            return lowStockMedicines.size() == 0; 
        }
        
        public boolean hasOutOfStockMedicines() { 
            return outOfStockMedicines.size() > 0; 
        }
        
        public boolean hasLowStockMedicines() { 
            return lowStockMedicines.size() > 0; 
        }
        
        public boolean hasRequestInfo() { 
            return requestInfo != null && !requestInfo.isEmpty(); 
        }
        
        public String getOutOfStockDisplay() { return outOfStockDisplay; }
        public String getLowStockDisplay() { return lowStockDisplay; }
        public String getRequestInfo() { return requestInfo; }
        public String getAvailableForRequestDisplay() { return availableForRequestDisplay; }
        public int getAvailableForRequestCount() { return availableForRequest.size(); }
        public MyList<Medicine> getAvailableForRequest() { return availableForRequest; }
    }
    
    /**
     * Get low stock alert information for UI
     */
    public LowStockAlertInfo getLowStockAlertInfo() {
        MyList<Medicine> lowStockMedicines = getLowStockMedicines(UtilityClass.LOW_STOCK_THRESHOLD);
        MyList<Medicine> outOfStockMedicines = getOutOfStockMedicines();

        if (lowStockMedicines.size() == 0) {
            return new LowStockAlertInfo(outOfStockMedicines, lowStockMedicines, 
                    new DynamicList<>(), "", "", null, "");
        }

        // Build display strings
        StringBuilder outOfStockDisplay = new StringBuilder();
        StringBuilder lowStockDisplay = new StringBuilder();
        
        // Process out of stock medicines
        for (int i = 0; i < outOfStockMedicines.size(); i++) {
            Medicine medicine = outOfStockMedicines.get(i);
            String statusLabel = getStockStatusLabel(medicine);
            String formattedMedicine = formatMedicineDisplay(medicine);
            outOfStockDisplay.append(formattedMedicine).append(" ").append(statusLabel).append("\n");
        }

        // Process low stock medicines
        for (int i = 0; i < lowStockMedicines.size(); i++) {
            Medicine medicine = lowStockMedicines.get(i);
            String statusLabel = getStockStatusLabel(medicine);
            String formattedMedicine = formatMedicineDisplay(medicine);
            lowStockDisplay.append(formattedMedicine).append(" ").append(statusLabel).append("\n");
        }

        // Find medicines available for request
        MyList<Medicine> availableForRequest = new DynamicList<>();
        for (int i = 0; i < outOfStockMedicines.size(); i++) {
            Medicine med = outOfStockMedicines.get(i);
            if (!hasPendingRequestForMedicine(med.getMedicineID())) {
                availableForRequest.add(med);
            }
        }
        for (int i = 0; i < lowStockMedicines.size(); i++) {
            Medicine med = lowStockMedicines.get(i);
            if (!hasPendingRequestForMedicine(med.getMedicineID())) {
                availableForRequest.add(med);
            }
        }

        String requestInfo = null;
        if (availableForRequest.size() == 0) {
            requestInfo = "\n[INFO] All low/out-of-stock medicines already have pending restock requests.";
        }

        // Build available for request display
        StringBuilder availableDisplay = new StringBuilder();
        for (int i = 0; i < availableForRequest.size(); i++) {
            Medicine med = availableForRequest.get(i);
            availableDisplay.append((i + 1)).append(". ").append(med.getMedicineName())
                    .append(" (ID: ").append(med.getMedicineID()).append(") - Current: ")
                    .append(med.getQuantity()).append("\n");
        }

        return new LowStockAlertInfo(outOfStockMedicines, lowStockMedicines, availableForRequest,
                outOfStockDisplay.toString(), lowStockDisplay.toString(), requestInfo,
                availableDisplay.toString());
    }
    
    private String getStockStatusLabel(Medicine medicine) {
        if (hasPendingRequestForMedicine(medicine.getMedicineID())) {
            int pendingQty = getTotalPendingQuantityForMedicine(medicine.getMedicineID());
            return "[REQUESTED: " + pendingQty + " units]";
        } else if (medicine.getQuantity() == 0) {
            return "[OUT OF STOCK]";
        } else {
            return "[LOW STOCK]";
        }
    }
    
    public String formatMedicineDisplay(Medicine medicine) {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        String quantityForm = medicine.getQuantity() + "(" + medicine.getDosageForm() + ")";
        return String.format("%-4s | %-30s | %-16s | %-20s | %-14.2f | %-22s | %s",
            medicine.getMedicineID(),
            medicine.getMedicineName(),
            quantityForm,
            medicine.getCategory(),
            medicine.getPrice(),
            medicine.getManufacturer(),
            sdf.format(medicine.getExpiryDate())
        );
    }
    
    /**
     * Create restock request by choice index
     */
    public String createRestockRequestByChoice(LowStockAlertInfo alertInfo, int choiceIndex) {
        MyList<Medicine> availableForRequest = alertInfo.getAvailableForRequest();
        if (choiceIndex >= 0 && choiceIndex < availableForRequest.size()) {
            Medicine selectedMedicine = availableForRequest.get(choiceIndex);
            
            // For UI-triggered requests, use a reasonable default quantity
            int defaultQuantity = Math.max(UtilityClass.LOW_STOCK_THRESHOLD * 2, 50);
            String requestID = createStockRequest(selectedMedicine.getMedicineID(), defaultQuantity);
            
            if (requestID != null) {
                return "Stock request created successfully!\n" +
                       "Request ID: " + requestID + "\n" +
                       "Medicine: " + selectedMedicine.getMedicineName() + "\n" +
                       "Requested quantity: " + defaultQuantity + " " + selectedMedicine.getDosageForm() + "\n" +
                       "Status: PENDING";
            } else {
                return "Failed to create stock request.";
            }
        }
        return "Invalid choice.";
    }
    
    // Find medicines with low stock
    public MyList<Medicine> getLowStockMedicines(int threshold) {
        return medicines.findAll(m -> m.getQuantity() <= threshold);
    }
    
    // Get medicines that are out of stock
    public MyList<Medicine> getOutOfStockMedicines() {
        return medicines.findAll(m -> m.getQuantity() == 0);
    }
    
    // ===== STOCK REQUEST MANAGEMENT =====
    
    /**
     * Information holder for stock request creation
     */
    public static class StockRequestCreationInfo {
        private MyList<Medicine> availableMedicines;
        private String medicineListDisplay;
        
        public StockRequestCreationInfo(MyList<Medicine> availableMedicines, String medicineListDisplay) {
            this.availableMedicines = availableMedicines;
            this.medicineListDisplay = medicineListDisplay;
        }
        
        public boolean hasNoMedicines() { return availableMedicines.size() == 0; }
        public String getMedicineListDisplay() { return medicineListDisplay; }
    }
    
    /**
     * Get stock request creation information for UI
     */
    public StockRequestCreationInfo getStockRequestCreationInfo() {
        if (medicines.size() == 0) {
            return new StockRequestCreationInfo(medicines, "");
        }

        StringBuilder display = new StringBuilder();
        
        display.append("Available medicines:\n");
        display.append("ID   | Name                           | Quantity(Form)   | Category             | Price (RM)     | Manufacturer           | Expiry\n");
        display.append("=".repeat(135)).append("\n");
        
        for (int i = 0; i < medicines.size(); i++) {
            Medicine medicine = medicines.get(i);
            display.append(formatMedicineDisplay(medicine)).append("\n");
        }

        return new StockRequestCreationInfo(medicines, display.toString());
    }
    
    /**
     * Information holder for stock request preparation
     */
    public static class StockRequestInfo {
        private Medicine medicine;
        private String displayInfo;
        
        public StockRequestInfo(Medicine medicine, String displayInfo) {
            this.medicine = medicine;
            this.displayInfo = displayInfo;
        }
        
        public Medicine getMedicine() { return medicine; }
        public String getDisplayInfo() { return displayInfo; }
    }
    
    /**
     * Prepare stock request information
     */
    public StockRequestInfo prepareStockRequestInfo(String medicineID) {
        Medicine medicine = findById(medicineID);
        if (medicine == null) {
            return null;
        }

        StringBuilder displayInfo = new StringBuilder();
        displayInfo.append("\nSelected medicine: ").append(medicine.getMedicineName()).append("\n");
        displayInfo.append("Current stock: ").append(medicine.getQuantity()).append(" ")
                   .append(medicine.getDosageForm()).append("\n");

        // Check if there are pending requests
        if (hasPendingRequestForMedicine(medicineID)) {
            int pendingQty = getTotalPendingQuantityForMedicine(medicineID);
            displayInfo.append("!!! Warning: There are pending requests for ")
                      .append(pendingQty).append(" units of this medicine.\n");
        }

        return new StockRequestInfo(medicine, displayInfo.toString());
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
    
    /**
     * Create stock request with validation and feedback
     */
    public String createStockRequestWithValidation(String medicineID, int requestedQuantity) {
        if (requestedQuantity <= 0) {
            return "Invalid quantity. Must be greater than 0.";
        }

        Medicine medicine = findById(medicineID);
        if (medicine == null) {
            return "Medicine not found.";
        }

        String requestID = createStockRequest(medicineID, requestedQuantity);
        if (requestID != null) {
            return "Stock request created successfully!\n" +
                   "Request ID: " + requestID + "\n" +
                   "Medicine: " + medicine.getMedicineName() + "\n" +
                   "Requested quantity: " + requestedQuantity + " " + medicine.getDosageForm() + "\n" +
                   "Status: PENDING";
        } else {
            return "Failed to create stock request.";
        }
    }
    
    private String generateRequestID() {
        String candidateId;
        boolean isUnique;

        do {
            candidateId = "REQ" + String.format("%03d", requestCounter++);
            isUnique = true;

            for (int i = 0; i < stockRequests.size(); i++) {
                if (stockRequests.get(i).getRequestID().equals(candidateId)) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);

        return candidateId;
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
    
    /**
     * Get pending stock requests display
     */
    public String getPendingStockRequestsDisplay() {
        MyList<StockRequest> pendingRequests = getPendingStockRequests();

        if (pendingRequests.size() == 0) {
            return "No pending stock requests.";
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < pendingRequests.size(); i++) {
            StockRequest request = pendingRequests.get(i);
            display.append(request.toString()).append("\n");
        }
        
        return display.toString();
    }
    
    /**
     * Get stock request history display
     */
    public String getStockRequestHistoryDisplay() {
        MyList<StockRequest> completedRequests = getCompletedStockRequests();

        if (completedRequests.size() == 0) {
            return "No completed stock requests.";
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < completedRequests.size(); i++) {
            StockRequest request = completedRequests.get(i);
            display.append(request.toString()).append("\n");
        }
        
        return display.toString();
    }
    
    /**
     * Information holder for stock request processing
     */
    public static class StockRequestProcessingInfo {
        private StockRequest request;
        private String detailsDisplay;
        private boolean isPending;
        private String status;
        
        public StockRequestProcessingInfo(StockRequest request, String detailsDisplay, 
                boolean isPending, String status) {
            this.request = request;
            this.detailsDisplay = detailsDisplay;
            this.isPending = isPending;
            this.status = status;
        }
        
        public StockRequest getRequest() { return request; }
        public String getDetailsDisplay() { return detailsDisplay; }
        public boolean isPending() { return isPending; }
        public String getStatus() { return status; }
    }
    
    /**
     * Prepare stock request processing information
     */
    public StockRequestProcessingInfo prepareStockRequestProcessing(String requestID) {
        StockRequest request = findStockRequestById(requestID);

        if (request == null) {
            return null;
        }

        boolean isPending = "PENDING".equals(request.getStatus());
        
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATETIME_FORMAT);
        StringBuilder details = new StringBuilder();
        details.append("\n--- Request Details ---\n");
        details.append("Request ID: ").append(request.getRequestID()).append("\n");
        details.append("Medicine: ").append(request.getMedicineName())
               .append(" (ID: ").append(request.getMedicineID()).append(")\n");
        details.append("Requested quantity: ").append(request.getRequestedQuantity()).append("\n");
        details.append("Request date: ").append(sdf.format(request.getRequestDate())).append("\n");

        return new StockRequestProcessingInfo(request, details.toString(), 
                isPending, request.getStatus());
    }
    
    /**
     * Process stock request action with feedback
     */
    public String processStockRequestAction(String requestID, int action) {
        switch (action) {
            case 1:
                if (approveStockRequest(requestID)) {
                    StockRequest request = findStockRequestById(requestID);
                    Medicine medicine = findById(request.getMedicineID());
                    StringBuilder result = new StringBuilder();
                    result.append("Request approved and stock updated!");
                    if (medicine != null) {
                        result.append("\nNew stock level: ").append(medicine.getQuantity())
                              .append(" ").append(medicine.getDosageForm());
                    }
                    return result.toString();
                } else {
                    return "Failed to approve request.";
                }
            case 2:
                if (updateStockRequestStatus(requestID, "REJECTED")) {
                    return "Request rejected.";
                } else {
                    return "Failed to reject request.";
                }
            case 0:
                return "Operation cancelled.";
            default:
                return "Invalid choice.";
        }
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
    
    // ===== ADVANCED SEARCH FUNCTIONALITY =====
    
    /**
     * Information holder for search results
     */
    public static class SearchResultsInfo {
        private MyList<Medicine> results;
        private String description;
        private String resultsDisplay;
        
        public SearchResultsInfo(MyList<Medicine> results, String description, String resultsDisplay) {
            this.results = results;
            this.description = description;
            this.resultsDisplay = resultsDisplay;
        }
        
        public boolean isEmpty() { return results.size() == 0; }
        public int getCount() { return results.size(); }
        public String getDescription() { return description; }
        public String getResultsDisplay() { return resultsDisplay; }
        public MyList<Medicine> getResults() { return results; }
    }
    
    /**
     * Get search results information for UI display
     */
    public SearchResultsInfo getSearchResultsInfo(MyList<Medicine> results, String description) {
        StringBuilder display = new StringBuilder();
        display.append("\n--- Search Results ---\n");
        display.append("Found ").append(results.size()).append(" ").append(description).append("\n");

        if (results.size() == 0) {
            display.append("No medicines found matching the criteria.\n");
        } else {
            display.append("ID   | Name                           | Quantity(Form)   | Category             | Price (RM)     | Manufacturer           | Expiry\n");
            display.append("=".repeat(135)).append("\n");
            for (int i = 0; i < results.size(); i++) {
                display.append(formatMedicineDisplay(results.get(i))).append("\n");
            }
        }

        return new SearchResultsInfo(results, description, display.toString());
    }
    
    /**
     * Get available categories display for UI
     */
    public String getAvailableCategoriesDisplay() {
        DynamicList<String> categories = new DynamicList<>();
        for (int i = 0; i < medicines.size(); i++) {
            String category = medicines.get(i).getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < categories.size(); i++) {
            display.append((i + 1)).append(". ").append(categories.get(i)).append("\n");
        }
        return display.toString();
    }
    
    /**
     * Get available manufacturers display for UI
     */
    public String getAvailableManufacturersDisplay() {
        DynamicList<String> manufacturers = new DynamicList<>();
        for (int i = 0; i < medicines.size(); i++) {
            String manufacturer = medicines.get(i).getManufacturer();
            if (!manufacturers.contains(manufacturer)) {
                manufacturers.add(manufacturer);
            }
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < manufacturers.size(); i++) {
            display.append((i + 1)).append(". ").append(manufacturers.get(i)).append("\n");
        }
        return display.toString();
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
        return medicines.filter(m ->
                m.getCategory().equalsIgnoreCase(category));
    }

    public MyList<Medicine> filterByDosageForm(String dosageForm) {
        return medicines.filter(m ->
                m.getDosageForm().equalsIgnoreCase(dosageForm));
    }

    public MyList<Medicine> filterByManufacturer(String manufacturer) {
        return medicines.filter(m ->
                m.getManufacturer().equalsIgnoreCase(manufacturer));
    }

    public MyList<Medicine> filterByMultipleCriteria(
            String namePattern,
            String category,
            String manufacturer,
            Double minPrice,
            Double maxPrice) {

        // Start with all medicines and apply filters cumulatively
        MyList<Medicine> results = medicines;

        // Apply name pattern filter
        if (namePattern != null && !namePattern.trim().isEmpty()) {
            results = results.filter(m ->
                    m.getMedicineName().toLowerCase().contains(namePattern.toLowerCase()));
        }

        // Apply category filter
        if (category != null && !category.trim().isEmpty()) {
            results = results.filter(m ->
                    m.getCategory().equalsIgnoreCase(category));
        }

        // Apply manufacturer filter
        if (manufacturer != null && !manufacturer.trim().isEmpty()) {
            results = results.filter(m ->
                    m.getManufacturer().equalsIgnoreCase(manufacturer));
        }

        // Apply price range filter
        if (minPrice != null || maxPrice != null) {
            double min = (minPrice == null) ? Double.MIN_VALUE : minPrice;
            double max = (maxPrice == null) ? Double.MAX_VALUE : maxPrice;
            results = results.filter(m ->
                    m.getPrice() >= min && m.getPrice() <= max);
        }

        return results;
    }
    
    public MyList<Medicine> getMedicinesNearExpiry(int months) {
        Date currentDate = new Date();
        Date futureDate = UtilityClass.addMonthsToDate(currentDate, months);

        return medicines.filter(m -> {
            if (m.getExpiryDate() == null) {
                return false;
            }

            // Medicine expires after now AND before/on the future date
            return m.getExpiryDate().after(currentDate)
                    && m.getExpiryDate().before(futureDate);
        });
    }
    
    // ===== STATISTICS & REPORTS =====
    
    public int getMedicineCount() {
        return medicines.size();
    }
    
    public double calculateTotalInventoryValue() {
        double total = 0.0;
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            total += med.getPrice() * med.getQuantity();
        }
        return total;
    }
    
    /**
     * Get inventory statistics display for UI
     */
    public String getInventoryStatisticsDisplay() {
        StringBuilder display = new StringBuilder();
        display.append("\n").append("=".repeat(60)).append("\n");
        display.append("                    INVENTORY STATISTICS\n");
        display.append("=".repeat(60)).append("\n");

        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;

        if (medicineList.isEmpty()) {
            display.append("│ No medicines in inventory.                           │\n");
            display.append("=".repeat(60)).append("\n");
            return display.toString();
        }

        var quantityStats = medicineList.getStatistics(Medicine::getQuantity);
        var priceStats = medicineList.getStatistics(m -> m.getPrice());

        // Combined Statistics Table
        display.append("+").append("-".repeat(58)).append("+\n");
        display.append("|                    INVENTORY OVERVIEW                    |\n");
        display.append("+").append("-".repeat(58)).append("+\n");
        display.append(String.format("| Total medicines: %-39d |%n", getMedicineCount()));
        display.append(String.format("| Total inventory value: RM %-30.2f |%n", calculateTotalInventoryValue()));
        display.append("+").append("-".repeat(58)).append("+\n");
        display.append("|                  QUANTITY STATISTICS                     |\n");
        display.append("+").append("-".repeat(58)).append("+\n");
        display.append(String.format("| Average quantity(units): %-30.2f  |%n", quantityStats.average));
        display.append(String.format("| Minimum stock(units): %-33d  |%n", (int) quantityStats.min));
        display.append(String.format("| Maximum stock(units): %-33d  |%n", (int) quantityStats.max));
        display.append(String.format("| Standard deviation: %-36.2f |%n", quantityStats.standardDeviation));
        display.append("+").append("-".repeat(58)).append("+\n");
        display.append("|                   PRICE STATISTICS                       |\n");
        display.append("+").append("-".repeat(58)).append("+\n");
        display.append(String.format("| Average price: RM %-38.2f |%n", priceStats.average));
        display.append(String.format("| Lowest price: RM %-39.2f |%n", priceStats.min));
        display.append(String.format("| Highest price: RM %-38.2f |%n", priceStats.max));
        display.append(String.format("| Price standard deviation: RM %-27.2f |%n", priceStats.standardDeviation));
        display.append("+").append("-".repeat(58)).append("+\n");
        display.append("=".repeat(60)).append("\n");

        return display.toString();
    }
    
    /**
     * Get inventory summary display for UI
     */
    public String getInventorySummaryDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        StringBuilder display = new StringBuilder();

        display.append("\n").append("=".repeat(70)).append("\n");
        display.append("                        INVENTORY SUMMARY\n");
        display.append("=".repeat(70)).append("\n");

        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) medicines;

        if (medicineList.isEmpty()) {
            display.append("| No medicines in inventory.                                 |\n");
            display.append("=".repeat(70)).append("\n");
            return display.toString();
        }

        // Create clones for different sorting
        MyList<Medicine> byQuantity = medicineList.clone();
        UtilityClass.quickSort(byQuantity, java.util.Comparator.comparing(Medicine::getQuantity));

        MyList<Medicine> byPrice = medicineList.clone();
        UtilityClass.quickSort(byPrice, java.util.Comparator.comparing(Medicine::getPrice).reversed());

        // Use existing methods
        MyList<Medicine> expired = getExpiredMedicines();
        MyList<Medicine> nearExpiry = getMedicinesNearExpiry(6); // 6 months

        // Combined Summary Table
        display.append("+").append("-".repeat(68)).append("+\n");
        display.append("|                         LOWEST STOCK                               |\n");
        display.append("+").append("-".repeat(68)).append("+\n");
        for (int i = 0; i < Math.min(3, byQuantity.size()); i++) {
            Medicine med = byQuantity.get(i);
            String line = String.format("| %d. %-35s: %3d %-22s |",
                    (i + 1),
                    med.getMedicineName(),
                    med.getQuantity(),
                    med.getDosageForm());
            display.append(line).append("\n");
        }
        display.append("+").append("-".repeat(68)).append("+\n");
        display.append("|                   HIGHEST VALUE MEDICINES                          |\n");
        display.append("+").append("-".repeat(68)).append("+\n");
        for (int i = 0; i < Math.min(3, byPrice.size()); i++) {
            Medicine med = byPrice.get(i);
            String line = String.format("| %d. %-35s: RM %23.2f |",
                    (i + 1),
                    med.getMedicineName(),
                    med.getPrice());
            display.append(line).append("\n");
        }
        display.append("+").append("-".repeat(68)).append("+\n");
        display.append(String.format("|        NEAR EXPIRY (within 6 months): %2d medicines                 |%n", nearExpiry.size()));
        display.append("+").append("-".repeat(68)).append("+\n");
        if (nearExpiry.size() > 0) {
            for (int i = 0; i < Math.min(3, nearExpiry.size()); i++) {
                Medicine med = nearExpiry.get(i);
                String line = String.format("| %d. %-34s - expires %18s |",
                        (i + 1),
                        med.getMedicineName(),
                        sdf.format(med.getExpiryDate()));
                display.append(line).append("\n");
            }
            if (nearExpiry.size() > 3) {
                display.append(String.format("|     ... and %2d more medicines                              |%n", (nearExpiry.size() - 3)));
            }
        } else {
            display.append("|                    No medicines near expiry                 |\n");
        }
        display.append("+").append("-".repeat(68)).append("+\n");
        display.append(String.format("|             EXPIRED MEDICINES: %2d medicines                        |%n", expired.size()));
        display.append("+").append("-".repeat(68)).append("+\n");
        if (expired.size() > 0) {
            for (int i = 0; i < Math.min(3, expired.size()); i++) {
                Medicine med = expired.get(i);
                String line = String.format("| %d. %-34s - expired %18s |",
                        (i + 1),
                        med.getMedicineName(),
                        sdf.format(med.getExpiryDate()));
                display.append(line).append("\n");
            }
            if (expired.size() > 3) {
                display.append(String.format("|     ... and %2d more medicines                              |%n", (expired.size() - 3)));
            }
        } else {
            display.append("|                     No expired medicines                           |\n");
        }
        display.append("+").append("-".repeat(68)).append("+\n");
        display.append("=".repeat(70)).append("\n");

        return display.toString();
    }
    
    // ===== BULK OPERATIONS =====
    
    /**
     * Information holder for inventory snapshot
     */
    public static class SnapshotInfo {
        private MyList<Medicine> snapshot;
        private String summaryDisplay;
        private String fullDisplay;
        
        public SnapshotInfo(MyList<Medicine> snapshot, String summaryDisplay, String fullDisplay) {
            this.snapshot = snapshot;
            this.summaryDisplay = summaryDisplay;
            this.fullDisplay = fullDisplay;
        }
        
        public boolean isEmpty() { return snapshot.isEmpty(); }
        public String getSummaryDisplay() { return summaryDisplay; }
        public String getFullDisplay() { return fullDisplay; }
        public MyList<Medicine> getSnapshot() { return snapshot; }
    }
    
    /**
     * Create inventory snapshot with information for UI
     */
    public SnapshotInfo createInventorySnapshotWithInfo() {
        MyList<Medicine> snapshot = medicines.clone();

        if (snapshot.isEmpty()) {
            return new SnapshotInfo(snapshot, "", "");
        }

        double totalValue = calculateSnapshotTotalValue(snapshot);
        double averageStock = calculateSnapshotAverageStock(snapshot);

        StringBuilder summaryDisplay = new StringBuilder();
        summaryDisplay.append("Inventory snapshot created with ").append(snapshot.size()).append(" medicines.\n");
        summaryDisplay.append("This snapshot can be used for backup or comparison purposes.\n");
        summaryDisplay.append("\n=== SNAPSHOT SUMMARY ===\n");
        summaryDisplay.append("Total medicines: ").append(snapshot.size()).append("\n");
        summaryDisplay.append("Total inventory value: RM ").append(String.format("%.2f", totalValue)).append("\n");
        summaryDisplay.append(String.format("Average stock per medicine: %.2f units%n", averageStock));

        StringBuilder fullDisplay = new StringBuilder();
        fullDisplay.append("ID   | Name                           | Quantity(Form)   | Category             | Price (RM)     | Manufacturer           | Expiry\n");
        fullDisplay.append("=".repeat(135)).append("\n");
        for (int i = 0; i < snapshot.size(); i++) {
            Medicine medicine = snapshot.get(i);
            fullDisplay.append(formatMedicineDisplay(medicine)).append("\n");
        }

        return new SnapshotInfo(snapshot, summaryDisplay.toString(), fullDisplay.toString());
    }
    
    /**
     * Information holder for expired medicines
     */
    public static class ExpiredMedicinesInfo {
        private MyList<Medicine> expiredMedicines;
        private String displayInfo;
        
        public ExpiredMedicinesInfo(MyList<Medicine> expiredMedicines, String displayInfo) {
            this.expiredMedicines = expiredMedicines;
            this.displayInfo = displayInfo;
        }
        
        public boolean isEmpty() { return expiredMedicines.isEmpty(); }
        public String getDisplayInfo() { return displayInfo; }
        public MyList<Medicine> getExpiredMedicines() { return expiredMedicines; }
    }
    
    /**
     * Get expired medicines information for UI
     */
    public ExpiredMedicinesInfo getExpiredMedicinesInfo() {
        MyList<Medicine> expired = getExpiredMedicines();

        if (expired.isEmpty()) {
            return new ExpiredMedicinesInfo(expired, "");
        }

        StringBuilder displayInfo = new StringBuilder();
        displayInfo.append("Found ").append(expired.size()).append(" expired medicines:\n");
        displayInfo.append("ID   | Name                           | Quantity(Form)   | Category             | Price (RM)     | Manufacturer           | Expiry\n");
        displayInfo.append("=".repeat(135)).append("\n");
        for (int i = 0; i < expired.size(); i++) {
            displayInfo.append(formatMedicineDisplay(expired.get(i))).append(" [EXPIRED]\n");
        }

        return new ExpiredMedicinesInfo(expired, displayInfo.toString());
    }
    
    /**
     * Remove all expired medicines with feedback
     */
    public String removeAllExpiredMedicines() {
        int removedCount = removeExpiredMedicines();
        return removedCount + " expired medicines removed from inventory.";
    }
    
    public boolean isMedicineExpired(Medicine medicine) {
        if (medicine == null || medicine.getExpiryDate() == null) {
            return false;
        }
        return medicine.getExpiryDate().before(new Date());
    }

    public MyList<Medicine> getExpiredMedicines() {
        return medicines.findAll(medicine -> isMedicineExpired(medicine));
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
    
    public MyList<Medicine> createInventorySnapshot() { 
        return medicines.clone(); 
    }
    
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

        DynamicList<Medicine> medicineList = (DynamicList<Medicine>) snapshot;
        var stats = medicineList.getStatistics(Medicine::getQuantity);
        return stats.average;
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
}
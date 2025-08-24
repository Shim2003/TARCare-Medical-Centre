    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import Control.PharmacyManagement;
import Control.PrescriptionCalculator;
import Entity.Medicine;
import Entity.MedicalTreatmentItem;
import Entity.Prescription;
import Entity.StockRequest;
import ADT.MyList;
import DAO.ClinicData;
import Utility.UtilityClass;
import java.text.SimpleDateFormat;

public class PharmacyUI {

    private static final Scanner sc = new Scanner(System.in);
    private static final PharmacyManagement service = new PharmacyManagement();

    public static void main(String[] args) {
        
        ClinicData.addSampleMedicine();
        ClinicData.addSamplePrescriptions(service);
        ClinicData.addSampleStockRequests(service);
        
        int choice;
        do {
            printMenu();
            choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> addMedicine();
                case 2 -> updateMedicine();
                case 3 -> deleteMedicine();
                case 4 -> displayMedicines();
                case 5 -> displayPrescriptionQueue();
                case 6 -> processPrescription();
                case 7 -> displayLowStockAlert();
                case 8 -> stockRequestMenu();
                case 9 -> advancedSearchMenu();
                case 10 -> statisticsReportsMenu();
                case 11 -> bulkOperationsMenu();
                case 0 -> System.out.println("Bye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n===== Pharmacy Management System =====");
        System.out.println("1. Add medicine");
        System.out.println("2. Update medicine");
        System.out.println("3. Delete medicine");
        System.out.println("4. Display all medicines");
        System.out.println("5. Display prescription queue");
        System.out.println("6. Process prescription (Distribute medicines)");
        System.out.println("7. Display low stock alert");
        System.out.println("8. Stock Request Management");
        System.out.println("9. Advanced Search & Filter"); // NEW
        System.out.println("10. Statistics & Reports"); // NEW
        System.out.println("11. Bulk Operations"); // NEW
        System.out.println("0. Exit");
    }
    
    private static void stockRequestMenu() {
        int choice;
        do {
            System.out.println("\n===== Stock Request Management =====");
            System.out.println("1. Create stock request");
            System.out.println("2. View pending requests");
            System.out.println("3. View request history");
            System.out.println("4. Approve/Process request");
            System.out.println("0. Back to main menu");

            choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> createStockRequestManually();
                case 2 -> viewPendingStockRequests();
                case 3 -> viewStockRequestHistory();
                case 4 -> processStockRequest();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void addMedicine() {
        System.out.println("\n--- Add Medicine ---");
        Medicine m = readMedicineData(null);

        // Show new medicine info for confirmation
        System.out.println("\n--- New Medicine Details ---");
        printMedicineHeader();
        System.out.println(formatMedicineDisplay(m));

        System.out.print("\nConfirm add this medicine? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            if (service.addMedicine(m)) {
                System.out.println("Medicine added successfully!");
            } else {
                System.out.println("Medicine ID already exists!");
            }
        } else {
            System.out.println("Medicine addition cancelled.");
        }
    }

    private static void updateMedicine() {
        System.out.println("\n--- Update Medicine ---");
        String id = readLine("Enter medicine ID to update: ");
        Medicine existing = service.findById(id);
        if (existing == null) {
            System.out.println("Medicine not found.");
            return;
        }
        System.out.println("Current details: ");
        printMedicineHeader();
        System.out.println(formatMedicineDisplay(existing));

        Medicine updated = readMedicineData(existing);
        if (service.updateMedicine(id, updated)) {
            System.out.println("Updated successfully!");
        } else {
            System.out.println("Failed to update.");
        }
    }

    private static void deleteMedicine() {
        System.out.println("\n--- Delete Medicine ---");
        String id = readLine("Enter medicine ID to delete: ");
        Medicine existing = service.findById(id);
        if (existing == null) {
            System.out.println("Medicine not found.");
            return;
        }

        // Show medicine details
        System.out.println("\n--- Medicine to be deleted ---");
        printMedicineHeader();
        System.out.println(formatMedicineDisplay(existing));

        // First confirmation
        System.out.print("\nAre you sure you want to delete this medicine? (y/n): ");
        String firstConfirm = sc.nextLine().trim().toLowerCase();

        if (!firstConfirm.equals("y") && !firstConfirm.equals("yes")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        // Double confirmation
        System.out.print("This action cannot be undone. Type 'DELETE' to confirm: ");
        String secondConfirm = sc.nextLine().trim();

        if (secondConfirm.equals("DELETE")) {
            if (service.deleteMedicine(id)) {
                System.out.println("Medicine deleted successfully.");
            } else {
                System.out.println("Failed to delete medicine.");
            }
        } else {
            System.out.println("Delete operation cancelled. You must type 'DELETE' exactly to confirm.");
        }
    }

    private static void displayMedicines() {
        System.out.println("\n--- Medicine Inventory ---");
        if (service.getAll().size() == 0) {
            System.out.println("No medicines in inventory.");
            return;
        }

        // Create a clone and sort alphabetically by default
        MyList<Medicine> Medicines = service.getAll();

        printMedicineHeader();
        for (int i = 0; i < Medicines.size(); i++) {
            Medicine medicine = Medicines.get(i);
            System.out.println(formatMedicineDisplay(medicine));
        }
    }

    private static void displayPrescriptionQueue() {
        System.out.println("\n--- Prescription Queue ---");
        if (service.getQueueSize() == 0) {
            System.out.println("No prescriptions in queue.");
            return;
        }

        for (int i = 0; i < service.getQueueSize(); i++) {
            Prescription p = service.getQueueAt(i);
            System.out.println("=== Queue Position " + (i + 1) + " ===");
            System.out.println("Prescription ID: " + p.getPrescriptionID());
            System.out.println("Patient: " + p.getPatient().getFullName() + " (ID: " + p.getPatient().getPatientID() + ")");
            System.out.println("Phone: " + p.getPatient().getContactNumber());
            System.out.println("Doctor ID: " + p.getDoctorId());
            System.out.println("Status: " + p.getStatus());
            System.out.println("Medicine Items:");
            for (int j = 0; j < p.getMedicineItems().size(); j++) {
                MedicalTreatmentItem item = p.getMedicineItems().get(j);

                // Get medicine to access dosage form
                Medicine medicine = service.findByName(item.getMedicineName());
                String dosageForm = medicine != null ? medicine.getDosageForm() : "unit";

                System.out.println("  - " + item.getMedicineName() + 
                                 " | Dosage: " + PrescriptionCalculator.getCompleteDosageDescription(item, dosageForm) +
                                 " | Frequency: " + item.getFrequency() +
                                 " | Duration: " + item.getDuration() +
                                 " | Method: " + item.getMethod() +
                                 " | Calculated Quantity: " + PrescriptionCalculator.calculateQuantityNeeded(item));
            }
            System.out.println();
        }
    }

    private static void processPrescription() {
        System.out.println("\n--- Process Prescription (Distribute Medicines) ---");
        if (service.getQueueSize() == 0) {
            System.out.println("No prescriptions in queue.");
            return;
        }

        // Show next prescription in queue
        Prescription nextPrescription = service.getNextInQueue();
        System.out.println("Processing prescription for: " + nextPrescription.getPatient().getFullName());
        System.out.println("Prescription ID: " + nextPrescription.getPrescriptionID());
        System.out.println("\nRequired medicines:");

        boolean canProcess = true;
        double totalCost = 0.0;
        MyList<String> insufficientMedicines = new DynamicList<>();

        // Check stock availability using calculated quantities
        for (int i = 0; i < nextPrescription.getMedicineItems().size(); i++) {
            MedicalTreatmentItem item = nextPrescription.getMedicineItems().get(i);
            Medicine medicine = service.findByName(item.getMedicineName());
            int quantityNeeded = PrescriptionCalculator.calculateQuantityNeeded(item);

            if (medicine == null) {
                System.out.println("[X] " + item.getMedicineName() + " - MEDICINE NOT FOUND");
                canProcess = false;
            } else if (medicine.getQuantity() < quantityNeeded) {
                System.out.println("[X] " + item.getMedicineName() + 
                                 " - INSUFFICIENT STOCK (Available: " + medicine.getQuantity() + 
                                 ", Calculated Need: " + quantityNeeded + ")");
                insufficientMedicines.add(medicine.getMedicineID() + ":" + medicine.getMedicineName());
                canProcess = false;
            } else {
                double itemCost = medicine.getPrice() * quantityNeeded;
                totalCost += itemCost;
                System.out.println("[OK] " + item.getMedicineName() + 
                                 " - Available (Calculated Need: " + quantityNeeded + 
                                 ", Cost: $" + String.format("%.2f", itemCost) + ")");
                System.out.println("     Prescription: " + item.getDosage() + ", " + 
                                 item.getFrequency() + " for " + item.getDuration());
                System.out.println("     Medicine Form: " + medicine.getCompleteDosage());
            }
        }

        System.out.println("\nTotal cost: $" + String.format("%.2f", totalCost));

        if (!canProcess) {
            System.out.println("\n[X] Cannot process prescription due to stock issues.");

            if (insufficientMedicines.size() > 0) {
                System.out.println("\nOptions:");
                System.out.println("1. Create restock request for insufficient medicines");
                System.out.println("2. Exit");

                int choice = readInt("Choose option: ");

                if (choice == 1) {
                    for (int i = 0; i < insufficientMedicines.size(); i++) {
                        String[] parts = insufficientMedicines.get(i).split(":");
                        String medicineID = parts[0];
                        String medicineName = parts[1];

                        System.out.println("\nCreating restock request for: " + medicineName);
                        createStockRequest(medicineID, medicineName);
                    }
                }
            }

            System.out.print("Press Enter to continue...");
            sc.nextLine();
            return;
        }

        System.out.print("\nProceed with distribution? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            // Distribute medicines and update stock
            boolean success = service.processPrescription();

            if (success) {
                System.out.println("[SUCCESS] Prescription processed successfully!");
                System.out.println("[$] Total amount charged: $" + String.format("%.2f", totalCost));
                System.out.println("[INFO] Medicine stock has been updated based on calculated quantities.");
                System.out.println("[PATIENT] Patient " + nextPrescription.getPatient().getFullName() + " can collect medicines.");
            } else {
                System.out.println("[ERROR] Failed to process prescription.");
            }
        } else {
            System.out.println("Prescription processing cancelled.");
        }

        System.out.print("Press Enter to continue...");
        sc.nextLine();
    }

    private static void displayLowStockAlert() {
        System.out.println("\n--- Low Stock Alert ---");
        boolean hasLowStock = false;
        MyList<Medicine> lowStockMedicines = new DynamicList<>();

        for (int i = 0; i < service.getAll().size(); i++) {
            Medicine medicine = service.getAll().get(i);
            if (medicine.getQuantity() <= UtilityClass.LOW_STOCK_THRESHOLD) { // Low stock threshold
                lowStockMedicines.add(medicine);
                if (!hasLowStock) {
                    System.out.println("[WARNING] LOW STOCK MEDICINES:");
                    printMedicineHeader();
                    hasLowStock = true;
                }

                // Check if there's a pending request for this medicine
                String statusLabel;
                if (service.hasPendingRequestForMedicine(medicine.getMedicineID())) {
                    int pendingQty = service.getTotalPendingQuantityForMedicine(medicine.getMedicineID());
                    statusLabel = "[REQUESTED: " + pendingQty + " units]";
                } else {
                    statusLabel = "[LOW STOCK]";
                }

                System.out.println(formatMedicineDisplay(medicine) + " " + statusLabel);
            }
        }

        if (!hasLowStock) {
            System.out.println("[OK] All medicines have sufficient stock.");
            return;
        }

        // Filter out medicines that already have pending requests for the selection menu
        MyList<Medicine> availableForRequest = new DynamicList<>();
        for (int i = 0; i < lowStockMedicines.size(); i++) {
            Medicine med = lowStockMedicines.get(i);
            if (!service.hasPendingRequestForMedicine(med.getMedicineID())) {
                availableForRequest.add(med);
            }
        }

        if (availableForRequest.size() == 0) {
            System.out.println("\n[INFO] All low stock medicines already have pending restock requests.");
            return;
        }

        System.out.print("\nWould you like to create restock requests for medicines without pending requests? (y/n): ");
        String response = sc.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            System.out.println("\nSelect medicine to create restock request:");
            for (int i = 0; i < availableForRequest.size(); i++) {
                Medicine med = availableForRequest.get(i);
                System.out.println((i + 1) + ". " + med.getMedicineName() + " (ID: " + med.getMedicineID() + ") - Current: " + med.getQuantity());
            }
            System.out.println("0. Exit");

            int choice = readInt("Select medicine (1-" + availableForRequest.size() + "): ");
            if (choice >= 1 && choice <= availableForRequest.size()) {
                Medicine selectedMedicine = availableForRequest.get(choice - 1);
                createStockRequest(selectedMedicine.getMedicineID(), selectedMedicine.getMedicineName());
            } else if (choice != 0) {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static Medicine readMedicineData(Medicine base) {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        Medicine m = new Medicine();

        m.setMedicineID(readLineWithDefault("ID", base == null ? null : base.getMedicineID()));
        m.setMedicineName(readLineWithDefault("Name", base == null ? null : base.getMedicineName()));

        // Only allow quantity input for new medicines (base == null)
        if (base == null) {
            m.setQuantity(readInt("Quantity: "));
        } else {
            m.setQuantity(base.getQuantity()); // Keep existing quantity - no message displayed
        }

        m.setCategory(readLineWithDefault("Category", base == null ? null : base.getCategory()));
        m.setPrice(readDoubleWithDefault("Price", base == null ? null : base.getPrice()));
        m.setManufacturer(readLineWithDefault("Manufacturer", base == null ? null : base.getManufacturer()));

        // Dosage form selection (only field needed)
        System.out.println("\nSelect dosage form:");
        String[] dosageForms = UtilityClass.DOSAGE_FORMS;
        for (int i = 0; i < dosageForms.length; i++) {
            System.out.println((i + 1) + ". " + dosageForms[i]);
        }

        String currentForm = base == null ? null : base.getDosageForm();
        if (currentForm != null) {
            System.out.println("Current: " + currentForm);
        }

        int formChoice = readIntWithDefault("Select dosage form (1-" + dosageForms.length + ")", 
                                           currentForm == null ? 1 : findFormIndex(currentForm) + 1);
        if (formChoice >= 1 && formChoice <= dosageForms.length) {
            m.setDosageForm(dosageForms[formChoice - 1]);
        } else {
            m.setDosageForm("tablet"); // default
        }

        // Expiry date
        while (true) {
            String prompt = "Expiry date (dd/MM/yyyy)";
            String def = base == null || base.getExpiryDate() == null ? null : sdf.format(base.getExpiryDate());
            String s = readLineWithDefault(prompt, def);
            try {
                Date d = sdf.parse(s);
                m.setExpiryDate(d);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }

        return m;
    }
    
    private static int findFormIndex(String form) {
        String[] forms = UtilityClass.DOSAGE_FORMS;
        for (int i = 0; i < forms.length; i++) {
            if (forms[i].equalsIgnoreCase(form)) {
                return i;
            }
        }
        return 0; // default to first
    }

    // IO Helper Methods
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static String readLineWithDefault(String field, String def) {
        if (def == null) {
            return readLine(field + ": ");
        }
        System.out.print(field + " [" + def + "]: ");
        String line = sc.nextLine().trim();
        return line.isEmpty() ? def : line;
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static Integer readIntWithDefault(String field, Integer def) {
        if (def == null) return readInt(field + ": ");
        while (true) {
            System.out.print(field + " [" + def + "]: ");
            String s = sc.nextLine().trim();
            if (s.isEmpty()) return def;
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static Double readDoubleWithDefault(String field, Double def) {
        if (def == null) return readDouble(field + ": ");
        while (true) {
            System.out.print(field + " [" + def + "]: ");
            String s = sc.nextLine().trim();
            if (s.isEmpty()) return def;
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
    
    private static void printMedicineHeader() {
        System.out.println("ID   | Name                           | Quantity(Form)   | Category             | Price (RM)     | Manufacturer           | Expiry");
        System.out.println("=".repeat(135));
    }

    // Helper function to format medicine display
    private static String formatMedicineDisplay(Medicine medicine) {
        String quantityForm = medicine.getQuantity() + "(" + medicine.getDosageForm() + ")";
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
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
    
    // Create stock request manually
    private static void createStockRequestManually() {
        System.out.println("\n--- Create Stock Request ---");

        // Display available medicines first
        if (service.getAll().size() == 0) {
            System.out.println("No medicines in inventory.");
            return;
        }

        System.out.println("Available medicines:");
        printMedicineHeader();
        for (int i = 0; i < service.getAll().size(); i++) {
            Medicine medicine = service.getAll().get(i);
            System.out.println(formatMedicineDisplay(medicine));
        }

        String medicineID = readLine("\nEnter medicine ID for restock request: ");
        Medicine medicine = service.findById(medicineID);

        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.println("\nSelected medicine: " + medicine.getMedicineName());
        System.out.println("Current stock: " + medicine.getQuantity() + " " + medicine.getDosageForm());

        // Check if there are pending requests
        if (service.hasPendingRequestForMedicine(medicineID)) {
            int pendingQty = service.getTotalPendingQuantityForMedicine(medicineID);
            System.out.println("!!! Warning: There are pending requests for " + pendingQty + " units of this medicine.");
        }

        int requestedQuantity = readInt("Enter quantity to request: ");

        if (requestedQuantity <= 0) {
            System.out.println("Invalid quantity. Must be greater than 0.");
            return;
        }

        String requestID = service.createStockRequest(medicineID, requestedQuantity);
        if (requestID != null) {
            System.out.println("Stock request created successfully!");
            System.out.println("Request ID: " + requestID);
            System.out.println("Medicine: " + medicine.getMedicineName());
            System.out.println("Requested quantity: " + requestedQuantity + " " + medicine.getDosageForm());
            System.out.println("Status: PENDING");
        } else {
            System.out.println("Failed to create stock request.");
        }
    }

    // Helper function to create stock request (called from other functions)
    private static void createStockRequest(String medicineID, String medicineName) {
        System.out.println("\n--- Create Stock Request ---");
        System.out.println("Medicine: " + medicineName + " (ID: " + medicineID + ")");

        Medicine medicine = service.findById(medicineID);
        if (medicine != null) {
            System.out.println("Current stock: " + medicine.getQuantity() + " " + medicine.getDosageForm());

            // Check if there are pending requests
            if (service.hasPendingRequestForMedicine(medicineID)) {
                int pendingQty = service.getTotalPendingQuantityForMedicine(medicineID);
                System.out.println("!!! Warning: There are pending requests for " + pendingQty + " units of this medicine.");
            }
        }

        int requestedQuantity = readInt("Enter quantity to request: ");

        if (requestedQuantity <= 0) {
            System.out.println("Invalid quantity. Must be greater than 0.");
            return;
        }

        String requestID = service.createStockRequest(medicineID, requestedQuantity);
        if (requestID != null) {
            System.out.println("Stock request created successfully!");
            System.out.println("Request ID: " + requestID);
            System.out.println("Requested quantity: " + requestedQuantity);
            System.out.println("Status: PENDING");
        } else {
            System.out.println("Failed to create stock request.");
        }
    }

    // View pending stock requests
    private static void viewPendingStockRequests() {
        System.out.println("\n--- Pending Stock Requests ---");
        MyList<StockRequest> pendingRequests = service.getPendingStockRequests();

        if (pendingRequests.size() == 0) {
            System.out.println("No pending stock requests.");
            return;
        }

        printStockRequestHeader();
        for (int i = 0; i < pendingRequests.size(); i++) {
            StockRequest request = pendingRequests.get(i);
            System.out.println(request);
        }
    }

    // View stock request history
    private static void viewStockRequestHistory() {
        System.out.println("\n--- Stock Request History ---");
        MyList<StockRequest> completedRequests = service.getCompletedStockRequests();

        if (completedRequests.size() == 0) {
            System.out.println("No completed stock requests.");
            return;
        }

        printStockRequestHeader();
        for (int i = 0; i < completedRequests.size(); i++) {
            StockRequest request = completedRequests.get(i);
            System.out.println(request);
        }
    }

    // Process/Approve stock request
    private static void processStockRequest() {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATETIME_FORMAT);
        System.out.println("\n--- Process Stock Request ---");
        MyList<StockRequest> pendingRequests = service.getPendingStockRequests();

        if (pendingRequests.size() == 0) {
            System.out.println("No pending stock requests to process.");
            return;
        }

        System.out.println("Pending requests:");
        printStockRequestHeader();
        for (int i = 0; i < pendingRequests.size(); i++) {
            StockRequest request = pendingRequests.get(i);
            System.out.println(request);
        }

        String requestID = readLine("\nEnter request ID to process: ");
        StockRequest request = service.findStockRequestById(requestID);

        if (request == null) {
            System.out.println("Request not found.");
            return;
        }

        if (!"PENDING".equals(request.getStatus())) {
            System.out.println("Request is not pending. Current status: " + request.getStatus());
            return;
        }

        System.out.println("\n--- Request Details ---");
        System.out.println("Request ID: " + request.getRequestID());
        System.out.println("Medicine: " + request.getMedicineName() + " (ID: " + request.getMedicineID() + ")");
        System.out.println("Requested quantity: " + request.getRequestedQuantity());
        System.out.println("Request date: " + sdf.format(request.getRequestDate()));

        System.out.println("\nChoose action:");
        System.out.println("1. Approve and add stock");
        System.out.println("2. Reject request");
        System.out.println("0. Cancel");

        int action = readInt("Select action: ");

        switch (action) {
            case 1:
                if (service.approveStockRequest(requestID)) {
                    System.out.println("Request approved and stock updated!");
                    Medicine medicine = service.findById(request.getMedicineID());
                    if (medicine != null) {
                        System.out.println("New stock level: " + medicine.getQuantity() + " " + medicine.getDosageForm());
                    }
                } else {
                    System.out.println("Failed to approve request.");
                }
                break;
            case 2:
                if (service.updateStockRequestStatus(requestID, "REJECTED")) {
                    System.out.println("Request rejected.");
                } else {
                    System.out.println("Failed to reject request.");
                }
                break;
            case 0:
                System.out.println("Operation cancelled.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Print stock request header
    private static void printStockRequestHeader() {
        System.out.println("Req ID   | Med ID   | Medicine Name        | Quantity | Request Date     | Status");
        System.out.println("=".repeat(85));
    }
    
    private static void advancedSearchMenu() {
        int choice;
        do {
            System.out.println("\n===== Advanced Search & Filter =====");
            System.out.println("1. Search medicines by name pattern");
            System.out.println("2. Filter by price range");
            System.out.println("3. Filter by category");
            System.out.println("4. Filter by dosage form");
            System.out.println("5. Filter by manufacturer");
            System.out.println("6. Find medicines near expiry");
            System.out.println("7. Multiple criteria search");
            System.out.println("0. Back to main menu");

            choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> searchByNamePattern();
                case 2 -> filterByPriceRange();
                case 3 -> filterByCategory();
                case 4 -> filterByDosageForm();
                case 5 -> filterByManufacturer();
                case 6 -> findNearExpiryMedicines();
                case 7 -> multipleCriteriaSearch();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void searchByNamePattern() {
        String pattern = readLine("Enter name pattern to search: ");
        MyList<Medicine> results = service.searchMedicinesByPattern(pattern);
        displaySearchResults("medicines matching '" + pattern + "'",
                (DynamicList<Medicine>) results);
    }

    private static void filterByPriceRange() {
        System.out.println("\n--- Filter by Price Range ---");
        double minPrice = readDouble("Enter minimum price (RM): ");
        double maxPrice = readDouble("Enter maximum price (RM): ");

        DynamicList<Medicine> results = (DynamicList<Medicine>) service.getAll()
            .filter(m -> m.getPrice() >= minPrice && m.getPrice() <= maxPrice);

        displaySearchResults("medicines in price range RM" + minPrice + " - RM" + maxPrice, results);
    }

    private static void filterByCategory() {
        System.out.println("\n--- Filter by Category ---");

        // Show available categories first
        DynamicList<String> categories = new DynamicList<>();
        for (int i = 0; i < service.getAll().size(); i++) {
            String category = service.getAll().get(i).getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }

        System.out.println("Available categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        String selectedCategory = readLine("Enter category name: ");
        DynamicList<Medicine> results = (DynamicList<Medicine>) service.getAll()
            .filter(m -> m.getCategory().equalsIgnoreCase(selectedCategory));

        displaySearchResults("medicines in category '" + selectedCategory + "'", results);
    }

    private static void filterByDosageForm() {
        System.out.println("\n--- Filter by Dosage Form ---");

        // Show available dosage forms
        System.out.println("Available dosage forms:");
        String[] forms = UtilityClass.DOSAGE_FORMS;
        for (int i = 0; i < forms.length; i++) {
            System.out.println((i + 1) + ". " + forms[i]);
        }

        int choice = readInt("Select dosage form (1-" + forms.length + "): ");
        if (choice >= 1 && choice <= forms.length) {
            String selectedForm = forms[choice - 1];
            DynamicList<Medicine> results = (DynamicList<Medicine>) service.getAll()
                .filter(m -> m.getDosageForm().equalsIgnoreCase(selectedForm));

            displaySearchResults("medicines in " + selectedForm + " form", results);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void filterByManufacturer() {
        System.out.println("\n--- Filter by Manufacturer ---");

        // Show available manufacturers
        DynamicList<String> manufacturers = new DynamicList<>();
        for (int i = 0; i < service.getAll().size(); i++) {
            String manufacturer = service.getAll().get(i).getManufacturer();
            if (!manufacturers.contains(manufacturer)) {
                manufacturers.add(manufacturer);
            }
        }

        System.out.println("Available manufacturers:");
        for (int i = 0; i < manufacturers.size(); i++) {
            System.out.println((i + 1) + ". " + manufacturers.get(i));
        }

        String selectedManufacturer = readLine("Enter manufacturer name: ");
        DynamicList<Medicine> results = (DynamicList<Medicine>) service.getAll()
            .filter(m -> m.getManufacturer().equalsIgnoreCase(selectedManufacturer));

        displaySearchResults("medicines from '" + selectedManufacturer + "'", results);
    }

    private static void findNearExpiryMedicines() {
        System.out.println("\n--- Find Near Expiry Medicines ---");
        int months = readInt("Enter number of months from now: ");

        // Use control layer method instead of manual filtering
        MyList<Medicine> results = service.getMedicinesNearExpiry(months);

        displaySearchResults("medicines expiring within " + months + " months", (DynamicList<Medicine>) results);
    }

    private static void multipleCriteriaSearch() {
        System.out.println("\n--- Multiple Criteria Search ---");
        System.out.println("Enter search criteria (press Enter to skip):");

        String namePattern = readLine("Name pattern: ").trim();
        String categoryFilter = readLine("Category: ").trim();
        String manufacturerFilter = readLine("Manufacturer: ").trim();

        System.out.print("Minimum price (Enter for no limit): ");
        String minPriceStr = sc.nextLine().trim();
        Double minPrice = minPriceStr.isEmpty() ? null : Double.parseDouble(minPriceStr);

        System.out.print("Maximum price (Enter for no limit): ");
        String maxPriceStr = sc.nextLine().trim();
        Double maxPrice = maxPriceStr.isEmpty() ? null : Double.parseDouble(maxPriceStr);

        DynamicList<Medicine> results = (DynamicList<Medicine>) service.getAll().filter(m -> {
            boolean matches = true;

            if (!namePattern.isEmpty()) {
                matches = matches && m.getMedicineName().toLowerCase().contains(namePattern.toLowerCase());
            }
            if (!categoryFilter.isEmpty()) {
                matches = matches && m.getCategory().equalsIgnoreCase(categoryFilter);
            }
            if (!manufacturerFilter.isEmpty()) {
                matches = matches && m.getManufacturer().equalsIgnoreCase(manufacturerFilter);
            }
            if (minPrice != null) {
                matches = matches && m.getPrice() >= minPrice;
            }
            if (maxPrice != null) {
                matches = matches && m.getPrice() <= maxPrice;
            }

            return matches;
        });

        displaySearchResults("medicines matching multiple criteria", results);
    }

    private static void displaySearchResults(String description, DynamicList<Medicine> results) {
        System.out.println("\n--- Search Results ---");
        System.out.println("Found " + results.size() + " " + description);

        if (results.isEmpty()) {
            System.out.println("No medicines found matching the criteria.");
            return;
        }

        printMedicineHeader();
        for (int i = 0; i < results.size(); i++) {
            System.out.println(formatMedicineDisplay(results.get(i)));
        }
    }

    private static void statisticsReportsMenu() {
        int choice;
        do {
            System.out.println("\n===== Statistics & Reports =====");
            System.out.println("1. Inventory statistics");
            System.out.println("2. Display inventory summary");
            System.out.println("0. Back to main menu");

            choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> displayInventoryStatistics();
                case 2 -> displayInventorySummary();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void displayInventoryStatistics() {
        System.out.println("\n--- Inventory Statistics ---");
        DynamicList<Medicine> medicines = (DynamicList<Medicine>) service.getAll();

        if (medicines.isEmpty()) {
            System.out.println("No medicines in inventory.");
            return;
        }

        var quantityStats = medicines.getStatistics(Medicine::getQuantity);
        var priceStats = medicines.getStatistics(m -> m.getPrice());

        System.out.println("=== INVENTORY OVERVIEW ===");
        System.out.println("Total medicines: " + medicines.size());
        System.out.println("Total inventory value: RM" + String.format("%.2f", service.calculateTotalInventoryValue()));

        System.out.println("\n=== QUANTITY STATISTICS ===");
        System.out.printf("Average quantity: %.2f units%n", quantityStats.average);
        System.out.println("Minimum stock: " + (int)quantityStats.min + " units");
        System.out.println("Maximum stock: " + (int)quantityStats.max + " units");
        System.out.printf("Standard deviation: %.2f%n", quantityStats.standardDeviation);

        System.out.println("\n=== PRICE STATISTICS ===");
        System.out.printf("Average price: RM%.2f%n", priceStats.average);
        System.out.printf("Lowest price: RM%.2f%n", priceStats.min);
        System.out.printf("Highest price: RM%.2f%n", priceStats.max);
        System.out.printf("Price standard deviation: RM%.2f%n", priceStats.standardDeviation);
    }

    private static void displayInventorySummary() {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        System.out.println("\n--- Inventory Summary ---");
        DynamicList<Medicine> medicines = (DynamicList<Medicine>) service.getAll();

        if (medicines.isEmpty()) {
            System.out.println("No medicines in inventory.");
            return;
        }

        // Create clones for different sorting
        MyList<Medicine> byQuantity = medicines.clone();
        UtilityClass.quickSort(byQuantity, java.util.Comparator.comparing(Medicine::getQuantity));

        MyList<Medicine> byPrice = medicines.clone();
        UtilityClass.quickSort(byPrice, java.util.Comparator.comparing(Medicine::getPrice).reversed());

        // Use control layer methods instead of manual filtering
        MyList<Medicine> expired = service.getExpiredMedicines();
        MyList<Medicine> nearExpiry = service.getMedicinesNearExpiry(180); // 6 months = ~180 days

        System.out.println("\nLOWEST STOCK:");
        for (int i = 0; i < Math.min(3, byQuantity.size()); i++) {
            Medicine med = byQuantity.get(i);
            System.out.println("  " + med.getMedicineName() + ": " + med.getQuantity() + " " + med.getDosageForm());
        }

        System.out.println("\nHIGHEST VALUE MEDICINES:");
        for (int i = 0; i < Math.min(3, byPrice.size()); i++) {
            Medicine med = byPrice.get(i);
            System.out.println("  " + med.getMedicineName() + ": RM" + String.format("%.2f", med.getPrice()));
        }

        System.out.println("\nNEAR EXPIRY (within 6 months): " + nearExpiry.size() + " medicines");
        if (nearExpiry.size() > 0) {
            for (int i = 0; i < Math.min(3, nearExpiry.size()); i++) {
                Medicine med = nearExpiry.get(i);
                System.out.println("  " + med.getMedicineName() + " - expires " + sdf.format(med.getExpiryDate()));
            }
            if (nearExpiry.size() > 3) {
                System.out.println("  ... and " + (nearExpiry.size() - 3) + " more");
            }
        }

        System.out.println("\nEXPIRED MEDICINES: " + expired.size() + " medicines");
        if (expired.size() > 0) {
            for (int i = 0; i < Math.min(3, expired.size()); i++) {
                Medicine med = expired.get(i);
                System.out.println("  " + med.getMedicineName() + " - expired " + sdf.format(med.getExpiryDate()));
            }
            if (expired.size() > 3) {
                System.out.println("  ... and " + (expired.size() - 3) + " more");
            }
        }
    }
    
    private static void bulkOperationsMenu() {
        int choice;
        do {
            System.out.println("\n===== Bulk Operations =====");
            System.out.println("1. Clone inventory snapshot");
            System.out.println("2. Remove expired medicines");
            System.out.println("0. Back to main menu");

            choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> cloneInventorySnapshot();
                case 2 -> removeExpiredMedicines();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
    
    private static void cloneInventorySnapshot() {
        System.out.println("\n--- Clone Inventory Snapshot ---");

        DynamicList<Medicine> medicines = (DynamicList<Medicine>) service.getAll();
        if (medicines.isEmpty()) {
            System.out.println("No medicines to clone.");
            return;
        }

        // Create a clone of the current inventory
        MyList<Medicine> snapshot = medicines.clone();

        System.out.println("Inventory snapshot created with " + snapshot.size() + " medicines.");
        System.out.println("This snapshot can be used for backup or comparison purposes.");

        // Show snapshot summary
        var stats = snapshot.getStatistics(Medicine::getQuantity);
        double totalValue = 0;
        for (int i = 0; i < snapshot.size(); i++) {
            Medicine med = snapshot.get(i);
            totalValue += med.getPrice() * med.getQuantity();
        }

        System.out.println("\n=== SNAPSHOT SUMMARY ===");
        System.out.println("Total medicines: " + snapshot.size());
        System.out.println("Total inventory value: RM" + String.format("%.2f", totalValue));
        System.out.printf("Average stock per medicine: %.2f units%n", stats.average);

        // Optionally save snapshot details to display later
        System.out.print("Display full snapshot details? (y/n): ");
        String show = sc.nextLine().trim().toLowerCase();
        if (show.equals("y") || show.equals("yes")) {
            printMedicineHeader();
            for (int i = 0; i < snapshot.size(); i++) {
                System.out.println(formatMedicineDisplay(snapshot.get(i)));
            }
        }
    }
    
    private static void removeExpiredMedicines() {
        System.out.println("\n--- Remove Expired Medicines ---");

        // Use control layer method to get expired medicines
        MyList<Medicine> expired = service.getExpiredMedicines();

        if (expired.isEmpty()) {
            System.out.println("No expired medicines found.");
            return;
        }

        System.out.println("Found " + expired.size() + " expired medicines:");
        printMedicineHeader();
        for (int i = 0; i < expired.size(); i++) {
            System.out.println(formatMedicineDisplay(expired.get(i)) + " [EXPIRED]");
        }

        System.out.print("Remove all expired medicines? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            // Use control layer method to remove expired medicines
            int removedCount = service.removeExpiredMedicines();
            System.out.println(removedCount + " expired medicines removed from inventory.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    
}
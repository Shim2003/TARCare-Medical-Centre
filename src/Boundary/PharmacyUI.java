    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Predicate;
import Control.PharmacyManagement;
import Entity.Medicine;
import Entity.MedicalTreatmentItem;
import Entity.Prescription;
import Entity.Patient;
import Entity.StockRequest;
import ADT.MyList;

public class PharmacyUI {

    private static final Scanner sc = new Scanner(System.in);
    private static final PharmacyManagement service = new PharmacyManagement();

    public static void main(String[] args) {
        
        try {
            // Initialize medicine inventory with only dosage form
            service.addMedicine(new Medicine("M001", "Paracetamol", 100, "Analgesic", 0.25, "China", 
                service.parseDate("31/12/2030"), "tablet"));
            service.addMedicine(new Medicine("M002", "Aspirin", 50, "Analgesic", 1.00, "Bayer", 
                service.parseDate("15/11/2029"), "tablet"));
            service.addMedicine(new Medicine("M003", "Amoxicillin", 200, "Antibiotic", 1.50, "Pfizer", 
                service.parseDate("01/07/2031"), "capsule"));
            service.addMedicine(new Medicine("M004", "Vitamin C", 150, "Supplement", 0.8, "Blackmores", 
                service.parseDate("10/05/2028"), "tablet"));
            service.addMedicine(new Medicine("M005", "Benadryl Cough Syrup", 80, "Cold & Flu", 0.30, "Johnson", 
                service.parseDate("20/03/2032"), "ml"));
            service.addMedicine(new Medicine("M006", "ORS Sachet", 300, "Electrolyte", 1.20, "Cipla", 
                service.parseDate("15/08/2029"), "sachet"));
            service.addMedicine(new Medicine("M007", "Hydrocortisone Cream", 40, "Topical", 4.00, "GSK", 
                service.parseDate("30/06/2030"), "cream"));
            service.addMedicine(new Medicine("M008", "Omeprazole", 100, "Antacid", 1.75, "AstraZeneca", 
                service.parseDate("12/09/2031"), "capsule"));

            // Initialize sample prescription queue (hardcoded patients)
            initializeSampleQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                case 8 -> stockRequestMenu(); // New case
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
        System.out.println("8. Stock Request Management"); // New option
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

    private static void initializeSampleQueue() {
        try {
            // Sample patients using your existing Patient entity
            Patient patient1 = new Patient("John Doe", "123456789012", 
                                          service.parseDate("01/01/1990"), 'M',
                                          "123-456-7890", "john@email.com", 
                                          "123 Main St", "456-789-0123",
                                          new Date());

            Prescription prescription1 = new Prescription("RX001", patient1, "DR001");
            // Updated calls - removed quantityNeeded parameter (it's now calculated)
            prescription1.addMedicineItem("Paracetamol", "1", "3x/day after meals", "5 days", "Oral");
            prescription1.addMedicineItem("Amoxicillin", "1", "3x/day", "7 days", "Oral");
            service.addToQueue(prescription1);

            Patient patient2 = new Patient("Jane Smith", "987654321098",
                                          service.parseDate("15/05/1985"), 'F',
                                          "987-654-3210", "jane@email.com",
                                          "456 Oak Ave", "321-654-9870",
                                          new Date());

            Prescription prescription2 = new Prescription("RX002", patient2, "DR002");
            prescription2.addMedicineItem("Benadryl Cough Syrup", "10", "2x/day", "5 days", "Oral");
            prescription2.addMedicineItem("ORS Sachet", "1", "after each loose stool", "as needed", "dissolve in water");
            service.addToQueue(prescription2);

            Patient patient3 = new Patient("Bob Johnson", "555123456789",
                                          service.parseDate("20/12/1975"), 'M',
                                          "555-123-4567", "bob@email.com",
                                          "789 Pine St", "654-321-0987",
                                          new Date());

            Prescription prescription3 = new Prescription("RX003", patient3, "DR003");
            prescription3.addMedicineItem("Hydrocortisone Cream", "Apply thin layer", "2 times a day", "7 days", "Topical");
            prescription3.addMedicineItem("Omeprazole", "1", "Before breakfast", "7 days", "Oral");
            service.addToQueue(prescription3);
        } catch (Exception e) {
            System.err.println("Error initializing sample queue: " + e.getMessage());
        }
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

        printMedicineHeader();

        for (int i = 0; i < service.getAll().size(); i++) {
            Medicine medicine = service.getAll().get(i);
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
                                 " | Dosage: " + item.getCompleteDosageDescription(dosageForm) +
                                 " | Frequency: " + item.getFrequency() +
                                 " | Duration: " + item.getDuration() +
                                 " | Method: " + item.getMethod() +
                                 " | Calculated Quantity: " + item.calculateQuantityNeeded());
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
            int quantityNeeded = item.calculateQuantityNeeded();

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
            if (medicine.getQuantity() <= 20) { // Low stock threshold
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
        String[] dosageForms = PharmacyManagement.DOSAGE_FORMS;
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
            String def = base == null || base.getExpiryDate() == null ? null
                    : new java.text.SimpleDateFormat("dd/MM/yyyy").format(base.getExpiryDate());
            String s = readLineWithDefault(prompt, def);
            try {
                Date d = service.parseDate(s);
                m.setExpiryDate(d);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }

        return m;
    }
    
    private static int findFormIndex(String form) {
        String[] forms = PharmacyManagement.DOSAGE_FORMS;
        for (int i = 0; i < forms.length; i++) {
            if (forms[i].equalsIgnoreCase(form)) {
                return i;
            }
        }
        return 0; // default to first
    }
    
    private static int findUnitIndex(String unit) {
        String[] units = PharmacyManagement.DOSAGE_UNITS;
        for (int i = 0; i < units.length; i++) {
            if (units[i].equalsIgnoreCase(unit)) {
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
        System.out.println("ID   | Name                 | Quantity(Form)   | Category     | Price (RM) | Manufacturer | Expiry");
        System.out.println("=".repeat(105));
    }

    // Helper function to format medicine display
    private static String formatMedicineDisplay(Medicine medicine) {
        String quantityForm = medicine.getQuantity() + "(" + medicine.getDosageForm() + ")";
        return String.format("%-4s | %-20s | %-16s | %-12s | %-10.2f | %-12s | %s",
            medicine.getMedicineID(),
            medicine.getMedicineName(),
            quantityForm,
            medicine.getCategory(),
            medicine.getPrice(),
            medicine.getManufacturer(),
            new java.text.SimpleDateFormat("dd/MM/yyyy").format(medicine.getExpiryDate())
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
            System.out.println("⚠️  Warning: There are pending requests for " + pendingQty + " units of this medicine.");
        }

        int requestedQuantity = readInt("Enter quantity to request: ");

        if (requestedQuantity <= 0) {
            System.out.println("Invalid quantity. Must be greater than 0.");
            return;
        }

        String requestID = service.createStockRequest(medicineID, requestedQuantity);
        if (requestID != null) {
            System.out.println("✅ Stock request created successfully!");
            System.out.println("Request ID: " + requestID);
            System.out.println("Medicine: " + medicine.getMedicineName());
            System.out.println("Requested quantity: " + requestedQuantity + " " + medicine.getDosageForm());
            System.out.println("Status: PENDING");
        } else {
            System.out.println("❌ Failed to create stock request.");
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
                System.out.println("⚠️  Warning: There are pending requests for " + pendingQty + " units of this medicine.");
            }
        }

        int requestedQuantity = readInt("Enter quantity to request: ");

        if (requestedQuantity <= 0) {
            System.out.println("Invalid quantity. Must be greater than 0.");
            return;
        }

        String requestID = service.createStockRequest(medicineID, requestedQuantity);
        if (requestID != null) {
            System.out.println("✅ Stock request created successfully!");
            System.out.println("Request ID: " + requestID);
            System.out.println("Requested quantity: " + requestedQuantity);
            System.out.println("Status: PENDING");
        } else {
            System.out.println("❌ Failed to create stock request.");
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
        System.out.println("Request date: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(request.getRequestDate()));

        System.out.println("\nChoose action:");
        System.out.println("1. Approve and add stock");
        System.out.println("2. Reject request");
        System.out.println("0. Cancel");

        int action = readInt("Select action: ");

        switch (action) {
            case 1:
                if (service.approveStockRequest(requestID)) {
                    System.out.println("✅ Request approved and stock updated!");
                    Medicine medicine = service.findById(request.getMedicineID());
                    if (medicine != null) {
                        System.out.println("New stock level: " + medicine.getQuantity() + " " + medicine.getDosageForm());
                    }
                } else {
                    System.out.println("❌ Failed to approve request.");
                }
                break;
            case 2:
                if (service.updateStockRequestStatus(requestID, "REJECTED")) {
                    System.out.println("❌ Request rejected.");
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


}
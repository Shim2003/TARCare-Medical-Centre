    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Predicate;
import Control.PharmacyManagement;
import Entity.Medicine;
import Entity.MedicalTreatmentItem;
import Entity.Prescription;
import Entity.Patient;

public class PharmacyUI {

    private static final Scanner sc = new Scanner(System.in);
    private static final PharmacyManagement service = new PharmacyManagement();

    public static void main(String[] args) {
        
        try {
            // Initialize medicine inventory
            service.addMedicine(new Medicine("M001", "Paracetamol", 100, "Analgesic", 0.25, "China", service.parseDate("31/12/2030")));
            service.addMedicine(new Medicine("M002", "Aspirin", 50, "Analgesic", 1.00, "Bayer", service.parseDate("15/11/2029")));
            service.addMedicine(new Medicine("M003", "Amoxicillin", 200, "Antibiotic", 1.50, "Pfizer", service.parseDate("01/07/2031")));
            service.addMedicine(new Medicine("M004", "Vitamin C", 150, "Supplement", 0.8, "Blackmores", service.parseDate("10/05/2028")));
            service.addMedicine(new Medicine("M005", "Benadryl Cough Syrup", 80, "Cold & Flu", 0.30, "Johnson", service.parseDate("20/03/2032")));
            service.addMedicine(new Medicine("M006", "ORS Sachet", 300, "Electrolyte", 1.20, "Cipla", service.parseDate("15/08/2029")));
            service.addMedicine(new Medicine("M007", "Hydrocortisone Cream", 40, "Topical", 4.00, "GSK", service.parseDate("30/06/2030")));
            service.addMedicine(new Medicine("M008", "Omeprazole", 120, "Antacid", 1.75, "AstraZeneca", service.parseDate("12/09/2031")));
            
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
        System.out.println("0. Exit");
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
            prescription1.addMedicineItem("Paracetamol", "1 tablet", "3x/day after meals", "5 days", "Oral");
            prescription1.addMedicineItem("Amoxicillin", "1 capsule", "3x/day", "7 days", "Oral");
            service.addToQueue(prescription1);

            Patient patient2 = new Patient("Jane Smith", "987654321098",
                                          service.parseDate("15/05/1985"), 'F',
                                          "987-654-3210", "jane@email.com",
                                          "456 Oak Ave", "321-654-9870",
                                          new Date());

            Prescription prescription2 = new Prescription("RX002", patient2, "DR002");
            prescription2.addMedicineItem("Benadryl Cough Syrup", "10ml", "2x/day", "5 days", "Oral");
            prescription2.addMedicineItem("ORS Sachet", "1 sachet", "after each loose stool", "as needed", "dissolve in water");
            service.addToQueue(prescription2);

            Patient patient3 = new Patient("Bob Johnson", "555123456789",
                                          service.parseDate("20/12/1975"), 'M',
                                          "555-123-4567", "bob@email.com",
                                          "789 Pine St", "654-321-0987",
                                          new Date());

            Prescription prescription3 = new Prescription("RX003", patient3, "DR003");
            prescription3.addMedicineItem("Hydrocortisone Cream", "Apply thin layer", "2 times a day", "7 days", "Topical");
            prescription3.addMedicineItem("Omeprazole", "1 capsule", "Before breakfast", "7 days", "Oral");
            service.addToQueue(prescription3);
        } catch (Exception e) {
            System.err.println("Error initializing sample queue: " + e.getMessage());
        }
    }

    private static void addMedicine() {
        System.out.println("\n--- Add Medicine ---");
        Medicine m = readMedicineData(null);
        if (service.addMedicine(m)) {
            System.out.println("Medicine added successfully!");
        } else {
            System.out.println("Medicine ID already exists!");
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
        System.out.println(header());
        System.out.println(existing);

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
        if (service.deleteMedicine(id)) {
            System.out.println("Medicine deleted successfully.");
        } else {
            System.out.println("Medicine not found.");
        }
    }

    private static void displayMedicines() {
        System.out.println("\n--- Medicine Inventory ---");
        if (service.getAll().size() == 0) {
            System.out.println("No medicines in inventory.");
            return;
        }
        System.out.println(header());
        System.out.println("=".repeat(100));
        for (int i = 0; i < service.getAll().size(); i++) {
            System.out.println(service.getAll().get(i));
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
                System.out.println("  - " + item.getMedicineName() + 
                                 " | Dosage: " + item.getDosage() +
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
                canProcess = false;
            } else {
                double itemCost = medicine.getPrice() * quantityNeeded;
                totalCost += itemCost;
                System.out.println("[OK] " + item.getMedicineName() + 
                                 " - Available (Calculated Need: " + quantityNeeded + 
                                 ", Cost: $" + String.format("%.2f", itemCost) + ")");
                System.out.println("     Prescription: " + item.getDosage() + ", " + 
                                 item.getFrequency() + " for " + item.getDuration());
            }
        }

        System.out.println("\nTotal cost: $" + String.format("%.2f", totalCost));

        if (!canProcess) {
            System.out.println("\n[X] Cannot process prescription due to stock issues.");
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
        
        for (int i = 0; i < service.getAll().size(); i++) {
            Medicine medicine = service.getAll().get(i);
            if (medicine.getQuantity() <= 20) { // Low stock threshold
                if (!hasLowStock) {
                    System.out.println("[WARNING] LOW STOCK MEDICINES:");
                    System.out.println(header());
                    System.out.println("=".repeat(100));
                    hasLowStock = true;
                }
                System.out.println(medicine + " [LOW STOCK]");
            }
        }
        
        if (!hasLowStock) {
            System.out.println("[OK] All medicines have sufficient stock.");
        }
    }

    private static String header() {
        return String.format("%-8s | %-20s | %5s | %-12s | %8s | %-15s | %s",
                "ID", "Name", "Qty", "Category", "Price", "Manufacturer", "Expiry");
    }

    private static Medicine readMedicineData(Medicine base) {
        Medicine m = new Medicine();

        m.setMedicineID(readLineWithDefault("ID", base == null ? null : base.getMedicineID()));
        m.setMedicineName(readLineWithDefault("Name", base == null ? null : base.getMedicineName()));
        m.setQuantity(readIntWithDefault("Quantity", base == null ? null : base.getQuantity()));
        m.setCategory(readLineWithDefault("Category", base == null ? null : base.getCategory()));
        m.setPrice(readDoubleWithDefault("Price", base == null ? null : base.getPrice()));
        m.setManufacturer(readLineWithDefault("Manufacturer", base == null ? null : base.getManufacturer()));

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
}
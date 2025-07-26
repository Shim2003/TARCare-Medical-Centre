/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

/**
 *
 * @author shim
 */
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import Control.PharmacyService;
import Entity.Medicine;

public class PharmacyUI {

    private static final Scanner sc = new Scanner(System.in);
    private static final PharmacyService service = new PharmacyService();

    public static void main(String[] args) {
        
        try {
            service.addMedicine(new Medicine("M001", "Panadol", 10, "Analgesic", 10.90, "China", service.parseDate("31/12/2030")));
            service.addMedicine(new Medicine("M002", "Aspirin", 20, "Analgesic", 12.50, "Bayer", service.parseDate("15/11/2029")));
            service.addMedicine(new Medicine("M003", "Amoxicillin", 50, "Antibiotic", 25.00, "Pfizer", service.parseDate("01/07/2031")));
            service.addMedicine(new Medicine("M004", "Vitamin C", 100, "Supplement", 5.20, "Blackmores", service.parseDate("10/05/2028")));
            service.addMedicine(new Medicine("M005", "Cough Syrup", 30, "Cold & Flu", 18.75, "Johnson", service.parseDate("20/03/2032")));
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
                case 0 -> System.out.println("Bye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n===== Pharmacy Menu =====");
        System.out.println("1. Add medicine");
        System.out.println("2. Update medicine");
        System.out.println("3. Delete medicine");
        System.out.println("4. Display all medicines");
        System.out.println("0. Exit");
    }

    private static void addMedicine() {
        System.out.println("\n--- Add Medicine ---");
        Medicine m = readMedicineData(null);
        if (service.addMedicine(m)) {
            System.out.println("Medicine added!");
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
        System.out.println("Current: ");
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
            System.out.println("Deleted.");
        } else {
            System.out.println("Medicine not found.");
        }
    }

    private static void displayMedicines() {
        System.out.println("\n--- Medicine List ---");
        if (service.getAll().isEmpty()) {
            System.out.println("No medicines.");
            return;
        }
        System.out.println(header());
        for (int i = 0; i < service.getAll().size(); i++) {
            System.out.println(service.getAll().get(i));
        }
    }

    private static String header() {
        return String.format("%-8s | %-20s | %5s | %-12s | %8s | %-15s | %s",
                "ID", "Name", "Qty", "Category", "Price", "Manufacturer", "Expiry");
    }

    /**
     * If `base` is not null, allow pressing Enter to keep old value.
     */
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
                System.out.println("Invalid date. Try again.");
            }
        }

        return m;
    }

    // ---------------- small IO helpers ----------------

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
                System.out.println("Invalid number. Try again.");
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
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
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
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}


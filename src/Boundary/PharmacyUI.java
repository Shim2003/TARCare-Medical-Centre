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
import Entity.Medicine;
import Entity.MedicalTreatmentItem;
import Entity.Prescription;
import Entity.StockRequest;
import ADT.MyList;
import Utility.UtilityClass;
import java.text.SimpleDateFormat;

public class PharmacyUI {

    private static final Scanner sc = new Scanner(System.in);
    private static final PharmacyManagement service = new PharmacyManagement();

    public static void PharmacyMainMenu() {
        
        int choice;
        do {
            printMenu();
            choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> medicineManagementMenu();
                case 2 -> displayPrescriptionQueue();
                case 3 -> processPrescription();
                case 4 -> displayLowStockAlert();
                case 5 -> stockRequestMenu();
                case 6 -> advancedSearchMenu();
                case 7 -> statisticsReportsMenu();
                case 8 -> bulkOperationsMenu();
                case 0 -> System.out.println("Bye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n===== Pharmacy Management System =====");
        System.out.println("1. Medicine Management");
        System.out.println("2. Display prescription queue");
        System.out.println("3. Process prescription (Distribute medicines)");
        System.out.println("4. Display low stock alert");
        System.out.println("5. Stock Request Management");
        System.out.println("6. Advanced Search & Filter");
        System.out.println("7. Statistics & Reports");
        System.out.println("8. Bulk Operations");
        System.out.println("0. Exit");
    }
    
    private static void medicineManagementMenu() {
        int choice;
        do {
            System.out.println("\n===== Medicine Management =====");
            System.out.println("1. Add medicine");
            System.out.println("2. Update medicine");
            System.out.println("3. Delete medicine");
            System.out.println("4. Display all medicines");
            System.out.println("0. Back to main menu");

            choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> addMedicine();
                case 2 -> updateMedicine();
                case 3 -> deleteMedicine();
                case 4 -> displayMedicines();
                case 0 -> System.out.println("Returning to Pharmacy MainMenu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
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
                case 0 -> System.out.println("Returning to Pharmacy MainMenu...");
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
            // Delegate to control layer
            String result = service.addMedicineWithValidation(m);
            System.out.println(result);
        } else {
            System.out.println("Medicine addition cancelled.");
        }
    }

    private static void updateMedicine() {
        System.out.println("\n--- Update Medicine ---");
        String id = readLine("Enter medicine ID to update: ");
        
        // Delegate validation to control layer
        Medicine existing = service.findById(id);
        if (existing == null) {
            System.out.println("Medicine not found.");
            return;
        }
        
        System.out.println("Current details: ");
        printMedicineHeader();
        System.out.println(formatMedicineDisplay(existing));

        Medicine updated = readMedicineData(existing);
        
        // Delegate to control layer
        String result = service.updateMedicineWithValidation(id, updated);
        System.out.println(result);
    }

    private static void deleteMedicine() {
        System.out.println("\n--- Delete Medicine ---");
        String id = readLine("Enter medicine ID to delete: ");
        
        // Get medicine details from control layer
        Medicine existing = service.findById(id);
        if (existing == null) {
            System.out.println("Medicine not found.");
            return;
        }

        // Show medicine details
        System.out.println("\n--- Medicine to be deleted ---");
        printMedicineHeader();
        System.out.println(formatMedicineDisplay(existing));

        // Get user confirmations
        if (!getDeleteConfirmation()) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        // Delegate to control layer
        String result = service.deleteMedicineWithValidation(id);
        System.out.println(result);
    }

    private static boolean getDeleteConfirmation() {
        // First confirmation
        System.out.print("\nAre you sure you want to delete this medicine? (y/n): ");
        String firstConfirm = sc.nextLine().trim().toLowerCase();

        if (!firstConfirm.equals("y") && !firstConfirm.equals("yes")) {
            return false;
        }

        // Double confirmation
        System.out.print("This action cannot be undone. Type 'DELETE' to confirm: ");
        String secondConfirm = sc.nextLine().trim();

        return secondConfirm.equals("DELETE");
    }

    private static void displayMedicines() {
        System.out.println("\n--- Medicine Inventory ---");

        // Get formatted display from control layer
        String inventoryDisplay = service.getMedicineInventoryDisplay();
        System.out.println(inventoryDisplay);
    }

    private static void displayPrescriptionQueue() {
        System.out.println("\n--- Prescription Queue ---");
        
        // Get queue information from control layer
        String queueDisplay = service.getPrescriptionQueueDisplay();
        System.out.println(queueDisplay);
    }

    private static void processPrescription() {
        System.out.println("\n--- Process Prescription (Distribute Medicines) ---");
        
        // Get prescription processing information from control layer
        PharmacyManagement.PrescriptionProcessingInfo processingInfo = 
            service.preparePrescriptionProcessing();
        
        if (processingInfo == null) {
            System.out.println("No prescriptions in queue.");
            return;
        }

        // Display processing information
        System.out.println("Processing prescription for Patient ID: " + processingInfo.getPatientId());
        System.out.println("Prescription ID: " + processingInfo.getPrescriptionId());
        System.out.println("\nRequired medicines:");
        System.out.println(processingInfo.getMedicineAvailabilityDisplay());
        System.out.println("\nTotal cost: $" + String.format("%.2f", processingInfo.getTotalCost()));

        if (!processingInfo.canProcess()) {
            System.out.println("\n[X] Cannot process prescription due to stock issues.");

            if (processingInfo.hasInsufficientMedicines()) {
                handleInsufficientStock(processingInfo);
            }

            System.out.print("Press Enter to continue...");
            sc.nextLine();
            return;
        }

        System.out.print("\nProceed with distribution? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            // Delegate to control layer
            String result = service.processPrescriptionWithFeedback();
            System.out.println(result);
        } else {
            System.out.println("Prescription processing cancelled.");
        }

        System.out.print("Press Enter to continue...");
        sc.nextLine();
    }

    private static void handleInsufficientStock(PharmacyManagement.PrescriptionProcessingInfo processingInfo) {
        System.out.println("\nOptions:");
        System.out.println("1. Create restock request for insufficient medicines");
        System.out.println("2. Exit");

        int choice = readInt("Choose option: ");

        if (choice == 1) {
            // Delegate to control layer
            String result = service.createRestockRequestsForInsufficientMedicines(processingInfo);
            System.out.println(result);
        }
    }

    private static void displayLowStockAlert() {
        System.out.println("\n--- Low Stock Alert ---");
        
        // Get low stock alert information from control layer
        PharmacyManagement.LowStockAlertInfo alertInfo = service.getLowStockAlertInfo();
        
        if (alertInfo.hasNoAlerts()) {
            System.out.println("[OK] All medicines have sufficient stock.");
            return;
        }

        // Display alerts
        if (alertInfo.hasOutOfStockMedicines()) {
            System.out.println("[CRITICAL] OUT OF STOCK MEDICINES:");
            printMedicineHeader();
            System.out.println(alertInfo.getOutOfStockDisplay());
        }

        if (alertInfo.hasLowStockMedicines()) {
            System.out.println("\n[WARNING] LOW STOCK MEDICINES:");
            printMedicineHeader();
            System.out.println(alertInfo.getLowStockDisplay());
        }

        if (alertInfo.hasRequestInfo()) {
            System.out.println(alertInfo.getRequestInfo());
            return;
        }

        System.out.print("\nWould you like to create restock requests for medicines without pending requests? (y/n): ");
        String response = sc.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            handleRestockRequestCreation(alertInfo);
        }
    }

    private static void handleRestockRequestCreation(PharmacyManagement.LowStockAlertInfo alertInfo) {
        System.out.println("\nSelect medicine to create restock request:");
        System.out.println(alertInfo.getAvailableForRequestDisplay());
        System.out.println("0. Exit");

        int maxChoice = alertInfo.getAvailableForRequestCount();
        int choice = readInt("Select medicine (1-" + maxChoice + "): ");
        
        if (choice >= 1 && choice <= maxChoice) {
            String result = service.createRestockRequestByChoice(alertInfo, choice - 1);
            System.out.println(result);
        } else if (choice != 0) {
            System.out.println("Invalid choice.");
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

        // Dosage form selection
        String selectedDosageForm = selectDosageForm(base == null ? null : base.getDosageForm());
        m.setDosageForm(selectedDosageForm);

        // Expiry date
        Date expiryDate = readExpiryDate(base == null || base.getExpiryDate() == null ? null : base.getExpiryDate());
        m.setExpiryDate(expiryDate);

        return m;
    }

    private static String selectDosageForm(String currentForm) {
        System.out.println("\nSelect dosage form:");
        String[] dosageForms = UtilityClass.DOSAGE_FORMS;
        for (int i = 0; i < dosageForms.length; i++) {
            System.out.println((i + 1) + ". " + dosageForms[i]);
        }

        if (currentForm != null) {
            System.out.println("Current: " + currentForm);
        }

        int formChoice = readIntWithDefault("Select dosage form (1-" + dosageForms.length + ")", 
                                           currentForm == null ? 1 : findFormIndex(currentForm) + 1);
        if (formChoice >= 1 && formChoice <= dosageForms.length) {
            return dosageForms[formChoice - 1];
        } else {
            return "tablet"; // default
        }
    }

    private static Date readExpiryDate(Date currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        
        while (true) {
            String prompt = "Expiry date (dd/MM/yyyy)";
            String def = currentDate == null ? null : sdf.format(currentDate);
            String s = readLineWithDefault(prompt, def);
            try {
                return sdf.parse(s);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }
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

        // Get stock request creation info from control layer
        PharmacyManagement.StockRequestCreationInfo creationInfo
                = service.getStockRequestCreationInfo();

        if (creationInfo.hasNoMedicines()) {
            System.out.println("No medicines in inventory.");
            return;
        }

        System.out.println(creationInfo.getMedicineListDisplay());

        String medicineID = readLine("\nEnter medicine ID for restock request: ");

        // Get medicine info and validation from control layer
        PharmacyManagement.StockRequestInfo requestInfo
                = service.prepareStockRequestInfo(medicineID);

        if (requestInfo == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.println(requestInfo.getDisplayInfo());

        int requestedQuantity = readInt("Enter quantity to request: ");

        // Delegate to control layer
        String result = service.createStockRequestWithValidation(medicineID, requestedQuantity);
        System.out.println(result);
    }

    // View pending stock requests
    private static void viewPendingStockRequests() {
        System.out.println("\n--- Pending Stock Requests ---");
        
        // Get display from control layer
        String display = service.getPendingStockRequestsDisplay();
        System.out.println(display);
    }

    // View stock request history
    private static void viewStockRequestHistory() {
        System.out.println("\n--- Stock Request History ---");
        
        // Get display from control layer
        String display = service.getStockRequestHistoryDisplay();
        System.out.println(display);
    }

    // Process/Approve stock request
    private static void processStockRequest() {
        System.out.println("\n--- Process Stock Request ---");
        
        // Get pending requests display from control layer
        String pendingDisplay = service.getPendingStockRequestsDisplay();
        
        if (pendingDisplay.contains("No pending")) {
            System.out.println(pendingDisplay);
            return;
        }

        System.out.println("Pending requests:");
        printStockRequestHeader();
        System.out.println(pendingDisplay);

        String requestID = readLine("\nEnter request ID to process: ");
        
        // Get request details from control layer
        PharmacyManagement.StockRequestProcessingInfo processingInfo = 
            service.prepareStockRequestProcessing(requestID);
        
        if (processingInfo == null) {
            System.out.println("Request not found.");
            return;
        }

        if (!processingInfo.isPending()) {
            System.out.println("Request is not pending. Current status: " + processingInfo.getStatus());
            return;
        }

        System.out.println(processingInfo.getDetailsDisplay());

        System.out.println("\nChoose action:");
        System.out.println("1. Approve and add stock");
        System.out.println("2. Reject request");
        System.out.println("0. Cancel");

        int action = readInt("Select action: ");

        // Delegate to control layer
        String result = service.processStockRequestAction(requestID, action);
        System.out.println(result);
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
                case 0 -> System.out.println("Returning to Pharmacy MainMenu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void searchByNamePattern() {
        String pattern = readLine("Enter name pattern to search: ");
        MyList<Medicine> results = service.searchMedicinesByPattern(pattern);
        displaySearchResults("medicines matching '" + pattern + "'", (DynamicList<Medicine>) results);
    }

    private static void filterByPriceRange() {
        System.out.println("\n--- Filter by Price Range ---");
        double minPrice = readDouble("Enter minimum price (RM): ");
        double maxPrice = readDouble("Enter maximum price (RM): ");

        MyList<Medicine> results = service.filterByPriceRange(minPrice, maxPrice);
        displaySearchResults("medicines in price range RM" + minPrice + " - RM" + maxPrice,
                (DynamicList<Medicine>) results);
    }

    private static void filterByCategory() {
        System.out.println("\n--- Filter by Category ---");

        // Get available categories from control layer
        String categoriesDisplay = service.getAvailableCategoriesDisplay();
        System.out.println("Available categories:");
        System.out.println(categoriesDisplay);

        String selectedCategory = readLine("Enter category name: ");
        MyList<Medicine> results = service.filterByCategory(selectedCategory);

        displaySearchResults("medicines in category '" + selectedCategory + "'",
                (DynamicList<Medicine>) results);
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
            MyList<Medicine> results = service.filterByDosageForm(selectedForm);

            displaySearchResults("medicines in " + selectedForm + " form",
                    (DynamicList<Medicine>) results);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void filterByManufacturer() {
        System.out.println("\n--- Filter by Manufacturer ---");

        // Get available manufacturers from control layer
        String manufacturersDisplay = service.getAvailableManufacturersDisplay();
        System.out.println("Available manufacturers:");
        System.out.println(manufacturersDisplay);

        String selectedManufacturer = readLine("Enter manufacturer name: ");
        MyList<Medicine> results = service.filterByManufacturer(selectedManufacturer);

        displaySearchResults("medicines from '" + selectedManufacturer + "'",
                (DynamicList<Medicine>) results);
    }

    private static void findNearExpiryMedicines() {
        System.out.println("\n--- Find Near Expiry Medicines ---");
        int months = readInt("Enter number of months from now: ");

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
        Double minPrice = null;
        if (!minPriceStr.isEmpty()) {
            try {
                minPrice = Double.parseDouble(minPriceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid minimum price format. Ignoring this criteria.");
            }
        }

        System.out.print("Maximum price (Enter for no limit): ");
        String maxPriceStr = sc.nextLine().trim();
        Double maxPrice = null;
        if (!maxPriceStr.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(maxPriceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid maximum price format. Ignoring this criteria.");
            }
        }

        DynamicList<Medicine> results = (DynamicList<Medicine>) service.filterByMultipleCriteria(
                namePattern, categoryFilter, manufacturerFilter, minPrice, maxPrice);

        displaySearchResults("medicines matching multiple criteria", results);
    }

    private static void displaySearchResults(String description, DynamicList<Medicine> results) {
        // Get formatted search results from control layer
        PharmacyManagement.SearchResultsInfo searchInfo
                = service.getSearchResultsInfo(results, description);

        System.out.println(searchInfo.getResultsDisplay());
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
                case 0 -> System.out.println("Returning to Pharmacy MainMenu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void displayInventoryStatistics() {
        // Get statistics from control layer
        String statisticsDisplay = service.getInventoryStatisticsDisplay();
        System.out.println(statisticsDisplay);
    }

    private static void displayInventorySummary() {
        // Get summary from control layer
        String summaryDisplay = service.getInventorySummaryDisplay();
        System.out.println(summaryDisplay);
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
                case 0 -> System.out.println("Returning to Pharmacy MainMenu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
    
    private static void cloneInventorySnapshot() {
        System.out.println("\n--- Clone Inventory Snapshot ---");

        // Get snapshot information from control layer
        PharmacyManagement.SnapshotInfo snapshotInfo = service.createInventorySnapshotWithInfo();

        if (snapshotInfo.isEmpty()) {
            System.out.println("No medicines to clone.");
            return;
        }

        System.out.println(snapshotInfo.getSummaryDisplay());

        // Display logic only
        System.out.print("Display full snapshot details? (y/n): ");
        String show = sc.nextLine().trim().toLowerCase();
        if (show.equals("y") || show.equals("yes")) {
            System.out.println(snapshotInfo.getFullDisplay());
        }
    }
    
    private static void removeExpiredMedicines() {
        System.out.println("\n--- Remove Expired Medicines ---");

        // Get expired medicines information from control layer
        PharmacyManagement.ExpiredMedicinesInfo expiredInfo = service.getExpiredMedicinesInfo();

        if (expiredInfo.isEmpty()) {
            System.out.println("No expired medicines found.");
            return;
        }

        System.out.println(expiredInfo.getDisplayInfo());

        System.out.print("Remove all expired medicines? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            // Delegate to control layer
            String result = service.removeAllExpiredMedicines();
            System.out.println(result);
        } else {
            System.out.println("Operation cancelled.");
        }
    }
}
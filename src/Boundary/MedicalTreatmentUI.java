/*
    * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
    * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.MedicalTreatmentManagement;
import Entity.*;
import Utility.UtilityClass;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class MedicalTreatmentUI {

    private static final Scanner scanner = new Scanner(System.in);

    // Date format for displaying dates
    private static final SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);

    public static void main(String[] args) {
        medicalTreatmentMainMenu();
    }

    public static void medicalTreatmentMainMenu() {
        while (true) {
            System.out.println("\n=== Medical Treatment Management System ===");
            System.out.println("1. Diagnosis Management");
            System.out.println("2. Medical Treatment Management");
            System.out.println("3. Exit to Main Menu");

            System.out.print("Enter your choice (1-4): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        DiagnosisUI.diagnosisMenu();
                    case 2 ->
                        medicalTreatmentMenu();
                    case 3 -> {
                        System.out.println("Exiting to Main Menu...");
                        scanner.close(); // Close the scanner before exiting
                        return; // Exit to main menu
                    }
                    default -> {
                        System.out.println("Invalid Choice. Please enter again from 1 to 5.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid Input. Please enter a number.");
                //clear buffer
                scanner.nextLine();
            }
        }
    }

    //medical treatment menu
    public static void medicalTreatmentMenu() {
        while (true) {
            System.out.println("\n=== Medical Treatment Management System ===");
            System.out.println("1. Create New Medical Treatment");
            System.out.println("2. View Treatment History By Patient ID");
            System.out.println("3. View Treatment History By Treatment ID");
            System.out.println("4. Update Treatment History");
            System.out.println("5. Generate Monthly Report");
            System.out.println("6. Generate Overall Treatment History Report");
            System.out.println("7. Exit to Main Menu");

            System.out.print("Enter your choice (1-6): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        createTreatmentTest();
                    case 2 ->
                        viewPatientTreatmentHistoryByPatientId();
                    case 3 ->
                        viewSpecificTreatmentHistory();
                    case 4 ->
                        updateTreatmentStatus();
                    case 5 ->
                        generateMonthlyReport();
                    case 6 -> 
                        generateTreatmentReports();
                    case 7 -> {
                        System.out.println("Exiting to Main Menu...");
                        medicalTreatmentMainMenu(); // Exit to main menu
                    }
                    default -> {
                        System.out.println("Invalid Choice. Please enter again from 1 to 5.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid Input. Please enter a number.");
                //clear buffer
                scanner.nextLine();
            }
        }
    }

    // Method to create a new medical treatment with dummy data
    // This method is for testing purposes and can be removed later
    public static void createTreatmentTest() {
        System.out.println("\n=== Create Medical Treatment ===");

        // Create dummy medicine list with multiple items
        DynamicList<MedicalTreatmentItem> medicineList = new DynamicList<>();

        // Add multiple dummy medicines for testing
        MedicalTreatmentItem medicine1 = new MedicalTreatmentItem(
                "Paracetamol", "500mg", "Twice a day", "5 days", "Oral");
        MedicalTreatmentItem medicine2 = new MedicalTreatmentItem(
                "Amoxicillin", "250mg", "Three times a day", "7 days", "Oral");
        MedicalTreatmentItem medicine3 = new MedicalTreatmentItem(
                "Ibuprofen", "200mg", "As needed", "3 days", "Oral");

        medicineList.add(medicine1);
        medicineList.add(medicine2);
        medicineList.add(medicine3);

        // Dummy data for other fields
        Date treatmentDate = new Date();
        String treatmentAdvice = "Follow the prescribed dosage and frequency. Take medicines after meals. Drink plenty of water and get adequate rest.";

        // Dummy consultation data
        String consultationId = "C001";
        String patientId = "P001";
        String doctorId = "D001";

        // Create a new MedicalTreatment object
        MedicalTreatment treatment = new MedicalTreatment(consultationId, patientId, doctorId,
                treatmentDate, "Active", treatmentAdvice, medicineList, "Pending");

        // Add the new MedicalTreatment object to the treatmentList
        MedicalTreatmentManagement.addMedicalTreatment(treatment);

        String notes = "Patient responded well to initial consultation. No allergic reactions reported.";

        TreatmentHistory history = new TreatmentHistory(treatment.getTreatmentId(), consultationId, patientId, doctorId,
                treatmentDate, null, "Ongoing", "Active", treatmentAdvice, notes, medicineList);

        // Add the new TreatmentHistory object to the historyList
        MedicalTreatmentManagement.addTreatmentHistory(history);

        // Display creation summary
        System.out.println("Treatment Date: " + sdf.format(treatmentDate));
        System.out.println("Treatment Advice: " + treatmentAdvice);
        System.out.println("\nDummy Medicines Added:");
        for (int i = 0; i < medicineList.size(); i++) {
            MedicalTreatmentItem item = medicineList.get(i);
            System.out.println("  " + (i + 1) + ". " + item.getMedicineName() + " - " + item.getDosage());
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println(">> Medical Treatment created successfully!");
        System.out.println("Patient ID: " + patientId);
        System.out.println("Treatment ID: " + treatment.getTreatmentId());
        System.out.println("Total medicines prescribed: " + medicineList.size());
        System.out.println("=".repeat(50));
    }

    
    // Method to create a new medical treatment
    // This method will prompt the user for input and create a new treatment record
    public static void createTreatment1() {

        System.out.println("\n=== Create Medical Treatment ===");

        DynamicList<MedicalTreatmentItem> medicineList = new DynamicList<>();

        int i = 1;
        // if it is medication, ask for the medicine name and for many day(s)

        //the doctor have to enter a list of medicine and its details, 
        //if doctor enter a "x" means that enough for medicine
        System.out.println("Medicine List (Press 'x' to stop):");
        while (true) {

            System.out.print("[ " + i + " ] " + "Medicine Name:");
            String medicineName = scanner.nextLine();
            if (medicineName.equals("x")) {
                break;
            }

            System.out.print("Dosage: ");
            String dosage = scanner.nextLine();
            if (dosage.equals("x")) {
                break;
            }

            System.out.print("Frequency: ");
            String frequency = scanner.nextLine();
            if (frequency.equals("x")) {
                break;
            }

            System.out.print("Duration: ");
            String duration = scanner.nextLine();
            if (duration.equals("x")) {
                break;
            }

            System.out.print("Method: ");
            String method = scanner.nextLine();
            if (method.equals("x")) {
                break;
            }

            //put the medicine details into the medicineList
            MedicalTreatmentItem item = new MedicalTreatmentItem(medicineName, dosage, frequency,
                    duration, method);
            medicineList.add(item);

            System.out.println("Medicine added: " + medicineName);
            i++;
        }

        // Show summary of added medicines
        if (medicineList.isEmpty()) {
            System.out.println("No medicines added to the treatment.");
        } else {
            System.out.println("\nTotal medicines added: " + medicineList.size());
        }

        // Get the treatment date automatically(use the format in the utility class)
        System.out.println("Treatment Date: " + sdf.format(new Date()));
        Date treatmentDate = new Date();

        System.out.print("Enter Medical Treatment Advice(s): ");
        // String treatmentAdvice = scanner.nextLine();

        // Consultation currentConsultation = Consultation.getCurrentConsultation();
        // String consultationId = currentConsultation.getConsultationId();
        // String patientId = currentConsultation.getPatientId();
        // String doctorId = currentConsultation.getDoctorId();
        // sample data for consultation and patient
        String consultationId = "C001";
        String patientId = "P001";
        String doctorId = "D001";
        String treatmentAdvice = "Follow the prescribed dosage and frequency. Drink plenty of water.";

        // Create a new MedicalTreatment object
        MedicalTreatment treatment = new MedicalTreatment(consultationId, patientId, doctorId,
                treatmentDate, "Active", treatmentAdvice, medicineList, "Pending");

        // Add the new MedicalTreatment object to the treatmentList
        MedicalTreatmentManagement.addMedicalTreatment(treatment);

        // Create treatment history
        TreatmentHistory history = new TreatmentHistory(treatment.getTreatmentId(), consultationId, patientId, doctorId,
                treatmentDate, null, "Ongoing", "Active", treatmentAdvice, medicineList);

        // Add the new TreatmentHistory object to the historyList
        MedicalTreatmentManagement.addTreatmentHistory(history);

        System.out.println("Medical Treatment created successfully! Treatment ID: " + treatment.getTreatmentId());
    }

    //View Patient Treatment History
    public static void viewPatientTreatmentHistoryByPatientId() {
        System.out.println("\n=== Patient Treatment History ===");
        System.out.print("Enter Treatment ID: ");
        String patientId = scanner.nextLine().trim();

        if (patientId.isEmpty()) {
            System.out.println("Treatment ID cannot be empty.");
            return;
        }

        // Get all treatment history for the patient
        DynamicList<TreatmentHistory> treatmentHistoryList
                = MedicalTreatmentManagement.getTreatmentHistoryByPatientIdList(patientId);

        if (treatmentHistoryList.isEmpty()) {
            System.out.println("No treatment history found for Patient ID: " + patientId);
            return;
        }

        System.out.println("\n" + "=".repeat(68));
        System.out.println("PATIENT TREATMENT HISTORY - PATIENT ID: " + patientId);
        System.out.println("Total Records Found: " + treatmentHistoryList.size());
        System.out.println("=".repeat(68));

        for (int i = 0; i < treatmentHistoryList.size(); i++) {
            TreatmentHistory history = treatmentHistoryList.get(i);
            System.out.println("\nRecord " + (i + 1) + " of " + treatmentHistoryList.size() + ":");
            System.out.println(history.toString()); // Using the enhanced toString method

            if (i < treatmentHistoryList.size() - 1) {
                System.out.println("\n" + "+".repeat(68));
                // UtilityClass.pressEnterToContinue();
            }
        }

        System.out.println("\n" + "=".repeat(68));
        System.out.println("End of treatment history for Patient ID: " + patientId);
    }

    // New method to view a specific treatment history by Treatment ID
    public static void viewSpecificTreatmentHistory() {
        System.out.println("\n=== View Specific Treatment History ===");
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine().trim();

        if (treatmentId.isEmpty()) {
            System.out.println("Treatment ID cannot be empty.");
            return;
        }

        TreatmentHistory history = MedicalTreatmentManagement.getTreatmentHistoryById(treatmentId);

        if (history == null) {
            System.out.println("No treatment history found for Treatment ID: " + treatmentId);
            return;
        }

        System.out.println("\n" + "=".repeat(68));
        System.out.println("TREATMENT HISTORY DETAILS");
        System.out.println("=".repeat(68));
        System.out.println(history.toString()); // Using the enhanced toString method
    }

    // updateTreatmentStatus method
    public static void updateTreatmentStatus() {
        System.out.println("\n=== Update Treatment Status ===");
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine().trim();

        if (treatmentId.isEmpty()) {
            System.out.println("Treatment ID cannot be empty.");
            return;
        }

        TreatmentHistory treatment = MedicalTreatmentManagement.getTreatmentHistoryById(treatmentId);

        if (treatment == null) {
            System.out.println("No treatment found with ID: " + treatmentId);
            return;
        }

        // Display current treatment details using toString
        System.out.println("\nCurrent Treatment Details:");
        System.out.println(treatment.toString());

        System.out.println("\n" + "=".repeat(50));
        System.out.print("Would you like to update this treatment? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Update cancelled.");
            return;
        }

        // Update Status
        System.out.println("\nUpdate Treatment Status:");
        System.out.println("[1] Active");
        System.out.println("[2] Completed");
        System.out.println("[3] Cancelled");
        System.out.print("Enter new status (1-3): ");

        try {
            int statusChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            String newStatus;
            switch (statusChoice) {
                case 1 ->
                    newStatus = "Active";
                case 2 ->
                    newStatus = "Completed";
                case 3 ->
                    newStatus = "Cancelled";
                default -> {
                    System.out.println("Invalid choice. Status update cancelled.");
                    return;
                }
            }
            treatment.setStatus(newStatus);
            System.out.println(">> Treatment status updated to: " + newStatus);

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number between 1-3.");
            scanner.nextLine(); // Clear buffer
            return;
        }

        // Update Treatment Outcome
        System.out.println("\nUpdate Treatment Outcome:");
        System.out.println("[1] Successful");
        System.out.println("[2] Partial");
        System.out.println("[3] Failed");
        System.out.println("[4] Ongoing");
        System.out.print("Enter new outcome (1-4): ");

        try {
            int outcomeChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            String newOutcome;
            switch (outcomeChoice) {
                case 1 ->
                    newOutcome = "Successful";
                case 2 ->
                    newOutcome = "Partial";
                case 3 ->
                    newOutcome = "Failed";
                case 4 ->
                    newOutcome = "Ongoing";
                default -> {
                    System.out.println("Invalid choice. Outcome update cancelled.");
                    return;
                }
            }
            treatment.setTreatmentOutcome(newOutcome);
            System.out.println(">> Treatment outcome updated to: " + newOutcome);

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number between 1-4.");
            scanner.nextLine(); // Clear buffer
            return;
        }

        // Update Follow-Up Date
        System.out.print("Enter Follow-Up Date (" + UtilityClass.DATE_FORMAT + ") or press Enter to skip: ");
        String followUpDateStr = scanner.nextLine();

        if (!followUpDateStr.trim().isEmpty()) {
            Date followUpDate = UtilityClass.parseDate(followUpDateStr);
            if (followUpDate == null) {
                System.out.println("Invalid date format. Follow-up date not updated.");
            } else {
                treatment.setFollowUpDate(followUpDate);
                System.out.println(">> Follow-Up Date updated to: " + sdf.format(followUpDate));
            }
        }

        // Update Additional Notes
        System.out.print("Enter Additional Notes (or press Enter to skip): ");
        String notes = scanner.nextLine();
        if (!notes.trim().isEmpty()) {
            treatment.setNotes(notes);
            System.out.println(">> Notes updated successfully.");
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("TREATMENT UPDATE COMPLETED SUCCESSFULLY!");
        System.out.println("=".repeat(50));

        // Display updated treatment details
        System.out.println("\nUpdated Treatment Details:");
        System.out.println(treatment.toString());
    }

    // Generate patient history report based on month
    public static void generateMonthlyReport() {

        System.out.println("\n\n=== Generate Monthly Treatment Report ===");

        // show the available year first
        DynamicList<Integer> availableYears = MedicalTreatmentManagement.getAvailableYears();
        if(availableYears.isEmpty()) {
            System.out.println("No treatment history found.");
            return;
        }

        System.out.print("Enter the year: ");
        String yearInput = scanner.nextLine();

        int year;
        try {
            year = Integer.parseInt(yearInput);
            if(year<2020 || year>Year.now().getValue() + 1) {
                System.out.println("Please enter a valid year from 2020 until now.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a year.");
            return;
        }

        // get the month input
        System.out.print("Enter the month (01-12): ");
        String monthInput = scanner.nextLine().trim();

        //ensure the format match the history
        if(monthInput.length() == 1) {
            monthInput = "0" + monthInput;
        }

        int month;
        try {
            month = Integer.parseInt(monthInput);
            if(month < 1 || month > 12) {
                System.out.println("Invalid input. Please enter a month between 01-12.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a month between 01-12.");
            return;
        }

        // generate the report method
        generateMonthReportDetails(year, month);
    }

    private static void generateMonthReportDetails(int year, int month) {
        String monthName = UtilityClass.getMonthName(month);
        String monthYearStr = monthName + " " + year;

        System.out.println("\n==============================================================");
        System.out.println(">                    MONTHLY PATIENT REPORT                   <");
        System.out.println("===============================================================");

        System.out.printf("Reporting Period: %s | Generated: %s\n", monthYearStr, sdf.format(new Date()));
        System.out.println("===============================================================");

        DynamicList<TreatmentHistory> monthlyTreatments = MedicalTreatmentManagement.getMonthlyTreatments(year, month);

        if (monthlyTreatments.isEmpty()) {
            System.out.println("No treatments found for " + monthYearStr + ".");
            return;
        }

        System.out.printf("Total Treatments in %s: %d\n", monthYearStr, monthlyTreatments.size());
        System.out.println("===============================================================");
        
        for(int i = 0;i<monthlyTreatments.size();i++) {
            TreatmentHistory treatment = monthlyTreatments.get(i);
            System.out.println("\nTreatment Record " + (i + 1) + ":");
            System.out.println(treatment.toString());
        }

        System.out.println(">> Monthly report generated successfully for " + monthYearStr + "!");
    }

    //Generate Treatment Reports
    public static void generateTreatmentReports() {
    System.out.println("\n=== Generate Treatment Summary Report ===");

    // Prompt user to enter the year
    System.out.print("Enter the year (e.g., 2025): ");
    String yearInput = scanner.nextLine().trim();

    int year;
    try {
        year = Integer.parseInt(yearInput);
        if (year < 2020 || year > Year.now().getValue()) {
            System.out.println("Please enter a valid year between 2020 and the current year.");
            return;
        }
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid year.");
        return;
    }

    // Prompt user to enter the month
    System.out.print("Enter the month (01-12): ");
    String monthInput = scanner.nextLine().trim();

    if (monthInput.length() == 1) {
        monthInput = "0" + monthInput; // Ensure the month is in two-digit format
    }

    int month;
    try {
        month = Integer.parseInt(monthInput);
        if (month < 1 || month > 12) {
            System.out.println("Invalid input. Please enter a month between 01 and 12.");
            return;
        }
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid month.");
        return;
    }

    // Retrieve treatments for the specified year and month
    DynamicList<TreatmentHistory> treatments = MedicalTreatmentManagement.getMonthlyTreatments(year, month);

    if (treatments.isEmpty()) {
        System.out.printf("No treatments found for %s %d.\n", UtilityClass.getMonthName(month), year);
        return;
    }

    // Initialize counters and organize treatments by status
    int activeCount = 0, completedCount = 0, cancelledCount = 0;
    StringBuilder activeTreatments = new StringBuilder();
    StringBuilder completedTreatments = new StringBuilder();
    StringBuilder cancelledTreatments = new StringBuilder();

    for (int i = 0; i < treatments.size(); i++) {
        TreatmentHistory treatment = treatments.get(i);
        String treatmentDetails = String.format(
            "Treatment ID: %s | Patient ID: %s | Doctor ID: %s | Date: %s\n",
            treatment.getTreatmentId(),
            treatment.getPatientId(),
            treatment.getDoctorId(),
            sdf.format(treatment.getTreatmentDate())
        );

        switch (treatment.getStatus()) {
            case "Active" -> {
                activeCount++;
                activeTreatments.append(treatmentDetails);
            }
            case "Completed" -> {
                completedCount++;
                completedTreatments.append(treatmentDetails);
            }
            case "Cancelled" -> {
                cancelledCount++;
                cancelledTreatments.append(treatmentDetails);
            }
            default -> {
                System.out.println("Unknown treatment status: " + treatment.getStatus());
            }
        }
    }

    // Display the report
    System.out.println("\n==============================================================");
    System.out.printf("Treatment Summary Report for %s %d\n", UtilityClass.getMonthName(month), year);
    System.out.println("==============================================================");
    System.out.printf("Total Treatments: %d\n", treatments.size());
    System.out.printf("Active Treatments: %d\n", activeCount);
    System.out.printf("Completed Treatments: %d\n", completedCount);
    System.out.printf("Cancelled Treatments: %d\n", cancelledCount);
    System.out.println("==============================================================");

    if (activeCount > 0) {
        System.out.println("\n--- Active Treatments ---");
        System.out.println(activeTreatments);
    }

    if (completedCount > 0) {
        System.out.println("\n--- Completed Treatments ---");
        System.out.println(completedTreatments);
    }

    if (cancelledCount > 0) {
        System.out.println("\n--- Cancelled Treatments ---");
        System.out.println(cancelledTreatments);
    }

    System.out.println("==============================================================");
    System.out.println(">> Treatment summary report generated successfully!");
}
}

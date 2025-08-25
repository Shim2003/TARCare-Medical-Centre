/*
    * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
    * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import ADT.MyList;
import Control.DiagnosisManagement;
import Control.MedicalTreatmentManagement;
import Control.PharmacyManagement;
import Control.PrescriptionCalculator;
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

            System.out.print("Enter your choice (1-3): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        DiagnosisUI.diagnosisMenu();
                    case 2 ->
                        medicalTreatmentMenu();
                    case 3 -> {
                        System.out.println("Exiting...");
                        TARCareMedicalCentre.adminMainMenu();
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
            System.out.println("1. View Treatment History");
            System.out.println("2. Update Treatment History");
            System.out.println("3. Delete Treatment History");
            System.out.println("4. Generate Monthly Report");
            System.out.println("5. Generate Overall Treatment History Report");
            System.out.println("6. Exit to Main Menu");

            System.out.print("Enter your choice (1-6): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        viewTreatmentHistoryMenu();
                    case 2 ->
                        updateTreatmentStatus();
                    case 3 ->
                        deleteTreatmentHistoryMenu();
                    case 4 ->
                        generateMonthlyReport();
                    case 5 -> 
                        generateTreatmentReports();
                    case 6 -> {
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

    public static void viewTreatmentHistoryMenu() {
        while (true) {
            System.out.println("\n=== View Medical Treatment ===");
            System.out.println("1. View Treatment History By Patient ID");
            System.out.println("2. View Treatment History By Treatment ID");
            System.out.println("3. Exit to Previous Menu");

            System.out.print("Enter your choice (1-3): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        viewPatientTreatmentHistoryByPatientId();
                    case 2 ->
                        viewSpecificTreatmentHistoryByTreatmentId();
                    case 3 -> {
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

    public static void deleteTreatmentHistoryMenu() {
        while (true) {
            System.out.println("\n=== Delete Medical Treatment ===");
            System.out.println("1. Delete Treatment History By Treatment ID");
            System.out.println("2. Delete All Treatment History By Patient ID");
            System.out.println("3. Delete All Treatment History");
            System.out.println("4. Exit to Previous Menu");

            System.out.print("Enter your choice (1-4): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        deleteTreatmentById();
                    case 2 ->
                        viewPatientTreatmentHistoryByPatientId();
                    case 3 ->
                        deleteAllTreatmentHistory();
                    case 4 -> {
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
        String diagnosisId = "DIAG001";
        String consultationId = "C1001";
        String patientId = "P1001";
        String doctorId = "D1001";
        String notes = "Patient responded well to initial consultation. No allergic reactions reported.";

        // Create a new MedicalTreatment object
        MedicalTreatment treatment = new MedicalTreatment(diagnosisId, patientId, doctorId,
                treatmentDate, treatmentDate, "Active", "Ongoing", treatmentAdvice, notes, medicineList);

        // Add the new MedicalTreatment object to the treatmentList
        MedicalTreatmentManagement.addMedicalTreatment(treatment);

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
    public static void createTreatment() {

        System.out.println("\n=== Create Medical Treatment ===");

        MyList<MedicalTreatmentItem> medicineList = new DynamicList<>();

        //print the diagnosis id
        String diagnosisId = DiagnosisManagement.getCurrentDiagnosisId();
        System.out.print("Enter Diagnosis ID: " + diagnosisId + "\n");

        // Get the current serving patient ID from the queue list
        String patientId = DiagnosisManagement.getCurrentServingPatient();
        System.out.println("Patient ID: " + patientId);

        // Get the current serving doctor ID from the queue list
        String doctorId = DiagnosisManagement.getCurrentServingDoctor();
        System.out.println("Doctor ID: " + doctorId);

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
            MedicalTreatmentItem item = new MedicalTreatmentItem(medicineName, dosage, frequency, duration, method);
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
        String treatmentAdvice = scanner.nextLine();

        System.out.print("Enter Additional Notes: ");
        String notes = scanner.nextLine();

        // pass the medicineList to prescription by constructor
        
        // Create a new MedicalTreatment object
        MedicalTreatment treatment = new MedicalTreatment(diagnosisId, patientId, doctorId,
                treatmentDate, treatmentDate, "Active", "Ongoing", treatmentAdvice, notes, medicineList);


        Prescription prescription = new Prescription(PrescriptionCalculator.generateNewPrescriptionId(), patientId,
        doctorId, medicineList, "PENDING");

        PharmacyManagement.addToQueue(prescription);

        // Add the new MedicalTreatment object to the treatmentList
        MedicalTreatmentManagement.addMedicalTreatment(treatment);

        System.out.println("Medical Treatment created successfully! Treatment ID: " + treatment.getTreatmentId());
    }

    //View Patient Treatment History
    public static void viewPatientTreatmentHistoryByPatientId() {
        StringBuilder sb = new StringBuilder();
        System.out.println("\n=== Patient Treatment History ===");
        System.out.print("Enter Treatment ID: ");
        String patientId = scanner.nextLine().trim();

        if (patientId.isEmpty()) {
            System.out.println("Treatment ID cannot be empty.");
            return;
        }

        // Get all treatment history for the patient
        MyList<MedicalTreatment> treatmentHistoryList
                = MedicalTreatmentManagement.getTreatmentHistoryByPatientIdList(patientId);

        if (MedicalTreatmentManagement.isTreatmentListEmpty() || treatmentHistoryList.isEmpty()) {
            System.out.println("No treatment history found for Patient ID: " + patientId);
            return;
        }

        System.out.println("\n" + "=".repeat(68));
        System.out.println("PATIENT TREATMENT HISTORY - PATIENT ID: " + patientId);
        System.out.println("Total Records Found: " + MedicalTreatmentManagement.getTreatmentListSize());
        System.out.println("=".repeat(68));

        for (int i = 0; i < treatmentHistoryList.size(); i++) {
            MedicalTreatment history = treatmentHistoryList.get(i);
            System.out.println("\nRecord " + (i + 1) + " of " + MedicalTreatmentManagement.getTreatmentListSize() + ":");
            treatmentHistoryDisplayForm(history.getTreatmentId());

            if (i < MedicalTreatmentManagement.getTreatmentListSize() - 1) {
                System.out.println("\n" + "+".repeat(68));
                // UtilityClass.pressEnterToContinue();
            }
        }

        System.out.println("\n" + "=".repeat(68));
        System.out.println("End of treatment history for Patient ID: " + patientId);
    }

    // New method to view a specific treatment history by Treatment ID
    public static void viewSpecificTreatmentHistoryByTreatmentId() {
        System.out.println("\n=== View Specific Treatment History ===");
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine().trim();

        if (treatmentId.isEmpty()) {
            System.out.println("Treatment ID cannot be empty.");
            return;
        }

        MedicalTreatment history = MedicalTreatmentManagement.getTreatmentHistoryById(treatmentId);

        if (history == null) {
            System.out.println("No treatment history found for Treatment ID: " + treatmentId);
            return;
        }

        System.out.println("\n" + "=".repeat(68));
        System.out.println("TREATMENT HISTORY DETAILS");
        System.out.println("=".repeat(68));
        treatmentHistoryDisplayForm(treatmentId);
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

        MedicalTreatment treatment = MedicalTreatmentManagement.getTreatmentHistoryById(treatmentId);

        if (treatment == null) {
            System.out.println("No treatment found with ID: " + treatmentId);
            return;
        }

        // Display current treatment details using toString
        System.out.println("\nCurrent Treatment Details:");
        treatmentHistoryDisplayForm(treatmentId);

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
            treatment.setTreatmentStatus(newStatus);
            System.out.println(">> Treatment status updated to: " + newStatus);

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number between 1-3.");
            scanner.nextLine(); // Clear buffer
            return;
        }

        // Update Treatment Outcome
        System.out.println("\nUpdate Treatment Outcome:");
        System.out.println("[1] Successful");
        System.out.println("[2] Needs Follow-up");
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
                    newOutcome = "Needs Follow-up";
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
        treatmentHistoryDisplayForm(treatmentId);
    }

    public static void deleteTreatmentById() {
        System.out.println("\n=== Delete Treatment By ID ===");
        System.out.print("Enter Treatment ID to delete: ");
        String treatmentId = scanner.nextLine().trim();

        if (treatmentId.isEmpty()) {
            System.out.println("Treatment ID cannot be empty.");
            return;
        }

        System.out.print("Are you sure you want to delete Treatment ID " + treatmentId + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            boolean deleted = MedicalTreatmentManagement.deleteTreatmentById(treatmentId);
            if (deleted) {
                System.out.println(">> Treatment ID " + treatmentId + " deleted successfully.");
            } else {
                System.out.println("No treatment found with ID: " + treatmentId);
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public static void deleteAllTreatmentsByPatientId() {
        System.out.println("\n=== Delete All Treatments By Patient ID ===");
        System.out.print("Enter Patient ID to delete all treatments: ");
        String patientId = scanner.nextLine().trim();

        if (patientId.isEmpty()) {
            System.out.println("Patient ID cannot be empty.");
            return;
        }

        System.out.print("Are you sure you want to delete all treatments for Patient ID " + patientId + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            boolean deletedCount = MedicalTreatmentManagement.deleteAllTreatmentsByPatientId(patientId);
            if (deletedCount) {
                System.out.println(">> All treatments for Patient ID " + patientId + " deleted successfully. Total deleted: " + deletedCount);
            } else {
                System.out.println("No treatments found for Patient ID: " + patientId);
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // delete all the treatment history in once
    public static void deleteAllTreatmentHistory() {
        System.out.println("\n=== Delete All Treatment History ===");
        System.out.print("Are you sure you want to delete ALL treatment history? This action cannot be undone. (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            MedicalTreatmentManagement.deleteAllTreatments();
            System.out.println(">> All treatment history deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // Generate patient history report based on month
    public static void generateMonthlyReport() {

        System.out.println("\n\n=== Generate Monthly Treatment Report ===");

        // show the available year first
        MyList<Integer> availableYears = MedicalTreatmentManagement.getAvailableYears();
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

        MyList<MedicalTreatment> monthlyTreatments = MedicalTreatmentManagement.getMonthlyTreatments(year, month);

        if (monthlyTreatments.isEmpty()) {
            System.out.println("No treatments found for " + monthYearStr + ".");
            return;
        }

        System.out.printf("Total Treatments in %s: %d\n", monthYearStr, monthlyTreatments.size());
        System.out.println("===============================================================");
        
        for(int i = 0;i<monthlyTreatments.size();i++) {
            MedicalTreatment treatment = monthlyTreatments.get(i);
            System.out.println("\nTreatment Record " + (i + 1) + ":");
            treatmentHistoryDisplayForm(treatment.getTreatmentId());
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
    MyList<MedicalTreatment> treatments = MedicalTreatmentManagement.getMonthlyTreatments(year, month);

    if (MedicalTreatmentManagement.isTreatmentListEmpty() || treatments.isEmpty()) {
        System.out.printf("No treatments found for %s %d.\n", UtilityClass.getMonthName(month), year);
        return;
    }

    // Initialize counters and organize treatments by status
    int activeCount = 0, completedCount = 0, cancelledCount = 0;
    StringBuilder activeTreatments = new StringBuilder();
    StringBuilder completedTreatments = new StringBuilder();
    StringBuilder cancelledTreatments = new StringBuilder();

    for (int i = 0; i < MedicalTreatmentManagement.getTreatmentListSize(); i++) {
        MedicalTreatment treatment = treatments.get(i);
        String treatmentDetails = String.format(
            "Treatment ID: %s | Patient ID: %s | Doctor ID: %s | Date: %s\n",
            treatment.getTreatmentId(),
            treatment.getPatientId(),
            treatment.getDoctorId(),
            sdf.format(treatment.getTreatmentDate())
        );

        switch (treatment.getTreatmentStatus()) {
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
                System.out.println("Unknown treatment status: " + treatment.getTreatmentStatus());
            }
        }
    }

    // Display the report
    System.out.println("\n==============================================================");
    System.out.printf("Treatment Summary Report for %s %d\n", UtilityClass.getMonthName(month), year);
    System.out.println("==============================================================");
    System.out.printf("Total Treatments: %d\n", MedicalTreatmentManagement.getTreatmentListSize());
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

    public static void treatmentHistoryDisplayForm(String treatmentId) {

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        MedicalTreatment treatment = MedicalTreatmentManagement.getTreatmentHistoryById(treatmentId);

        sb.append("==================================================================\n");
        sb.append(">                    TREATMENT HISTORY RECORD                    <\n");
        sb.append("==================================================================\n");
        sb.append(String.format("> Treatment ID    : %-44s <\n", treatment.getTreatmentId()));
        sb.append(String.format("> Patient ID      : %-44s <\n", treatment.getPatientId()));
        sb.append(String.format("> Doctor ID       : %-44s <\n", treatment.getDoctorId()));
        sb.append("==================================================================\n");
        sb.append(String.format("> Treatment Date  : %-44s <\n", 
            treatment.getTreatmentDate() != null ? dateFormatter.format(treatment.getTreatmentDate()) : "Not specified"));
        sb.append(String.format("> Follow-up Date  : %-44s <\n", 
            treatment.getFollowUpDate() != null ? dateFormatter.format(treatment.getFollowUpDate()) : "Not scheduled"));
        sb.append("==================================================================\n");
        sb.append(String.format("> Status          : %-44s <\n", treatment.getTreatmentStatus()));
        sb.append(String.format("> Outcome         : %-44s <\n", treatment.getTreatmentOutcome()));
        sb.append("==================================================================\n");
        
        // Treatment Advice
        sb.append("> Treatment Advice:                                              <\n");
        if (treatment.getMedicalTreatmentAdvise() != null && !treatment.getMedicalTreatmentAdvise().trim().isEmpty()) {
            String[] adviceLines = wrapText(treatment.getMedicalTreatmentAdvise(), 60);
            for (String line : adviceLines) {
                sb.append(String.format("> %-62s <\n", line));
            }
        } else {
            sb.append("> No advice provided                                             <\n");
        }
        
        // Notes
        if (treatment.getNotes() != null && !treatment.getNotes().trim().isEmpty()) {
            sb.append("==================================================================\n");
            sb.append("> Additional Notes:                                              <\n");
            String[] notesLines = wrapText(treatment.getNotes(), 60);
            for (String line : notesLines) {
                sb.append(String.format("> %-62s <\n", line));
            }
        }
        
        // Medicine List
        sb.append("==================================================================\n");
        sb.append("> PRESCRIBED MEDICATIONS:                                        <\n");
        sb.append("==================================================================\n");
        
        if (treatment.getMedicineList() != null && !treatment.getMedicineList().isEmpty()) {
            for (int i = 0; i < treatment.getMedicineList().size(); i++) {
                MedicalTreatmentItem medicine = treatment.getMedicineList().get(i);
                sb.append(String.format("> %d. %-59s <\n", (i + 1), medicine.getMedicineName()));
                sb.append(String.format(">    Dosage    : %-48s <\n", medicine.getDosage()));
                sb.append(String.format(">    Frequency : %-48s <\n", medicine.getFrequency()));
                sb.append(String.format(">    Duration  : %-48s <\n", medicine.getDuration()));
                sb.append(String.format(">    Method    : %-48s <\n", medicine.getMethod()));
                if (i < treatment.getMedicineList().size() - 1) {
                    sb.append(">                                                                <\n");
                }
            }
        } else {
            sb.append("> No medications prescribed                                      <\n");
        }
        
        sb.append("==================================================================\n");
    }

    private static String[] wrapText(String text, int maxLineLength) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();

        for (String word : words) {
            if (line.length() + word.length() + 1 > maxLineLength) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
            }
        }
        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines.toArray(new String[0]);
    }
}

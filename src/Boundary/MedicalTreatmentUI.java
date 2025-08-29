/*
    * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
    * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.MyList;
import Control.DiagnosisManagement;
import Control.MedicalTreatmentManagement;
import Control.PharmacyManagement;
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
                        TARCareMedicalCentre.staffMainMenu();
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
            System.out.println("5. Retrieve Treatment Outcome");
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
                        retrieveTreatmentOutcome();
                    case 6 -> {
                        System.out.println("Exiting to Main Menu...");
                        medicalTreatmentMainMenu(); // Exit to main menu
                    }
                    case 7 -> 
                        createTreatment();
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
            System.out.println("3. View Overall Treatment History");
            System.out.println("4. Exit to Previous Menu");

            System.out.print("Enter your choice (1-4): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        viewPatientTreatmentHistoryByPatientId();
                    case 2 ->
                        viewSpecificTreatmentHistoryByTreatmentId();
                    case 3 ->
                        viewTreatmentHistoryByYearAndMonth();
                    case 4 -> {
                        System.out.println("Exiting to Main Menu...");
                        medicalTreatmentMenu(); // Exit to main menu
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
            System.out.println("4. Restore Deleted Treatment History");
            System.out.println("5. Exit to Previous Menu");

            System.out.print("Enter your choice (1-5): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        deleteTreatmentById();
                    case 2 ->
                        deleteAllTreatmentsByPatientId();
                    case 3 ->
                        deleteAllTreatmentHistory();
                    case 4 -> {
                        restoreTreatmentHistory();
                    }
                    case 5 -> {
                        System.out.println("Exiting to Main Menu...");
                        medicalTreatmentMenu(); // Exit to main menu
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

    // Method to create a new medical treatment
    // This method will prompt the user for input and create a new treatment record
    public static void createTreatment() {

        System.out.println("\n=== Create Medical Treatment ===");

        //print the diagnosis id
        String diagnosisId = DiagnosisManagement.getCurrentDiagnosisId();
        System.out.print("Enter Diagnosis ID: " + diagnosisId + "\n");

        // Get the current serving patient ID from the queue list
        String patientId = DiagnosisManagement.getCurrentServingPatient();
        System.out.println("Patient ID: " + patientId);

        // Get the current serving doctor ID from the queue list
        String doctorId = DiagnosisManagement.getCurrentServingDoctor();
        System.out.println("Doctor ID: " + doctorId);

        MedicalTreatmentManagement.initMedicalTreatmentList();

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
            MedicalTreatmentManagement.addMedicine(item);
            System.out.println("Medicine added: " + medicineName);
            i++;
        }

        // Show summary of added medicines
        if (MedicalTreatmentManagement.isMedicineListEmpty()) {
            System.out.println("No medicines added to the treatment.");
        } else {
            System.out.println("\nTotal medicines added: " + MedicalTreatmentManagement.getMedicineListSize());
        }

        // Get the treatment date automatically(use the format in the utility class)
        System.out.println("Treatment Date: " + sdf.format(new Date()));
        Date treatmentDate = new Date();

        System.out.print("Enter Medical Treatment Advice(s): ");
        String treatmentAdvice = scanner.nextLine();

        System.out.print("Enter Additional Notes: ");
        String notes = scanner.nextLine();

        
        
        // Create a new MedicalTreatment object
        MedicalTreatment treatment = new MedicalTreatment(diagnosisId, patientId, doctorId,
                treatmentDate, treatmentDate, "Active", "Ongoing", treatmentAdvice, notes,
                MedicalTreatmentManagement.getMedicineList());

        // pass the medicineList to prescription by constructor
        Prescription prescription = new Prescription(PharmacyManagement.generateNewPrescriptionId(), patientId,
        doctorId, MedicalTreatmentManagement.getMedicineList(), "PENDING");

        PharmacyManagement.addToQueue(prescription);

        // Add the new MedicalTreatment object to the treatmentList
        MedicalTreatmentManagement.addMedicalTreatment(treatment);

        System.out.println("Medical Treatment created successfully! Treatment ID: " + treatment.getTreatmentId());
    }

    //View Patient Treatment History
    public static void viewPatientTreatmentHistoryByPatientId() {
        StringBuilder sb = new StringBuilder();
        System.out.println("\n=== Patient Treatment History ===");
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();

        if (DiagnosisManagement.isIdEmpty(patientId)) {
            System.out.println("Patient ID cannot be empty.");
            return;
        }

        // Get all treatment history for the patient
        MyList<MedicalTreatment> treatmentHistoryList
                = MedicalTreatmentManagement.getTreatmentHistoryByPatientIdList(patientId);

        //print out the treatment history list of checking whether it is empty or not
        if (MedicalTreatmentManagement.isTreatmentListEmpty(treatmentHistoryList)) {
            System.out.println("No treatment history found for Patient ID: " + patientId);
            return;
        }

        System.out.println("\n" + "=".repeat(68));
        System.out.println("PATIENT TREATMENT HISTORY - PATIENT ID: " + patientId);
        System.out.println("Total Records Found: " + MedicalTreatmentManagement.getTreatmentHistoryById(patientId));
        System.out.println("=".repeat(68) + "\n");

        for (int i = 0; i < MedicalTreatmentManagement.getTreatmentListSize(); i++) {
            MedicalTreatment history = MedicalTreatmentManagement.getTreatmentByIndex(treatmentHistoryList, i);
            // display the treatment history list "Treatment history found for ID TRMT1011"
            System.out.println("Treatment history found for ID " + history.getTreatmentId());
        }

        System.out.println("\n" + "=".repeat(68));
        System.out.println("End of treatment history for Patient ID: " + patientId + "\n");

        // allow users to input the treatment id to view the details with validation if users enter wrong then loop back again if enter 'x' then quit directly
        boolean found = true;
        while (found) {
            System.out.print("Enter the Treatment ID to view the details or 'x' to quit: ");
            String treatmentId = scanner.nextLine().trim();

            if (DiagnosisManagement.isIdEmpty(treatmentId)) {
                System.out.println("Treatment ID cannot be empty.");
                return;
            } else if (treatmentId.equals("x")) {
                break;
            } else {
              treatmentHistoryDisplayForm(treatmentId);  
                found = false;
            }

            //Press Enter to continue after view the history
            UtilityClass.pressEnterToContinue();
        }
    }

    // New method to view a specific treatment history by Treatment ID
    public static void viewSpecificTreatmentHistoryByTreatmentId() {
        System.out.println("\n=== View Specific Treatment History ===");
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine().trim();

        if (DiagnosisManagement.isIdEmpty(treatmentId)) {
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

    // view the treatment history details based on the year and month enter by user
    public static void viewTreatmentHistoryByYearAndMonth() {
        System.out.print("\n\nEnter the year: ");
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
        MyList<MedicalTreatment> filteredList = MedicalTreatmentManagement.getTreatmentHistoryByYearAndMonth(year, month);

        if (MedicalTreatmentManagement.isTreatmentListEmpty(filteredList)) {
            System.out.println("No treatment history found for " + year + "-" + String.format("%02d", month));
            return;
        }

        // Table header
        System.out.println("\n=== Treatment History for " + year + "-" + String.format("%02d", month) + " ===\n");
        System.out.printf("%-15s | %-12s | %-15s | %-15s\n", "Treatment ID", "Date", "Status", "Outcome");
        System.out.println("---------------------------------------------------------------");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // full date format

        // Table content
        for (int i = 0; i < MedicalTreatmentManagement.getTreatmentSize(filteredList); i++) {
            MedicalTreatment t = MedicalTreatmentManagement.getTreatmentByIndex(filteredList, i);
            String dateStr = t.getTreatmentDate() != null ? sdf.format(t.getTreatmentDate()) : "N/A";
            String status = t.getTreatmentStatus() != null ? t.getTreatmentStatus() : "N/A";
            String outcome = t.getTreatmentOutcome() != null ? t.getTreatmentOutcome() : "N/A";

            System.out.printf("%-15s | %-12s | %-15s | %-15s\n",
                    t.getTreatmentId(), dateStr, status, outcome);
        }

        System.out.println("---------------------------------------------------------------\n");
    }

    // updateTreatmentStatus method
    public static void updateTreatmentStatus() {
        System.out.println("\n=== Update Treatment Status ===");
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine().trim();

        if (DiagnosisManagement.isIdEmpty(treatmentId)) {
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
            MedicalTreatmentManagement.setTreatmentStatus(treatment, newStatus);
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
            MedicalTreatmentManagement.setTreatmentOutcome(treatment, newOutcome);
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
                MedicalTreatmentManagement.setFollowUpDate(treatment, followUpDate);
                System.out.println(">> Follow-Up Date updated to: " + sdf.format(followUpDate));
            }
        }

        // Update Additional Notes
        System.out.print("Enter Additional Notes (or press Enter to skip): ");
        String notes = scanner.nextLine();
        if (!notes.trim().isEmpty()) {
            MedicalTreatmentManagement.setTreatmentNote(treatment, notes);
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

        if (DiagnosisManagement.isIdEmpty(treatmentId)) {
            System.out.println("Treatment ID cannot be empty.");
            return;
        }

        System.out.print("Are you sure you want to delete Treatment ID " + treatmentId + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            // clone the current list
            MedicalTreatmentManagement.cloneTreatmentList();
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

        if (DiagnosisManagement.isIdEmpty(patientId)) {
            System.out.println("Patient ID cannot be empty.");
            return;
        }

        System.out.print("Are you sure you want to delete all treatments for Patient ID " + patientId + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            MedicalTreatmentManagement.cloneTreatmentList();
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
        System.out.print("Are you sure you want to delete ALL treatment history?(y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            MedicalTreatmentManagement.cloneTreatmentList();
            MedicalTreatmentManagement.deleteAllTreatments();
            System.out.println(">> All treatment history deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // restore the list of latest change made
    public static void restoreTreatmentHistory() {
        System.out.println("\n=== Restore Treatment History ===");
        System.out.print("Are you sure you want to restore the latest changes? This action cannot be undone. (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            MedicalTreatmentManagement.restoreTreatmentList();
            System.out.println(">> Treatment history restored successfully.");
        } else {
            System.out.println("Restoration cancelled.");
        }
    }

    // Generate patient history report based on month
    public static void generateMonthlyReport() {

        System.out.println("\n\n=== Generate Monthly Treatment Report ===");

        // show the available year first
        if (!MedicalTreatmentManagement.hasTreatmentHistory()) {
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

        System.out.println("\n=========================================================================");
        System.out.println(">                         MONTHLY TREATMENT REPORT                       <");
        System.out.println("==========================================================================");

        System.out.printf("Reporting Period: %s | Generated: %s\n", monthYearStr, sdf.format(new Date()));
        System.out.println("==========================================================================");

        MyList<MedicalTreatment> monthlyTreatments = MedicalTreatmentManagement.getMonthlyTreatments(year, month);

        if (MedicalTreatmentManagement.isTreatmentListEmpty(monthlyTreatments)) {
            System.out.println("No treatments found for " + monthYearStr + ".");
            return;
        }

        System.out.printf("Total Treatments in %s: %d\n", monthYearStr, MedicalTreatmentManagement.getTreatmentSize(monthlyTreatments));
        System.out.println("==========================================================================");

        MyList<MedicalTreatment> success = MedicalTreatmentManagement.getSuccessfulTreatmentHistory(year, month);
        // display the treatment history report based on the outcome in portion by portion,"Successful", "Needs Follow up", "Failed", "Ongoing"
        System.out.println("\n==========================================================================");
        System.out.println(">> Successful Treatments:");
        System.out.println("==========================================================================");
        System.out.println("Treatment ID | Patient ID | Treatment Date | Treatment Status | Outcome ");
        System.out.println("==========================================================================");
        // fetch the treatment list using the getSuccessfulTreatmentHistory method in the medical treatment management
        for(int i = 0 ; i < MedicalTreatmentManagement.getTreatmentSize(success); i++) {
            MedicalTreatment t = MedicalTreatmentManagement.getTreatment(success, i);
            System.out.printf("%-12s | %-10s | %-14s | %-16s | %-15s\n",
                    t.getTreatmentId(), t.getPatientId(), sdf.format(t.getTreatmentDate()), t.getTreatmentStatus(), t.getTreatmentOutcome());
        }
        System.out.println("==========================================================================");
        // print the total amount
        System.out.print("Total Successful Treatments: ");
        System.out.println(MedicalTreatmentManagement.getTreatmentSize(success));
        System.out.println("==========================================================================");

        MyList<MedicalTreatment> followUp = MedicalTreatmentManagement.getFollowUpTreatmentHistory(year, month);
            
        // display the treatment history report based on the outcome in portion by portion,"Successful", "Needs Follow up", "Failed", "Ongoing"
        System.out.println("\n==========================================================================");
        System.out.println(">> Needs Follow Up Treatments:");
        System.out.println("==========================================================================");
        System.out.println("Treatment ID | Patient ID | Treatment Date | Treatment Status | Outcome");
        System.out.println("==========================================================================");
        // fetch the treatment list using the getSuccessfulTreatmentHistory method in the medical treatment management
        for(int i = 0 ; i < MedicalTreatmentManagement.getTreatmentSize(followUp); i++) {
            MedicalTreatment t = MedicalTreatmentManagement.getTreatment(followUp, i);
            System.out.printf("%-12s | %-10s | %-14s | %-16s | %-15s\n",
                    t.getTreatmentId(), t.getPatientId(), sdf.format(t.getTreatmentDate()), t.getTreatmentStatus(), t.getTreatmentOutcome());
        }
        System.out.println("==========================================================================");
        // print the total amount
        System.out.print("Total Needs Follow Up Treatments: ");
        System.out.println(MedicalTreatmentManagement.getTreatmentSize(followUp));
        System.out.println("==========================================================================");

        MyList<MedicalTreatment> failed = MedicalTreatmentManagement.getFailedTreatmentHistory(year, month);

        // display the treatment history report based on the outcome in portion by portion,"Successful", "Needs Follow up", "Failed", "Ongoing"
        System.out.println("\n==========================================================================");
        System.out.println(">> Failed Treatments:");
        System.out.println("==========================================================================");
        System.out.println("Treatment ID | Patient ID | Treatment Date | Treatment Status | Outcome");
        System.out.println("==========================================================================");
        // fetch the treatment list using the getSuccessfulTreatmentHistory method in the medical treatment management
        for(int i = 0 ; i < MedicalTreatmentManagement.getTreatmentSize(failed); i++) {
            MedicalTreatment t = MedicalTreatmentManagement.getTreatment(failed, i);
            System.out.printf("%-12s | %-10s | %-14s | %-16s | %-15s\n",
                    t.getTreatmentId(), t.getPatientId(), sdf.format(t.getTreatmentDate()), t.getTreatmentStatus(), t.getTreatmentOutcome());
        }
        System.out.println("==========================================================================");
        // print the total amount 
        System.out.print("Total Failed Treatments: ");
        System.out.println(MedicalTreatmentManagement.getTreatmentSize(failed));
        System.out.println("==========================================================================");

        MyList<MedicalTreatment> ongoing = MedicalTreatmentManagement.getOngoingTreatmentHistory(year, month);

        // display the treatment history report based on the outcome in portion by portion,"Successful", "Needs Follow up", "Failed", "Ongoing"
        System.out.println("\n==========================================================================");
        System.out.println(">> Ongoing Treatments:");
        System.out.println("==========================================================================");
        System.out.println("Treatment ID | Patient ID | Treatment Date | Treatment Status | Outcome");
        System.out.println("==========================================================================");
        // fetch the treatment list using the getSuccessfulTreatmentHistory method in the medical treatment management
        for(int i = 0 ; i < MedicalTreatmentManagement.getTreatmentSize(ongoing); i++) {
            MedicalTreatment t = MedicalTreatmentManagement.getTreatment(ongoing, i);
            System.out.printf("%-12s | %-10s | %-14s | %-16s | %-15s\n",
                    t.getTreatmentId(), t.getPatientId(), sdf.format(t.getTreatmentDate()), t.getTreatmentStatus(), t.getTreatmentOutcome());
        }
        System.out.println("==========================================================================");
        // print the total amount 
        System.out.print("Total Ongoing Treatments: ");
        System.out.println(MedicalTreatmentManagement.getTreatmentSize(ongoing));
        System.out.println("==========================================================================");

        // state the percentage for overall treatment outcome
        System.out.println("\nOverall Treatment Outcome Ratio:");
        System.out.println("==========================================================================");
        System.out.println("Successful: " + MedicalTreatmentManagement.calculatePercentageOfSuccessOutcome(year, month) + "%");
        System.out.println("Needs Follow Up: " + MedicalTreatmentManagement.calculatePercentageOfFollowUpOutcome(year, month) + "%");
        System.out.println("Failed: " + MedicalTreatmentManagement.calculatePercentageOfFailOutcome(year, month) + "%");
        System.out.println("Ongoing: " + MedicalTreatmentManagement.calculatePercentageOfOngoingOutcome(year, month) + "%");
        System.out.println("==========================================================================\n");

        System.out.println(">> Monthly report generated successfully for " + monthYearStr + "!");
    }

    // Generate Treatment Reports
    public static void retrieveTreatmentOutcome() {
        while (true) {
            System.out.println("\n=== Retrieve Treatment Summary Report ===");
            System.out.println("1. Successful");
            System.out.println("2. Needs Follow Up");
            System.out.println("3. Failed");
            System.out.println("4. Ongoing");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1-5).");
                continue;
            }

            if (choice == 5) {
                System.out.println("Exiting treatment report...");
                return;
            }

            String outcomeLabel;
            MyList<MedicalTreatment> treatmentList;

            switch (choice) {
                case 1:
                    outcomeLabel = "Successful Treatments";
                    treatmentList = MedicalTreatmentManagement.getSuccessList();
                    break;
                case 2:
                    outcomeLabel = "Treatments that Need Follow Up";
                    treatmentList = MedicalTreatmentManagement.getFollowUpList();
                    break;
                case 3:
                    outcomeLabel = "Failed Treatments";
                    treatmentList = MedicalTreatmentManagement.getFailedList();
                    break;
                case 4:
                    outcomeLabel = "Ongoing Treatments";
                    treatmentList = MedicalTreatmentManagement.getOngoingList();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            displayTreatmentList(outcomeLabel, treatmentList);

            // allow viewing details

            if(MedicalTreatmentManagement.isTreatmentListEmpty(treatmentList) == false) {
                System.out.print("\nEnter a Treatment ID to view details (or 'x' to return): ");
                String treatmentId = scanner.nextLine().trim();
                if (!treatmentId.equalsIgnoreCase("x")) {
                    treatmentHistoryDisplayForm(treatmentId);
                }
            } else {
                System.out.println("No records found.");
            }
            
        }
    }

    private static void displayTreatmentList(String label, MyList<MedicalTreatment> list) {
        System.out.println("\n" + label + ":");
        System.out.println("=======================================================================");
        if (list == null || MedicalTreatmentManagement.isTreatmentListEmpty(list) == true) {
            System.out.println("No records found.");
        } else {
            for (int i = 0; i < MedicalTreatmentManagement.getTreatmentSize(list); i++) {
                MedicalTreatment treatment = MedicalTreatmentManagement.getTreatmentByIndex(list, i);
                System.out.println("Treatment ID: " + treatment.getTreatmentId()
                        + "\tPatient ID: " + treatment.getPatientId());
            }
        }
        System.out.println("=======================================================================");
        System.out.println("Total " + label + ": " + (list == null ? 0 : MedicalTreatmentManagement.getTreatmentSize(list)));
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
        
        if (treatment.getMedicineList() != null && MedicalTreatmentManagement.hasMedicines(treatment)) {
            for (int i = 0; i < MedicalTreatmentManagement.getMedicineCount(treatment); i++) {
                MedicalTreatmentItem medicine = MedicalTreatmentManagement.getMedicineByIndex(treatment, i);
                sb.append(String.format("> %d. %-59s <\n", (i + 1), medicine.getMedicineName()));
                sb.append(String.format(">    Dosage    : %-48s <\n", medicine.getDosage()));
                sb.append(String.format(">    Frequency : %-48s <\n", medicine.getFrequency()));
                sb.append(String.format(">    Duration  : %-48s <\n", medicine.getDuration()));
                sb.append(String.format(">    Method    : %-48s <\n", medicine.getMethod()));
                if (i < MedicalTreatmentManagement.getMedicineCount(treatment) - 1) {
                    sb.append(">                                                                <\n");
                }
            }
        } else {
            sb.append("> No medications prescribed                                      <\n");
        }
        sb.append("==================================================================\n");
        System.out.println(sb.toString());
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
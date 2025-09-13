/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import ADT.MyList;
import Control.DiagnosisManagement;
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
public class DiagnosisUI {

    public static final Scanner scanner = new Scanner(System.in);
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void diagnosisMenu() {

        while (true) {
            System.out.println("\n=== Diagnosis Management System ===");
            System.out.println("1. View Diagnosis List");
            System.out.println("2. Update Diagnosis Details");
            System.out.println("3. Delete Diagnosis");
            System.out.println("4. Generate Diagnosis Record");
            System.out.println("5. Severity Level And Symptoms Check");
            System.out.println("6. Exit to Main Menu");

            System.out.print("Enter your choice (1-6): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 ->
                        viewDiagnosisDetails();
                    case 2 ->
                        updateDiagnosisDetails();
                    case 3 ->
                        deleteDiagnosis();
                    case 4 -> {
                        String report = displayDiagnosisList();
                        System.out.println(report);
                    }
                    case 5 ->
                        severityAndSymptomCheck();
                    case 6 -> {
                        System.out.println("Exiting to Main Menu...");
                        return; // Exit to main menu
                    }
                    default -> {
                        System.out.println("Invalid Choice. Please enter again from 1 to 7.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Invalid Input. Please enter a number.");
                //clear buffer
                scanner.nextLine();
            }
        }
    }

    public static void addDiagnosis() {
        try {
            System.out.println("\n=== Add New Diagnosis ===");

            // Get the current serving patient ID from the queue list
            String patientId = DiagnosisManagement.getCurrentServingPatient();
            System.out.println("Patient ID: " + patientId);

            // Get the current serving doctor ID from the queue list
            String doctorId = DiagnosisManagement.getCurrentServingDoctor();
            System.out.println("Doctor ID: " + doctorId);

            DiagnosisManagement.startSymptomCollection();

            // Create a diagnosis array for doctor to write the symptoms while if the doctor enter with an empty value then end the loop
            int i = 1;
            System.out.println("Enter Patient's Symptoms (Press Enter to finish) ");
            while (true) {
                System.out.print("[" + i + "] ");
                String symptom = scanner.nextLine();
                if (symptom.trim().isEmpty()) {
                    break;
                } else {
                    DiagnosisManagement.addSymptom(symptom);
                    i++;
                }
            }

            System.out.print("Enter Diagnosis Description: ");
            String diagnosisDescription = scanner.nextLine();

                        String severityLevel = null;
            while (true) {
                System.out.println("Severity Level");
                System.out.println("1. Low");
                System.out.println("2. Medium");
                System.out.println("3. High");
                System.out.println("4. Critical");
                System.out.print("Select Severity Level (1-4): ");
                String severity = scanner.nextLine();

                switch (severity) {
                    case "1" -> severityLevel = "Low";
                    case "2" -> severityLevel = "Medium";
                    case "3" -> severityLevel = "High";
                    case "4" -> severityLevel = "Critical";
                    default -> {
                        System.out.println("Invalid Input. Please try again.");
                        continue; // ask again instead of exiting
                    }
                }
                break; // exit loop once valid input is given
            }


            System.out.print("Enter Recommendations: ");
            String recommendations = scanner.nextLine();

            System.out.print("Additional Notes: ");
            String additionalNotes = scanner.nextLine();

            // Create a new Diagnosis object
            Diagnosis diagnosis = new Diagnosis(patientId, doctorId, new Date(),
                    DiagnosisManagement.getSymptoms(), diagnosisDescription,
                    severityLevel, recommendations, additionalNotes);

            // Add the new Diagnosis object to the diagnosisList
            DiagnosisManagement.addDiagnosis(diagnosis);

        } catch (Exception c) {
            System.out.println("Invalid Input. Please select again.");
        }
    }
    
    //View Diagnosis Details
    public static void viewDiagnosisDetails() {
        System.out.println("\n=== Diagnosis Details ===");
        System.out.print("Enter the Year: ");
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

        System.out.print("Enter the Month: ");
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

        MyList<Diagnosis> filteredDiagnoses = DiagnosisManagement.getDiagnosesByYearAndMonth(year, month);

        if (DiagnosisManagement.isDiagnosisEmpty(filteredDiagnoses)) {
            System.out.println("No diagnoses found for the specified year and month.");
            return;
        }

        // Display available diagnosis IDs for reference
        System.out.println("\nAvailable Diagnosis IDs:");
        System.out.println("------------------------");
        for (int i = 0; i < DiagnosisManagement.getDiagnosisSize(filteredDiagnoses); i++) {
            Diagnosis d = DiagnosisManagement.getDiagnosisListByIndex(i);
            System.out.printf(">> %s (Patient: %s, Date: %s)\n",
                    d.getDiagnosisId(),
                    d.getPatientId() != null ? d.getPatientId() : "N/A",
                    d.getDiagnosisDate() != null
                    ? new SimpleDateFormat("dd/MM/yyyy").format(d.getDiagnosisDate()) : "N/A");
        }
        System.out.println();

        System.out.print("Enter Diagnosis ID: ");
        String diagnosisId = scanner.nextLine().trim();

        // Validate input
        if (DiagnosisManagement.isIdEmpty(diagnosisId)) {
            System.out.println(">> Diagnosis ID cannot be empty. Please try again.");
            return;
        }

        // Find the diagnosis
        Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListById(diagnosisId);

        if (DiagnosisManagement.isDiagnosisEmpty(diagnosis)) {
            System.out.println(">> No diagnosis found with ID: " + diagnosisId);
            System.out.println("Please check the available Diagnosis IDs above and try again.");
            return;
        }

        // Sort symptoms alphabetically
        MyList<String> symptoms = diagnosis.getSymptoms();
        if (symptoms != null && !DiagnosisManagement.isSymptomEmpty()) {
            UtilityClass.quickSort(symptoms, String::compareToIgnoreCase);
        }

        // Display diagnosis details
        System.out.println("\n==============================================================");
        System.out.printf("Diagnosis ID: %s\n", diagnosis.getDiagnosisId());
        System.out.printf("Patient ID: %s\n", diagnosis.getPatientId());
        System.out.printf("Doctor ID: %s\n", diagnosis.getDoctorId());
        System.out.printf("Diagnosis Date: %s\n",
                diagnosis.getDiagnosisDate() != null
                ? new SimpleDateFormat("dd/MM/yyyy").format(diagnosis.getDiagnosisDate()) : "N/A");
        System.out.printf("Severity Level: %s\n", diagnosis.getSeverityLevel());
        System.out.println("Symptoms:");
        if (symptoms != null && !DiagnosisManagement.isSymptomEmpty()) {
            for (int i = 0; i < DiagnosisManagement.getSymptomSize(symptoms); i++) {
                System.out.printf("  [%d] %s\n", i + 1, DiagnosisManagement.getSymptomById(diagnosis.getDiagnosisId(), i));
            }
        } else {
            System.out.println("  No symptoms recorded.");
        }
        System.out.println("==============================================================");
    }

    // Update diagnosis details method
    // This method will prompt the user for input and update the diagnosis record
    public static void updateDiagnosisDetails() {
        System.out.println("\n=== Update Diagnosis Details ===");
        System.out.print("Enter Diagnosis ID: ");
        String diagnosisId = scanner.nextLine();

        Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListById(diagnosisId);
        if (diagnosis == null) {
            return;
        }

        System.out.print("Enter New Diagnosis Description: ");
        String newDiagnosisDescription = scanner.nextLine();

        System.out.println("Severity Level");
        System.out.println("1. Low");
        System.out.println("2. Medium");
        System.out.println("3. High");
        System.out.println("4. Critical");
        System.out.print("Select Severity Level(1-4): ");
        String newSeverity = scanner.nextLine();
        String newSeverityLevel;
        switch (newSeverity) {
            case "1" ->
                newSeverityLevel = "Low";
            case "2" ->
                newSeverityLevel = "Medium";
            case "3" ->
                newSeverityLevel = "High";
            case "4" ->
                newSeverityLevel = "Critical";
            default -> {
                System.out.println("Invalid Input. Please select again.");
                return;
            }
        }

        System.out.print("Enter New Recommendations: ");
        String newRecommendations = scanner.nextLine();

        System.out.print("Enter New Additional Notes: ");
        String newAdditionalNotes = scanner.nextLine();

        //update the input to the certain diagnosis
        Diagnosis updatedDiagnosis = new Diagnosis(diagnosisId, newDiagnosisDescription, newSeverityLevel, newRecommendations, newAdditionalNotes);
        
        DiagnosisManagement.setPatientId(updatedDiagnosis, diagnosis.getPatientId());
        DiagnosisManagement.setDoctorId(updatedDiagnosis, diagnosis.getDoctorId());
        DiagnosisManagement.setDiagnosisDate(updatedDiagnosis, diagnosis.getDiagnosisDate());
        DiagnosisManagement.setSymptoms(updatedDiagnosis, diagnosis.getSymptoms());

        // Update the diagnosis details
        DiagnosisManagement.updateDiagnosisDetails(diagnosisId, updatedDiagnosis);
        System.out.println("Diagnosis details updated successfully.");
    }

    public static void deleteDiagnosis() {
        System.out.print("\n\nEnter Diagnosis ID to remove: ");
        String diagId = scanner.nextLine().trim();

        // Find the Diagnosis object by ID
        Diagnosis diagnosis = DiagnosisManagement.findDiagnosisById(diagId);

        if (diagnosis == null) {
            System.out.println("Diagnosis with ID " + diagId + " not found.");
            return;
        }

        // Confirm deletion
        System.out.print("Are you sure you want to remove this diagnosis? (Y/N): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            boolean removed = DiagnosisManagement.removeDiagnosis(diagnosis);
            if (removed) {
                System.out.println("Diagnosis removed successfully!");
            } else {
                System.out.println("Failed to remove the diagnosis.");
            }
        } else {
            System.out.println("Removal canceled.");
        }
    }

    public static String displayDiagnosisList() {
        StringBuilder sb = new StringBuilder();

        System.out.print("\nEnter Diagnosis ID to view details (or press Enter to exit): ");
        String diagnosisId = scanner.nextLine();

        if (DiagnosisManagement.isIdEmpty(diagnosisId)) {
            return "No Diagnosis Available. Exiting Diagnosis Details View.\n"; // Exit if no ID is provided
        }

        Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListById(diagnosisId);

        if (DiagnosisManagement.isDiagnosisEmpty(diagnosis)) {
            return "No Diagnosis found with ID: " + diagnosisId + ". Please check the ID and try again.\n";
        }

        sb.append("=================================================================\n");
        sb.append(">                        MEDICAL DIAGNOSIS                      <\n");
        sb.append("=================================================================\n");
        sb.append(String.format("> Diagnosis ID      : %-41s <\n", diagnosis.getDiagnosisId() != null ? diagnosis.getDiagnosisId() : "N/A"));
        sb.append(String.format("> Patient ID        : %-41s <\n", diagnosis.getPatientId() != null ? diagnosis.getPatientId() : "N/A"));
        sb.append(String.format("> Doctor ID         : %-41s <\n", diagnosis.getDoctorId() != null ? diagnosis.getDoctorId() : "N/A"));
        sb.append(String.format("> Diagnosis Date    : %-41s <\n", diagnosis.getDiagnosisDate() != null ? dateFormat.format(diagnosis.getDiagnosisDate()) : "N/A"));
        sb.append("=================================================================\n");

        // Severity Level with visual indicator
        String severity = diagnosis.getSeverityLevel();
        String severityDisplay = DiagnosisManagement.getSeverityDisplay(severity);

        sb.append(String.format("> Severity Level    : %-41s <\n", severityDisplay));

        sb.append("=================================================================\n");

        // Symptoms
        sb.append("> Symptoms:                                                     <\n");
        if (DiagnosisManagement.hasSymptoms(diagnosis)) {
            for (int i = 0; i < DiagnosisManagement.getSymptomSize(diagnosis); i++) {
                sb.append(String.format(">   [%d] %-56s<\n", i + 1, DiagnosisManagement.getSymptomByIndex(i)));
            }
        } else {
            sb.append(">   N/A                                                         <\n");
        }

        sb.append("=================================================================\n");
        // Diagnosis Description
        sb.append("> Diagnosis Description:                                        <\n");
        appendWrappedText(sb, diagnosis.getDiagnosisDescription());
        sb.append("=================================================================\n");
        // Recommendations
        sb.append("> Recommendations:                                              <\n");
        appendWrappedText(sb, diagnosis.getRecommendations());
        sb.append("=================================================================\n");
        // Additional Notes
        sb.append("> Additional Notes:                                             <\n");
        appendWrappedText(sb, diagnosis.getNotes());
        sb.append("=================================================================\n");
        sb.append(">                        END OF REPORT                          <\n");
        sb.append("=================================================================\n");
        sb.append("\n");

        return sb.toString();
    }

    private static void appendWrappedText(StringBuilder sb, String text) {
        if (text == null || DiagnosisManagement.isIdEmpty(text)) {
            sb.append(">   N/A                                                         <\n");
            return;
        }
        
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder(">   ");
        
        for (String word : words) {
            if (line.length() + word.length() + 1 > 63) {
                sb.append(String.format("%-63s <\n", line.toString()));
                line = new StringBuilder(">   ");
            }
            line.append(word).append(" ");
        }
        
        if (line.length() > 4) {
            sb.append(String.format("%-63s <\n", line.toString().trim()));
        }
    }

    // Allow users to enter the severity level to filter the diagnosis list and display the diagnosis ID and its patient ID
    public static void severityAndSymptomCheck() {
        System.out.println("\n\n=== Filter Diagnosis by Severity Level ===");

        DiagnosisManagement.startSymptomCollection();

        // Get Year
        System.out.print("Enter the Year: ");
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

        // Get Month
        System.out.print("Enter the Month: ");
        String monthInput = scanner.nextLine().trim();
        if (monthInput.length() == 1) monthInput = "0" + monthInput; // two-digit format
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

        String monthName = UtilityClass.getMonthName(month);
        String monthYearStr = monthName + " " + year;

        // Get filtered diagnoses from control
        MyList<Diagnosis> filteredList = DiagnosisManagement.getDiagnosesByYearAndMonth(year, month);
        if (DiagnosisManagement.isDiagnosisEmpty(filteredList)) {
            System.out.println("No diagnoses found for the specified year and month.");
            return;
        }


        // Counters
        int lowCount = 0, mediumCount = 0, highCount = 0, criticalCount = 0;

        // Process filtered diagnoses
        for (int i = 0; i < DiagnosisManagement.getDiagnosisSize(filteredList); i++) {
            Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListByIndex(filteredList, i);
            String severity = diagnosis.getSeverityLevel();

            switch (severity.toLowerCase()) {
                case "low" -> lowCount++;
                case "medium" -> mediumCount++;
                case "high" -> highCount++;
                case "critical" -> criticalCount++;
            }

            // Add all symptoms to control
            MyList<String> symptoms = diagnosis.getSymptoms();
            for (int j = 0; j < DiagnosisManagement.getSymptomSize(symptoms); j++) {
                String symptom = DiagnosisManagement.getSymptomByIndex(symptoms, j); // Fixed method call
                DiagnosisManagement.addSymptom(symptom);
            }
        }

        // Display diagnosis count by severity
        System.out.println("\n>> Diagnoses by Severity Level (" + monthYearStr + "):");
        System.out.println("===========================================");
        System.out.println("Severity Level | Count");
        System.out.println("===========================================");
        System.out.printf("Low            | %d\n", lowCount);
        System.out.printf("Medium         | %d\n", mediumCount);
        System.out.printf("High           | %d\n", highCount);
        System.out.printf("Critical       | %d\n\n", criticalCount);

        // get top 3 symptoms
        MyList<DiagnosisManagement.SymptomCount> top3 = DiagnosisManagement.getTopSymptoms(3);

        // Display Top 3 Symptoms
        System.out.println(">> Top 3 Symptoms:");
        System.out.println("===========================================");
        for (int i = 0; i < DiagnosisManagement.getTop3Size(top3); i++) {
            String symptomName = DiagnosisManagement.getTop3SymptomName(top3, i);
            int symptomCount = DiagnosisManagement.getTop3SymptomCount(top3, i);
            System.out.printf("%d. %s - %d occurrences\n", i + 1, symptomName, symptomCount);
        }

        // get overall symptom statistics
        DynamicList.ListStatistics<DiagnosisManagement.SymptomCount> allStats = DiagnosisManagement.getAllSymptomStatistics();

        // Display overall statistics
        System.out.println("\n>> Overall Symptom Statistics:");
        System.out.println("===========================================");
        // if the average is high, it possibly means health issue or seasonal illness
        System.out.printf("Average occurrences: %.2f\n", allStats.average);
        System.out.printf("Minimum occurrences: %.0f -> %s\n", allStats.min, DiagnosisManagement.getMinSymptom());
        System.out.printf("Maximum occurrences: %.0f -> %s\n", allStats.max, DiagnosisManagement.getMaxSymptom());
        // high sd means some symptoms are much more common than others
        // low sd means symptoms are more evenly distributed across patient
        // This is can help to understand one or two symptoms dominate or whether cases are balanced
        System.out.printf("Standard deviation: %.2f\n", allStats.standardDeviation);
        System.out.println("===========================================\n");

        // Optional: show recommended medicine for top 3 symptoms
        System.out.println(">> Recommended Medicines for Top 3 Symptoms:");
        System.out.println("===========================================");
        for (int i = 0; i < DiagnosisManagement.getTop3Size(top3); i++) {
            String symptomName = DiagnosisManagement.getTop3SymptomName(top3, i);
            String medicine = DiagnosisManagement.getMedicineForSymptom(symptomName);
            System.out.printf("%d. %s -> %s\n", i + 1, symptomName, medicine);
        }
        System.out.println("===========================================");

    }
}

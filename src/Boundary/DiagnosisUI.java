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

            // Create a diagnosis array for doctor to write the symptoms while if the doctor enter with an empty value then end the loop
            MyList<String> symptomsInput = new DynamicList<>();
            int i = 1;
            System.out.println("Enter Patient's Symptoms (Press Enter to finish) ");
            while (true) {

                System.out.print("[" + i + "] ");
                String symptom = scanner.nextLine();
                if (symptom.isEmpty()) {
                    break;
                } else {
                    symptomsInput.add(symptom);
                    i++;
                }
            }
            MyList<String> symptomsList = DiagnosisManagement.addSymptoms(symptomsInput);

            System.out.print("Enter Diagnosis Description: ");
            String diagnosisDescription = scanner.nextLine();

            System.out.println("Severity Level");
            System.out.println("1. Low");
            System.out.println("2. Medium");
            System.out.println("3. High");
            System.out.println("4. Critical");
            System.out.print("Select Severity Level(1-4): ");
            String severity = scanner.nextLine();
            String severityLevel;
            switch (severity) {
                case "1" ->
                    severityLevel = "Low";
                case "2" ->
                    severityLevel = "Medium";
                case "3" ->
                    severityLevel = "High";
                case "4" ->
                    severityLevel = "Critical";
                default -> {
                    System.out.println("Invalid Input. Please select again.");
                    return;
                }
            }

            System.out.print("Enter Recommendations: ");
            String recommendations = scanner.nextLine();

            System.out.print("Additional Notes: ");
            String additionalNotes = scanner.nextLine();

            // Create a new Diagnosis object
            Diagnosis diagnosis = new Diagnosis(patientId, doctorId, new Date(),
                    symptomsList, diagnosisDescription,
                    severityLevel, recommendations, additionalNotes);

            // Add the new Diagnosis object to the diagnosisList
            DiagnosisManagement.addDiagnosis(diagnosis);

        } catch (Exception c) {
            System.out.println("Invalid Input. Please select again.");
        }
    }

    // add diagnosis method with dummy data for testing
    public static void addDiagnosisTest() {
        System.out.println("\n=== Add New Diagnosis ===");

        // Dummy data for testing
        String patientId = "P1001";
        String doctorId = "D1001";

        DynamicList<String> symptomsInput = new DynamicList<>();
        symptomsInput.add("Fever");
        symptomsInput.add("Awake");
        symptomsInput.add("baking powder");

        MyList<String> symptomsList = DiagnosisManagement.addSymptoms(symptomsInput);

        String diagnosisDescription = "Patient shows signs of flu.";
        String severityLevel = "Medium";
        String recommendations = "Rest and hydration recommended.";
        String additionalNotes = "Patient advised to monitor symptoms.";

        // Create a new Diagnosis object
        Diagnosis diagnosis = new Diagnosis(patientId, doctorId, new Date(),
                symptomsList, diagnosisDescription,
                severityLevel, recommendations, additionalNotes);

        // Add the new Diagnosis object to the diagnosisList
        DiagnosisManagement.addDiagnosis(diagnosis);

        System.out.println("Diagnosis added successfully for Patient ID: " + patientId);
        System.out.println("Diagnosis ID: " + diagnosis.getDiagnosisId());
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

        if (filteredDiagnoses.isEmpty()) {
            System.out.println("No diagnoses found for the specified year and month.");
            return;
        }

        // Display available diagnosis IDs for reference
        System.out.println("\nAvailable Diagnosis IDs:");
        System.out.println("------------------------");
        for (int i = 0; i < filteredDiagnoses.size(); i++) {
            Diagnosis d = filteredDiagnoses.get(i);
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
        if (diagnosisId.isEmpty()) {
            System.out.println(">> Diagnosis ID cannot be empty. Please try again.");
            return;
        }

        // Find the diagnosis
        Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListById(diagnosisId);

        if (diagnosis == null) {
            System.out.println(">> No diagnosis found with ID: " + diagnosisId);
            System.out.println("Please check the available Diagnosis IDs above and try again.");
            return;
        }

        // Sort symptoms alphabetically
        MyList<String> symptoms = diagnosis.getSymptoms();
        if (symptoms != null && !symptoms.isEmpty()) {
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
        if (symptoms != null && !symptoms.isEmpty()) {
            for (int i = 0; i < symptoms.size(); i++) {
                System.out.printf("  [%d] %s\n", i + 1, symptoms.get(i));
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

        // Update the diagnosis details
        DiagnosisManagement.updateDiagnosisDetails(diagnosisId, updatedDiagnosis);
        System.out.println("Diagnosis details updated successfully.");
    }

    public static void deleteDiagnosis() {
        System.out.println("\n=== Delete Diagnosis ===");
        System.out.print("Enter Diagnosis ID to delete: ");
        String diagnosisId = scanner.nextLine();

        if (DiagnosisManagement.removeDiagnosisById(diagnosisId)) {
            System.out.print("Enter 'Y' to confirm deletion: ");
            String confirmation = scanner.nextLine();
            while (confirmation.equalsIgnoreCase("Y")) {
                System.out.println("Diagnosis with ID " + diagnosisId + " has been deleted successfully.");
                break;
            }
        } else {
            System.out.println("No diagnosis found with ID: " + diagnosisId);
        }
    }

    public static String displayDiagnosisList() {
        StringBuilder sb = new StringBuilder();

        System.out.print("\nEnter Diagnosis ID to view details (or press Enter to exit): ");
        String diagnosisId = scanner.nextLine();

        if (diagnosisId.isEmpty()) {
            return "No Diagnosis Available. Exiting Diagnosis Details View.\n"; // Exit if no ID is provided
        }

        Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListById(diagnosisId);

        if (diagnosis == null) {
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
        if (diagnosis.getSymptoms() != null && !diagnosis.getSymptoms().isEmpty()) {
            for (int i = 0; i < diagnosis.getSymptoms().size(); i++) {
                sb.append(String.format(">   [%d] %-56s<\n", i + 1, diagnosis.getSymptoms().get(i)));
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
        if (text == null || text.trim().isEmpty()) {
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
        int year;
        int month = 0;
        // Prompt user to enter the year
        System.out.print("Enter the year (e.g., 2025): ");
        String yearInput = scanner.nextLine().trim();

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

        //display all the diagnosis based on severity level and its count
        MyList<Diagnosis> filteredList = DiagnosisManagement.getDiagnosesByYear(year);

        if (filteredList.isEmpty()) {
            System.out.println("No diagnoses found for the specified year and month.");
            return;
        }

        // Counters
        int lowCount = 0;
        int mediumCount = 0;
        int highCount = 0;
        int criticalCount = 0;

        // Collect all symptoms in a DynamicList
        DynamicList<String> allSymptoms = new DynamicList<>();

        // Loop through filtered list and count by severity
        for (int i = 0; i < filteredList.size(); i++) {
            Diagnosis diagnosis = filteredList.get(i);
            String severity = diagnosis.getSeverityLevel();

            if (severity.equalsIgnoreCase("low")) {
                lowCount++;
            } else if (severity.equalsIgnoreCase("medium")) {
                mediumCount++;
            } else if (severity.equalsIgnoreCase("high")) {
                highCount++;
            } else if (severity.equalsIgnoreCase("critical")) {
                criticalCount++;
            }

            //add all symptoms to the list
            MyList<String> symptoms = diagnosis.getSymptoms();
            for (int j = 0; j < symptoms.size(); j++) {
                allSymptoms.add(symptoms.get(j));
            }
        }

        // Display the result
        System.out.println("\nDiagnoses by Severity Level (" + year + "):");
        System.out.println("===========================================");
        System.out.println("Severity Level | Count");
        System.out.println("===========================================");
        System.out.printf("Low           | %d\n", lowCount);
        System.out.printf("Medium        | %d\n", mediumCount);
        System.out.printf("High          | %d\n", highCount);
        System.out.printf("Critical      | %d\n\n", criticalCount);

        // Calculate top 3 symptoms
        DynamicList<String> topSymptoms = new DynamicList<>();
        int[] symptomCounts = new int[allSymptoms.size()];

        for (int i = 0; i < allSymptoms.size(); i++) {
            String symptom = allSymptoms.get(i);
            boolean found = false;
            for (int j = 0; j < topSymptoms.size(); j++) {
                if (topSymptoms.get(j).equalsIgnoreCase(symptom)) {
                    symptomCounts[j]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                topSymptoms.add(symptom);
                symptomCounts[topSymptoms.size() - 1] = 1;
            }
        }

        // Sort topSymptoms by count (simple bubble sort)
        for (int i = 0; i < topSymptoms.size() - 1; i++) {
            for (int j = i + 1; j < topSymptoms.size(); j++) {
                if (symptomCounts[j] > symptomCounts[i]) {
                    // Swap counts
                    int tempCount = symptomCounts[i];
                    symptomCounts[i] = symptomCounts[j];
                    symptomCounts[j] = tempCount;

                    // Swap symptoms
                    String tempSymptom = topSymptoms.get(i);
                    topSymptoms.replace(i, topSymptoms.get(j));
                    topSymptoms.replace(j, tempSymptom);
                }
            }
        }

        // Display top 3 symptoms
        System.out.println("Top 3 Symptoms:");
        System.out.println("===========================================");
        int topN = Math.min(3, topSymptoms.size());
        for (int i = 0; i < topN; i++) {
            System.out.printf("%d. %s - %d occurrences\n", i + 1, topSymptoms.get(i), symptomCounts[i]);
        }
    }

    // General method that helps users to enter the year and month
    private static MyList<Diagnosis> EnterYearAndMonth(int year, int month) {
        // Prompt user to enter the year
        System.out.print("Enter the year (e.g., 2025): ");
        String yearInput = scanner.nextLine().trim();

        try {
            year = Integer.parseInt(yearInput);
            if (year < 2020 || year > Year.now().getValue()) {
                System.out.println("Please enter a valid year between 2020 and the current year.");
                return new DynamicList<>();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid year.");
            return new DynamicList<>();
        }

        // Prompt user to enter the month
        System.out.print("Enter the month (01-12): ");
        String monthInput = scanner.nextLine().trim();

        if (monthInput.length() == 1) {
            monthInput = "0" + monthInput; // Ensure the month is in two-digit format
        }

        try {
            month = Integer.parseInt(monthInput);
            if (month < 1 || month > 12) {
                System.out.println("Invalid input. Please enter a month between 01 and 12.");
                return new DynamicList<>();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid month.");
            return new DynamicList<>();
        }
        return DiagnosisManagement.getDiagnosesByYearAndMonth(year, month);
    }
}

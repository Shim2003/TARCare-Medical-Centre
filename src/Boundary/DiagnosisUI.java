/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.DiagnosisManagement;
import Entity.*;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class DiagnosisUI {
    public static final Scanner scanner = new Scanner(System.in);
    public static void diagnosisMenu() {
        
        while(true) {
            System.out.println("\n=== Diagnosis Management System ===");
            System.out.println("1. Add New Diagnosis");
            System.out.println("2. View Diagnosis List");
            System.out.println("3. Update Diagnosis Details");
            System.out.println("4. Delete Diagnosis");
            System.out.println("5. Generate Diagnosis Reports");
            System.out.println("6. Generate Diagnosis Statistics Report");
            System.out.println("7. Exit to Main Menu");

            System.out.print("Enter your choice (1-7): ");

            try{
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer

                switch (choice) {
                    case 1 -> 
                        addDiagnosis();
                    case 2 -> 
                        viewDiagnosisDetails();
                    case 3 -> 
                        updateDiagnosisDetails();
                    case 4 ->
                        deleteDiagnosis();
                    case 5 ->
                        DiagnosisManagement.displayDiagnosisList();
                    case 6 ->
                        generateDiagnosisStatisticsReport();
                    case 7 -> {
                        System.out.println("Exiting to Main Menu...");
                        return; // Exit to main menu
                        }
                    default -> {
                        System.out.println("Invalid Choice. Please enter again from 1 to 7.");
                        }
                    }
                    
            } catch(Exception e) {
            System.out.println("Invalid Input. Please enter a number.");
            //clear buffer
            scanner.nextLine();
            } 
        }
    }

    public static void addDiagnosis1() {
        String patientId;
        try {
            System.out.println("\n=== Add New Diagnosis ===");
            
            System.out.print("Enter Patient ID: ");
            patientId = scanner.nextLine();
            
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine();
            
            // Create a diagnosis array for doctor to write the symptoms while if the doctor enter with an empty value then end the loop
            DynamicList<String> symptomsInput = new DynamicList<>();
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
            DynamicList<String> symptomsList = DiagnosisManagement.addSymptoms(symptomsInput);
            
            System.out.print("Enter Diagnosis Description: ");
            String diagnosisDescription = scanner.nextLine();

            System.out.println("Severity Level");
            System.out.println("1. Low");
            System.out.println("2. Medium");
            System.out.println("3. High");
            System.out.println("4. Critical");
            System.out.print("Select Severity Level (1-4): ");
            String severityLevel = scanner.nextLine();

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
    public static void addDiagnosis() {
        System.out.println("\n=== Add New Diagnosis ===");
        
        // Dummy data for testing
        String patientId = "P001";
        String doctorId = "D001";
        
        DynamicList<String> symptomsInput = new DynamicList<>();
        symptomsInput.add("Fever");
        symptomsInput.add("Cough");
        symptomsInput.add("Sore Throat");
        DynamicList<String> symptomsList = DiagnosisManagement.addSymptoms(symptomsInput);
        
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

    public static void updateDiagnosisDetails() {
        System.out.println("\n=== Update Diagnosis Details ===");
        System.out.print("Enter Diagnosis ID: ");
        String diagnosisId = scanner.nextLine();

        Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListById(diagnosisId);
        if (diagnosis == null) {
            return;
        }

        diagnosis.toString();

        System.out.print("Enter New Diagnosis Description: ");
        String newDiagnosisDescription = scanner.nextLine();

        System.out.print("Enter New Severity Level: ");
        String newSeverityLevel = scanner.nextLine();

        System.out.print("Enter New Recommendations: ");
        String newRecommendations = scanner.nextLine();

        System.out.print("Enter New Additional Notes: ");
        String newAdditionalNotes = scanner.nextLine();

        //update the input to the certain diagnosis
        Diagnosis updatedDiagnosis = new Diagnosis(diagnosis.getPatientId(), diagnosis.getDoctorId(), diagnosis.getDiagnosisDate
            (), diagnosis.getSymptoms(), newDiagnosisDescription, newSeverityLevel, newRecommendations, newAdditionalNotes);

        // Update the diagnosis details
        DiagnosisManagement.updateDiagnosisDetails(diagnosisId, updatedDiagnosis);
        System.out.println("Diagnosis details updated successfully.");
    }

    public static void deleteDiagnosis() {
        System.out.println("\n=== Delete Diagnosis ===");
        System.out.print("Enter Diagnosis ID to delete: ");
        String diagnosisId = scanner.nextLine();

        if (DiagnosisManagement.removeDiagnosisById(diagnosisId)) {
            System.out.println("Diagnosis with ID " + diagnosisId + " has been deleted successfully.");
        } else {
            System.out.println("No diagnosis found with ID: " + diagnosisId);
        }
    }

    //View Diagnosis Details
    public static void viewDiagnosisDetails() {
        System.out.println("\n=== Diagnosis Details ===");
        
        if (DiagnosisManagement.getDiagnosisList().isEmpty()) {
            System.out.println("No diagnoses record currently.");
            // UtilityClass.pressEnterToContinue();
            return;
        }
        
        // Display available diagnosis IDs for reference
        System.out.println("\nAvailable Diagnosis IDs:");
        System.out.println("------------------------");
        for (int i = 0; i < DiagnosisManagement.getDiagnosisList().size(); i++) {
            Diagnosis d = DiagnosisManagement.getDiagnosisList().get(i);
            System.out.printf(">> %s (Patient: %s, Date: %s)\n", 
                d.getDiagnosisId(), 
                d.getPatientId() != null ? d.getPatientId() : "N/A",
                d.getDiagnosisDate() != null ? 
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(d.getDiagnosisDate()) : "N/A");
        }
        System.out.println();
        
        System.out.print("Enter Diagnosis ID: ");
        String diagnosisId = scanner.nextLine().trim();

        // Validate input
        if (diagnosisId.isEmpty()) {
            System.out.println(">> Diagnosis ID cannot be empty. Please try again.");
            // UtilityClass.pressEnterToContinue();
            return;
        }

        // Find the diagnosis
        Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListById(diagnosisId);

        if (diagnosis == null) {
            System.out.println(">> No diagnosis found with ID: " + diagnosisId);
            System.out.println("Please check the available Diagnosis IDs above and try again.");
            // UtilityClass.pressEnterToContinue();
            return;
        }

        diagnosis.toString();
    }

    // Generate diagnosis statistics report
    public static void generateDiagnosisStatisticsReport() {
        System.out.println("\n=== Diagnosis Statistics Report ===");
        System.out.println("Total Diagnoses: " + DiagnosisManagement.getDiagnosisList().size());
        
        int lowCount = 0, mediumCount = 0, highCount = 0, criticalCount = 0;
        
        for (int i = 0; i < DiagnosisManagement.getDiagnosisList().size(); i++) {
            Diagnosis diagnosis = DiagnosisManagement.getDiagnosisList().get(i);
            switch (diagnosis.getSeverityLevel().toUpperCase()) {
                case "LOW"->
                    lowCount++;
                case "MEDIUM"->
                    mediumCount++;
                case "HIGH"->
                    highCount++;
                case "CRITICAL"->
                    criticalCount++;
            }
        }
        
        System.out.println("Low Severity: " + lowCount);
        System.out.println("Medium Severity: " + mediumCount);
        System.out.println("High Severity: " + highCount);
        System.out.println("Critical Severity: " + criticalCount);
    }

}
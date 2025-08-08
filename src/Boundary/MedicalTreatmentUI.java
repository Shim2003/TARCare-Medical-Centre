/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.MedicalTreatmentManagement;
import Control.PatientManagement;
import Entity.*;
import Utility.UtilityClass;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class MedicalTreatmentUI {
    
    private static final Scanner scanner = new Scanner(System.in);

    // Collections to store data
    private static final  DynamicList<Diagnosis> diagnosisList = new DynamicList<>();
    private static final DynamicList<MedicalTreatment> treatmentList = new DynamicList<>();
    private static final DynamicList<TreatmentHistory> historyList = new DynamicList<>();

    // ID counters
    private static int diagnosisIdCounter = 1001;
    private static int treatmentIdCounter = 2001;
    private static int historyIdCounter = 3001;

// Date format for displaying dates
    private static final SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
    
    public static void main(String[] args) {
        MedicalTreatmentMenu();
    }
    
    public static void MedicalTreatmentMenu() {
        while(true) {
            System.out.println("\n=== Medical Treatment Management System ===");
            System.out.println("1. Add New Diagnosis");
            System.out.println("2. Create Medical Treatment");
            System.out.println("3. View Diagnosis Details");
            System.out.println("4. View Patient Treatment History");
            System.out.println("5. Update Treatment Status");
            System.out.println("6. Generate Treatment Reports");
            System.out.println("7. Exit to Main Menu");
            
            System.out.print("Enter your choice (1-7): ");
            
            try{
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer
                
                switch (choice) {
                    case 1 -> {
                        break;
                    }
                    case 2 -> {
                        break;
                    }
                    case 3 -> {
                        break;
                    }
                    case 4 -> {
                        break;
                    }
                    case 5 -> {
                        break;
                    }
                    case 6 -> {
                        break;
                    }
                    case 7 -> {
                        break;
                    }
                    default -> {
                        System.out.println("Invalid Choice. Please enter again from 1 to 7.");
                    }
                }
            } catch(Exception e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
    
    public static void addDiagnosis() {
        String patientId = null;
        try {
            System.out.println("\n=== Add New Diagnosis ===");
            
            while(true) {
                System.out.println("Enter Patient ID: ");
                patientId = scanner.nextLine();
                
                if (patientId == null || patientId.trim().isEmpty()) {
                    System.out.println("Patient ID cannot be empty. Please try again.");
                    continue;
                }
                
                // Check if patient exists in the system
                // Assuming a method findPatientById exists in PatientManagement class
                if (PatientManagement.findPatientById(patientId) == null) {
                    System.out.println("No such Patient ID found. Please try again.");
                    continue;
                }
                
                break;
            }
            
            System.out.println("Enter Doctor ID: ");
            String doctorId = scanner.nextLine();
            
            // Create a diagnosis array for doctor to write the symptoms while if the doctor enter with an empty value then end the loop
            DynamicList<String> symptomsInput = new DynamicList<>();
            int i = 1;
            while (true) {
                System.out.println("Enter Patient's Symptoms (Press Enter to finish): ");
                System.out.print("[" + i + "] ");
                String symptom = scanner.nextLine();
                if (symptom.isEmpty()) {
                    break;
                } else {
                    symptomsInput.add(symptom);
                    i++;
                }
            }
            DynamicList<String> symptomsList = MedicalTreatmentManagement.addSymptoms(symptomsInput);
            
            System.out.println("Enter Diagnosis Description: ");
            String diagnosisDescription = scanner.nextLine();

            System.out.println("Severity Level");
            System.out.println("1. Low");
            System.out.println("2. Medium");
            System.out.println("3. High");
            System.out.println("4. Critical");
            System.out.print("Select Severity Level (1-4): ");
            String severityLevel = scanner.nextLine();

            System.out.println("Enter Recommendations: ");
            String recommendations = scanner.nextLine();

            System.out.println("Additional Notes: ");
            String additionalNotes = scanner.nextLine();

            //Generate Diagnosis ID
            String diagnosisId = "D" + diagnosisIdCounter++;

            // Create a new Diagnosis object
            Diagnosis diagnosis = new Diagnosis(diagnosisId, patientId, doctorId, new Date(),
                symptomsList, diagnosisDescription,
                severityLevel, recommendations, additionalNotes);

            // Add the new Diagnosis object to the diagnosisList
            MedicalTreatmentManagement.addDiagnosis(diagnosis);

        } catch (Exception c) {
            System.out.println("Invalid Input. Please select again.");
        }
    }

    public static void createTreatment() {
        try {
            System.out.println("\n=== Create Medical Treatment ===");


            System.out.println("Enter Diagnosis ID: ");
            String diagnosisId = scanner.nextLine();

            //Find the diagnosis ID
            Diagnosis diagnosis = diagnosisList.findFirst(
                d -> d.getDiagnosisId().equals(diagnosisId));

            if (diagnosis == null) {
                System.out.println("Diagnosis ID not found.");
            }

            System.out.print("Enter Treatment Type (Medication/Surgery/Therapy): ");
            String treatmentType = scanner.nextLine();

            int i = 1;
            DynamicList<String> medicineList = new DynamicList<>();
            // if it is medication, ask for the medicine name and for many day(s)
            if (treatmentType.toLowerCase().equals("medication")) {
                
                //the doctor have to enter a list of medicine and its details, 
                //if doctor enter a "x" means that enough for medicine
                while (true) { 
                    System.out.println("Medicine" + "[ " + i + " ] ");

                    System.out.print("Medicine Name:");
                    String medicineName = scanner.nextLine();
                    if (medicineName.equals("x")) {
                        break;
                    }

                    System.out.print("Dosage: ");
                    String dosage = scanner.nextLine();
                    if (medicineName.equals("x")) {
                        break;
                    }

                    System.out.print("Frequency: ");
                    String frequency = scanner.nextLine();
                    if (medicineName.equals("x")) {
                        break;
                    }

                    System.out.print("Duration: ");
                    String duration = scanner.nextLine();
                    if (medicineName.equals("x")) {
                        break;
                    }

                    System.out.print("Method: ");
                    String method = scanner.nextLine();
                    if (medicineName.equals("x")) {
                        break;
                    }

                    //put the medicine details into the medicineList
                    MedicalTreatmentManagement.addMedicineList(medicineName, dosage, frequency, 
                        duration, method);
                }
            }

            System.out.print("Enter Treatment Description: ");
            String treatmentDescription = scanner.nextLine();

            // Get the treatment date automatically(use the format in the utility class)
            System.out.println("Treatment Date: " + sdf.format(new Date()));
            Date treatmentDate = new Date();

            System.out.print("Enter Treatment Duration: ");
            String treatmentDuration = scanner.nextLine();

            System.out.print("Enter Medical Treatment Advice(s): ");
            String treatmentAdvice = scanner.nextLine();

            // Generate Treatment ID
            String treatmentId = "T" + treatmentIdCounter++;

            // Create a new MedicalTreatment object
            MedicalTreatment treatment = new MedicalTreatment(treatmentId, diagnosisId, 
            diagnosis.getPatientId(), diagnosis.getDoctorId(),treatmentType, treatmentDescription, 
            treatmentDate,"Active", treatmentDuration, treatmentAdvice);

            // Add the new MedicalTreatment object to the treatmentList
            MedicalTreatmentManagement.addMedicalTreatment(treatment);

            // Create treatment history
            String historyId = "H" + historyIdCounter++;
            TreatmentHistory history = new TreatmentHistory(historyId, treatmentId, diagnosisId,
            diagnosis.getPatientId(), diagnosis.getDoctorId(), treatmentDate, treatmentDate, "Ongoing",
            "Active", "Treatment initiated");

            // Add the new TreatmentHistory object to the historyList
            historyList.add(history);

            System.out.println("Medical Treatment created successfully! Treatment ID: " + treatmentId);
        } catch (Exception e) {
            System.out.println("Invalid Input. Please select again.");
        }
    }

    //View Diagnosis Details
    public static void viewDiagnosisDetails() {
        System.out.println("\n=== Diagnosis Details ===");
        if (diagnosisList.isEmpty()) {
            System.out.println("No diagnoses record currently.");
            //back to main menu
            return;
        } 
        System.out.print("Enter Diagnosis ID: ");
        String diagnosisId = scanner.nextLine();

        // Find the diagnosis in the diagnosisList
        if (diagnosisId == null || diagnosisId.trim().isEmpty()) {
            System.out.println("Diagnosis ID cannot be empty. Please try again.");
            return;
        }

        Diagnosis diagnosis = MedicalTreatmentManagement.getDiagnosisListById(diagnosisId);

        if (diagnosis == null) {
            System.out.println("No such Diagnosis ID found.");
            //back to main menu
            return;
        }

        System.out.println("\nDiagnosis Details");
        System.out.println("==========================");
        System.out.println("Diagnosis ID: " + diagnosis.getDiagnosisId());
        System.out.println("Patient ID: " + diagnosis.getPatientId());
        System.out.println("Doctor ID: " + diagnosis.getDoctorId());
        System.out.println("Diagnosis Date: " + diagnosis.getDiagnosisDate());
        System.out.println("Diagnosis Description: " + diagnosis.getDiagnosisDescription());
        System.out.println("Symptoms: " + diagnosis.getSymptoms());
        System.out.println("Severity Level: " + diagnosis.getSeverityLevel());
        System.out.println("Recommendations: " + diagnosis.getRecommendations());
        System.out.println("Notes: " + diagnosis.getNotes());
    }

    //View Patient Treatment History
    public static void viewPatientTreatmentHistory() {
        System.out.println("\n=== Patient Treatment History ===");
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        // Find the patient in the patientList
        TreatmentHistory patient = MedicalTreatmentManagement.getTreatmentHistoryByPatientId(patientId);
        if (patient == null) {
            System.out.println("No such Patient ID found.");
            //back to main menu
            return;
        }

        // List the treatment history for the patient
        System.out.println("\nPatient Treatment History for Patient ID: " + patientId);
        System.out.println("===================================");

        for (int i = 0; i < historyList.size(); i++) {
            TreatmentHistory history = historyList.get(i);
            if (history.getPatientId().equals(patientId)) {
                System.out.println("History ID: " + history.getHistoryId());
                System.out.println("Treatment ID: " + history.getTreatmentId());
                System.out.println("Diagnosis ID: " + history.getDiagnosisId());
                System.out.println("Doctor ID: " + history.getDoctorId());
                System.out.println("Treatment Date: " + history.getTreatmentDate());
                if (history.getFollowUpDate() != null) {
                    System.out.println("Follow-Up Date: " + history.getFollowUpDate());
                } else {
                    System.out.println("Follow-Up Date: Not Scheduled");
                }
                System.out.println("Follow-Up Date: " + history.getFollowUpDate());
                System.out.println("Outcome: " + history.getTreatmentOutcome());
                System.out.println("Status: " + history.getStatus());
                System.out.println("Notes: " + history.getNotes());
                System.out.println("-------------------------------\n");
            }
        }
    }

    // Update Treatment Status
    public static void updateTreatmentStatus() {
        System.out.println("\n=== Update Treatment Status ===");
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine();

        TreatmentHistory treatment = MedicalTreatmentManagement.getTreatmentHistoryById(treatmentId);
        
        if (treatment == null) {
            System.out.println("No such Treatment ID found.");
            //back to main menu
            return;
        }

        System.out.println("\nCurrent Treatment Status: " + treatment.getStatus());
        System.out.println("[1] Active");
        System.out.println("[2] Completed");
        System.out.println("[3] Cancelled");
        System.out.print("Enter new status(1 - 3): ");
        String newStatus = scanner.nextLine();

        switch (newStatus) {
            case "1" ->
                treatment.setStatus("Active");
            case "2" ->
                treatment.setStatus("Completed");
            case "3" ->
                treatment.setStatus("Cancelled");
            default -> {
                System.out.println("Invalid status. Please try again.");
                return;
            }
        }

        System.out.println("Treatment status updated successfully.");

        System.out.println("Enter Treatment Outcome: ");
        System.out.println("[1] Successful");
        System.out.println("[2] Partial");
        System.out.println("[3] Failed");
        System.out.println("[4] Ongoing");
        System.out.print("Enter new outcome(1 - 4): ");
        String newOutcome = scanner.nextLine();

        switch (newOutcome) {
            case "1"->
                treatment.setTreatmentOutcome("Successful");
            case "2"->
                treatment.setTreatmentOutcome("Partial");
            case "3"->
                treatment.setTreatmentOutcome("Failed");
            case "4"->
                treatment.setTreatmentOutcome("Ongoing");
            default-> {
                System.out.println("Invalid outcome. Please try again.");
                return;
            }
        }
        System.out.println("Treatment outcome updated successfully.");

        System.out.print("Enter Follow-Up Date ("+UtilityClass.DATE_FORMAT+") or Enter to skip: ");
        String followUpDateStr = scanner.nextLine();

        Date followUpDate;
        if (!followUpDateStr.isEmpty()) {
            followUpDate = UtilityClass.parseDate(followUpDateStr);
            if (followUpDate == null) {
                System.out.println("Invalid date format. Please try again.");
                return;
            }
            treatment.setFollowUpDate(followUpDate);
        }
        System.out.println("Follow-Up Date updated successfully.");

        System.out.print("Enter Additional Notes(Enter to skip): ");
        String notes = scanner.nextLine();
        if (!notes.isEmpty()) {
            treatment.setNotes(notes);
        }
    }

    //Generate Treatment Reports
    public static void generateTreatmentReports() {
     System.out.println("\n=== Treatment Summary Report ===");
        System.out.println("Total Treatments: " + treatmentList.size());
        
        int activeCount = 0, completedCount = 0, cancelledCount = 0;
        
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            switch (treatment.getTreatmentStatus()) {
                case "Active":
                    activeCount++;
                    break;
                case "Completed":
                    completedCount++;
                    break;
                case "Cancelled":
                    cancelledCount++;
                    break;
            }
        }
        
        System.out.println("Active Treatments: " + activeCount);
        System.out.println("Completed Treatments: " + completedCount);
        System.out.println("Cancelled Treatments: " + cancelledCount);
        
        // Treatment types breakdown
        System.out.println("\nTreatment Types Breakdown:");
        int medicationCount = 0, surgeryCount = 0, therapyCount = 0, otherCount = 0;
        
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            switch (treatment.getTreatmentType()) {
                case "Medication":
                    medicationCount++;
                    break;
                case "Surgery":
                    surgeryCount++;
                    break;
                case "Therapy":
                    therapyCount++;
                    break;
                default:
                    otherCount++;
                    break;
            }
        }
        
        System.out.println("Medication: " + medicationCount);
        System.out.println("Surgery: " + surgeryCount);
        System.out.println("Therapy: " + therapyCount);
        System.out.println("Other: " + otherCount);
    }

    // Generate diagnosis statistics report
    public static void generateDiagnosisStatisticsReport() {
        System.out.println("\n=== Diagnosis Statistics Report ===");
        System.out.println("Total Diagnoses: " + diagnosisList.size());
        
        int lowCount = 0, mediumCount = 0, highCount = 0, criticalCount = 0;
        
        for (int i = 0; i < diagnosisList.size(); i++) {
            Diagnosis diagnosis = diagnosisList.get(i);
            switch (diagnosis.getSeverityLevel()) {
                case "Low":
                    lowCount++;
                    break;
                case "Medium":
                    mediumCount++;
                    break;
                case "High":
                    highCount++;
                    break;
                case "Critical":
                    criticalCount++;
                    break;
            }
        }
        
        System.out.println("Low Severity: " + lowCount);
        System.out.println("Medium Severity: " + mediumCount);
        System.out.println("High Severity: " + highCount);
        System.out.println("Critical Severity: " + criticalCount);
    }

    // Generate patient history report
    public static void generatePatientHistoryReport() {
        System.out.println("\n=== Patient Treatment History Report ===");
        
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        
        System.out.println("\nPatient ID: " + patientId);
        System.out.println("=====================================");
        
        // Find all diagnoses for this patient
        System.out.println("Diagnoses:");
        boolean foundDiagnosis = false;
        for (int i = 0; i < diagnosisList.size(); i++) {
            Diagnosis diagnosis = diagnosisList.get(i);
            if (diagnosis.getPatientId().equals(patientId)) {
                foundDiagnosis = true;
                System.out.println("- " + diagnosis.getDiagnosisDescription() + " (" + diagnosis.getSeverityLevel() + ")");
            }
        }
        
        if (!foundDiagnosis) {
            System.out.println("No diagnoses found.");
        }
        
        // Find all treatments for this patient
        System.out.println("\nTreatments:");
        boolean foundTreatment = false;
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getPatientId().equals(patientId)) {
                foundTreatment = true;
                System.out.println("- " + treatment.getTreatmentType() + " (" + treatment.getTreatmentStatus() + ")");
            }
        }
        
        if (!foundTreatment) {
            System.out.println("No treatments found.");
        }
    }
}
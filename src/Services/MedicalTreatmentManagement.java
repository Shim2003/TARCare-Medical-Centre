/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ADT.DynamicList;
import Entity.*;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class MedicalTreatmentManagement {
    
    // Collections to store data
    private static DynamicList<Diagnosis> diagnosisList = new DynamicList<>();
    private static DynamicList<MedicalTreatment> treatmentList = new DynamicList<>();
    private static DynamicList<TreatmentHistory> historyList = new DynamicList<>();
    
    // Constants
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    
    // ID counters
    private static int diagnosisIdCounter = 1001;
    private static int treatmentIdCounter = 2001;
    private static int historyIdCounter = 3001;
    
    // Scanner for input
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        menu();
    }
    
    public static void menu() {
        while (true) {
            System.out.println("\n=== Medical Treatment Management System ===");
            System.out.println("1. Add New Diagnosis");
            System.out.println("2. Create Medical Treatment");
            System.out.println("3. Update Treatment Status");
            System.out.println("4. View Patient Treatment History");
            System.out.println("5. View Diagnosis Details");
            System.out.println("6. Generate Treatment Reports");
            System.out.println("7. Exit");
            
            System.out.print("Enter your choice (1-7): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1:
                        addDiagnosis();
                        break;
                    case 2:
                        createTreatment();
                        break;
                    case 3:
                        updateTreatmentStatus();
                        break;
                    case 4:
                        viewPatientTreatmentHistory();
                        break;
                    case 5:
                        viewDiagnosisDetails();
                        break;
                    case 6:
                        generateReports();
                        break;
                    case 7:
                        System.out.println("Exiting Medical Treatment Management System. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select from 1 to 7.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }
    
    // Add new diagnosis
    public static void addDiagnosis() {
        try {
            System.out.println("\n=== Add New Diagnosis ===");
            
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine();
            
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine();
            
            System.out.print("Enter Symptoms: ");
            String symptoms = scanner.nextLine();
            
            System.out.print("Enter Diagnosis Description: ");
            String diagnosisDescription = scanner.nextLine();
            
            System.out.print("Enter Severity Level (Low/Medium/High/Critical): ");
            String severityLevel = scanner.nextLine();
            
            System.out.print("Enter Recommendations: ");
            String recommendations = scanner.nextLine();
            
            System.out.print("Enter Additional Notes: ");
            String notes = scanner.nextLine();
            
            // Generate diagnosis ID
            String diagnosisId = "D" + diagnosisIdCounter++;
            
            // Create diagnosis
            Diagnosis diagnosis = new Diagnosis(diagnosisId, patientId, doctorId, new Date(), 
                                              symptoms, diagnosisDescription, severityLevel, 
                                              recommendations, notes);
            
            diagnosisList.add(diagnosis);
            System.out.println("Diagnosis added successfully! Diagnosis ID: " + diagnosisId);
            
        } catch (Exception e) {
            System.out.println("Error adding diagnosis: " + e.getMessage());
        }
    }
    
    // Create medical treatment
    public static void createTreatment() {
        try {
            System.out.println("\n=== Create Medical Treatment ===");
            
            System.out.print("Enter Diagnosis ID: ");
            String diagnosisId = scanner.nextLine();
            
            // Find diagnosis
            Diagnosis diagnosis = findDiagnosisById(diagnosisId);
            if (diagnosis == null) {
                System.out.println("Diagnosis not found!");
                return;
            }
            
            System.out.print("Enter Treatment Name: ");
            String treatmentName = scanner.nextLine();
            
            System.out.print("Enter Treatment Description: ");
            String treatmentDescription = scanner.nextLine();
            
            System.out.print("Enter Treatment Type (Medication/Surgery/Therapy/Other): ");
            String treatmentType = scanner.nextLine();
            
            System.out.print("Enter Treatment Duration: ");
            String treatmentDuration = scanner.nextLine();
            
            System.out.print("Enter Medical Treatment Advice: ");
            String medicalTreatmentAdvise = scanner.nextLine();
            
            // Generate treatment ID
            String treatmentId = "T" + treatmentIdCounter++;
            
            // Create treatment
            MedicalTreatment treatment = new MedicalTreatment(treatmentId, diagnosis.getPatientId(), 
                                                             diagnosis.getDoctorId(), treatmentName, 
                                                             treatmentDescription, "Active", 
                                                             treatmentType, treatmentDuration, 
                                                             medicalTreatmentAdvise);
            
            treatmentList.add(treatment);
            
            // Create treatment history
            String historyId = "H" + historyIdCounter++;
            TreatmentHistory history = new TreatmentHistory(historyId, treatmentId, diagnosisId, 
                                                          diagnosis.getPatientId(), diagnosis.getDoctorId(), 
                                                          new Date(), null, "Ongoing", "Active", 
                                                          "Treatment initiated");
            
            historyList.add(history);
            
            System.out.println("Treatment created successfully! Treatment ID: " + treatmentId);
            
        } catch (Exception e) {
            System.out.println("Error creating treatment: " + e.getMessage());
        }
    }
    
    // Update treatment status
    public static void updateTreatmentStatus() {
        try {
            System.out.println("\n=== Update Treatment Status ===");
            
            System.out.print("Enter Treatment ID: ");
            String treatmentId = scanner.nextLine();
            
            MedicalTreatment treatment = findTreatmentById(treatmentId);
            if (treatment == null) {
                System.out.println("Treatment not found!");
                return;
            }
            
            System.out.println("Current Status: " + treatment.getTreatmentStatus());
            System.out.print("Enter New Status (Active/Completed/Cancelled): ");
            String newStatus = scanner.nextLine();
            
            System.out.print("Enter Treatment Outcome (Successful/Partial/Failed/Ongoing): ");
            String outcome = scanner.nextLine();
            
            System.out.print("Enter Follow-up Date (" + DATE_FORMAT + ") or press Enter to skip: ");
            String followUpDateStr = scanner.nextLine();
            
            Date followUpDate = null;
            if (!followUpDateStr.isEmpty()) {
                followUpDate = sdf.parse(followUpDateStr);
            }
            
            System.out.print("Enter Notes: ");
            String notes = scanner.nextLine();
            
            // Update treatment status
            treatment.setTreatmentStatus(newStatus);
            
            // Update treatment history
            TreatmentHistory history = findHistoryByTreatmentId(treatmentId);
            if (history != null) {
                history.setStatus(newStatus);
                history.setTreatmentOutcome(outcome);
                history.setFollowUpDate(followUpDate);
                history.setNotes(notes);
            }
            
            System.out.println("Treatment status updated successfully!");
            
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use " + DATE_FORMAT);
        } catch (Exception e) {
            System.out.println("Error updating treatment status: " + e.getMessage());
        }
    }
    
    // View patient treatment history
    public static void viewPatientTreatmentHistory() {
        System.out.println("\n=== Patient Treatment History ===");
        
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        
        System.out.println("\nTreatment History for Patient: " + patientId);
        System.out.println("=====================================");
        
        boolean found = false;
        for (int i = 0; i < historyList.size(); i++) {
            TreatmentHistory history = historyList.get(i);
            if (history.getPatientId().equals(patientId)) {
                found = true;
                System.out.println("History ID: " + history.getHistoryId());
                System.out.println("Treatment ID: " + history.getTreatmentId());
                System.out.println("Diagnosis ID: " + history.getDiagnosisId());
                System.out.println("Treatment Date: " + sdf.format(history.getTreatmentDate()));
                System.out.println("Status: " + history.getStatus());
                System.out.println("Outcome: " + history.getTreatmentOutcome());
                if (history.getFollowUpDate() != null) {
                    System.out.println("Follow-up Date: " + sdf.format(history.getFollowUpDate()));
                }
                System.out.println("Notes: " + history.getNotes());
                System.out.println("-------------------------------------");
            }
        }
        
        if (!found) {
            System.out.println("No treatment history found for this patient.");
        }
    }
    
    // View diagnosis details
    public static void viewDiagnosisDetails() {
        System.out.println("\n=== Diagnosis Details ===");
        
        System.out.print("Enter Diagnosis ID: ");
        String diagnosisId = scanner.nextLine();
        
        Diagnosis diagnosis = findDiagnosisById(diagnosisId);
        if (diagnosis == null) {
            System.out.println("Diagnosis not found!");
            return;
        }
        
        System.out.println("\nDiagnosis Details:");
        System.out.println("==================");
        System.out.println("Diagnosis ID: " + diagnosis.getDiagnosisId());
        System.out.println("Patient ID: " + diagnosis.getPatientId());
        System.out.println("Doctor ID: " + diagnosis.getDoctorId());
        System.out.println("Diagnosis Date: " + sdf.format(diagnosis.getDiagnosisDate()));
        System.out.println("Symptoms: " + diagnosis.getSymptoms());
        System.out.println("Diagnosis: " + diagnosis.getDiagnosisDescription());
        System.out.println("Severity: " + diagnosis.getSeverityLevel());
        System.out.println("Recommendations: " + diagnosis.getRecommendations());
        System.out.println("Notes: " + diagnosis.getNotes());
    }
    
    // Generate reports
    public static void generateReports() {
        while (true) {
            System.out.println("\n=== Generate Reports ===");
            System.out.println("1. Treatment Summary Report");
            System.out.println("2. Diagnosis Statistics Report");
            System.out.println("3. Patient Treatment History Report");
            System.out.println("4. Back to Main Menu");
            
            System.out.print("Enter your choice (1-4): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1:
                        generateTreatmentSummaryReport();
                        break;
                    case 2:
                        generateDiagnosisStatisticsReport();
                        break;
                    case 3:
                        generatePatientHistoryReport();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please select from 1 to 4.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }
    
    // Generate treatment summary report
    public static void generateTreatmentSummaryReport() {
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
                System.out.println("- " + treatment.getTreatmentName() + " (" + treatment.getTreatmentStatus() + ")");
            }
        }
        
        if (!foundTreatment) {
            System.out.println("No treatments found.");
        }
    }
    
    // Helper methods
    private static Diagnosis findDiagnosisById(String diagnosisId) {
        for (int i = 0; i < diagnosisList.size(); i++) {
            Diagnosis diagnosis = diagnosisList.get(i);
            if (diagnosis.getDiagnosisId().equals(diagnosisId)) {
                return diagnosis;
            }
        }
        return null;
    }
    
    private static MedicalTreatment findTreatmentById(String treatmentId) {
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getTreatmentId().equals(treatmentId)) {
                return treatment;
            }
        }
        return null;
    }
    
    private static TreatmentHistory findHistoryByTreatmentId(String treatmentId) {
        for (int i = 0; i < historyList.size(); i++) {
            TreatmentHistory history = historyList.get(i);
            if (history.getTreatmentId().equals(treatmentId)) {
                return history;
            }
        }
        return null;
    }
}

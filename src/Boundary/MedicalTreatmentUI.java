/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.DiagnosisManagement;
import Control.MedicalTreatmentManagement;
import Control.TreatmentHistoryManagement;
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

    // Date format for displaying dates
    private static final SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
    
    public static void main(String[] args) {
        MedicalTreatmentMenu();
    }
    
    public static void MedicalTreatmentMenu() {
        while(true) {
            System.out.println("\n=== Medical Treatment Management System ===");
            System.out.println("1. Create New Diagnosis");
            System.out.println("2. Create Medical Treatment");
            System.out.println("3. View Diagnosis List");
            System.out.println("4. View Patient Treatment History");
            System.out.println("5. Update Treatment Status");
            System.out.println("6. Generate Diagnosis Reports");
            System.out.println("7. Generate Treatment Reports");
            System.out.println("8. Exit to Main Menu");
            
            System.out.print("Enter your choice (1-8): ");
            
            try{
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer
                
                switch (choice) {
                    case 1 -> 
                        addDiagnosis();
                    case 2 -> 
                        createTreatment();
                    case 3 -> 
                        viewDiagnosisDetails();
                    case 4 -> 
                        viewPatientTreatmentHistory();
                    case 5 -> 
                        updateTreatmentStatus();
                    case 6 ->
                        generateDiagnosisStatisticsReport();
                    case 7 -> 
                        generateTreatmentReports();
                    case 8 -> {
                        System.out.println("Exiting to Main Menu...");
                        scanner.close(); // Close the scanner before exiting
                        return; // Exit to main menu
                    }
                    default -> {
                        System.out.println("Invalid Choice. Please enter again from 1 to 5.");
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
        String patientId = null;
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

    public static void createTreatment() {
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
            treatmentDate, "Active", treatmentAdvice, medicineList);

        // Add the new MedicalTreatment object to the treatmentList
        MedicalTreatmentManagement.addMedicalTreatment(treatment);

        String notes = "Patient responded well to initial consultation. No allergic reactions reported.";
        
        TreatmentHistory history = new TreatmentHistory(treatment.getTreatmentId(), consultationId, patientId, doctorId,
            treatmentDate, null, "Ongoing", "Active", treatmentAdvice, notes, medicineList);

        // Add the new TreatmentHistory object to the historyList
        TreatmentHistoryManagement.addTreatmentHistory(history);

        // Display creation summary
        System.out.println("Treatment Date: " + sdf.format(treatmentDate));
        System.out.println("Treatment Advice: " + treatmentAdvice);
        System.out.println("\nDummy Medicines Added:");
        for (int i = 0; i < medicineList.size(); i++) {
            MedicalTreatmentItem item = medicineList.get(i);
            System.out.println("  " + (i+1) + ". " + item.getMedicineName() + " - " + item.getDosage());
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println(">> Medical Treatment created successfully!");
        System.out.println("Patient ID: " + patientId);
        System.out.println("Treatment ID: " + treatment.getTreatmentId());
        System.out.println("Total medicines prescribed: " + medicineList.size());
        System.out.println("=".repeat(50));
    }

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


            //sample data for medicine
            medicineName = "Paracetamol";
            dosage = "500mg";
            frequency = "Twice a day";
            duration = "5 days";
            method = "Oral";


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
        String treatmentAdvice = "scanner.nextLine();";

        Consultation currentConsultation = Consultation.getCurrentConsultation();
        // String consultationId = currentConsultation.getConsultationId();
        // String patientId = currentConsultation.getPatientId();
        // String doctorId = currentConsultation.getDoctorId();
        
        // sample data for consultation and patient
        String consultationId = "C001";
        String patientId = "P001";
        String doctorId = "D001";
        treatmentAdvice = "Follow the prescribed dosage and frequency. Drink plenty of water.";

        // Create a new MedicalTreatment object
        MedicalTreatment treatment = new MedicalTreatment(consultationId, patientId, doctorId,
        treatmentDate,"Active", treatmentAdvice, medicineList);

        // Add the new MedicalTreatment object to the treatmentList
        MedicalTreatmentManagement.addMedicalTreatment(treatment);

        // Create treatment history
        TreatmentHistory history = new TreatmentHistory(treatment.getTreatmentId(), consultationId, patientId, doctorId,
            treatmentDate, null, "Ongoing", "Active", treatmentAdvice, medicineList);

        // Add the new TreatmentHistory object to the historyList
        TreatmentHistoryManagement.addTreatmentHistory(history);

        System.out.println("Medical Treatment created successfully! Treatment ID: " + treatment.getTreatmentId());
    }

    
//View Diagnosis Details
public static void viewDiagnosisDetails() {
    System.out.println("\n=== Diagnosis Details ===");
    
    if (DiagnosisManagement.getDiagnosisList().isEmpty()) {
        System.out.println("No diagnoses record currently.");
        System.out.println("Press Enter to return to main menu...");
        scanner.nextLine();
        return;
    }
    
    // Display available diagnosis IDs for reference
    System.out.println("\nAvailable Diagnosis IDs:");
    System.out.println("------------------------");
    for (int i = 0; i < DiagnosisManagement.getDiagnosisList().size(); i++) {
        Diagnosis d = DiagnosisManagement.getDiagnosisList().get(i);
        System.out.printf("• %s (Patient: %s, Date: %s)\n", 
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
        System.out.println("Press Enter to return to main menu...");
        scanner.nextLine();
        return;
    }

    // Find the diagnosis
    Diagnosis diagnosis = DiagnosisManagement.getDiagnosisListById(diagnosisId);

    if (diagnosis == null) {
        System.out.println(">> No diagnosis found with ID: " + diagnosisId);
        System.out.println("Please check the available Diagnosis IDs above and try again.");
        System.out.println("Press Enter to return to main menu...");
        scanner.nextLine();
        return;
    }

    // Display the diagnosis details using the enhanced toString method
    System.out.println("\n" + diagnosis.toString());
}

    //View Patient Treatment History
    public static void viewPatientTreatmentHistory() {
    System.out.println("\n=== Patient Treatment History ===");
    System.out.print("Enter Patient ID: ");
    String patientId = scanner.nextLine().trim();

    if (patientId.isEmpty()) {
        System.out.println("Patient ID cannot be empty.");
        return;
    }

    // Get all treatment history for the patient
    DynamicList<TreatmentHistory> treatmentHistoryList = 
        TreatmentHistoryManagement.getTreatmentHistoryByPatientIdList(patientId);
        
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
            System.out.println("\n" + "─".repeat(68));
            System.out.print("Press Enter to view next record...");
            scanner.nextLine();
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

        TreatmentHistory history = TreatmentHistoryManagement.getTreatmentHistoryById(treatmentId);
        
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

        TreatmentHistory treatment = TreatmentHistoryManagement.getTreatmentHistoryById(treatmentId);
        
        if (treatment == null) {
            System.out.println("No treatment found with ID: " + treatmentId);
            return;
        }

        // Display current treatment details using toString
        System.out.println("\nCurrent Treatment Details:");
        System.out.println(treatment.toString());
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Would you like to update this treatment? (y/n): ");
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
                case 1 -> newStatus = "Active";
                case 2 -> newStatus = "Completed";
                case 3 -> newStatus = "Cancelled";
                default -> {
                    System.out.println("Invalid choice. Status update cancelled.");
                    return;
                }
            }
            treatment.setStatus(newStatus);
            System.out.println("✓ Treatment status updated to: " + newStatus);
            
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
                case 1 -> newOutcome = "Successful";
                case 2 -> newOutcome = "Partial";
                case 3 -> newOutcome = "Failed";
                case 4 -> newOutcome = "Ongoing";
                default -> {
                    System.out.println("Invalid choice. Outcome update cancelled.");
                    return;
                }
            }
            treatment.setTreatmentOutcome(newOutcome);
            System.out.println("✓ Treatment outcome updated to: " + newOutcome);
            
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
                System.out.println("✓ Follow-Up Date updated to: " + sdf.format(followUpDate));
            }
        }

        // Update Additional Notes
        System.out.print("Enter Additional Notes (or press Enter to skip): ");
        String notes = scanner.nextLine();
        if (!notes.trim().isEmpty()) {
            treatment.setNotes(notes);
            System.out.println("✓ Notes updated successfully.");
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TREATMENT UPDATE COMPLETED SUCCESSFULLY!");
        System.out.println("=".repeat(50));
        
        // Display updated treatment details
        System.out.println("\nUpdated Treatment Details:");
        System.out.println(treatment.toString());
    }

    // Generate diagnosis statistics report
    public static void generateDiagnosisStatisticsReport() {
        System.out.println("\n=== Diagnosis Statistics Report ===");
        System.out.println("Total Diagnoses: " + DiagnosisManagement.getDiagnosisList().size());
        
        int lowCount = 0, mediumCount = 0, highCount = 0, criticalCount = 0;
        
        for (int i = 0; i < DiagnosisManagement.getDiagnosisList().size(); i++) {
            Diagnosis diagnosis = DiagnosisManagement.getDiagnosisList().get(i);
            switch (diagnosis.getSeverityLevel()) {
                case "Low"->
                    lowCount++;
                case "Medium"->
                    mediumCount++;
                case "High"->
                    highCount++;
                case "Critical"->
                    criticalCount++;
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
        for (int i = 0; i < DiagnosisManagement.getDiagnosisList().size(); i++) {
            Diagnosis diagnosis = DiagnosisManagement.getDiagnosisList().get(i);
            if (diagnosis.getPatientId().equals(patientId)) {
                foundDiagnosis = true;
                System.out.println("- " + diagnosis.getDiagnosisDescription() + " (" + diagnosis.getSeverityLevel() + ")");
            }
        }
        
        if (!foundDiagnosis) {
            System.out.println("No diagnoses found.");
        }
        
        // Print out the medicine list for this patient
        System.out.println("\nMedicines:");
        boolean foundMedicine = false;
        for (int i = 0; i < MedicalTreatmentManagement.getMedicalTreatmentList().size(); i++) {
            MedicalTreatment treatment = MedicalTreatmentManagement.getMedicalTreatmentList().get(i);
            if (treatment.getPatientId().equals(patientId)) {
                DynamicList<MedicalTreatmentItem> medicines = treatment.getMedicineList();
                if (medicines != null && medicines.size() > 0) {
                    foundMedicine = true;
                    for (int j = 0; j < medicines.size(); j++) {
                        MedicalTreatmentItem item = medicines.get(j);
                        System.out.printf("%-25s %-15s %-20s %-15s %-20s\n",
                            item.getMedicineName(),
                            item.getDosage(),
                            item.getFrequency(),
                            item.getDuration(),
                            item.getMethod()
                        );
                    }
                }
            }
        }
        if (!foundMedicine) {
            System.out.println("No medicines found.");
        }
    }

    //Generate Treatment Reports
    public static void generateTreatmentReports() {
     System.out.println("\n=== Treatment Summary Report ===");
        System.out.println("Total Treatments: " + MedicalTreatmentManagement.getTreatmentList().size());
        
        int activeCount = 0, completedCount = 0, cancelledCount = 0;
        
        for (int i = 0; i < MedicalTreatmentManagement.getTreatmentList().size(); i++) {
            MedicalTreatment treatment = MedicalTreatmentManagement.getTreatmentList().get(i);
            switch (treatment.getTreatmentStatus()) {
                case "Active"->
                    activeCount++;
                case "Completed"->
                    completedCount++;
                case "Cancelled"->
                    cancelledCount++;
                default -> {
                    System.out.println("Unknown treatment status: " + treatment.getTreatmentStatus());
                    return;
                }
            }
        }
        
        System.out.println("Active Treatments: " + activeCount);
        System.out.println("Completed Treatments: " + completedCount);
        System.out.println("Cancelled Treatments: " + cancelledCount);
    }
}
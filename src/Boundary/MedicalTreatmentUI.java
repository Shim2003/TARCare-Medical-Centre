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
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class MedicalTreatmentUI {
    
    private static final Scanner scanner = new Scanner(System.in);

    // ID counters
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
            System.out.println("1. Create Medical Treatment");
            System.out.println("2. View Patient Treatment History");
            System.out.println("3. Update Treatment Status");
            System.out.println("4. Generate Treatment Reports");
            System.out.println("5. Exit to Main Menu");
            
            System.out.print("Enter your choice (1-5): ");
            
            try{
                int choice = scanner.nextInt();
                scanner.nextLine(); //Clear buffer
                
                switch (choice) {
                    case 1 -> 
                        createTreatment();
                    case 2 -> 
                        viewPatientTreatmentHistory();
                    case 3 -> 
                        updateTreatmentStatus();
                    case 4 -> 
                        generateTreatmentReports();
                    case 5 -> {
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

    public static void createTreatment() {

        try {
            System.out.println("\n=== Create Medical Treatment ===");


            System.out.print("Enter Diagnosis ID: ");
            String diagnosisId = scanner.nextLine();

            DynamicList<MedicalTreatmentItem> medicineList = new DynamicList<>();
            int i = 1;
            // if it is medication, ask for the medicine name and for many day(s)
                
            //the doctor have to enter a list of medicine and its details, 
            //if doctor enter a "x" means that enough for medicine
            while (true) { 
                System.out.print("Medicine" + "[ " + i + " ] ");

                System.out.print("Medicine Name:");
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
                    i++;
            }

            // Get the treatment date automatically(use the format in the utility class)
            System.out.println("Treatment Date: " + sdf.format(new Date()));
            Date treatmentDate = new Date();

            System.out.print("Enter Medical Treatment Advice(s): ");
            String treatmentAdvice = scanner.nextLine();

            // Generate Treatment ID
            String treatmentId = "T" + treatmentIdCounter++;

            Consultation currentConsultation = Consultation.getCurrentConsultation();
            String consultationId = currentConsultation.getConsultationId();
            String patientId = currentConsultation.getPatientId();
            String doctorId = currentConsultation.getDoctorId();

            // Create a new MedicalTreatment object
            MedicalTreatment treatment = new MedicalTreatment(treatmentId, consultationId, patientId, doctorId,
            treatmentDate,"Active", treatmentAdvice, medicineList);

            // Add the new MedicalTreatment object to the treatmentList
            MedicalTreatmentManagement.addMedicalTreatment(treatment);

            // Create treatment history
            String historyId = "H" + historyIdCounter++;
            TreatmentHistory history = new TreatmentHistory(historyId, treatmentId, patientId, doctorId,
                treatmentDate, null, "Ongoing", "Active", "");

            // Add the new TreatmentHistory object to the historyList
            MedicalTreatmentManagement.addTreatmentHistory(history);

            System.out.println("Medical Treatment created successfully! Treatment ID: " + treatmentId);
        } catch (Exception e) {
            System.out.println("Invalid Input. Please select again.");
        }
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

        //trace the patient treatment history based on the patient ID
        DynamicList<TreatmentHistory> treatmentHistoryList = MedicalTreatmentManagement.getTreatmentHistoryByPatientIdList(patientId);
        if (treatmentHistoryList.isEmpty()) {
            System.out.println("No treatment history found for this patient.");
            return;
        } else {
            for (int i = 0; i < treatmentHistoryList.size(); i++) {
                System.out.println("Treatment ID: " + treatmentHistoryList.get(i).getTreatmentId());
                System.out.println("Consultation ID: " + treatmentHistoryList.get(i).get());
                System.out.println("Patient ID: " + treatmentHistoryList.get(i).getPatientId());
                System.out.println("Doctor ID: " + treatmentHistoryList.get(i).getDoctorId());
                System.out.println("Treatment Date: " + treatmentHistoryList.get(i).getTreatmentDate());
                System.out.println("Treatment Outcome: " + treatmentHistoryList.get(i).getTreatmentOutcome());
                System.out.println("Status: " + treatmentHistoryList.get(i).getStatus());
                System.out.println("Notes: " + treatmentHistoryList.get(i).getNotes());
                System.out.println("==========================");
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
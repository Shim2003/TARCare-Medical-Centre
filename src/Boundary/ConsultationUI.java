/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Control.ConsultationManagement;
import Control.LeaveManagement;
import Control.PatientManagement;
import Control.ConsultationManagement;
import DAO.ClinicData;
import java.util.Scanner;
import Boundary.DiagnosisUI;
import Boundary.MedicalTreatmentUI;
import Entity.Doctor; 
import ADT.MyList; 
import Entity.*;
import Utility.UtilityClass;
import ADT.DynamicList;
import java.time.format.DateTimeFormatter;
import java.time.Duration;


public class ConsultationUI {

    private static final ConsultationManagement consultationManagement = new ConsultationManagement();
    private static final Scanner sc = new Scanner(System.in);

    public static void run() {
        int choice;
        ConsultationManagement.syncCurrentConsultingToOngoing();
        do {
            System.out.println("\n======================================");
            System.out.println("          Consultation Module        ");
            System.out.println("======================================");
            System.out.println(" 1. MAIN FUNCTIONS (Daily Use)");
            System.out.println(" 2. MANAGEMENT (Admin/IT Use)");
            System.out.println(" 3. ANALYTICS & UTILITIES");
            System.out.println("--------------------------------------");
            System.out.println(" 0. Back to Main Menu");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showMainFunctionsMenu();
                case 2 -> showManagementMenu();
                case 3 -> showAnalyticsMenu();
                case 0 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // MAIN FUNCTIONS MENU
    private static void showMainFunctionsMenu() {
        int choice;
        do {
            System.out.println("\n====== MAIN FUNCTIONS (Daily Use) ======");
            System.out.println(" 1. Start Next Consultation");
            System.out.println(" 2. View Current Consulting Patients");
            System.out.println(" 3. End Consultation");
            System.out.println(" 4. View Patients Who Finished Consultation");
            System.out.println(" 5. View Consultation Report");
            System.out.println(" 0. Back");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> startNextConsultationUI();
                case 2 -> viewCurrentConsultingUI();
                case 3 -> endConsultationUI();
                case 4 -> viewCompletedPatientsUI();
                case 5 -> viewConsultationReportUI();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // MANAGEMENT MENU
    private static void showManagementMenu() {
        int choice;
        do {
            System.out.println("\n====== MANAGEMENT (Admin/IT Use) ======");
            System.out.println(" 1. Delete Consultation");
            System.out.println(" 2. Show All Completed Consultations");
            System.out.println(" 3. Show Ongoing Consultations");
            System.out.println(" 0. Back");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> deleteConsultationUI();
                case 2 -> viewCompletedPatientsUI2();
                case 3 -> viewCurrentConsultingUI2();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ANALYTICS MENU
    private static void showAnalyticsMenu() {
        int choice;
        do {
            System.out.println("\n====== ANALYTICS & UTILITIES ======");
            System.out.println(" 1. Show Consultation Duration Statistics");
            System.out.println(" 2. Backup Consultations");
            System.out.println(" 0. Back");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showConsultationDurationStatsUI();
                case 2 -> {
                    ConsultationManagement.backupConsultations();
                    System.out.println("Consultations backed up successfully!");
                    showAllBackupConsultationsUI();
                }
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
    
    public static void startNextConsultationUI() {
        if (!ConsultationManagement.canStartConsultation()) {
            System.out.println("Maximum consultations reached (3). Please wait for a consultation to finish.");
            return;
        }
        
        printDoctorsStatus("All Doctors Status Before Assignment");
        
        QueueEntry nextPatient = ConsultationManagement.getNextWaitingPatient();
        if (nextPatient == null) {
            System.out.println("No patient has been called or no free doctors available.");
            return;
        }

        // Enter Doctor ID
        Doctor assignedDoctor = null;
        while (assignedDoctor == null) {
            System.out.print("Enter Doctor ID to assign for Patient " + nextPatient.getPatientId() + ": ");
            String doctorId = sc.nextLine();
            Doctor d = DoctorManagement.findDoctorById(doctorId);

            if (d == null) {
                System.out.println("Doctor ID not found. Try again.");
            } else if (!d.getWorkingStatus().equals(UtilityClass.statusFree)) {
                System.out.println("Doctor is not free. Please choose another doctor.");
            } else {
                assignedDoctor = d;
            }
        }

        Patient patient = PatientManagement.findPatientById(nextPatient.getPatientId());
        System.out.print("Enter Symptoms for Patient " + patient.getFullName() + ": ");
        String symptoms = sc.nextLine();

        Consultation consultation = ConsultationManagement.startNextConsultation(assignedDoctor.getDoctorID(), symptoms);

        if (consultation != null) {
            System.out.println("\n================ Consultation Started ================");
            System.out.println("Consultation ID: " + consultation.getConsultationId());
            System.out.println("Doctor: " + assignedDoctor.getName() + " (ID: " + assignedDoctor.getDoctorID() + ")");
            System.out.println("Patient: " + patient.getFullName() + " (ID: " + patient.getPatientID() + ")");
            System.out.println("Start Time: " + UtilityClass.formatLocalDateTime(consultation.getStartTime()));
            System.out.println("Symptoms: " + (consultation.getSymptoms() != null && consultation.getSymptoms().length() > 0
                                   ? consultation.getSymptoms()
                                   : "-"));
            System.out.println("Doctor Status: " + assignedDoctor.getWorkingStatus());
            System.out.println("======================================================\n");

            printDoctorsStatus("All Doctors Status After Assignment");


            DiagnosisUI.addDiagnosis();
            MedicalTreatmentUI.createTreatment();
        } else {
            System.out.println("Failed to start consultation.");
        }
    }
    
    private static void endConsultationUI() {
        System.out.print("Enter Patient ID to end consultation: ");
        String id = sc.nextLine();
        boolean success = ConsultationManagement.endConsultation(id);
        System.out.println(success ? "Consultation ended successfully." : "Failed to end consultation.");
    }
    
    private static void viewCurrentConsultingUI() {
        var list = ConsultationManagement.getCurrentConsultingInfo();
        if (!ConsultationManagement.hasCurrentConsulting()) {
            System.out.println("No current consultations.");
        } else {
            System.out.println("=== Current Consulting Patients ===");
            ConsultationManagement.printCurrentConsultingInfo(); // Control 内部处理遍历和打印
        }
    }
    
    private static void viewCurrentConsultingUI2() {
        String[] displayArr = ConsultationManagement.getOngoingConsultationsForDisplayArray();

        if (displayArr.length == 0) {
            System.out.println("No current consultations.");
        } else {
            System.out.println("\n=== Ongoing Consultations ===\n");
            for (String line : displayArr) {
                System.out.println(line);
            }
        }
    }

    
    private static void viewCompletedPatientsUI() {
        System.out.println("=== Completed Patients ===");
        ConsultationManagement.printCompletedPatientsInfo();
    }
    
    // Boundary.ConsultationUI.java
    private static void viewCompletedPatientsUI2() {
        System.out.println();
        var displayList = ConsultationManagement.getCompletedConsultationsDisplayList();
        for (String line : displayList) {
            System.out.println(line);
        }
    }

    
    private static void viewConsultationReportUI() {
        System.out.print("Enter Patient ID or Consultation ID to view report: ");
        String id = sc.nextLine();
        Consultation[] arr = ConsultationManagement.getConsultationReportArray(id);

        if (arr.length == 0) {
            System.out.println("No consultation report found.");
        } else {
            System.out.println("==============================================================================================================");
            System.out.printf("%-12s %-10s %-10s %-20s %-20s %-20s %-12s%n",
                              "ConsultID", "Patient", "Doctor", "Symptoms", "Start Time", "End Time", "Duration");
            System.out.println("==============================================================================================================");

            for (Consultation c : arr) {
                String startTime = (c.getStartTime() != null ? UtilityClass.formatLocalDateTime(c.getStartTime()) : "-");
                String endTime = (c.getEndTime() != null ? UtilityClass.formatLocalDateTime(c.getEndTime()) : "-");
                String symptoms = (c.getSymptoms() != null && !c.getSymptoms().isEmpty()) ? c.getSymptoms() : "-";
                String duration = (c.getEndTime() != null ? ConsultationManagement.formatDuration(c.getDurationSeconds()) : "-");

                System.out.printf("%-12s %-10s %-10s %-20s %-20s %-20s %-12s%n",
                                  c.getConsultationId(),
                                  c.getPatientId(),
                                  c.getDoctorId(),
                                  symptoms,
                                  startTime,
                                  endTime,
                                  duration);
            }
            System.out.println("==============================================================================================================");
        }
    }

    
    // ======== MANAGEMENT ========
    private static void deleteConsultationUI() {
        System.out.print("Enter Consultation ID to delete: ");
        String id = sc.nextLine();
        boolean success = ConsultationManagement.deleteConsultationById(id);
        System.out.println(success ? "Deleted successfully." : "Consultation ID not found.");
    }

    private static void printDoctorsStatus(String header) {
        System.out.println();
        ConsultationManagement.printAllDoctorsStatus(header);
        System.out.println();
    }
    
    // ======== ANALYTICS ========
    private static void showConsultationDurationStatsUI() {
        var stats = ConsultationManagement.getConsultationDurationStats();
        if (stats == null) {
            System.out.println("No completed consultations to calculate statistics.");
        } else {
            System.out.println("=== Consultation Duration Statistics (minutes) ===");
            System.out.println("Number of consultations: " + stats.count);
            System.out.println("Average duration       : " + stats.average);
            System.out.println("Maximum duration       : " + stats.max);
            System.out.println("Minimum duration       : " + stats.min);
            System.out.println("standard deviation     : " + stats.standardDeviation);
        }
    }
    
    private static void showAllBackupConsultationsUI() {
        Consultation[] backupArr = ConsultationManagement.exportConsultationsToArray();

        if (backupArr.length == 0) {
            System.out.println("No backed up consultations.");
            return;
        }

        System.out.println("=== Backup Consultations ===");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (Consultation c : backupArr) {
            String startTime = (c.getStartTime() != null) ? c.getStartTime().format(dtf) : "-";
            String endTime = (c.getEndTime() != null) ? c.getEndTime().format(dtf) : "-";
            String duration = (c.getStartTime() != null && c.getEndTime() != null)
                    ? ConsultationManagement.formatDuration(Duration.between(c.getStartTime(), c.getEndTime()).getSeconds())
                    : "-";
            String symptoms = (c.getSymptoms() != null && !c.getSymptoms().isEmpty()) ? c.getSymptoms() : "-";
            String status = (c.getEndTime() != null) ? "Completed" : "Ongoing";

            System.out.println("Consultation ID: " + c.getConsultationId()
                    + ", Patient: " + c.getPatientId()
                    + ", Doctor: " + c.getDoctorId()
                    + ", Start Time: " + startTime
                    + ", End Time: " + endTime
                    + ", Duration: " + duration
                    + ", Symptoms: " + symptoms
                    + ", Status: " + status);
        }
    }


    // ======== Helper ========
    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Enter a number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }
}

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
import DAO.ClinicData;
import java.util.Scanner;

public class ConsultationUI {

    private static final ConsultationManagement consultationManagement = new ConsultationManagement();
    private static final Scanner sc = new Scanner(System.in);

    public static void run() {
        int choice;
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
                case 1 -> consultationManagement.startNextConsultation();
                case 2 -> consultationManagement.viewCurrentConsulting();
                case 3 -> {
                    System.out.print("Enter Doctor ID to end consultation: ");
                    String id = sc.nextLine();
                    consultationManagement.endConsultation(id);
                }
                case 4 -> consultationManagement.viewCompletedPatients();
                case 5 -> {
                    System.out.print("Enter Patient ID or Consultation ID to view report: ");
                    String id = sc.nextLine();
                    consultationManagement.viewConsultationReport(id);
                }
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
            System.out.println(" 2. View All Doctors Status");
            System.out.println(" 3. Show All Completed Consultations");
            System.out.println(" 4. Show Ongoing Consultations");
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
                case 1 -> {
                    System.out.print("Enter Consultation ID to delete: ");
                    String consultationId = sc.nextLine();
                    consultationManagement.deleteConsultationById(consultationId);
                }
                case 2 -> consultationManagement.printAllDoctorsStatus("=== Current Doctors Status ===");
                case 3 -> consultationManagement.showCompletedConsultations();
                case 4 -> consultationManagement.showOngoingConsultations();
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
            System.out.println(" 2. Show First & Last Completed Consultation");
            System.out.println(" 3. Backup & Compare Consultations");
            System.out.println(" 4. Export Completed Consultations to Array");
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
                case 1 -> consultationManagement.showConsultationDurationStats();
                case 2 -> consultationManagement.showFirstAndLastConsultation();
                case 3 -> {
                    consultationManagement.backupConsultations();
                    consultationManagement.compareConsultations(consultationManagement.getCompletedConsultations());
                }
                case 4 -> consultationManagement.exportConsultationsToArray();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        ClinicData.run();
        
        // TODO code application logic here
        ConsultationUI ui = new ConsultationUI();
        ui.run();
    }
}

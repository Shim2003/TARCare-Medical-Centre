/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import Control.AppointmentManagement;
import Control.PatientManagement;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Control.LeaveManagement;
import DAO.ClinicData;
import java.util.Scanner;
/**
 *
 * @author leekeezhan
 */
public class AppointmentUI {

    private static final Scanner sc = new Scanner(System.in);

    public static void run1() {
        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("           Appointment Module         ");
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
            System.out.println(" 1. Schedule Appointment");
            System.out.println(" 2. View Appointments by ID");
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
                    System.out.print("Enter Patient ID: ");
                    String patientId = sc.nextLine();
                    System.out.print("Enter Doctor ID: ");
                    String doctorId = sc.nextLine();
                    System.out.print("Enter appointment date and time (dd-MM-yyyy HH:mm): ");
                    String dateTimeStr = sc.nextLine();
                    System.out.print("Enter reason: ");
                    String reason = sc.nextLine();

                    AppointmentManagement.scheduleNextAppointment(patientId, doctorId, dateTimeStr, reason);
                }
                case 2 -> AppointmentManagement.promptAndViewAppointments();
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
            System.out.println(" 1. Delete Appointment");
            System.out.println(" 2. Modify Appointment");
            System.out.println(" 3. Clone Appointment List");
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
                    System.out.print("Enter Appointment ID to delete: ");
                    String appointmentId = sc.nextLine();
                    AppointmentManagement.deleteAppointmentById(appointmentId);
                }
                case 2 -> {
                    System.out.print("Enter Appointment ID to modify: ");
                    String appointmentId = sc.nextLine();
                    AppointmentManagement.modifyAppointment(appointmentId);
                }
                case 3 -> AppointmentManagement.demoClone();
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
            System.out.println(" 1. View Appointment Reports");
            System.out.println(" 2. Check Doctor Appointments Next Week");
            System.out.println(" 3. Doctor Statistics");
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
                    System.out.print("Enter Appointment or Patient or Doctor ID: ");
                    String id = sc.nextLine().trim();
                    AppointmentManagement.viewConsultationReportById(id);
                }
                case 2 -> AppointmentManagement.checkDoctorNextWeekAppointments();
                case 3 -> AppointmentManagement.doctorStatistics();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        ClinicData.addSamplePatients();
        DoctorManagement.addSampleDoctor();
        LeaveManagement.addSampleLeaves();
        ScheduleManagement.addSampleSchedules();
        AppointmentManagement.addSampleAppointments();

        AppointmentUI ui = new AppointmentUI();
        ui.run1();
    }
}

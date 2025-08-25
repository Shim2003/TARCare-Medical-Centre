/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Boundary;

import static Boundary.PatientUI.adminUserMenu;
import static Boundary.PatientUI.patientUserMenu;
import DAO.ClinicData;
import java.util.Scanner;

/**
 *
 * @author jecsh
 */
public class TARCareMedicalCentre {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClinicData.run();
        mainMenu();
    }

    public static void mainMenu() {
        System.out.println("--- Welcome to TAR UMT Clinic Management System ---");

        while (true) {
            System.out.println("\nSelect User Role:");
            System.out.println("1. Staff");
            System.out.println("2. Patient");
            System.out.println("3. Exit");

            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    adminMainMenu();
                    break;
                case "2":
                    patientMainMenu();
                    break;
                case "3":
                    System.out.println("Exiting system. Goodbye!");
                    System.exit(0);
//                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    public static void adminMainMenu() {
        while (true) {
            System.out.println("\n--- Admin Main Menu ---");
            QueueUI.displayCurrentQueue();
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Queue Management");
            System.out.println("4. Consultation Management");
            System.out.println("5. Appointment Management");
            System.out.println("6. Medical Treatment Management");
            System.out.println("7. Prescription Management");
            System.out.println("8. Back to Role Selection");

            System.out.print("Enter your choice (1-8): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    adminUserMenu();
                    break;
                case "2":
                    DoctorUI.DoctorStaffMode();
                    break;
                case "3":
                    QueueUI.adminQueueMenu();
                    break;
                case "4":
                    ConsultationUI.run();
                    break;
                case "5":
                    AppointmentUI.run1();
                    break;
                case "6":
                    MedicalTreatmentUI.medicalTreatmentMainMenu();
                    break;
                case "7":
                    PharmacyUI.PharmacyMainMenu();
                    break;
                case "8":
                    mainMenu();
//                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-8.");
            }
        }
    }

    public static void patientMainMenu() {
        while (true) {
            System.out.println("\n--- Patient Main Menu ---");
            QueueUI.displayCurrentQueue();
            System.out.println("1. Patient Profile Management");
            System.out.println("2. Queue Management");
            System.out.println("3. Consultation Management");
            System.out.println("4. Appointment Management");
            System.out.println("5. Doctor(s) & Timetable info");
            System.out.println("6. Back to Role Selection");

            System.out.print("Enter your choice (1-6): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    patientUserMenu();
                    break;
                case "2":
                    QueueUI.patientQueueMenu();
                    break;
                case "3":
                    ConsultationUI.run();
                break;
                case "4":
                    AppointmentUI.run1();
                    break;
                case "5":
                    DoctorUI.DoctorUserMode();
                    break;
                case "6":
                    mainMenu();
//                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-6.");
            }
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Boundary;

import static Boundary.PatientUI.adminUserMenu;
import static Boundary.PatientUI.patientUserMenu;
import Control.ConsultationManagement;
import Control.PatientManagement;
import java.util.Scanner;

/**
 *
 * @author jecsh
 */
public class TARCareMedicalCentre {

    private static Scanner scanner = new Scanner(System.in);
        private static ConsultationManagement conMan = new ConsultationManagement();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PatientManagement.addSamplePatients();
        conMan.addSamplePatients();
        conMan.viewScheduledAppointments();
        mainMenu();
    }

    public static void mainMenu() {
        System.out.println("--- Welcome to TAR UMT Clinic Management System ---");

        while (true) {
            System.out.println("\nSelect User Role:");
            System.out.println("1. Admin");
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
                    return;
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
            System.out.println("2. Queue Management");
            System.out.println("3. Back to Role Selection");

            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    adminUserMenu();
                    break;
                case "2":
                    QueueUI.adminQueueMenu();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    public static void patientMainMenu() {
        while (true) {
            System.out.println("\n--- Patient Main Menu ---");
            QueueUI.displayCurrentQueue();
            System.out.println("1. Patient Profile Management");
            System.out.println("2. Queue Management");
            System.out.println("3. Back to Role Selection");

            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    patientUserMenu();
                    break;
                case "2":
                    QueueUI.patientQueueMenu();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

}

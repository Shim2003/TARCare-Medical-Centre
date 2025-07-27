/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.PatientManagement;
import Utility.UtilityClass;
import Entity.Patient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Lee Wei Hao
 */
public class PatientUI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientManagement patientControl = new PatientManagement();
    private static final UtilityClass utility = new UtilityClass();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
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
                    adminMenu();
                    break;
                case "2":
                    patientMenu();
                    break;
                case "3":
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    public static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Patient Management Menu ---");
            QueueUI.displayCurrentQueue();
            System.out.println("1. Register New Patient");
            System.out.println("2. Update Patient Profile");
            System.out.println("3. Search Patient by Identity Number");
            System.out.println("4. Display All Patients");
            System.out.println("5. Remove Patient");
            System.out.println("6. Generate Patient Report");
            System.out.println("7. Get Next Queue Patient");
            System.out.println("8. Back to Role Selection");

            System.out.print("Enter your choice (1-7): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addPatient();
                    break;
                case "2":
                    updatePatient();
                    break;
                case "3":
                    displayPatientInfo();
                    break;
                case "4":
                    displayAll();
                    break;
                case "5":
                    removePatient();
                    break;
                case "6":
//                    generateReport(); // Optional: implement this
                    break;
                case "7":
                    QueueUI.getNextInQueue();
                case "8":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-7.");
            }
        }
    }

    public static void patientMenu() {
        while (true) {
            System.out.println("\n--- Patient Menu ---");
            QueueUI.displayCurrentQueue();
            System.out.println("1. Queue for consultation");
            System.out.println("2. View My Profile");
            System.out.println("3. Update My Profile");
            System.out.println("4. Back to Role Selection");

            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    QueueUI.startQueue();  // You may prompt them to enter IC
                    break;
                case "2":
                    displayPatientInfo();  // You may prompt them to enter IC
                    break;
                case "3":
                    updatePatient();       // You may validate identity before allowing edit
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    public static void addPatient() {
        Scanner scanner = new Scanner(System.in);
        try {

            System.out.println("\n\n\n== Register as new Patient ==");
            
            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();

            System.out.print("Enter Identity Number: ");
            String identityNumber = scanner.nextLine();

            System.out.print("Enter Date of Birth (" + utility.DATE_FORMAT + "): ");
            String dobInput = scanner.nextLine();
            Date dateOfBirth = sdf.parse(dobInput);

            System.out.print("Enter Gender (M/F): ");
            char gender = scanner.nextLine().toUpperCase().charAt(0);

            System.out.print("Enter Contact Number: ");
            String contactNumber = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            System.out.print("Enter Emergency Contact: ");
            String emergencyContact = scanner.nextLine();

            Date registrationDate = new Date(); // current date

            Patient p = new Patient(fullName, identityNumber, dateOfBirth, gender, contactNumber, email, address, emergencyContact, registrationDate);

            if (patientControl.add(p)) {
                System.out.println("Patient registered successfully.");
            } else {
                System.out.println("Failed to register patient.");
            }

            System.out.println("Press Enter to return to continue...");
            scanner.nextLine();  // waits for user input

        } catch (ParseException e) {
            System.out.println("Invalid date format! Please use " + utility.DATE_FORMAT + ".");

        }

    }

    public static void updatePatient() {
        System.out.print("Enter the Identity Number of the patient to update: ");
        String identityNumber = scanner.nextLine();

        Patient patient = patientControl.findPatientByIdentity(identityNumber); // service layer

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Patient Found: " + patient.getFullName());

        int choice = -1;
        while (choice < 1 || choice > 6) {
            System.out.println("What would you like to update?");
            System.out.println("1. Full Name");
            System.out.println("2. Contact Number");
            System.out.println("3. Email");
            System.out.println("4. Address");
            System.out.println("5. Emergency Contact");
            System.out.println("6. Cancel");

            System.out.print("Enter your choice (1-6): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice < 1 || choice > 6) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        if (choice == 6) {
            System.out.println("Update cancelled.");
            return;
        }

        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();

        boolean updated = patientControl.update(patient, choice, newValue);

        if (updated) {
            System.out.println("Patient information updated successfully.");
        } else {
            System.out.println("Update failed. Invalid choice.");
        }

    }

    public static void displayPatientInfo() {

        System.out.print("Enter the Identity Number to display info: ");
        String identityNumber = scanner.nextLine();

        Patient patient = patientControl.findPatientByIdentity(identityNumber);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("\n--- Patient Details ---");
        System.out.println("Full Name: " + patient.getFullName());
        System.out.println("Identity Number: " + patient.getIdentityNumber());
        System.out.println("Date of Birth: " + sdf.format(patient.getDateOfBirth()));
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact Number: " + patient.getContactNumber());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("Emergency Contact: " + patient.getEmergencyContact());
        System.out.println("Registration Date: " + sdf.format(patient.getRegistrationDate()));

    }

    public static void removePatient() {
        int choice = -1;

        while (choice < 1 || choice > 3) {
            System.out.println("\n--- Remove Patient ---");
            System.out.println("1. Remove Specific Patient");
            System.out.println("2. Remove All Patients");
            System.out.println("3. Cancel");

            System.out.print("Enter your choice (1-3): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid choice. Please select a number between 1 and 3.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        switch (choice) {
            case 1:
                removeSpecificPatient();
                break;
            case 2:
                removeAllPatients();
                break;
            case 3:
                System.out.println("Operation cancelled.");
                break;
        }
    }

    public static void removeAllPatients() {
        System.out.print("Are you sure you want to remove all patients? (Y/N): ");
        char confirm = scanner.nextLine().toUpperCase().charAt(0);

        if (confirm == 'Y') {
            patientControl.clearAll(); // You must implement this method in PatientManagement
            System.out.println("All patients have been removed.");
        } else {
            System.out.println("Removal of all patients cancelled.");
        }
    }

    public static void removeSpecificPatient() {
        System.out.print("Enter the Identity Number of the patient to remove: ");
        String identityNumber = scanner.nextLine();

        Patient patient = patientControl.findPatientByIdentity(identityNumber);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Are you sure you want to remove this patient? (Y/N): ");
        char confirm = scanner.nextLine().toUpperCase().charAt(0);
        if (confirm == 'Y') {
            patientControl.remove(confirm, patient);
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    public static void displayAll() {

        DynamicList<Patient> patientList = patientControl.getPatientList();

        if (patientList.isEmpty()) {
            System.out.println("No patients registered yet.");
            return;
        }

        System.out.println("\n------------------------------------------------------------ PATIENT LIST ------------------------------------------------------------");
        System.out.printf("| %-5s | %-20s | %-15s | %-10s | %-6s | %-15s | %-25s |\n",
                "ID", "Full Name", "Identity No", "Birth Date", "Gender", "Contact", "Email");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < patientList.size(); i++) {
            Patient p = patientList.get(i);
            System.out.printf("| %-5s | %-20s | %-15s | %-10s | %-6s | %-15s | %-25s |\n",
                    p.getPatientID(),
                    UtilityClass.truncate(p.getFullName(), 20),
                    p.getIdentityNumber(),
                    sdf.format(p.getDateOfBirth()),
                    String.valueOf(p.getGender()),
                    p.getContactNumber(),
                    UtilityClass.truncate(p.getEmail(), 25));
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
    }
}

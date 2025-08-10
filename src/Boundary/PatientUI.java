/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.PatientManagement;
import Entity.Patient;
import Utility.UtilityClass;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Lee Wei Hao
 */
public class PatientUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PatientManagement.addSamplePatients();
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

    public static void adminUserMenu() {
        while (true) {
            System.out.println("\n--- Admin Patient Management Menu ---");
            System.out.println("1. Register New Patient");
            System.out.println("2. Update Patient Profile");
            System.out.println("3. Search Patient by Identity Number");
            System.out.println("4. Display All Patients");
            System.out.println("5. Remove Patient");
            System.out.println("6. Generate Patient Report");
            System.out.println("7. Back to Admin Main Menu");

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
                    generateDemographicsReport();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-7.");
            }
        }
    }

    public static void patientUserMenu() {
        while (true) {
            System.out.println("\n--- Patient Profile Management Menu ---");
            System.out.println("1. Register as New Patient");
            System.out.println("2. View My Profile");
            System.out.println("3. Update My Profile");
            System.out.println("4. Back to Patient Main Menu");

            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addPatient();
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
                    System.out.println("Invalid choice. Please enter 1-4.");
            }
        }
    }

    public static void addPatient() {

        System.out.println("\n\n\n== Register as new Patient ==");

        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter Identity Number: ");
        String identityNumber = scanner.nextLine();

        System.out.print("Enter Date of Birth (" + UtilityClass.DATE_FORMAT + "): ");
        String dobInput = scanner.nextLine();
        Date dateOfBirth = UtilityClass.parseDate(dobInput);

        if (dateOfBirth == null) {
            System.out.println("Invalid date format! Please use " + UtilityClass.DATE_FORMAT + ".");
            return; // Exit the method early
        }

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

        if (PatientManagement.add(p)) {
            System.out.println("Patient registered successfully.");
            System.out.println("Patient ID : " + p.getPatientID());
        } else {
            System.out.println("Failed to register patient.");
        }

        System.out.println("Press Enter to return to continue...");
        scanner.nextLine();  // waits for user input

    }

    public static void updatePatient() {
        System.out.print("Enter the Patient ID of the patient to update: ");
        String patientId = scanner.nextLine();

        Patient existingPatient = PatientManagement.findPatientById(patientId); // service layer

        if (existingPatient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Patient Found: " + existingPatient.getFullName());

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

        boolean updated = PatientManagement.update(patientId, choice, newValue);

        if (updated) {
            System.out.println("Patient information updated successfully.");
        } else {
            System.out.println("Update failed. Invalid choice.");
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // waits for user input

    }

    public static void displayPatientInfo() {

        System.out.print("Enter the Patient Id to display info: ");
        String patientId = scanner.nextLine();

        Patient patient = PatientManagement.findPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("\n--- Patient Details ---");
        System.out.println("Full Name: " + patient.getFullName());
        System.out.println("Identity Number: " + patient.getIdentityNumber());
        System.out.println("Date of Birth: " + UtilityClass.formatDate(patient.getDateOfBirth()));
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact Number: " + patient.getContactNumber());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("Emergency Contact: " + patient.getEmergencyContact());
        System.out.println("Registration Date: " + UtilityClass.formatDate(patient.getRegistrationDate()));

        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // waits for user input

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
                System.out.println("Press Enter to continue...");
                scanner.nextLine();  // waits for user input
                break;
        }
    }

    public static void removeAllPatients() {
        System.out.print("Are you sure you want to remove all patients? (Y/N): ");
        char confirm = scanner.nextLine().toUpperCase().charAt(0);

        if (confirm == 'Y') {
            PatientManagement.clearAll(); // You must implement this method in PatientManagement
            System.out.println("All patients have been removed.");
        } else {
            System.out.println("Removal of all patients cancelled.");
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // waits for user input
    }

    public static void removeSpecificPatient() {
        System.out.print("Enter the Identity Number of the patient to remove: ");
        String patientId = scanner.nextLine();

        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Are you sure you want to remove this patient? (Y/N): ");
        char confirm = scanner.nextLine().toUpperCase().charAt(0);
        if (confirm == 'Y') {
            PatientManagement.remove(confirm, patient);
        } else {
            System.out.println("Removal cancelled.");
        }

        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // waits for user input
    }

    public static void displayAll() {

        DynamicList<Patient> patientList = PatientManagement.getPatientList();

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
                    UtilityClass.formatDate(p.getDateOfBirth()),
                    String.valueOf(p.getGender()),
                    p.getContactNumber(),
                    UtilityClass.truncate(p.getEmail(), 25));
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // waits for user input
    }

    public static void generateDemographicsReport() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           PATIENT DEMOGRAPHICS REPORT");
            System.out.println("=".repeat(50));
            System.out.println("1. Total Registered Patients");
            System.out.println("2. Gender Distribution");
            System.out.println("3. Back to Admin Menu");

            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    generateTotalPatientsReport();
                    break;
                case "2":
                    generateGenderDistributionReport();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    public static void generateGenderDistributionReport() {
        DynamicList<Patient> patientList = PatientManagement.getPatientList();

        if (patientList.isEmpty()) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           GENDER DISTRIBUTION REPORT");
            System.out.println("=".repeat(50));
            System.out.println("No patients registered yet. Cannot generate report.");
            System.out.println("=".repeat(50));
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        // Count gender distribution
        int maleCount = 0;
        int femaleCount = 0;
        int totalPatients = patientList.size();

        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);
            char gender = patient.getGender();

            if (gender == 'M' || gender == 'm') {
                maleCount++;
            } else if (gender == 'F' || gender == 'f') {
                femaleCount++;
            }
        }

        // Calculate percentages
        double malePercentage = totalPatients > 0 ? (double) maleCount / totalPatients * 100 : 0;
        double femalePercentage = totalPatients > 0 ? (double) femaleCount / totalPatients * 100 : 0;

        // Display the report
        System.out.println("\n" + "=".repeat(60));
        System.out.println("             GENDER DISTRIBUTION REPORT");
        System.out.println("=".repeat(60));

        System.out.printf("Total Patients Analyzed: %d\n", totalPatients);
        System.out.println("-".repeat(60));

        System.out.println("GENDER BREAKDOWN:");
        System.out.printf("%-15s: %3d patients (%.1f%%)\n", "Male", maleCount, malePercentage);
        System.out.printf("%-15s: %3d patients (%.1f%%)\n", "Female", femaleCount, femalePercentage);

        // Visual representation
        System.out.println("\nVISUAL REPRESENTATION:");
        System.out.print("Male   [");
        int maleBarLength = (int) (malePercentage / 2); // Scale down for display
        for (int i = 0; i < maleBarLength; i++) {
            System.out.print("#");
        }
        System.out.printf("] %.1f%%\n", malePercentage);

        System.out.print("Female [");
        int femaleBarLength = (int) (femalePercentage / 2); // Scale down for display
        for (int i = 0; i < femaleBarLength; i++) {
            System.out.print("#");
        }
        System.out.printf("] %.1f%%\n", femalePercentage);

        System.out.println("=".repeat(60));

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void generateTotalPatientsReport() {
        DynamicList<Patient> patientList = PatientManagement.getPatientList();
        int totalPatients = patientList.size();

        if (totalPatients == 0) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           TOTAL REGISTERED PATIENTS");
            System.out.println("=".repeat(50));
            System.out.println("No patients registered yet.");
            System.out.println("=".repeat(50));
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        int patientsPerPage = 10;
        int totalPages = (int) Math.ceil((double) totalPatients / patientsPerPage);
        int currentPage = 1;

        while (true) {
            // Display current page
            displayPatientPage(patientList, currentPage, patientsPerPage, totalPages, totalPatients);

            // Navigation menu
            System.out.println("\nNavigation Options:");
            if (currentPage > 1) {
                System.out.print("P - Previous Page | ");
            }
            if (currentPage < totalPages) {
                System.out.print("N - Next Page | ");
            }
            System.out.println("B - Back to Demographics Menu");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "P":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Already on the first page.");
                    }
                    break;
                case "N":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("Already on the last page.");
                    }
                    break;
                case "B":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayPatientPage(DynamicList<Patient> patientList, int currentPage,
            int patientsPerPage, int totalPages, int totalPatients) {
        System.out.println("\n" + "=".repeat(86));
        System.out.printf("                    TOTAL REGISTERED PATIENTS (Page %d of %d)\n", currentPage, totalPages);
        System.out.println("=".repeat(86));
        System.out.printf("Total Patients: %d\n", totalPatients);
        System.out.println("-".repeat(86));

        // Calculate start and end indices for current page
        int startIndex = (currentPage - 1) * patientsPerPage;
        int endIndex = Math.min(startIndex + patientsPerPage, totalPatients);

        // Display table header
        System.out.printf("| %-3s | %-8s | %-20s | %-15s | %-6s | %-15s |\n",
                "No.", "ID", "Full Name", "Identity No", "Gender", "Contact");
        System.out.println("|" + "-".repeat(5) + "|" + "-".repeat(10) + "|" + "-".repeat(22) + "|"
                + "-".repeat(17) + "|" + "-".repeat(8) + "|" + "-".repeat(17) + "|");

        // Display patients for current page
        for (int i = startIndex; i < endIndex; i++) {
            Patient p = patientList.get(i);
            int displayNumber = i + 1;

            System.out.printf("| %-3d | %-8s | %-20s | %-15s | %-6s | %-15s |\n",
                    displayNumber,
                    p.getPatientID(),
                    UtilityClass.truncate(p.getFullName(), 20),
                    p.getIdentityNumber(),
                    String.valueOf(p.getGender()),
                    p.getContactNumber());
        }

        System.out.println("-".repeat(86));
        System.out.printf("Showing patients %d-%d of %d\n", startIndex + 1, endIndex, totalPatients);
    }
}
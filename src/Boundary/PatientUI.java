/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import ADT.MyList;
import Control.PatientManagement;
import DAO.AppointmentInfo;
import Entity.Patient;
import Utility.UtilityClass;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Lee Wei Hao
 */
public class PatientUI {

    private static final Scanner scanner = new Scanner(System.in);
    private static ConsultationUI con = new ConsultationUI();

    public static void adminUserMenu() {
        while (true) {
            System.out.println("\n--- Admin Patient Management Menu ---");
            System.out.println("1. Register New Patient");
            System.out.println("2. Update Patient Profile");
            System.out.println("3. Search Patient by Patient ID");
            System.out.println("4. Remove Patient");
            System.out.println("5. Generate Patient Report");
            System.out.println("6. Back to Admin Main Menu");

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
                    removePatient();
                    break;
                case "5":
                    generateDemographicsReport();
                    break;
                case "6":
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

        UtilityClass.pressEnterToContinue();

    }

    public static void updatePatient() {
        System.out.print("\n\n\nEnter the Patient ID of the patient to update: ");
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

        UtilityClass.pressEnterToContinue();

    }

    public static void displayPatientInfo() {

        System.out.print("Enter the Patient Id to display info: ");
        String patientId = scanner.nextLine();

        Patient patient = PatientManagement.findPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        System.out.println("\n\n");
        
        appointmentStatus(patient.getPatientID());
        System.out.println("--- Patient Details ---");
        System.out.println("Full Name: " + patient.getFullName());
        System.out.println("Identity Number: " + patient.getIdentityNumber());
        System.out.println("Date of Birth: " + UtilityClass.formatDate(patient.getDateOfBirth()));
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact Number: " + patient.getContactNumber());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("Emergency Contact: " + patient.getEmergencyContact());
        System.out.println("Registration Date: " + UtilityClass.formatDate(patient.getRegistrationDate()));

        UtilityClass.pressEnterToContinue();

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
                UtilityClass.pressEnterToContinue();
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

        UtilityClass.pressEnterToContinue();
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

        UtilityClass.pressEnterToContinue();
    }

    public static void generateDemographicsReport() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           PATIENT DEMOGRAPHICS REPORT");
            System.out.println("=".repeat(50));
            System.out.println("1. Total Registered Patients");
            System.out.println("2. Gender Distribution");
            System.out.println("3. Age Statistics Report");
            System.out.println("4. Back to Admin Menu");

            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    generateTotalPatientsReport();
                    break;
                case "2":
                    generateGenderDistributionReport();
                    break;
                case "3":
                    generateAgeStatisticsReport();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    public static void generateGenderDistributionReport() {
        MyList<Patient> patientList = PatientManagement.getPatientList();

        if (patientList.isEmpty()) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           GENDER DISTRIBUTION REPORT");
            System.out.println("=".repeat(50));
            System.out.println("No patients registered yet. Cannot generate report.");
            System.out.println("=".repeat(50));
            UtilityClass.pressEnterToContinue();
            return;
        }

        MyList<Patient> malePatients = PatientManagement.getMalePatients();
        MyList<Patient> femalePatients = PatientManagement.getFemalePatients();
        PatientManagement.GenderStatistics genderStats = PatientManagement.getGenderStatistics();

        // Display the report
        System.out.println("\n" + "=".repeat(60));
        System.out.println("             GENDER DISTRIBUTION REPORT");
        System.out.println("=".repeat(60));

        System.out.printf("Total Patients Analyzed: %d\n", genderStats.totalCount);
        System.out.println("-".repeat(60));

        System.out.println("GENDER BREAKDOWN:");
        System.out.printf("%-15s: %3d patients (%.1f%%)\n", "Male", genderStats.maleCount, genderStats.malePercentage);
        System.out.printf("%-15s: %3d patients (%.1f%%)\n", "Female", genderStats.femaleCount, genderStats.femalePercentage);

        // Visual representation
        System.out.println("\nVISUAL REPRESENTATION:");
        System.out.print("Male   [");
        int maleBarLength = (int) (genderStats.malePercentage / 2); // Scale down for display
        for (int i = 0; i < maleBarLength; i++) {
            System.out.print("#");
        }
        System.out.printf("] %.1f%%\n", genderStats.malePercentage);

        System.out.print("Female [");
        int femaleBarLength = (int) (genderStats.femalePercentage / 2); // Scale down for display
        for (int i = 0; i < femaleBarLength; i++) {
            System.out.print("#");
        }
        System.out.printf("] %.1f%%\n", genderStats.femalePercentage);

        System.out.println("=".repeat(60));

        UtilityClass.pressEnterToContinue();
    }

    public static void generateTotalPatientsReport() {
        MyList<Patient> patientList = PatientManagement.getPatientsSortedBy("name");
        int totalPatients = patientList.size();

        if (totalPatients == 0) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           TOTAL REGISTERED PATIENTS");
            System.out.println("=".repeat(50));
            System.out.println("No patients registered yet.");
            System.out.println("=".repeat(50));
            UtilityClass.pressEnterToContinue();
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

    private static void displayPatientPage(MyList<Patient> patientList, int currentPage,
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

    public static void generateAgeStatisticsReport() {
        MyList<Patient> patientList = PatientManagement.getPatientList();

        if (patientList.isEmpty()) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("           AGE STATISTICS REPORT");
            System.out.println("=".repeat(60));
            System.out.println("No patients registered yet. Cannot generate report.");
            System.out.println("=".repeat(60));
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        // Get age statistics using the new statistical function
        DynamicList.ListStatistics<Patient> ageStats = PatientManagement.getAgeStatistics();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("                   AGE STATISTICS ANALYSIS");
        System.out.println("=".repeat(70));

        System.out.printf("Total Patients Analyzed: %d\n", ageStats.count);
        System.out.println("-".repeat(70));

        System.out.println("AGE DISTRIBUTION STATISTICS:");
        System.out.printf("|- Average Age: %.1f years\n", ageStats.average);
        System.out.printf("|- Youngest Patient: %.0f years old\n", ageStats.min);
        System.out.printf("|- Oldest Patient: %.0f years old\n", ageStats.max);
        System.out.printf("|- Age Range: %.0f years\n", ageStats.max - ageStats.min);
        System.out.printf("|- Standard Deviation: %.1f years\n", ageStats.standardDeviation);

        // Age group analysis
        MyList<Patient> pediatric = PatientManagement.getPatientsByAgeGroup("pediatric");
        MyList<Patient> adult = PatientManagement.getPatientsByAgeGroup("adult");
        MyList<Patient> geriatric = PatientManagement.getPatientsByAgeGroup("geriatric");

        System.out.println("\nAGE GROUP BREAKDOWN:");
        System.out.printf("|- Pediatric (0-17 years): %d patients (%.1f%%)\n",
                pediatric.size(), (double) pediatric.size() / ageStats.count * 100);
        System.out.printf("|- Adult (18-64 years): %d patients (%.1f%%)\n",
                adult.size(), (double) adult.size() / ageStats.count * 100);
        System.out.printf("|- Geriatric (65+ years): %d patients (%.1f%%)\n",
                geriatric.size(), (double) geriatric.size() / ageStats.count * 100);

        // Show top 3 oldest and youngest using sorting
        System.out.println("\nAGE EXTREMES:");
        MyList<Patient> oldest = PatientManagement.getOldestPatients(3);
        MyList<Patient> youngest = PatientManagement.getYoungestPatients(3);

        System.out.println("Oldest Patients:");
        for (int i = 0; i < oldest.size(); i++) {
            Patient p = oldest.get(i);
            System.out.printf("  %d. %s - %d years old\n", i + 1, p.getFullName(), PatientManagement.calculateAge(p));
        }

        System.out.println("Youngest Patients:");
        for (int i = 0; i < youngest.size(); i++) {
            Patient p = youngest.get(i);
            System.out.printf("  %d. %s - %d years old\n", i + 1, p.getFullName(), PatientManagement.calculateAge(p));
        }

        System.out.println("=".repeat(70));
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void appointmentStatus(String patientId) {
        AppointmentInfo nextAppointmentInfo = PatientManagement.checkPatientAppointments(patientId);

        if (nextAppointmentInfo != null) {
            String details = "Next Appointment: " + nextAppointmentInfo.getAppointment().getAppointmentId();
            String time = "Time: " + nextAppointmentInfo.getAppointment().getAppointmentTime()
                    .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            String remaining = "In: " + nextAppointmentInfo.getDayLeft() + " days " + nextAppointmentInfo.getHoursLeft() + " hours";

            // Find the width for the box
            int width = Math.max(details.length(), Math.max(time.length(), remaining.length()));
            String border = "+" + "-".repeat(width + 2) + "+";

            System.out.println(border);
            System.out.printf("| %-" + width + "s |\n", details);
            System.out.printf("| %-" + width + "s |\n", time);
            System.out.printf("| %-" + width + "s |\n", remaining);
            System.out.println(border);
        } else {
            String msg = "No upcoming appointments.";
            int width = msg.length();
            String border = "+" + "-".repeat(width + 2) + "+";

            System.out.println(border);
            System.out.printf("| %s |\n", msg);
            System.out.println(border);
        }
    }

}

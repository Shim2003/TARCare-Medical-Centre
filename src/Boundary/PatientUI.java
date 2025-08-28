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
 * PatientUI - Boundary layer for patient user interface
 * Contains all display logic and user interactions
 * 
 * @author Lee Wei Hao
 */
public class PatientUI {

    private static final Scanner scanner = new Scanner(System.in);
    private static ConsultationUI con = new ConsultationUI();

    public static void staffUserMenu() {
        while (true) {
            System.out.println("\n--- Staff Patient Management Menu ---");
            System.out.println("1. Register New Patient");
            System.out.println("2. Update Patient Profile");
            System.out.println("3. Search Patient by Patient ID");
            System.out.println("4. Remove Patient");
            System.out.println("5. Generate Patient Report");
            System.out.println("6. Back to Admin Main Menu");

            System.out.print("Enter your choice (1-6): ");
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
                    System.out.println("Invalid choice. Please enter 1-6.");
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

        // Display current patient details
        System.out.println("\n--- Current Patient Information ---");
        System.out.println("Patient ID       : " + existingPatient.getPatientID());
        System.out.println("Full Name        : " + existingPatient.getFullName());
        System.out.println("Contact Number   : " + existingPatient.getContactNumber());
        System.out.println("Email            : " + existingPatient.getEmail());
        System.out.println("Address          : " + existingPatient.getAddress());
        System.out.println("Emergency Contact: " + existingPatient.getEmergencyContact());
        System.out.println("-----------------------------------\n");

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

        displayAppointmentStatus(patient.getPatientID());
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
            PatientManagement.clearAll();
            System.out.println("All patients have been removed.");
        } else {
            System.out.println("Removal of all patients cancelled.");
        }

        UtilityClass.pressEnterToContinue();
    }

    public static void removeSpecificPatient() {
        System.out.print("Enter the Patient ID of the patient to remove: ");
        String patientId = scanner.nextLine();

        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            UtilityClass.pressEnterToContinue();
            return;
        }

        System.out.println("\n--- Patient to be removed ---");
        System.out.println("Patient ID: " + patient.getPatientID());
        System.out.println("Full Name: " + patient.getFullName());
        System.out.println("Identity Number: " + patient.getIdentityNumber());

        System.out.print("Are you sure you want to remove this patient? (Y/N): ");
        char confirm = scanner.nextLine().toUpperCase().charAt(0);
        
        if (confirm == 'Y') {
            boolean removed = PatientManagement.remove(patient);
            if (removed) {
                System.out.println("Patient removed successfully.");
            } else {
                System.out.println("Failed to remove patient.");
            }
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
                    System.out.println("Invalid choice. Please enter 1-4.");
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
        MyList<Patient> patientList = PatientManagement.getPatientList();
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

        // Ask user for sorting preference
        String sortBy = getSortingPreference();
        if (sortBy == null){
            return;
        }
        
        MyList<Patient> sortedPatientList = PatientManagement.getPatientsSortedBy(sortBy);

        int patientsPerPage = 10;
        int totalPages = (int) Math.ceil((double) totalPatients / patientsPerPage);
        int currentPage = 1;

        while (true) {
            // Display current page
            displayPatientPage(sortedPatientList, currentPage, patientsPerPage, totalPages, totalPatients, getSortDisplayName(sortBy));

            // Navigation menu
            System.out.println("\nNavigation Options:");
            if (currentPage > 1) {
                System.out.print("P - Previous Page | ");
            }
            if (currentPage < totalPages) {
                System.out.print("N - Next Page | ");
            }
            System.out.println("S - Change Sort | B - Back to Demographics Menu");

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
                case "S":
                    sortBy = getSortingPreference();
                    sortedPatientList = PatientManagement.getPatientsSortedBy(sortBy);
                    currentPage = 1; // Reset to first page when sorting changes
                    break;
                case "B":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static String getSortDisplayName(String sortBy) {
        switch (sortBy) {
            case "name":
                return "Name (A-Z)";
            case "id":
                return "Patient ID";
            case "age":
                return "Age (Youngest first)";
            case "age_desc":
                return "Age (Oldest first)";
            case "registration_desc":
                return "Registration Date (Newest first)";
            case "registration":
                return "Registration Date (Oldest first)";
            default:
                return "Name (A-Z)";
        }
    }

    private static String getSortingPreference() {
        int choice = -1;

        while (choice < 1 || choice > 7) {
            System.out.println("\n--- Choose Sorting Option ---");
            System.out.println("1. Sort by Name (A-Z)");
            System.out.println("2. Sort by Patient ID");
            System.out.println("3. Sort by Age (Youngest first)");
            System.out.println("4. Sort by Age (Oldest first)");
            System.out.println("5. Sort by Registration Date (Newest first)");
            System.out.println("6. Sort by Registration Date (Oldest first)");
            System.out.println("7. Back");

            System.out.print("Enter your choice (1-7): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice < 1 || choice > 7) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        switch (choice) {
            case 1:
                return "name";
            case 2:
                return "id";
            case 3:
                return "age";
            case 4:
                return "age_desc";
            case 5:
                return "registration_desc";
            case 6:
                return "registration";
            case 7:
                return null;
            default:
                return "name";
        }
    }

    private static void displayPatientPage(MyList<Patient> patientList, int currentPage,
            int patientsPerPage, int totalPages, int totalPatients, String sortBy) {
        System.out.println("\n" + "=".repeat(96));
        System.out.printf("              TOTAL REGISTERED PATIENTS (Page %d of %d)\n", currentPage, totalPages);
        System.out.printf("                        Sorted by: %s\n", sortBy);
        System.out.println("=".repeat(96));
        System.out.printf("Total Patients: %d\n", totalPatients);
        System.out.println("-".repeat(96));

        // Calculate start and end indices for current page
        int startIndex = (currentPage - 1) * patientsPerPage;
        int endIndex = Math.min(startIndex + patientsPerPage, totalPatients);

        // Display table header
        System.out.printf("| %-3s | %-8s | %-18s | %-13s | %-4s | %-6s | %-23s |\n",
                "No.", "ID", "Full Name", "Identity No", "Age", "Gender", "Contact");
        System.out.println("|" + "-".repeat(5) + "|" + "-".repeat(10) + "|" + "-".repeat(20) + "|"
                + "-".repeat(15) + "|" + "-".repeat(6) + "|" + "-".repeat(8) + "|" + "-".repeat(25) + "|");

        // Display patients for current page
        for (int i = startIndex; i < endIndex; i++) {
            Patient p = patientList.get(i);
            int displayNumber = i + 1;
            int age = PatientManagement.calculateAge(p);

            System.out.printf("| %-3d | %-8s | %-18s | %-13s | %-4d | %-6s | %-23s |\n",
                    displayNumber,
                    p.getPatientID(),
                    UtilityClass.truncate(p.getFullName(), 18),
                    p.getIdentityNumber(),
                    age,
                    String.valueOf(p.getGender()),
                    p.getContactNumber());
        }

        System.out.println("-".repeat(96));
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
            UtilityClass.pressEnterToContinue();
            return;
        }

        // Get age statistics using the statistical function
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
        UtilityClass.pressEnterToContinue();
    }

    /**
     * Display appointment status for a patient
     * @param patientId The patient ID to check appointments for
     */
    public static void displayAppointmentStatus(String patientId) {
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
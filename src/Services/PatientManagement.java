/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import java.util.InputMismatchException;
import java.util.Scanner;
import ADT.DynamicList;
import Entity.Patient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lee Wei Hao
 */
public class PatientManagement {

    // list to store patient details
    private static DynamicList<Patient> patientList = new DynamicList<>();

    // Constant
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // Declare global scanner
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        while (true) {
            System.out.println("\n--- Patient Management Menu ---");
            System.out.println("1. Register New Patient");
            System.out.println("2. Update Profile Info");
            System.out.println("3. Display Personal Info");
            System.out.println("4. Display Personal Info");
            System.out.println("5. Exit");

            System.out.print("Enter your choice (1-5): ");

            int choice = -1;

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer between 1 and 5.");
                scanner.nextLine(); // Clear buffer
                continue;
            }

            switch (choice) {
                case 1:
                    Patient newPatient = add();
                    if (newPatient != null) {
                        patientList.add(newPatient);
                        System.out.println("Patient successfully registered!");
                    }

                    System.out.println(patientList.getFirst().getFullName());
                    System.out.println(patientList.getFirst().getAddress());
                    break;
                case 2:
                    update();
                    System.out.println(patientList.getFirst().getFullName());
                    break;
                case 3:
                    displayPersonalInfo();
                    break;
                case 4:
                    displayPersonalInfo();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 4.");
            }
        }
    }

    //Register as new patient
    public static Patient add() {
        Scanner scanner = new Scanner(System.in);
        try {

            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();

            System.out.print("Enter Identity Number (IC/Passport): ");
            String identityNumber = scanner.nextLine();

            System.out.print("Enter Date of Birth (" + DATE_FORMAT + "): ");
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

            Patient patient = new Patient(fullName, identityNumber, dateOfBirth, gender, contactNumber, email, address, emergencyContact, registrationDate);

            return patient;

        } catch (ParseException e) {
            System.out.println("Invalid date format! Please use " + DATE_FORMAT + ".");
        } catch (Exception e) {
            System.out.println("Error during input: " + e.getMessage());
        }

        return null;
    }

    public static void update() {
        scanner.nextLine(); // clear buffer
        System.out.print("Enter the Identity Number of the patient to update: ");
        String identityNumber = scanner.nextLine();

        Patient patient = findPatientByIdentity(identityNumber);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Patient Found: " + patient.getFullName());
        System.out.println("What would you like to update?");
        System.out.println("1. Full Name");
        System.out.println("2. Contact Number");
        System.out.println("3. Email");
        System.out.println("4. Address");
        System.out.println("5. Emergency Contact");
        System.out.println("6. Cancel");

        System.out.print("Enter your choice (1-6): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        switch (choice) {
            case 1:
                System.out.print("Enter new Full Name: ");
                String newName = scanner.nextLine();
                patient.setFullName(newName);
                break;
            case 2:
                System.out.print("Enter new Contact Number: ");
                String newContact = scanner.nextLine();
                patient.setContactNumber(newContact);
                break;
            case 3:
                System.out.print("Enter new Email: ");
                String newEmail = scanner.nextLine();
                patient.setEmail(newEmail);
                break;
            case 4:
                System.out.print("Enter new Address: ");
                String newAddress = scanner.nextLine();
                patient.setAddress(newAddress);
                break;
            case 5:
                System.out.print("Enter new Emergency Contact: ");
                String newEmergency = scanner.nextLine();
                patient.setEmergencyContact(newEmergency);
                break;
            case 6:
                System.out.println("Update cancelled.");
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("Patient information updated successfully.");
    }

    public static Patient findPatientByIdentity(String identityNumber) {
        return patientList.findFirst(p -> p.getIdentityNumber().equalsIgnoreCase(identityNumber));
    }

    public static void displayPersonalInfo() {
        System.out.print("Enter the Identity Number to display info: ");
        String identityNumber = scanner.nextLine();

        Patient patient = findPatientByIdentity(identityNumber);

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
        System.out.print("Enter the Identity Number of the patient to remove: ");
        String identityNumber = scanner.nextLine();

        Patient patient = findPatientByIdentity(identityNumber);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Are you sure you want to remove this patient? (Y/N): ");
        char confirm = scanner.nextLine().toUpperCase().charAt(0);
        if (confirm == 'Y') {
            int index = patientList.indexOf(patient);  // get index of patient
            if (index >= 0) {
                patientList.remove(index);  // remove by index
                System.out.println("Patient removed successfully.");
            } else {
                System.out.println("Error: patient not found in list.");
            }
        } else {
            System.out.println("Removal cancelled.");
        }
    }

}

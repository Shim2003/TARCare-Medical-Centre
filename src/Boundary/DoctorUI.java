/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.DoctorManagement;
import Control.LeaveManagement;
import Control.ScheduleManagement;
import Entity.Doctor;
import Entity.Schedule;
import Utility.UtilityClass;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class DoctorUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //call methods to add sample objects
        LeaveManagement.addSampleLeaves();
        ScheduleManagement.addSampleSchedules();
        DoctorManagement.addSampleDoctor();
        //

        DoctorModuleMenu();
//       testing();
    }

    public static void DoctorModuleMenu() {

        boolean validOption = false;

        while (!validOption) {
            System.out.println("\n--- Welcome to TAR UMT Clinic Doctor Management System ---");
            System.out.println("Select a mode");
            System.out.println("1. Patients/User");
            System.out.println("2. Admin\n");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true; // valid choice → stop looping
                    UserMode();
                    break;
                case "2":
                    validOption = true; // valid choice → stop looping
                    AdminMode();
                    break;
                default:
                    System.out.println("Invalid Option!!! Pls try again");
                    UtilityClass.pressEnterToContinue();
            }
        }

    }

    public static void AdminMode() {
        boolean validOption = false;

        while (!validOption) {
            System.out.println("\n--- Welcome Admin ---");
            System.out.println("1. Manage Doctors");
            System.out.println("2. Manage Schedules");
            System.out.println("3. Manage Leaves");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    ManageDoctor();
                    break;
                case "2":
                    validOption = true;
                    ScheduleUI.ManageSchedule();
                    break;
                case "3":
                    validOption = true;
                    LeaveUI.ManageLeave();
                    break;
                case "4":
                    validOption = true;
                    DoctorModuleMenu();
                    break;
                default:
                    System.out.println("Invalid Option!!! Pls try again");
                    UtilityClass.pressEnterToContinue();
            }

        }
    }

    public static void UserMode() {

        boolean validOption = false;

        while (!validOption) {
            System.out.println("\n--- Welcome ---");
            System.out.println("1. Check Doctors");
            System.out.println("2. Check Schedules");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    ShowDoctors();
                    UtilityClass.pressEnterToContinue();
                    UserMode();
                    break;
                case "2":
                    validOption = true;
                    ScheduleUI.DisplayAllTimetable();
                    UtilityClass.pressEnterToContinue();
                    UserMode();
                    break;
                case "3":
                    validOption = true;
                    DoctorModuleMenu();
                default:
                    System.out.println("Invalid Option!!! Pls try again");
                    UtilityClass.pressEnterToContinue();
            }

        }

    }

    public static void ManageDoctor() {

        boolean validOption = false;

        while (!validOption) {
            System.out.println("\n-*-*- Doctor(s) Management -*-*-");
            System.out.println("1. Check All Doctors");
            System.out.println("2. Register a new doctor");
            System.out.println("3. Edit Doctor(s) detail");
            System.out.println("4. Remove Doctor(s)");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    DisplayAllDoctors();
                    UtilityClass.pressEnterToContinue();
                    ManageDoctor();
                    break;
                case "2":
                    validOption = true;
                    addDoctorUI();
                    break;
                case "3":
                    validOption = true;
                    editDoctorDetailsUI();
                    break;
                case "4":
                    validOption = true;
                    removeDoctorUI();
                    UtilityClass.pressEnterToContinue();
                    ManageDoctor();
                    break;
                case "5":
                    validOption = true;
                    AdminMode();
                    break;
                default:
                    System.out.println("Invalid Option!!! Pls try again");
                    UtilityClass.pressEnterToContinue();
            }

        }
    }

    public static void DisplayAllDoctors() {

        DynamicList<Doctor> doctorList = DoctorManagement.getAllDoctors();
        System.out.println("\n------------------------------------------------------------ DOCTOR LIST ---------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-20s | %-15s | %-8s | %-15s | %-25s | %-25s |\n",
                "ID", "Full Name", "Birth Date", "Gender", "Contact", "Email", "Qualifications");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < doctorList.size(); i++) {
            Doctor d = doctorList.get(i);
            System.out.printf("| %-5s | %-20s | %-15s | %-8s | %-15s | %-25s | %-25s |\n",
                    d.getDoctorID(), d.getName(), UtilityClass.formatDate(d.getDateOfBirth()),
                    d.getGender(), d.getContactNumber(),
                    d.getEmail(), d.getQualification());

        }

        System.out.println("Total of " + doctorList.size() + " doctor(s)");

    }
    
     public static void ShowDoctors() {

        DynamicList<Doctor> doctorList = DoctorManagement.getAllDoctors();
        System.out.println("\n------------------------------------------------ DOCTOR LIST ------------------------------------------------");
        System.out.printf(" %-20s | %-15s | %-25s | %-25s |\n",
                 "Full Name", "Contact", "Email", "Working Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < doctorList.size(); i++) {
            Doctor d = doctorList.get(i);
            System.out.printf(" %-20s | %-15s | %-30s | %-10s |\n",
                    d.getName(), 
                    d.getContactNumber(),
                    d.getEmail(), d.getWorkingStatus());

        }

        System.out.println("Total of " + doctorList.size() + " doctor(s)");

    }

    public static void addDoctorUI() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n=== Register New Doctor ===");

           String newDoctorId = DoctorManagement.generateNextDoctorId();

            // Name (not empty, not only spaces)
            String name;
            while (true) {
                System.out.print("Enter Doctor Name: ");
                name = sc.nextLine().trim();
                if (!name.isEmpty()) {
                    break;
                }
                System.out.println("❌ Name cannot be empty!");
            }

            // Date of Birth (must follow yyyy-MM-dd)
            Date dateOfBirth = null;
            while (true) {
                System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
                String dobInput = sc.nextLine().trim();
                try {
                    dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dobInput);
                    break;
                } catch (Exception e) {
                    System.out.println("❌ Invalid date format. Please use yyyy-MM-dd.");
                }
            }

            // Gender (only 1 char, M/F)
            char gender;
            while (true) {
                System.out.print("Enter Gender (M/F): ");
                String genderInput = sc.nextLine().trim().toUpperCase();
                if (genderInput.length() == 1 && (genderInput.charAt(0) == 'M' || genderInput.charAt(0) == 'F')) {
                    gender = genderInput.charAt(0);
                    break;
                }
                System.out.println("❌ Invalid gender. Enter M or F only.");
            }

            // Contact number (digits only)
            String contactNumber;
            while (true) {
                System.out.print("Enter Contact Number: ");
                contactNumber = sc.nextLine().trim();
                if (contactNumber.matches("\\d+")) {
                    break;
                }
                System.out.println("❌ Contact number must contain digits only.");
            }

            // Email (basic format check)
            String email;
            while (true) {
                System.out.print("Enter Email: ");
                email = sc.nextLine().trim();
                if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    break;
                }
                System.out.println("❌ Invalid email format. Example: doctor@example.com");
            }

            // Qualification (not empty)
            String qualification;
            while (true) {
                System.out.print("Enter Qualification: ");
                qualification = sc.nextLine().trim();
                if (!qualification.isEmpty()) {
                    break;
                }
                System.out.println("❌ Qualification cannot be empty!");
            }

            // Default working status
            String workingStatus = "Off";

            // Create new Doctor object
            Doctor newDoctor = new Doctor(
                    newDoctorId,
                    name,
                    dateOfBirth,
                    gender,
                    contactNumber,
                    email,
                    qualification,
                    workingStatus
            );

            // Add doctor using add() method
            if (DoctorManagement.add(newDoctor)) {
                System.out.println("Doctor registered successfully!");
                UtilityClass.pressEnterToContinue();
                ManageDoctor();
            } else {
                System.out.println("Failed to register doctor. Try again.");
                UtilityClass.pressEnterToContinue();
                ManageDoctor();
            }

        } catch (Exception e) {
            System.out.println("Error while registering doctor: " + e.getMessage());
        }
    }

    public static void editDoctorDetailsUI() {
        Scanner scanner = new Scanner(System.in);

        Doctor doctor = null;
        String doctorID;

        while (true) {
            System.out.print("Enter Doctor ID to edit (or 'x' to cancel): ");
            doctorID = scanner.nextLine().trim();

            if (doctorID.equalsIgnoreCase("x")) {
                System.out.println("❎ Edit cancelled.");
                return; // exit UI
            }

            // Normalize: trim + uppercase
            doctorID = doctorID.toUpperCase();

            doctor = DoctorManagement.findDoctorById(doctorID);
            if (doctor != null) {
                break; // found doctor, continue to editing
            }

            System.out.println("❌ Doctor not found! Please try again.");
        }

        System.out.println("Type 'xxx' to keep the current value.");

        // === Name ===
        String name;
        while (true) {
            System.out.print("Name (" + doctor.getName() + "): ");
            name = scanner.nextLine().trim();
            if (name.equalsIgnoreCase("xxx")) {
                name = doctor.getName();
                break;
            }
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("❌ Name cannot be empty!");
        }

        // === Contact Number ===
        String contactNumber;
        while (true) {
            System.out.print("Contact Number (" + doctor.getContactNumber() + "): ");
            contactNumber = scanner.nextLine().trim();
            if (contactNumber.equalsIgnoreCase("xxx")) {
                contactNumber = doctor.getContactNumber();
                break;
            }
            if (contactNumber.matches("\\d+")) {
                break;
            }
            System.out.println("❌ Contact number must contain digits only.");
        }

        // === Email ===
        String email;
        while (true) {
            System.out.print("Email (" + doctor.getEmail() + "): ");
            email = scanner.nextLine().trim();
            if (email.equalsIgnoreCase("xxx")) {
                email = doctor.getEmail();
                break;
            }
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                break;
            }
            System.out.println("❌ Invalid email format. Example: doctor@example.com");
        }

        // === Qualifications ===
        String qualifications;
        while (true) {
            System.out.print("Qualifications (" + doctor.getQualification() + "): ");
            qualifications = scanner.nextLine().trim();
            if (qualifications.equalsIgnoreCase("xxx")) {
                qualifications = doctor.getQualification();
                break;
            }
            if (!qualifications.isEmpty()) {
                break;
            }
            System.out.println("❌ Qualification cannot be empty!");
        }

        // Call edit method
        boolean success = DoctorManagement.editDoctorDetails(
                doctorID,
                name,
                contactNumber,
                email,
                qualifications
        );

        if (success) {
            System.out.println(" Doctor details updated successfully.");
            UtilityClass.pressEnterToContinue();
            AdminMode();
        } else {
            System.out.println(" Failed to update doctor details.");
        }
    }
    
    public static void removeDoctorUI() {

    while (true) {
        System.out.println("\n=== Remove Doctor ===");
        DisplayAllDoctors();

        System.out.print("\nEnter Doctor ID to remove (or 'xxx' to cancel): ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("xxx")) {
            System.out.println("Returning to menu...");
            return; // leave the method
        }

        String doctorID = input.toUpperCase();
        Doctor doctor = DoctorManagement.findDoctorById(doctorID);

        if (doctor == null) {
            System.out.println("❌ Doctor not found. Please try again.");
            continue;
        }

        // Show doctor details before deleting
        System.out.println("\nDoctor found:");
        doctorDetail(doctor.getDoctorID());

        System.out.print("Are you sure you want to remove this doctor and all their schedules? (y/n): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("y")) {
            boolean removed = DoctorManagement.removeDoctorById(doctorID);
            if (removed) {
                System.out.println("Doctor and their schedules removed successfully.");
            } else {
                System.out.println("❌ Failed to remove doctor.");
            }
            return; // exit after one removal
        } else {
            System.out.println("Removal cancelled.");
            return;
        }
    }
}
    
    public static void doctorDetail(String doctorID){
        Doctor doctor;
        doctor = DoctorManagement.findDoctorById(doctorID);
        
        System.out.println("-------------------------------------------------");
        System.out.printf("""
                          | Name: %-35s  %s
                          """, doctor.getName(), doctor.getDoctorID());
        System.out.printf("""
                          | DOB: %10s
                          """, UtilityClass.formatDate(doctor.getDateOfBirth()));
        System.out.printf("""
                          | Qualification: %s
                          """, doctor.getQualification());
        System.out.printf("""
                          | Contact No.: %s
                          """, doctor.getContactNumber());
        System.out.printf("""
                          | Email: %s
                          """, doctor.getEmail());
         System.out.println("-------------------------------------------------");
        
    }

    public static void testing() {
        //
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DayOfWeek currentDay = today.getDayOfWeek();
        //

//        System.out.println("Doctors available on 12/08/2025:");
//        ScheduleManagement.findAvailableDoctors(DayOfWeek.TUESDAY);
        DynamicList<Schedule> schedulesList = ScheduleManagement.getAllSchedules();
//        DynamicList<Doctor> availableDoctors = ScheduleManagement.findAvailableDoctors(DayOfWeek.TUESDAY);
//        DynamicList<Doctor> freeDoctors = DoctorManagement.getFreeDoctors();
//        boolean allBusy = DoctorManagement.areAllDoctorsBusy();
        DynamicList<Doctor> allDoctors = DoctorManagement.getAllDoctors();

        boolean check = LeaveManagement.isDoctorOnLeave("D002", LocalDate.of(2025, 8, 15));
        System.out.println(check);
        
        

        for (int i = 0; i < schedulesList.size(); i++) {
            Schedule s = schedulesList.get(i);
            System.out.println(s);
        }
//        
//        for (int i = 0; i < availableDoctors.size(); i++) {
//            Doctor d = availableDoctors.get(i);
//            System.out.println(d);
//        }
//        for (int i = 0; i < freeDoctors.size(); i++) {
//            Doctor d = freeDoctors.get(i);
//            System.out.println(d);
//        }
//
//        System.out.println(allBusy);
//
        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d);
        }
        
        DoctorManagement.removeDoctorById("D001");
        
        System.out.println("\n");
        
        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d);
        }
        
        for (int i = 0; i < schedulesList.size(); i++) {
            Schedule s = schedulesList.get(i);
            System.out.println(s);
        }
        
        
        

//        System.out.println("Today: " + currentDay + " " + currentTime);
    }
    
}

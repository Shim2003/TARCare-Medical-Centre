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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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

        DoctorMenu();
//        testing();
    }

    public static void DoctorMenu() {

        boolean validOption = false;

        while (!validOption) {
            System.out.println("--- Welcome to TAR UMT Clinic Doctor Management System ---");
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
            System.out.println("--- Welcome Admin ---");
            System.out.println("1. Check All Doctors");
            System.out.println("2. Check Schedules");
            System.out.println("3. Register a new doctor");
            System.out.println("4. Edit Doctors detail");
            System.out.println("5. Add a new schedule");
            System.out.println("6. Apply Leave(s)");
            System.out.println("7. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    DisplayAllDoctors();
                    break;
                case "2":
                    validOption = true;
                    System.out.println("check doctors");
                    break;
                case "3":
                    validOption = true;
                    System.out.println("check doctors");
                    break;
                case "4":
                    validOption = true;
                    editDoctorDetailsUI();
                    break;
                case "5":
                    validOption = true;
                    System.out.println("check doctors");
                    break;
                case "6":
                    validOption = true;
                    break;
                case "7":
                    validOption = true;
                    DoctorMenu();
                default:
                    System.out.println("Invalid Option!!! Pls try again");
                    UtilityClass.pressEnterToContinue();
            }

        }
    }

    public static void UserMode() {

        boolean validOption = false;

        while (!validOption) {
            System.out.println("--- Welcome ---");
            System.out.println("1. Check Doctors");
            System.out.println("2. Check Schedules");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    DisplayAllDoctors();
                    break;
                case "2":
                    validOption = true;
                    DisplayAllTimetable();
                    break;
                case "3":
                    validOption = true;
                    DoctorMenu();
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

    public static void DisplayAllSchedules() {

        DynamicList<Schedule> schedulesList = ScheduleManagement.getAllSchedules();

        System.out.println("-------------------------------------------------------------------");
        System.out.printf("%-10s %-10s %-12s %-10s %-10s%n",
                "SchedID", "DoctorID", "Day", "Start", "End");
        System.out.println("-------------------------------------------------------------------");

        for (int i = 0; i < schedulesList.size(); i++) {
            Schedule s = schedulesList.get(i);
            System.out.printf("%-10s %-10s %-12s %-10s %-10s%n",
                    s.getScheduleID(),
                    s.getDoctorID(),
                    s.getDayOfWeek(),
                    s.getStartTime(),
                    s.getEndTime()
            );
        }
        System.out.println("-------------------------------------------------------------------");

    }

    public static void DisplayAllTimetable() {

        DynamicList<Schedule> schedules = ScheduleManagement.getAllSchedules();
        DynamicList<Doctor> doctors = DoctorManagement.getAllDoctors();

        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

        System.out.println("");

        for (int d = 0; d < days.length; d++) {

            System.out.print(days[d] + ": ");

            boolean found = false;

            // Loop schedules for this day
            for (int i = 0; i < schedules.size(); i++) {
                Schedule s = schedules.get(i);

                // Match day
                if (s.getDayOfWeek().getValue() - 1 == d) {
                    // Find doctor name
                    String doctorName = "";
                    for (int j = 0; j < doctors.size(); j++) {
                        if (doctors.get(j).getDoctorID().equals(s.getDoctorID())) {
                            doctorName = doctors.get(j).getName();
                            break;
                        }
                    }

                    System.out.print("Dr. " + doctorName + " (" + s.getStartTime() + "-" + s.getEndTime() + ") " + "|");
                    found = true;
                }
            }

            if (!found) {
                System.out.print("No schedules");
            }

            System.out.println(); // new line for next day
            System.out.println("----------------------------------------------------------------------------------"); // new line for next day
        }

    }

    public static void editDoctorDetailsUI() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Doctor ID to edit: ");
        String doctorID = scanner.nextLine();

        Doctor doctor = DoctorManagement.findDoctorById(doctorID);
        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.println("Type 'xxx' to keep the current value.");

        // Name
        System.out.print("Name (" + doctor.getName() + "): ");
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("xxx")) {
            name = doctor.getName();
        }

        // Contact Number
        System.out.print("Contact Number (" + doctor.getContactNumber() + "): ");
        String contactNumber = scanner.nextLine();
        if (contactNumber.equalsIgnoreCase("xxx")) {
            contactNumber = doctor.getContactNumber();
        }

        // Email
        System.out.print("Email (" + doctor.getEmail() + "): ");
        String email = scanner.nextLine();
        if (email.equalsIgnoreCase("xxx")) {
            email = doctor.getEmail();
        }

        // Qualifications
        System.out.print("Qualifications (" + doctor.getQualification() + "): ");
        String qualifications = scanner.nextLine();
        if (qualifications.equalsIgnoreCase("xxx")) {
            qualifications = doctor.getQualification();
        }

        // Call your existing edit method
        boolean success = DoctorManagement.editDoctorDetails(doctorID, name, contactNumber, email, qualifications);

        if (success) {
            System.out.println("Doctor details updated successfully.");
            UtilityClass.pressEnterToContinue();
            AdminMode();
        } else {
            System.out.println("Failed to update doctor details.");
        }
    }

    public static void testing() {
        //
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DayOfWeek currentDay = today.getDayOfWeek();
        //

//        System.out.println("Doctors available on 12/08/2025:");
//        ScheduleManagement.findAvailableDoctors(DayOfWeek.TUESDAY);
        DynamicList<Schedule> schedulesList = ScheduleManagement.findSchedulesByDoctorId("D005");
//        DynamicList<Doctor> availableDoctors = ScheduleManagement.findAvailableDoctors(DayOfWeek.TUESDAY);
        DynamicList<Doctor> freeDoctors = DoctorManagement.getFreeDoctors();
        boolean allBusy = DoctorManagement.areAllDoctorsBusy();
        DynamicList<Doctor> allDoctors = DoctorManagement.getAllDoctors();

        boolean check = LeaveManagement.isDoctorOnLeave("D002", LocalDate.of(2025, 8, 15));
        System.out.println(check);

//        for (int i = 0; i < schedulesList.size(); i++) {
//            Schedule s = schedulesList.get(i);
//            System.out.println(s);
//        }
//        
//        for (int i = 0; i < availableDoctors.size(); i++) {
//            Doctor d = availableDoctors.get(i);
//            System.out.println(d);
//        }
        for (int i = 0; i < freeDoctors.size(); i++) {
            Doctor d = freeDoctors.get(i);
            System.out.println(d);
        }

        System.out.println(allBusy);

        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d);
        }

        if (freeDoctors.isEmpty()) {
            System.out.println("no doctor");
        }

        System.out.println("Today: " + currentDay + " " + currentTime);
    }
}

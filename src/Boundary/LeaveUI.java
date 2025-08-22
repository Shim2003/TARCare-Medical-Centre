/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.DoctorManagement;
import Control.LeaveManagement;
import Entity.Doctor;
import Entity.DoctorLeave;
import Utility.UtilityClass;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
/**
 *
 * @author ACER
 */
public class LeaveUI {
    
     private static final Scanner scanner = new Scanner(System.in);
    
    public static void ManageLeave() {

        boolean validOption = false;

        while (!validOption) {
            System.out.println("\n-*-*- Leave(s) Management -*-*-");
            System.out.println("1. Check Leave(s)");
            System.out.println("2. Apply Leave(s)");
            System.out.println("3. Remove Leave(s)");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    System.out.println("");
                    break;
                case "2":
                    validOption = true;
                    addLeaveUI();
                    UtilityClass.pressEnterToContinue();
                    ManageLeave();
                    break;
                case "3":
                    validOption = true;
                    System.out.println("");
                    break;
                case "4":
                    validOption = true;
                    DoctorUI.AdminMode();
                    break;
                default:
                    System.out.println("Invalid Option!!! Pls try again");
                    UtilityClass.pressEnterToContinue();
            }

        }
    }
    
    public static void addLeaveUI() {
        System.out.println("\n=== Add Doctor Leave ===");

        // Step 1: Enter Doctor ID
        String doctorId;
        while (true) {
            System.out.print("Enter Doctor ID (or 'xxx' to cancel): ");
            doctorId = scanner.nextLine().trim().toUpperCase();

            if (doctorId.equalsIgnoreCase("xxx")) {
                System.out.println("❌ Cancelled adding leave.");
                return;
            }

            Doctor doctor = DoctorManagement.findDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("❌ Doctor not found. Try again.");
            } else {
                System.out.println(" Doctor found: Dr. " + doctor.getName());
                break;
            }
        }

        // Step 2: Enter dateFrom
        LocalDate dateFrom = null;
        while (dateFrom == null) {
            System.out.print("Enter Leave Start Date (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            try {
                dateFrom = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        // Step 3: Enter dateTo
        LocalDate dateTo = null;
        while (dateTo == null) {
            System.out.print("Enter Leave End Date (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            try {
                dateTo = LocalDate.parse(input);
                if (dateTo.isBefore(dateFrom)) {
                    System.out.println("❌ End date cannot be before start date.");
                    dateTo = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        // Step 4: Enter reason
        String reason;
        while (true) {
            System.out.print("Enter Reason for Leave: ");
            reason = scanner.nextLine().trim();
            if (reason.isEmpty()) {
                System.out.println("❌ Reason cannot be empty.");
            } else {
                break;
            }
        }

        // Step 5: Generate Leave ID & create object
        String leaveId = LeaveManagement.generateNextLeaveId();
        DoctorLeave leave = new DoctorLeave(leaveId, doctorId, dateFrom, dateTo, reason);
      

        // Step 6: Add to list
        if (LeaveManagement.addLeave(leave)) {
            System.out.println(" Leave added successfully with ID: " + leaveId);
        } else {
            System.out.println("❌ Failed to add leave.");
        }
    }
}

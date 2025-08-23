/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import ADT.MyList;
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
                    displayAllLeavesUI();
                    UtilityClass.pressEnterToContinue();
                    ManageLeave();
                    break;
                case "2":
                    validOption = true;
                    addLeaveUI();
                    UtilityClass.pressEnterToContinue();
                    ManageLeave();
                    break;
                case "3":
                    validOption = true;
                    cancelLeaveUI();
                    UtilityClass.pressEnterToContinue();
                    ManageLeave();
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
    
    public static void displayAllLeavesUI() {
        System.out.println("\n=== All Doctor Leaves ===");

        MyList<DoctorLeave> leaves = LeaveManagement.getAllLeaves();

        if (leaves.isEmpty()) {
            System.out.println("❌ No leave records found.");
            return;
        }

        for (int i = 0; i < leaves.size(); i++) {
            DoctorLeave leave = leaves.get(i);

            System.out.println((i + 1) + ". LeaveID: " + leave.getLeaveID()
                    + " | DoctorID: " + leave.getDoctorID()
                    + " | From: " + leave.getDateFrom()
                    + " | To: " + leave.getDateTo()
                    + " | Reason: " + leave.getReason());
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

    public static void cancelLeaveUI() {
        System.out.println("\n=== Cancel Doctor Leave ===");

        // 1) Ask for a valid Doctor ID (or xxx to exit)
        String doctorID;
        DynamicList<DoctorLeave> leavesForDoctor;

        while (true) {
            System.out.print("Enter Doctor ID (or 'xxx' to cancel): ");
            doctorID = scanner.nextLine().trim().toUpperCase();

            if (doctorID.equals("XXX")) {
                System.out.println("Cancelled.");
                return;
            }

            Doctor doctor = DoctorManagement.findDoctorById(doctorID);
            if (doctor == null) {
                System.out.println("❌ Doctor not found. Try again or type 'xxx' to cancel.");
                continue;
            }

            // Fetch leaves for this doctor
            leavesForDoctor = LeaveManagement.findLeavesByDoctorId(doctorID);
            if (leavesForDoctor.isEmpty()) {
                System.out.println("ℹ️ No leave records found for " + doctorID + ".");
                // Let user try a different doctor or exit
                System.out.print("Try another Doctor ID? (Y/N): ");
                String ans = scanner.nextLine().trim().toUpperCase();
                if (ans.equals("Y")) {
                    continue; // loop to ask Doctor ID again
                } else {
                    System.out.println("Cancelled.");
                    return;
                }
            } else {
                // We have at least one leave for this doctor -> proceed
                System.out.println("✅ Doctor found: " + doctor.getName());
                break;
            }
        }

        // 2) Display the leaves for this doctor
        System.out.println("\nLeave records for Doctor ID " + doctorID + ":");
        for (int i = 0; i < leavesForDoctor.size(); i++) {
            DoctorLeave leave = leavesForDoctor.get(i);
            System.out.println((i + 1) + ". LeaveID: " + leave.getLeaveID()
                    + " | From: " + leave.getDateFrom()
                    + " | To: " + leave.getDateTo()
                    + " | Reason: " + leave.getReason());
        }

        // 3) Ask for a valid Leave ID (or xxx to exit)
        String leaveIDToCancel;
        while (true) {
            System.out.print("\nEnter LeaveID to cancel (or 'xxx' to cancel): ");
            leaveIDToCancel = scanner.nextLine().trim().toUpperCase();

            if (leaveIDToCancel.equals("XXX")) {
                System.out.println("Cancelled.");
                return;
            }

            boolean found = false;
            for (int i = 0; i < leavesForDoctor.size(); i++) {
                if (leavesForDoctor.get(i).getLeaveID().equalsIgnoreCase(leaveIDToCancel)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("❌ LeaveID not found for this doctor. Try again or type 'xxx' to cancel.");
            } else {
                break; // valid LeaveID selected
            }
        }

        // 4) Confirm and remove
        while (true) {
            System.out.print("Are you sure you want to cancel leave " + leaveIDToCancel + "? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();

            if (confirm.equals("Y")) {
                boolean removed = LeaveManagement.removeLeave(leaveIDToCancel);
                if (removed) {
                    System.out.println("Leave " + leaveIDToCancel + " has been cancelled successfully.");
                } else {
                    System.out.println("❌ LeaveID not found or could not be removed.");
                }
                return;
            } else if (confirm.equals("N")) {
                System.out.println("Operation aborted.");
                return;
            } else {
                System.out.println("Please enter Y or N.");
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import ADT.MyList;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Entity.Doctor;
import Entity.Schedule;
import Utility.UtilityClass;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class ScheduleUI {

    private static final Scanner scanner = new Scanner(System.in);

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

//     public static void main(String[] args) {
//        ManageSchedule();
//        
//    }
    public static void ManageSchedule() {

        boolean validOption = false;

        while (!validOption) {
            System.out.println("\n-*-*- Schedule(s) Management -*-*-");
            System.out.println("1. Check Schedules");
            System.out.println("2. Add a new schedule");
            System.out.println("3. Amend Schedule(s) detail");
            System.out.println("4. Remove Schedule(s)");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    DisplayAllSchedules();
                    UtilityClass.pressEnterToContinue();
                    ManageSchedule();
                    break;
                case "2":
                    validOption = true;
                    AddScheduleUI();
                    UtilityClass.pressEnterToContinue();
                    ManageSchedule();
                    break;
                case "3":
                    validOption = true;
                    editScheduleUI();
                    UtilityClass.pressEnterToContinue();
                    ManageSchedule();
                    break;
                case "4":
                    validOption = true;
                    removeScheduleUI();
                    UtilityClass.pressEnterToContinue();
                    ManageSchedule();
                    break;
                case "5":
                    validOption = true;
                    DoctorUI.DoctorStaffMode();
                    break;
                default:
                    System.out.println("Invalid Option!!! Pls try again");
                    UtilityClass.pressEnterToContinue();
            }

        }
    }

    public static void DisplayAllSchedules() {

        MyList<Schedule> schedulesList = ScheduleManagement.getAllSchedules();

        System.out.println("\n-------------------------------------------------------------------");
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

        MyList<Schedule> schedules = ScheduleManagement.getAllSchedules();
        MyList<Doctor> doctors = DoctorManagement.getAllDoctors();

        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

        System.out.println("");

        for (int d = 0; d < days.length; d++) {

            System.out.printf("%-11s | ", days[d]);

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

                    System.out.print("Dr. " + doctorName + " (" + s.getStartTime() + "-" + s.getEndTime() + ") " + "| ");
                    found = true;
                }
            }

            if (!found) {
                System.out.print("No schedules");
            }

            System.out.println(); // new line for next day
            System.out.println("--------------------------------------------------------------------------------------"); // new line for next day
        }

    }

    public static void AddScheduleUI() {
        System.out.println("\n=== Add Doctor Schedule ===");

        // Step 1: Enter Doctor ID
        String doctorId;
        Doctor doctor;
        while (true) {
            System.out.print("Enter Doctor ID (or 'xxx' to cancel): ");
            doctorId = scanner.nextLine().trim().toUpperCase();

            if (doctorId.equalsIgnoreCase("xxx")) {
                System.out.println("❌ Cancelled adding schedule.");
                return;
            }

            doctor = DoctorManagement.findDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("❌ Doctor not found. Try again.");
            } else {
                System.out.println(" Doctor found: Dr. " + doctor.getName());
                break;
            }
        }

        // Step 2: Generate Schedule ID
        String scheduleId = ScheduleManagement.generateNextScheduleId();

        while (true) {
            // Step 3: Enter Day of Week
            DayOfWeek dayOfWeek = null;
            while (dayOfWeek == null) {
                System.out.print("Enter Day of Week (e.g., MONDAY): ");
                String input = scanner.nextLine().trim().toUpperCase();
                try {
                    dayOfWeek = DayOfWeek.valueOf(input);
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Invalid day! Please use MONDAY, TUESDAY, etc.");
                }
            }

            // Step 4: Enter Start Time
            LocalTime startTime = null;
            while (startTime == null) {
                System.out.print("Enter Start Time (HH:mm): ");
                String input = scanner.nextLine().trim();
                try {
                    startTime = LocalTime.parse(input);
                } catch (Exception e) {
                    System.out.println("❌ Invalid time format. Please use HH:mm.");
                }
            }

            // Step 5: Enter End Time
            LocalTime endTime = null;
            while (endTime == null) {
                System.out.print("Enter End Time (HH:mm): ");
                String input = scanner.nextLine().trim();
                try {
                    endTime = LocalTime.parse(input);
                    if (!endTime.isAfter(startTime)) {
                        System.out.println("❌ End time must be after start time!");
                        endTime = null;
                    }
                } catch (Exception e) {
                    System.out.println("❌ Invalid time format. Please use HH:mm.");
                }
            }

            // Step 6: Create Schedule
            Schedule schedule = new Schedule(scheduleId, doctorId, dayOfWeek, startTime, endTime);

            // Step 7: Conflict Check
            if (ScheduleManagement.hasConflict(schedule)) {
                System.out.println("⚠️ Conflict detected! Doctor already has a schedule during this time.");
                System.out.println(" Please enter a different day/time.\n");
                continue; // loop back to Step 3
            }

            // Step 8: Try Adding
            if (ScheduleManagement.addSchedule(schedule)) {
                System.out.println(" Schedule added successfully with ID: " + scheduleId);
                scheduleDetail(schedule.getScheduleID()); // show details
            } else {
                System.out.println("❌ Failed to add schedule. Conflict with existing schedule");
            }
            break; // exit loop after success
        }
    }

    public static void editScheduleUI() {
        System.out.println("\n=== Edit Schedule ===");

        while (true) {
            System.out.print("Enter Schedule ID to edit (or 'xxx' to cancel): ");
            String scheduleId = scanner.nextLine().trim().toUpperCase();

            if (scheduleId.equalsIgnoreCase("xxx")) {
                System.out.println("Cancelled editing schedule.");
                return;
            }

            Schedule schedule = ScheduleManagement.findScheduleByScheduleId(scheduleId);
            if (schedule == null) {
                System.out.println("Schedule not found! Try again.");
                continue; // ask again
            }

            // Show current details
            System.out.println("\nCurrent Schedule Details:");
            scheduleDetail(scheduleId);

            // Ask for new details
            System.out.print("Enter new Doctor ID (leave blank to keep '" + schedule.getDoctorID() + "'): ");
            String newDoctorID = scanner.nextLine().trim();
            if (newDoctorID.isEmpty()) {
                newDoctorID = null;
            }

            System.out.print("Enter new Day of Week (e.g., MONDAY, leave blank to keep '" + schedule.getDayOfWeek() + "'): ");
            String newDayStr = scanner.nextLine().trim().toUpperCase();
            DayOfWeek newDay = null;
            if (!newDayStr.isEmpty()) {
                try {
                    newDay = DayOfWeek.valueOf(newDayStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid day entered, skipping update.");
                }
            }

            System.out.print("Enter new Start Time (HH:mm, leave blank to keep '" + schedule.getStartTime() + "'): ");
            String newStartStr = scanner.nextLine().trim();
            LocalTime newStartTime = null;
            if (!newStartStr.isEmpty()) {
                try {
                    newStartTime = LocalTime.parse(newStartStr, TIME_FORMATTER);
                } catch (Exception e) {
                    System.out.println("Invalid time format, skipping update.");
                }
            }

            System.out.print("Enter new End Time (HH:mm, leave blank to keep '" + schedule.getEndTime() + "'): ");
            String newEndStr = scanner.nextLine().trim();
            LocalTime newEndTime = null;
            if (!newEndStr.isEmpty()) {
                try {
                    newEndTime = LocalTime.parse(newEndStr, TIME_FORMATTER);
                } catch (Exception e) {
                    System.out.println("Invalid time format, skipping update.");
                }
            }

            // Call controller
            boolean success = ScheduleManagement.editSchedule(scheduleId, newDoctorID, newDay, newStartTime, newEndTime);

            if (success) {
                System.out.println("Schedule updated successfully!");
            } else {
                System.out.println("❌ Failed to update schedule.");
            }
            return; // end after one edit
        }
    }

    public static void removeScheduleUI() {
        System.out.println("\n=== Remove Schedule ===");

        while (true) {
            System.out.print("Enter Schedule ID to remove (or 'xxx' to cancel): ");
            String scheduleId = scanner.nextLine().trim().toUpperCase();

            if (scheduleId.equalsIgnoreCase("xxx")) {
                System.out.println("Cancelled removing schedule.");
                return;
            }

            Schedule schedule = ScheduleManagement.findScheduleByScheduleId(scheduleId);
            if (schedule == null) {
                System.out.println("❌ Schedule not found! Try again.");
                continue; // keep asking until valid
            }

            // Display schedule details
            System.out.println("");
            scheduleDetail(scheduleId);

            // Ask confirmation
            System.out.print("Are you sure you want to remove this schedule? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y")) {
                boolean removed = ScheduleManagement.removeScheduleById(scheduleId);
                if (removed) {
                    System.out.println("Schedule removed successfully!");
                } else {
                    System.out.println("❌ Failed to remove schedule.");
                }
                return;
            } else {
                System.out.println("❌ Removal cancelled.");
                return;
            }
        }
    }

    public static void scheduleDetail(String scheduleID) {
        Schedule schedule;
        schedule = ScheduleManagement.findScheduleByScheduleId(scheduleID);

        Doctor doctor;
        doctor = DoctorManagement.findDoctorById(schedule.getDoctorID());

        System.out.println("-------------------------------------------------");
        System.out.printf("""
                          | Name: %-35s  %s
                          """, doctor.getName(), schedule.getScheduleID());
        System.out.printf("""
                          | Day of Week : %s
                          """, schedule.getDayOfWeek());
        System.out.printf("""
                          | Start Time : %s
                          """, schedule.getStartTime());
        System.out.printf("""
                          | End Time   : %s
                          """, schedule.getEndTime());

        System.out.println("-------------------------------------------------");

    }

}

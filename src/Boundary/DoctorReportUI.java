/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import ADT.MyList;
import Control.DoctorManagement;
import Control.LeaveManagement;
import Control.ScheduleManagement;
import Entity.Doctor;
import Entity.DoctorLeave;
import Entity.Schedule;
import Utility.UtilityClass;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class DoctorReportUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void ReportMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n**** Doctor Module ****");
            System.out.println("--- Summary Reports ---");
            System.out.println("1. Work & Leave Utilization");
            System.out.println("2. Top Doctors by Leave Taken (Current Month)");
            System.out.println("3. Top Doctors by Working Hours");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    DisplayWorkLeaveDays();
                    UtilityClass.pressEnterToContinue();
                    break;
                case "2":
                    DisplayTopLeaveDoctors();
                    UtilityClass.pressEnterToContinue();
                    break;
                case "3":
                    displayTopHardworkingDoctorsUI();
                    UtilityClass.pressEnterToContinue();
                    break;
                case "4": // Back
                    exit = true; // break out of the loop
                    DoctorUI.DoctorStaffMode();
                    break;
                default:
                    System.out.println("Invalid Option!!! Pls try again");
                    UtilityClass.pressEnterToContinue();
            }
        }
    }

    public static void DisplayWorkLeaveDays() {
        MyList<Doctor> doctors = DoctorManagement.getAllDoctors();
        MyList<DoctorLeave> leaves = LeaveManagement.getAllLeaves();
        MyList<Schedule> schedules = ScheduleManagement.getAllSchedules();
        YearMonth currentMonth = YearMonth.now();

        System.out.printf("\n==================== Current Month (%s) Report ====================", currentMonth);
        System.out.printf("%-10s %-20s %-12s %-12s %-12s\n",
                "DoctorID", "Name", "WorkDays", "LeaveDays", "Utilization");

        for (int i = 0; i < doctors.size(); i++) {
            Doctor doc = doctors.get(i);

            int workDayCount = ScheduleManagement.countWorkingDaysInMonth(doc.getDoctorID(), currentMonth);

            // use the new method here
            int leaveDayCount = LeaveManagement.countLeaveDaysForDoctor(
                    doc.getDoctorID(),
                    currentMonth,
                    leaves,
                    schedules);

            int totalDays = workDayCount + leaveDayCount;
            double utilization = (totalDays == 0) ? 0 : ((double) workDayCount / totalDays) * 100;

            System.out.printf("%-10s %-20s %-12d %-12d %-10.2f%%\n",
                    doc.getDoctorID(), doc.getName(), workDayCount, leaveDayCount, utilization);
        }

        System.out.println("======================================================================");
    }

    public static void DisplayTopLeaveDoctors() {
        MyList<Doctor> doctors = DoctorManagement.getAllDoctors();
        YearMonth currentMonth = YearMonth.now();
        int year = currentMonth.getYear();
        int month = currentMonth.getMonthValue();

        // inner helper class
        class LeaveSummary {

            String id, name;
            int leaveDays;
            int leaveCount;

            LeaveSummary(String id, String name, int leaveDays, int leaveCount) {
                this.id = id;
                this.name = name;
                this.leaveDays = leaveDays;
                this.leaveCount = leaveCount;
            }
        }

        // use your own ADT
        MyList<LeaveSummary> summaries = new DynamicList<>();

        // build summary list
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doc = doctors.get(i);

            int leaveDays = LeaveManagement.countLeaveDaysInMonth(doc.getDoctorID(), currentMonth);
            int leaveCount = LeaveManagement.countLeaveRecordsInMonth(doc.getDoctorID(), currentMonth);

            if (leaveDays > 0) {
                summaries.add(new LeaveSummary(doc.getDoctorID(), doc.getName(), leaveDays, leaveCount));
            }
        }

        // sort using your UtilityClass
        UtilityClass.quickSort(summaries, (a, b) -> Integer.compare(b.leaveDays, a.leaveDays));

        // print report
        System.out.printf("\n============= Doctors by Leave Taken (%4d/%02d) =============", year, month);
        System.out.printf("%-8s %-28s %-12s %-12s\n", "DoctorID", "Name", "LeaveDays", "LeaveRecords");

        for (int i = 0; i < summaries.size(); i++) {
            LeaveSummary s = summaries.get(i);
            System.out.printf("%-8s %-28s %-12d %-12d\n", s.id, "Dr " + s.name, s.leaveDays, s.leaveCount);
        }

        System.out.println("==========================================================");
    }

    // ================= UI ==================
    public static void displayTopHardworkingDoctorsUI() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n===== Top Hardworking Doctors Report =====");
        System.out.print("Enter year (e.g. 2025): ");
        int year = sc.nextInt();

        System.out.print("Enter month (1-12): ");
        int month = sc.nextInt();

        // Validate input (default to current month if invalid)
        if (month < 1 || month > 12) {
            System.out.println("Invalid month entered. Defaulting to current month.");
            LocalDate now = LocalDate.now();
            year = now.getYear();
            month = now.getMonthValue();
        }

        YearMonth ym = YearMonth.of(year, month);

        // call the display method (calculation + report)
        displayTopHardworkingDoctors(ym);
    }

    public static void displayTopHardworkingDoctors(YearMonth month) {
        MyList<DoctorManagement.DoctorWorkSummary> summaries = DoctorManagement.calculateDoctorWorkingHours(month);

        // Sort by total working hours (descending)
        UtilityClass.quickSort(summaries, (a, b) -> Integer.compare(b.getTotalHours(), a.getTotalHours()));

        // Print report
        System.out.println("\n========== Top Hardworking Doctors ==========");
        System.out.printf("Month: %s %d\n", month.getMonth(), month.getYear());
        System.out.printf("%-10s %-20s %-15s\n", "DoctorID", "Name", "TotalHours");

        for (int i = 0; i < summaries.size(); i++) {
            DoctorManagement.DoctorWorkSummary s = summaries.get(i);
            System.out.printf("%-10s %-20s %-15d\n", s.getId(), s.getName(), s.getTotalHours());
        }

        System.out.println("=============================================");
    }

}

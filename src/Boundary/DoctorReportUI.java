/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.MyList;
import Control.DoctorManagement;
import Control.LeaveManagement;
import Control.ScheduleManagement;
import Entity.Doctor;
import Entity.DoctorLeave;
import Entity.Schedule;
import Utility.UtilityClass;
import java.time.YearMonth;
import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class DoctorReportUI {
    private static final Scanner scanner = new Scanner(System.in);
    
     public static void ReportMenu() {
        boolean validOption = false;

        while (!validOption) {
            System.out.println("\n---- Doctor Module ----");
            System.out.println("\n--- Summary Reports ---");
            System.out.println("1. Work & Leave Utilization");
            System.out.println("2. ");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    DisplayWorkLeaveDays();
                    UtilityClass.pressEnterToContinue();
                    ReportMenu();
                    break;
                case "2":
                    validOption = true;
                    System.out.println("");;
                    break;
                case "5":
                    validOption = true;
                    DoctorUI.AdminMode();
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

    System.out.println("\n===================== Doctor Monthly Report =====================");
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

}

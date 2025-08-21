/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;
import ADT.DynamicList;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Entity.Doctor;
import Entity.Schedule;
import Utility.UtilityClass;
import java.util.Scanner;
/**
 *
 * @author ACER
 */
public class ScheduleUI {
    
     private static final Scanner scanner = new Scanner(System.in);
     
//     public static void main(String[] args) {
//        ManageSchedule();
//        
//    }
     
     public static void ManageSchedule() {

        boolean validOption = false;

        while (!validOption) {
            System.out.println("\n--- Welcome Admin ---");
            System.out.println("1. Check Timetable");
            System.out.println("2. Add a new schedule");
            System.out.println("3. Edit Schedule(s) detail");
            System.out.println("4. Remove Schedule(s)");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    validOption = true;
                    DisplayAllTimetable();
                    break;
                case "2":
                    validOption = true;
                    System.out.println("");
                    break;
                case "3":
                    validOption = true;
                    System.out.println("");
                    break;
                case "4":
                    validOption = true;
                    System.out.println("");
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
     
    public static void DisplayAllSchedules() {

        DynamicList<Schedule> schedulesList = ScheduleManagement.getAllSchedules();

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
    
    public static void AddSchedule(){
        
    }
}

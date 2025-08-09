/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.Doctor;
import Entity.Schedule;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 *
 * @author ACER
 */
public class ScheduleManagement {
    
     private static DynamicList<Schedule> scheduleList = new DynamicList<>();
     
      // Add schedule
    public static boolean addSchedule(Schedule s) {
        if (s != null) {
            scheduleList.add(s);
            return true;
        }
        return false;
    }

    // Remove schedule by ID
    public static boolean removeScheduleById(String scheduleID) {
        for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleList.get(i).getScheduleID().equals(scheduleID)) {
                scheduleList.remove(i);
                return true;
            }
        }
        return false;
    }
    
     // Find schedules for a specific doctor
    public static DynamicList<Schedule> findSchedulesByDoctorId(String doctorID) {
        return scheduleList.findAll(s -> s.getDoctorID().equals(doctorID));
    }

    // Find doctors available on a given day
    public static DynamicList<Doctor> findAvailableDoctors(DayOfWeek day) {
        DynamicList<Doctor> availableDoctors = new DynamicList<>();

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule s = scheduleList.get(i);
            if (s.getDayOfWeek() == day) {
                Doctor d = DoctorManagement.findDoctorById(s.getDoctorID());
                if (d != null) {
                    availableDoctors.add(d);
                }
            }
        }

        return availableDoctors;
    }



// Sample schedules
public static void addSampleSchedules() {
    addSchedule(new Schedule("S001", "D001", DayOfWeek.TUESDAY, 
        LocalTime.of(9, 0), LocalTime.of(12, 0)));
    addSchedule(new Schedule("S002", "D002", DayOfWeek.TUESDAY, 
        LocalTime.of(13, 0), LocalTime.of(17, 0)));
    addSchedule(new Schedule("S003", "D001", DayOfWeek.WEDNESDAY, 
        LocalTime.of(9, 0), LocalTime.of(12, 0)));
}


}

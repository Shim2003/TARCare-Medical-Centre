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

    //Read
    public static DynamicList getAllSchedules() {
        return scheduleList;
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
    
    public static boolean removeScheduleByDoctorId(String doctorID) {
        return scheduleList.removeIf(s -> s.getDoctorID().equalsIgnoreCase(doctorID.trim()));
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

    public static boolean clearSchedulesByDoctorId(String doctorID) {
        boolean removed = false;

        // Temporary list to keep schedules that don't belong to this doctor
        DynamicList<Schedule> tempList = new DynamicList<>();

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule s = scheduleList.get(i);
            if (!s.getDoctorID().equals(doctorID)) {
                tempList.add(s); // keep other doctors' schedules
            } else {
                removed = true; // found at least one to remove
            }
        }

        // Clear original list
        scheduleList.clear();

        // Copy back the schedules that we kept
        for (int i = 0; i < tempList.size(); i++) {
            scheduleList.add(tempList.get(i));
        }

        return removed;
    }

// Sample schedules
    public static void addSampleSchedules() {
        addSchedule(new Schedule("S001", "D001", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S010", "D001", DayOfWeek.THURSDAY,
                LocalTime.of(0, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S011", "D003", DayOfWeek.THURSDAY,
                LocalTime.of(0, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S002", "D002", DayOfWeek.TUESDAY,
                LocalTime.of(13, 0), LocalTime.of(17, 0)));
        addSchedule(new Schedule("S003", "D001", DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 0), LocalTime.of(23, 45)));
        addSchedule(new Schedule("S006", "D002", DayOfWeek.FRIDAY,
                LocalTime.of(9, 0), LocalTime.of(23, 45)));
        addSchedule(new Schedule("S004", "D001", DayOfWeek.MONDAY,
                LocalTime.of(9, 30), LocalTime.of(12, 30)));
        addSchedule(new Schedule("S005", "D005", DayOfWeek.SUNDAY,
                LocalTime.of(9, 30), LocalTime.of(23, 45)));
        addSchedule(new Schedule("S007", "D004", DayOfWeek.SUNDAY,
                LocalTime.of(9, 30), LocalTime.of(23, 45)));
        addSchedule(new Schedule("S020", "D005", DayOfWeek.FRIDAY,
                LocalTime.of(9, 0), LocalTime.of(23, 45)));
    }

}

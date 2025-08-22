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
//    public static boolean addSchedule(Schedule s) {
//        if (s != null) {
//            scheduleList.add(s);
//            return true;
//        }
//        return false;
//    }
    public static boolean addSchedule(Schedule s) {
        if (s != null) {
            scheduleList.add(s);

            // Update doctor status right after adding schedule
            Doctor doctor = DoctorManagement.findDoctorById(s.getDoctorID());
            if (doctor != null) {
                DoctorManagement.updateWorkingStatus(doctor);
            }
            return true;
        }
        return false;
    }

    public static String generateNextScheduleId() {
        int maxId = 0;
        DynamicList<Schedule> schedules = getAllSchedules();

        for (int i = 0; i < schedules.size(); i++) {
            String id = schedules.get(i).getScheduleID().trim().toUpperCase();

            if (id.startsWith("S")) {
                try {
                    int num = Integer.parseInt(id.substring(1)); // remove 'S'
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                    // ignore invalid formats
                }
            }
        }

        int nextId = maxId + 1;
        return String.format("S%03d", nextId); // e.g. S004
    }

    //Read
    public static DynamicList getAllSchedules() {
        return scheduleList;
    }

    // Remove one schedule by ID
//    public static boolean removeScheduleById(String scheduleID) {
//        for (int i = 0; i < scheduleList.size(); i++) {
//            if (scheduleList.get(i).getScheduleID().equals(scheduleID)) {
//                scheduleList.remove(i);
//                return true;
//            }
//        }
//        return false;
//    }
    public static boolean removeScheduleById(String scheduleID) {
        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);
            if (schedule.getScheduleID().equals(scheduleID)) {
                String doctorId = schedule.getDoctorID(); // capture doctor before removing
                scheduleList.remove(i);

                // ✅ Update doctor status
                Doctor doctor = DoctorManagement.findDoctorById(doctorId);
                if (doctor != null) {
                    DoctorManagement.updateWorkingStatus(doctor);
                }

                return true;
            }
        }
        return false;
    }

    public static boolean removeScheduleByDoctorId(String doctorID) {
        boolean removed = scheduleList.removeIf(s -> s.getDoctorID().equalsIgnoreCase(doctorID.trim()));

        if (removed) {
            // ✅ Update the doctor's working status after removal
            Doctor doctor = DoctorManagement.findDoctorById(doctorID);
            if (doctor != null) {
                DoctorManagement.updateWorkingStatus(doctor);
            }
        }

        return removed;
    }

    // Find schedules for a specific doctor
    public static DynamicList<Schedule> findSchedulesByDoctorId(String doctorID) {
        return scheduleList.findAll(s -> s.getDoctorID().equals(doctorID));
    }

    // Find a single schedule by its ID
    public static Schedule findScheduleByScheduleId(String scheduleID) {
        return scheduleList.findFirst(s -> s.getScheduleID().equals(scheduleID));
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

    public static boolean editSchedule(String scheduleID, String newDoctorID,
            DayOfWeek newDay, LocalTime newStartTime, LocalTime newEndTime) {

        Schedule schedule = findScheduleByScheduleId(scheduleID);
        if (schedule == null) {
            return false; // schedule not found
        }

        String oldDoctorId = schedule.getDoctorID();

        // Update fields
        if (newDoctorID != null && !newDoctorID.isEmpty()) {
            schedule.setDoctorID(newDoctorID);
        }
        if (newDay != null) {
            schedule.setDayOfWeek(newDay);
        }
        if (newStartTime != null) {
            schedule.setStartTime(newStartTime);
        }
        if (newEndTime != null) {
            schedule.setEndTime(newEndTime);
        }

        // ✅ Update doctor status
        Doctor oldDoctor = DoctorManagement.findDoctorById(oldDoctorId);
        if (oldDoctor != null) {
            DoctorManagement.updateWorkingStatus(oldDoctor);
        }

        if (!oldDoctorId.equals(schedule.getDoctorID())) { // doctor changed
            Doctor newDoctor = DoctorManagement.findDoctorById(schedule.getDoctorID());
            if (newDoctor != null) {
                DoctorManagement.updateWorkingStatus(newDoctor);
            }
        }

        return true; // edited successfully
    }

//    public static boolean clearSchedulesByDoctorId(String doctorID) {
//        boolean removed = false;
//
//        // Temporary list to keep schedules that don't belong to this doctor
//        DynamicList<Schedule> tempList = new DynamicList<>();
//
//        for (int i = 0; i < scheduleList.size(); i++) {
//            Schedule s = scheduleList.get(i);
//            if (!s.getDoctorID().equals(doctorID)) {
//                tempList.add(s); // keep other doctors' schedules
//            } else {
//                removed = true; // found at least one to remove
//            }
//        }
//
//        // Clear original list
//        scheduleList.clear();
//
//        // Copy back the schedules that we kept
//        for (int i = 0; i < tempList.size(); i++) {
//            scheduleList.add(tempList.get(i));
//        }
//
//        return removed;
//    }
// Sample schedules
    public static void addSampleSchedules() {
        addSchedule(new Schedule("S001", "D001", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S002", "D001", DayOfWeek.THURSDAY,
                LocalTime.of(0, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S003", "D003", DayOfWeek.THURSDAY,
                LocalTime.of(0, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S004", "D002", DayOfWeek.TUESDAY,
                LocalTime.of(13, 0), LocalTime.of(17, 0)));
        addSchedule(new Schedule("S005", "D001", DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 0), LocalTime.of(23, 45)));
        addSchedule(new Schedule("S006", "D002", DayOfWeek.FRIDAY,
                LocalTime.of(9, 0), LocalTime.of(23, 45)));
        addSchedule(new Schedule("S007", "D001", DayOfWeek.MONDAY,
                LocalTime.of(9, 30), LocalTime.of(12, 30)));
        addSchedule(new Schedule("S008", "D005", DayOfWeek.SUNDAY,
                LocalTime.of(9, 30), LocalTime.of(23, 45)));
        addSchedule(new Schedule("S009", "D004", DayOfWeek.SUNDAY,
                LocalTime.of(9, 30), LocalTime.of(23, 45)));
//        addSchedule(new Schedule("S010", "D004", DayOfWeek.SATURDAY,
//                LocalTime.of(3, 30), LocalTime.of(23, 45)));
        addSchedule(new Schedule("S020", "D005", DayOfWeek.FRIDAY,
                LocalTime.of(9, 0), LocalTime.of(23, 45)));
    }

}

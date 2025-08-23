/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import Entity.Doctor;
import Entity.Schedule;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

/**
 *
 * @author ACER
 */
public class ScheduleManagement {

    private static MyList<Schedule> scheduleList = new DynamicList<>();

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
        MyList<Schedule> schedules = getAllSchedules();

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
    public static MyList<Schedule> getAllSchedules() {
        return scheduleList;
    }

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
    public static MyList<Schedule> findSchedulesByDoctorId(String doctorID) {
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

    // Find schedules (with doctor info) available on a given day
    public static DynamicList<Schedule> findSchedulesByDay(DayOfWeek day) {
        DynamicList<Schedule> availableSchedules = new DynamicList<>();

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule s = scheduleList.get(i);
            if (s.getDayOfWeek() == day) {
                availableSchedules.add(s);
            }
        }
        return availableSchedules;
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
    public static int countWorkingDaysInMonth(String doctorID, YearMonth month) {
        MyList<Schedule> schedules = getAllSchedules();
        LocalDate monthStart = month.atDay(1);
        LocalDate monthEnd = month.atEndOfMonth();

        // use DynamicList<LocalDate> instead of Set
        DynamicList<LocalDate> uniqueDates = new DynamicList<>();

        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            if (s.getDoctorID().equals(doctorID)) {
                DayOfWeek scheduleDay = s.getDayOfWeek();

                // find all matching days in this month
                LocalDate d = monthStart.with(TemporalAdjusters.nextOrSame(scheduleDay));
                while (!d.isAfter(monthEnd)) {
                    // avoid duplicates manually
                    boolean exists = false;
                    for (int j = 0; j < uniqueDates.size(); j++) {
                        if (uniqueDates.get(j).equals(d)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        uniqueDates.add(d);
                    }

                    d = d.plusWeeks(1);
                }
            }
        }

        return uniqueDates.size();
    }

// Sample schedules
    public static void addSampleSchedules() {
        addSchedule(new Schedule("S001", "D001", DayOfWeek.MONDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 0)));
        addSchedule(new Schedule("S002", "D002", DayOfWeek.MONDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 0)));
        addSchedule(new Schedule("S003", "D003", DayOfWeek.MONDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 0)));
        addSchedule(new Schedule("S004", "D004", DayOfWeek.MONDAY,
                LocalTime.of(13, 0), LocalTime.of(18, 0)));
        addSchedule(new Schedule("S005", "D005", DayOfWeek.MONDAY,
                LocalTime.of(13, 0), LocalTime.of(18, 0)));
        addSchedule(new Schedule("S006", "D001", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S007", "D003", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S008", "D005", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 0)));
        addSchedule(new Schedule("S009", "D004", DayOfWeek.TUESDAY,
                LocalTime.of(13, 0), LocalTime.of(15, 0)));
        addSchedule(new Schedule("S010", "D005", DayOfWeek.TUESDAY,
                LocalTime.of(15, 0), LocalTime.of(18, 0)));
        addSchedule(new Schedule("S011", "D005", DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S012", "D003", DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S013", "D001", DayOfWeek.THURSDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        addSchedule(new Schedule("S014", "D001", DayOfWeek.THURSDAY,
                LocalTime.of(13, 0), LocalTime.of(18, 0)));
        addSchedule(new Schedule("S020", "D002", DayOfWeek.FRIDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 30)));
    }

}

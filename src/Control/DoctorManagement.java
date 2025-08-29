/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import DAO.DoctorWorkSummary;
import Entity.Doctor;
import Entity.Schedule;
import Utility.UtilityClass;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

/**
 *
 * @author ACER
 */
public class DoctorManagement {

    // list to store patient details
    private static MyList<Doctor> doctorList = new DynamicList<>();

    // Constant
    public static final String DATE_FORMAT = UtilityClass.DATE_FORMAT;

    //Register as new doctor
    public static boolean add(Doctor d) {

        if (d != null) {
            doctorList.add(d);
            return true;
        }

        return false;
    }
    
    public static String generateNextDoctorId() {
        int maxId = 0;
        MyList<Doctor> doctors = getAllDoctors();

        for (int i = 0; i < doctors.size(); i++) {
            String id = doctors.get(i).getDoctorID().trim().toUpperCase();

            if (id.startsWith("D")) {
                try {
                    int num = Integer.parseInt(id.substring(1)); // remove 'D'
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                    // ignore invalid formats
                }
            }
        }

        int nextId = maxId + 1;
        return String.format("D%03d", nextId); // e.g. D004
    }
    
    public static boolean removeDoctorById(String doctorID) {
        // First, remove doctor
        boolean doctorRemoved = doctorList.removeIf(d -> d.getDoctorID().equalsIgnoreCase(doctorID.trim()));

        if (doctorRemoved) {
            // Cascade delete schedules of this doctor

            ScheduleManagement.removeScheduleByDoctorId(doctorID);
            LeaveManagement.removeLeaveByDoctorId(doctorID);
        }

        return doctorRemoved;
    }


    //Read
    public static MyList<Doctor> getAllDoctors() {
        return doctorList;
    }

    public static boolean editDoctorDetails(String doctorID, String name, String contactNumber,
            String email, String qualifications) {
        Doctor doctor = findDoctorById(doctorID);
        if (doctor != null) {
            doctor.setName(name);
            doctor.setContactNumber(contactNumber);
            doctor.setEmail(email);
            doctor.setQualification(qualifications);
            // Update working status for all doctors
            for (int i = 0; i < doctorList.size(); i++) {
                updateWorkingStatus(doctorList.get(i));
            }
            return true;
        }
        return false;
    }

    //find doctor
    public static Doctor findDoctorById(String doctorID) {
        return doctorList.findFirst(d -> d.getDoctorID().equals(doctorID));
    }

//    get number of doctor
    public static int getDoctorCount() {
        return doctorList.size();
    }
    
     // ✅ Method to get free doctor count
    public static int getDoctorCountFree() {
        return getFreeDoctors().size();
    }

    public static DynamicList<Doctor> getFreeDoctors() {
        DynamicList<Doctor> freeDoctors = new DynamicList<>();

        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            if (doctor.getWorkingStatus().equals(UtilityClass.statusFree)) {
                freeDoctors.add(doctor);
            }
        }

        return freeDoctors;
    }

    public static boolean areAllDoctorsBusy() {
        // If list is empty, we can treat it as "not all busy"
        if (doctorList.isEmpty()) {
            return false;
        }

        DynamicList<Doctor> freeDoctors = getFreeDoctors();

        return freeDoctors.isEmpty();
    }

    public static void updateWorkingStatus(Doctor doctor) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        DayOfWeek currentDay = today.getDayOfWeek();

        // 1️⃣ Check if doctor is on leave today
        if (LeaveManagement.isDoctorOnLeave(doctor.getDoctorID(), today)) {
            doctor.setWorkingStatus(UtilityClass.statusOnLeave);  // Or a constant in UtilityClass
            return; // No need to check schedule
        }

        // 2️⃣ Otherwise, check if doctor is working now
        boolean isWorkingNow = false;

        MyList<Schedule> schedules = ScheduleManagement.findSchedulesByDoctorId(doctor.getDoctorID());
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            if (s.getDayOfWeek().equals(currentDay)
                    && !now.isBefore(s.getStartTime())
                    && !now.isAfter(s.getEndTime())) {
                isWorkingNow = true;
                break;
            }
        }

        // 3️⃣ Set status
        if (isWorkingNow) {
            doctor.setWorkingStatus(UtilityClass.statusFree);  // Available for appointment
        } else {
            doctor.setWorkingStatus(UtilityClass.workingStatusOff);
        }
    }
    
     //help to generate most hardworking doctor report
    // ================= Helper ==================
    public static MyList<DoctorWorkSummary> calculateDoctorWorkingHours(YearMonth month) {
        MyList<Doctor> doctors = DoctorManagement.getAllDoctors();
        MyList<DoctorWorkSummary> summaries = new DynamicList<>();

        // get all schedules for all doctors once
        MyList<Schedule> allSchedules = ScheduleManagement.getAllSchedules();

        int year = month.getYear();
        int daysInMonth = month.lengthOfMonth();

        for (int i = 0; i < doctors.size(); i++) {
            Doctor doc = doctors.get(i);
            String docId = doc.getDoctorID();

            // collect schedules for this doctor
            MyList<Schedule> doctorSchedules = new DynamicList<>();
            for (int j = 0; j < allSchedules.size(); j++) {
                Schedule sch = allSchedules.get(j);
                if (sch.getDoctorID().equals(docId)) {
                    doctorSchedules.add(sch);
                }
            }

            // collect leave days for this doctor
            MyList<LocalDate> leaveDays = LeaveManagement.getLeaveDaysForDoctorInMonth(docId, month);

            int totalMinutes = 0;

            // loop all days of the month
            for (int day = 1; day <= daysInMonth; day++) {
                LocalDate current = LocalDate.of(year, month.getMonthValue(), day);

                // skip if on leave
                boolean isLeave = false;
                for (int k = 0; k < leaveDays.size(); k++) {
                    if (leaveDays.get(k).equals(current)) {
                        isLeave = true;
                        break;
                    }
                }
                if (isLeave) {
                    continue;
                }

                // match schedule for this day-of-week
                DayOfWeek dow = current.getDayOfWeek();
                for (int k = 0; k < doctorSchedules.size(); k++) {
                    Schedule sch = doctorSchedules.get(k);
                    if (sch.getDayOfWeek().equals(dow)) {
                        // add working time
                        int minutes = (int) Duration.between(sch.getStartTime(), sch.getEndTime()).toMinutes();
                        totalMinutes += minutes;
                    }
                }
            }

            int totalHours = totalMinutes / 60;

            summaries.add(new DoctorWorkSummary(
                    docId,
                    doc.getName(),
                    totalHours
            ));
        }

        return summaries;
    }

//help to generate most hardworking doctor report

}

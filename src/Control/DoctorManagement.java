/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import Entity.Doctor;
import Entity.Schedule;
import Utility.UtilityClass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class DoctorManagement {

    // list to store patient details
    private static MyList<Doctor> doctorList = new DynamicList<>();

    // Constant
    public static final String DATE_FORMAT = "dd/MM/yyyy";

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


    public static void addSampleDoctor() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Doctor d1 = new Doctor("D001", "Lee Wee Teck", sdf.parse("01/01/1990"), 'M',
                    "0123456789", "leewt@example.com", "Bachelor of Medicine, TARUMT", UtilityClass.statusFree);

            Doctor d2 = new Doctor("D002", "Lee Chong Wei", sdf.parse("02/01/1985"), 'M',
                    "0123456780", "chongwei@example.com", "Bachelor of Surgery, UTAR", UtilityClass.statusFree);

            Doctor d3 = new Doctor("D003", "Aaron Chia Teng Feng", sdf.parse("15/11/1997"), 'M',
                    "0123666789", "aaron@example.com", "Bachelor of Medicine, TARUMT", UtilityClass.statusFree);

            Doctor d4 = new Doctor("D004", "Soh Wooi Yik", sdf.parse("27/03/1998"), 'M',
                    "0123666789", "wooiyik@example.com", "Bachelor of Medicine, SUNWAY", UtilityClass.statusConsulting);

            Doctor d5 = new Doctor("D005", "Lee Zii Jia", sdf.parse("05/03/1998"), 'M',
                    "0123666789", "lzj@example.com", "Bachelor of Medicine, University of Melaya", UtilityClass.workingStatusOff);

            add(d1);
            add(d2);
            add(d3);
            add(d4);
            add(d5);
//            System.out.println("Doctors loaded: " + doctorList.size()); // DEBUG

            // 🔹 Once doctors are added, update each doctor's working status
            for (int i = 0; i < doctorList.size(); i++) {
                updateWorkingStatus(doctorList.get(i));
            }

        } catch (ParseException e) {
            System.out.println("Error parsing date in sample data.");
        }
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

    //replace
    public static boolean replaceDoctor(
            String doctorID,
            String name,
            Date dateOfBirth,
            char gender,
            String contactNumber,
            String email,
            String qualification,
            String workingStatus) {

        int doctorIndex = -1;
        Doctor oldDoctor = null;

        // Find the doctor index
        for (int i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getDoctorID().equals(doctorID)) {
                doctorIndex = i;
                oldDoctor = doctorList.get(i);
                break;
            }
        }

        if (doctorIndex == -1) {
            return false; // doctor not found
        }

        // Create a new Doctor object with updated details
        Doctor updatedDoctor = new Doctor(
                doctorID,
                name,
                dateOfBirth,
                gender,
                contactNumber,
                email,
                qualification,
                workingStatus
        );

        // Replace the old doctor with the new one
        doctorList.replace(doctorIndex, updatedDoctor);

        // Update working status for all doctors
        for (int i = 0; i < doctorList.size(); i++) {
            updateWorkingStatus(doctorList.get(i));
        }

        return true;
    }

    //find doctor
    public static Doctor findDoctorById(String doctorID) {
        return doctorList.findFirst(d -> d.getDoctorID().equals(doctorID));
    }

//    get number of doctor
    public static int getDoctorCount() {
        return doctorList.size();
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

}

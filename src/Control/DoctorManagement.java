/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
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
    private static DynamicList<Doctor> doctorList = new DynamicList<>();

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
    
    public static void addSampleDoctor() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Doctor d1 = new Doctor("D001", "Lee Wee Teck", sdf.parse("01/01/1990"), 'M',
                    "0123456789", "leewt@example.com", UtilityClass.statusFree);
            
            Doctor d2 = new Doctor("D002", "Lee Chong Wei", sdf.parse("02/01/1985"), 'M',
                    "0123456780", "chongwei@example.com", UtilityClass.statusFree);
             
            Doctor d3 = new Doctor("D003", "Aaron Chia Teng Feng", sdf.parse("15/11/1997"), 'M',
                    "0123666789", "aaron@example.com", UtilityClass.statusFree);
            
            Doctor d4 = new Doctor("D004", "Soh Wooi Yik", sdf.parse("27/03/1998"), 'M',
                    "0123666789", "wooiyik@example.com", UtilityClass.statusConsulting);
            
            Doctor d5 = new Doctor("D005", "Lee Zii Jia", sdf.parse("05/03/1998"), 'M',
                    "0123666789", "lzj@example.com", UtilityClass.workingStatusOff);

            add(d1);
            add(d2);
            add(d3);
            add(d4);
            add(d5);
            System.out.println("Doctors loaded: " + doctorList.size()); // DEBUG
            
            // 🔹 Once doctors are added, update each doctor's working status
            for (int i = 0; i < doctorList.size(); i++) {
                updateWorkingStatus(doctorList.get(i));
            }


        } catch (ParseException e) {
            System.out.println("Error parsing date in sample data.");
        }
    }

    //remove
    public static boolean removeDoctorById(String doctorID) {
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor d = doctorList.get(i);
            if (d.getDoctorID().equals(doctorID)) {
                doctorList.remove(i);
                return true;
            }
        }
        return false;
    }

    //Read
    public static DynamicList getAllDoctors() {
        return doctorList;
    }

    //update
    public static boolean updateDoctorDetails(String doctorID, String name, String contactNumber, String email) {
        Doctor doctor = findDoctorById(doctorID);
        if (doctor != null) {
            doctor.setName(name);
            doctor.setContactNumber(contactNumber);
            doctor.setEmail(email);
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

        // Check if every doctor has status != statusFree
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            if (doctor.getWorkingStatus().equals(UtilityClass.statusFree)) {
                return false; // Found at least one free doctor
            }
        }
        return true; // All doctors are busy
    }
    
    public static void updateWorkingStatus(Doctor doctor) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        DayOfWeek currentDay = today.getDayOfWeek();

        boolean isWorkingNow = false;

        DynamicList<Schedule> schedules = ScheduleManagement.findSchedulesByDoctorId(doctor.getDoctorID());

        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            if (s.getDayOfWeek().equals(currentDay)
                    && !now.isBefore(s.getStartTime())
                    && !now.isAfter(s.getEndTime())) {
                isWorkingNow = true;
                break;
            }
        }

        if (isWorkingNow) {
            doctor.setWorkingStatus(UtilityClass.statusFree);  // free means available for appointment
        } else {
            doctor.setWorkingStatus(UtilityClass.workingStatusOff);
        }
    }
    
    

    
    
}

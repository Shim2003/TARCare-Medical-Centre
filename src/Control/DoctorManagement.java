/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.Doctor;
import Utility.UtilityClass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

            add(d1);
            add(d2);
            add(d3);
            System.out.println("Doctors loaded: " + doctorList.size()); // DEBUG


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
    public static void printAllDoctors() {

        Doctor[] doctors = doctorList.toArray();

        for (Doctor d : doctors) {
            System.out.println(d); // You may override toString() in Doctor for clean output
        }
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

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.Doctor;

/**
 *
 * @author ACER
 */
public class DoctorManagement {

    // list to store patient details
    private static DynamicList<Doctor> doctorList = new DynamicList<>();

    // Constant
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    //Add doctor
    public static void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
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

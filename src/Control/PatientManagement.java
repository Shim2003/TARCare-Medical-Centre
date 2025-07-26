/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.util.InputMismatchException;
import java.util.Scanner;
import ADT.DynamicList;
import Entity.Patient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import Utility.UtilityClass;

/**
 *
 * @author Lee Wei Hao
 */
public class PatientManagement {

    // list to store patient details
    private static DynamicList<Patient> patientList = new DynamicList<>();

    // Constant
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // Declare global scanner
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //Register as new patient
    public static boolean add(Patient p) {

        if (p != null) {
            patientList.add(p);
            return true;
        }

        return false;

    }

    public static boolean update(Patient patient, int choice, String newValue) {

        if (patient == null) {
            return false;
        }

        switch (choice) {
            case 1 ->
                patient.setFullName(newValue);
            case 2 ->
                patient.setContactNumber(newValue);
            case 3 ->
                patient.setEmail(newValue);
            case 4 ->
                patient.setAddress(newValue);
            case 5 ->
                patient.setEmergencyContact(newValue);
            default -> {
                return false;
            }
        }
        return true;
    }

    public static Patient findPatientByIdentity(String identityNumber) {
        return patientList.findFirst(p -> p.getIdentityNumber().equalsIgnoreCase(identityNumber));
    }

    public static void remove(char confirm, Patient p) {
        int index = patientList.indexOf(p);  // get index of patient
        if (index >= 0) {
            patientList.remove(index);  // remove by index
            System.out.println("Patient removed successfully.");
        } else {
            System.out.println("Error: patient not found in list.");
        }
    }

    public static DynamicList<Patient> getPatientList() {

        return patientList;
    }
    
    public static void clearAll(){
        patientList.clear();
    }
}

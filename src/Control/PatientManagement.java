/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.Patient;
import Utility.UtilityClass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lee Wei Hao
 */
public class PatientManagement {

    // list to store patient details
    private static DynamicList<Patient> patientList = new DynamicList<>();

    public static void addSamplePatients() {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        try {
            Patient p1 = new Patient("Alice Tan", "A123456789", sdf.parse("01/01/1990"), 'F',
                    "0123456789", "alice@example.com", "123 Jalan ABC, Kuala Lumpur", "01122334455", new Date());

            Patient p2 = new Patient("Bob Lim", "B987654321", sdf.parse("15/05/1985"), 'M',
                    "0198765432", "bob@example.com", "456 Jalan XYZ, Penang", "01233445566", new Date());

            Patient p3 = new Patient("Charlie Wong", "C111222333", sdf.parse("20/12/1975"), 'M',
                    "0171122334", "charlie@example.com", "789 Jalan DEF, Johor", "01344556677", new Date());

            add(p1);
            add(p2);
            add(p3);

        } catch (ParseException e) {
            System.out.println("Error parsing date in sample data.");
        }
    }

    public static boolean add(Patient p) {

        if (p != null) {
            patientList.add(p);
            return true;
        }

        return false;

    }

    public static boolean update(String patientId, int choice, String newValue) {

        int index = patientList.findIndex(p -> p.getPatientID().equals(patientId));

        if (index == -1) {
            return false;
        }

        Patient patientToUpdate = patientList.get(index).clone();

        switch (choice) {
            case 1:
                patientToUpdate.setFullName(newValue);
                break;
            case 2:
                patientToUpdate.setContactNumber(newValue);
                break;
            case 3:
                patientToUpdate.setEmail(newValue);
                break;
            case 4:
                patientToUpdate.setAddress(newValue);
                break;
            case 5:
                patientToUpdate.setEmergencyContact(newValue);
                break;
            default: {
                return false;
            }
        }

        patientList.replace(index, patientToUpdate);
        return true;
    }

    public static Patient findPatientById(String patientId) {
        return patientList.findFirst(p -> p.getPatientID().equalsIgnoreCase(patientId));
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

    public static void clearAll() {
        patientList.clear();
    }

    public static boolean isPatientExists(String patientId) {
        return patientList.anyMatch(p -> p.getPatientID().equalsIgnoreCase(patientId));
    }

    public static String getPatientNameById(String patientId) {
        Patient p = patientList.findFirst(x -> x.getPatientID().equals(patientId));
        return (p != null) ? p.getFullName() : "Unknown";
    }

}

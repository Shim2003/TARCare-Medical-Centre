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
import java.util.Calendar;
import java.util.Comparator;
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

            Patient p4 = new Patient("Daphne Lee", "D555666777", sdf.parse("10/03/1992"), 'F',
                    "0169988776", "daphne@example.com", "12 Jalan Hijau, Melaka", "01455667788", new Date());

            Patient p5 = new Patient("Ethan Tan", "E999888777", sdf.parse("05/07/1988"), 'M',
                    "0183344556", "ethan@example.com", "23 Jalan Bunga, Selangor", "01566778899", new Date());

            Patient p6 = new Patient("Fiona Ng", "F112233445", sdf.parse("22/11/1995"), 'F',
                    "0127766554", "fiona@example.com", "34 Jalan Mawar, Perak", "01377889900", new Date());

            Patient p7 = new Patient("George Ho", "G556677889", sdf.parse("09/09/1980"), 'M',
                    "0174455667", "george@example.com", "45 Jalan Cempaka, Kedah", "01288990011", new Date());

            Patient p8 = new Patient("Hannah Lim", "H223344556", sdf.parse("17/02/1993"), 'F',
                    "0192233445", "hannah@example.com", "56 Jalan Kenanga, Terengganu", "01433445566", new Date());

            Patient p9 = new Patient("Ivan Chong", "I667788990", sdf.parse("28/06/1978"), 'M',
                    "0165566778", "ivan@example.com", "67 Jalan Teratai, Sabah", "01344556688", new Date());

            Patient p10 = new Patient("Jasmine Tan", "J334455667", sdf.parse("14/12/1999"), 'F',
                    "0129988775", "jasmine@example.com", "78 Jalan Dahlia, Sarawak", "01255667788", new Date());

            add(p1);
            add(p2);
            add(p3);
            add(p4);
            add(p5);
            add(p6);
            add(p7);
            add(p8);
            add(p9);
            add(p10);

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

    public static DynamicList.ListStatistics<Patient> getAgeStatistics() {
        return patientList.getStatistics(patient -> calculateAge(patient));
    }

    public static int calculateAge(Patient patient) {
        Calendar today = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(patient.getDateOfBirth());
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    public static DynamicList<Patient> getPatientsByAgeGroup(String ageGroup) {
        switch (ageGroup.toLowerCase()) {
            case "pediatric":
                return patientList.findAll(p -> calculateAge(p) < 18);
            case "adult":
                return patientList.findAll(p -> calculateAge(p) >= 18 && calculateAge(p) < 65);
            case "geriatric":
                return patientList.findAll(p -> calculateAge(p) >= 65);
            default:
                return new DynamicList<>();
        }
    }

    public static DynamicList<Patient> getOldestPatients(int n) {
        DynamicList<Patient> sortedByAge = getPatientsSortedBy("age");
        DynamicList<Patient> result = new DynamicList<>();

        int count = Math.min(n, sortedByAge.size());
        for (int i = sortedByAge.size() - count; i < sortedByAge.size(); i++) {
            result.add(sortedByAge.get(i));
        }
        return result;
    }

    public static DynamicList<Patient> getYoungestPatients(int n) {
        DynamicList<Patient> sortedByAge = getPatientsSortedBy("age");
        DynamicList<Patient> result = new DynamicList<>();

        int count = Math.min(n, sortedByAge.size());
        for (int i = 0; i < count; i++) {
            result.add(sortedByAge.get(i));
        }
        return result;
    }

    public static DynamicList<Patient> getPatientsSortedBy(String criteria) {
        DynamicList<Patient> sortedList = patientList.clone();

        switch (criteria.toLowerCase()) {
            case "name":
                sortedList.quickSort(Comparator.comparing(Patient::getFullName));
                break;
            case "age":
                sortedList.quickSort(Comparator.comparingInt(PatientManagement::calculateAge));
                break;
            case "registration":
                sortedList.quickSort(Comparator.comparing(Patient::getRegistrationDate));
                break;
            case "registration_desc":
                sortedList.quickSort(Comparator.comparing(Patient::getRegistrationDate).reversed());
                break;
            case "gender":
                sortedList.quickSort(Comparator.comparing(Patient::getGender));
                break;
            default:
                // Return unsorted if criteria not recognized
                break;
        }
        return sortedList;
    }
}

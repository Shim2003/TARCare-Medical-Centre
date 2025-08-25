package Control;

import ADT.DynamicList;
import ADT.MyList;
import DAO.AppointmentInfo;
import Entity.Appointment;
import Entity.Patient;
import Utility.UtilityClass;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Comparator;

/**
 * PatientManagement - Control layer for patient operations Contains only
 * business logic and data operations, no display logic
 *
 * @author Lee Wei Hao
 */
public class PatientManagement {

    // list to store patient details
    private static DynamicList<Patient> patientList = new DynamicList<>();

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

    public static boolean remove(Patient p) {
        int index = patientList.indexOf(p);
        if (index >= 0) {
            patientList.remove(index);
            return true;
        }
        return false;
    }

    public static MyList<Patient> getPatientList() {
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

    public static MyList<Patient> getPatientsByAgeGroup(String ageGroup) {
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

    public static MyList<Patient> getOldestPatients(int n) {
        MyList<Patient> sortedByAge = getPatientsSortedBy("age");
        MyList<Patient> result = new DynamicList<>();

        int count = Math.min(n, sortedByAge.size());
        for (int i = sortedByAge.size() - count; i < sortedByAge.size(); i++) {
            result.add(sortedByAge.get(i));
        }
        return result;
    }

    public static MyList<Patient> getYoungestPatients(int n) {
        MyList<Patient> sortedByAge = getPatientsSortedBy("age");
        MyList<Patient> result = new DynamicList<>();

        int count = Math.min(n, sortedByAge.size());
        for (int i = 0; i < count; i++) {
            result.add(sortedByAge.get(i));
        }
        return result;
    }

    public static MyList<Patient> getPatientsSortedBy(String criteria) {
        MyList<Patient> sortedList = patientList.clone();

        switch (criteria.toLowerCase()) {
            case "name":
                UtilityClass.quickSort(sortedList, Comparator.comparing(Patient::getFullName));
                break;
            case "id":
                UtilityClass.quickSort(sortedList, Comparator.comparing(Patient::getPatientID));
                break;
            case "age":
                UtilityClass.quickSort(sortedList, Comparator.comparingInt(PatientManagement::calculateAge));
                break;
            case "age_desc":
                UtilityClass.quickSort(sortedList, Comparator.comparingInt(PatientManagement::calculateAge).reversed());
                break;
            case "registration":
                UtilityClass.quickSort(sortedList, Comparator.comparing(Patient::getRegistrationDate));
                break;
            case "registration_desc":
                UtilityClass.quickSort(sortedList, Comparator.comparing(Patient::getRegistrationDate).reversed());
                break;
            case "gender":
                UtilityClass.quickSort(sortedList, Comparator.comparing(Patient::getGender));
                break;
            default:
                // Return unsorted if criteria not recognized
                break;
        }
        return sortedList;
    }

    public static MyList<Patient> getMalePatients() {
        return patientList.filter(patient
                -> patient.getGender() == 'M' || patient.getGender() == 'm');
    }

    public static MyList<Patient> getFemalePatients() {
        return patientList.filter(patient
                -> patient.getGender() == 'F' || patient.getGender() == 'f');
    }

    public static MyList<Patient> getPatientsByGender(char gender) {
        return patientList.filter(patient
                -> Character.toLowerCase(patient.getGender()) == Character.toLowerCase(gender));
    }

    public static GenderStatistics getGenderStatistics() {
        MyList<Patient> malePatients = getMalePatients();
        MyList<Patient> femalePatients = getFemalePatients();
        int totalPatients = patientList.size();

        return new GenderStatistics(
                malePatients.size(),
                femalePatients.size(),
                totalPatients
        );
    }

    public static AppointmentInfo checkPatientAppointments(String patientId) {
        // Get all appointment list 
        MyList<Appointment> appointmentList = AppointmentManagement.getScheduledAppointments();

        MyList<Appointment> patientAppointments = appointmentList.filter(
                appointment -> appointment.getPatientId().equalsIgnoreCase(patientId)
        );

        if (patientAppointments.isEmpty()) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        MyList<Appointment> upcomingAppointments = patientAppointments.filter(
                appointment -> appointment.getAppointmentTime().isAfter(now)
        );

        if (upcomingAppointments.isEmpty()) {
            return null;
        }

        UtilityClass.quickSort(upcomingAppointments, Comparator.comparing(Appointment::getAppointmentTime));

        Appointment nextAppointment = upcomingAppointments.getFirst();

        Duration duration = Duration.between(now, nextAppointment.getAppointmentTime());
        int totalHours = (int) duration.toHours();
        int days = totalHours / 24;
        int hours = totalHours % 24;

        // Convert to AppointmentInfo if needed
        return new AppointmentInfo(nextAppointment, days, hours);
    }

    /**
     * Inner class for gender statistics data
     */
    public static class GenderStatistics {

        public final int maleCount;
        public final int femaleCount;
        public final int totalCount;
        public final double malePercentage;
        public final double femalePercentage;

        public GenderStatistics(int maleCount, int femaleCount, int totalCount) {
            this.maleCount = maleCount;
            this.femaleCount = femaleCount;
            this.totalCount = totalCount;
            this.malePercentage = totalCount > 0 ? (double) maleCount / totalCount * 100 : 0;
            this.femalePercentage = totalCount > 0 ? (double) femaleCount / totalCount * 100 : 0;
        }

        @Override
        public String toString() {
            return String.format("Gender Statistics: Male=%d (%.1f%%), Female=%d (%.1f%%), Total=%d",
                    maleCount, malePercentage, femaleCount, femalePercentage, totalCount);
        }
    }
}

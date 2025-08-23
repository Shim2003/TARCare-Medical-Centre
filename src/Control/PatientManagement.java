package Control;

import ADT.DynamicList;
import ADT.MyList;
import DAO.AppointmentInfo;
import Entity.Appointment;
import Entity.Patient;
import Utility.UtilityClass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private static AppointmentManagement appMan = new AppointmentManagement();
    
    public static void addSamplePatients() {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        try {
            Patient p1 = new Patient("Alice Tan", "A123456789", sdf.parse("01/01/1990"), 'F', "0123456789", "alice@example.com", "123 Jalan ABC, Kuala Lumpur", "01122334455", new Date());
            Patient p2 = new Patient("Bob Lim", "B987654321", sdf.parse("15/05/1985"), 'M', "0198765432", "bob@example.com", "456 Jalan XYZ, Penang", "01233445566", new Date());
            Patient p3 = new Patient("Charlie Wong", "C111222333", sdf.parse("20/12/1975"), 'M', "0171122334", "charlie@example.com", "789 Jalan DEF, Johor", "01344556677", new Date());
            Patient p4 = new Patient("Daphne Lee", "D555666777", sdf.parse("10/03/1992"), 'F', "0169988776", "daphne@example.com", "12 Jalan Hijau, Melaka", "01455667788", new Date());
            Patient p5 = new Patient("Ethan Tan", "E999888777", sdf.parse("05/07/1988"), 'M', "0183344556", "ethan@example.com", "23 Jalan Bunga, Selangor", "01566778899", new Date());
            Patient p6 = new Patient("Fiona Ng", "F112233445", sdf.parse("22/11/1995"), 'F', "0127766554", "fiona@example.com", "34 Jalan Mawar, Perak", "01377889900", new Date());
            Patient p7 = new Patient("George Ho", "G556677889", sdf.parse("09/09/1980"), 'M', "0174455667", "george@example.com", "45 Jalan Cempaka, Kedah", "01288990011", new Date());
            Patient p8 = new Patient("Hannah Lim", "H223344556", sdf.parse("17/02/1993"), 'F', "0192233445", "hannah@example.com", "56 Jalan Kenanga, Terengganu", "01433445566", new Date());
            Patient p9 = new Patient("Ivan Chong", "I667788990", sdf.parse("28/06/1978"), 'M', "0165566778", "ivan@example.com", "67 Jalan Teratai, Sabah", "01344556688", new Date());
            Patient p10 = new Patient("Jasmine Tan", "J334455667", sdf.parse("14/12/1999"), 'F', "0129988775", "jasmine@example.com", "78 Jalan Dahlia, Sarawak", "01255667788", new Date());
            Patient p11 = new Patient("Kevin Ong", "K445566778", sdf.parse("03/08/1987"), 'M', "0156677889", "kevin@example.com", "89 Jalan Melati, Negeri Sembilan", "01366778899", new Date());
            Patient p12 = new Patient("Linda Goh", "L778899001", sdf.parse("12/04/1991"), 'F', "0134455667", "linda@example.com", "90 Jalan Orkid, Pahang", "01477889900", new Date());
            Patient p13 = new Patient("Marcus Teo", "M123789456", sdf.parse("25/09/1983"), 'M', "0187766554", "marcus@example.com", "101 Jalan Anggrek, Kelantan", "01588990011", new Date());
            Patient p14 = new Patient("Nina Choo", "N456123789", sdf.parse("07/01/1996"), 'F', "0165544332", "nina@example.com", "112 Jalan Bakawali, Perlis", "01699001122", new Date());
            Patient p15 = new Patient("Oscar Yap", "O789456123", sdf.parse("18/11/1979"), 'M', "0198877665", "oscar@example.com", "123 Jalan Flamboyan, Putrajaya", "01700112233", new Date());
            Patient p16 = new Patient("Priya Sharma", "P321654987", sdf.parse("29/06/1994"), 'F', "0177665544", "priya@example.com", "134 Jalan Bougainvillea, Labuan", "01511223344", new Date());
            Patient p17 = new Patient("Quincy Lee", "Q654987321", sdf.parse("13/03/1986"), 'M', "0144332211", "quincy@example.com", "145 Jalan Hibiskus, Kuala Lumpur", "01622334455", new Date());
            Patient p18 = new Patient("Rachel Koh", "R987321654", sdf.parse("02/10/1998"), 'F', "0166554433", "rachel@example.com", "156 Jalan Pokok Kelapa, Penang", "01733445566", new Date());
            Patient p19 = new Patient("Samuel Chin", "S135792468", sdf.parse("21/07/1981"), 'M', "0155443322", "samuel@example.com", "167 Jalan Pandan, Johor", "01844556677", new Date());
            Patient p20 = new Patient("Tiffany Woo", "T468135792", sdf.parse("08/12/1997"), 'F', "0122119988", "tiffany@example.com", "178 Jalan Cemara, Melaka", "01955667788", new Date());
            Patient p21 = new Patient("Uncle Ben", "U792468135", sdf.parse("16/05/1974"), 'M', "0199887766", "uncle.ben@example.com", "189 Jalan Pinang, Selangor", "01066778899", new Date());
            Patient p22 = new Patient("Victoria Soo", "V246813579", sdf.parse("04/02/1989"), 'F', "0177889900", "victoria@example.com", "190 Jalan Kelapa Sawit, Perak", "01177889900", new Date());
            Patient p23 = new Patient("William Yong", "W813579246", sdf.parse("23/09/1984"), 'M', "0188990011", "william@example.com", "201 Jalan Durian, Kedah", "01288001122", new Date());
            Patient p24 = new Patient("Xin Yi Chen", "X579246813", sdf.parse("11/06/1993"), 'F', "0155667788", "xinyi@example.com", "212 Jalan Rambutan, Terengganu", "01399112233", new Date());
            Patient p25 = new Patient("Yusuf Rahman", "Y357924681", sdf.parse("30/01/1977"), 'M', "0166778899", "yusuf@example.com", "223 Jalan Mangga, Sabah", "01400223344", new Date());
            Patient p26 = new Patient("Zara Hassan", "Z924681357", sdf.parse("19/08/1995"), 'F', "0133445566", "zara@example.com", "234 Jalan Jambu, Sarawak", "01511334455", new Date());
            Patient p27 = new Patient("Aaron Ooi", "AA111222333", sdf.parse("06/04/1982"), 'M', "0144556677", "aaron@example.com", "245 Jalan Kedondong, Negeri Sembilan", "01622445566", new Date());
            Patient p28 = new Patient("Bella Tan", "BB444555666", sdf.parse("24/10/1990"), 'F', "0177889911", "bella@example.com", "256 Jalan Belimbing, Pahang", "01733556677", new Date());
            Patient p29 = new Patient("Caleb Ng", "CC777888999", sdf.parse("15/07/1988"), 'M', "0188991122", "caleb@example.com", "267 Jalan Betik, Kelantan", "01844667788", new Date());
            Patient p30 = new Patient("Diana Lau", "DD000111222", sdf.parse("09/12/1996"), 'F', "0199112233", "diana@example.com", "278 Jalan Cempedak, Perlis", "01955778899", new Date());

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
            add(p11);
            add(p12);
            add(p13);
            add(p14);
            add(p15);
            add(p16);
            add(p17);
            add(p18);
            add(p19);
            add(p20);
            add(p21);
            add(p22);
            add(p23);
            add(p24);
            add(p25);
            add(p26);
            add(p27);
            add(p28);
            add(p29);
            add(p30);

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
        MyList<Appointment> appointmentList = appMan.getScheduledAppointments();

        MyList<Appointment> patientAppointments = appointmentList.filter(
                appointment -> appointment.getPatientId().equalsIgnoreCase(patientId)
        );

        if (patientAppointments.isEmpty()) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        DynamicList<Appointment> upcomingAppointments = patientAppointments.filter(
                appointment -> appointment.getAppointmentTime().isAfter(now)
        );

        if (upcomingAppointments.isEmpty()) {
            return null;
        }

        upcomingAppointments.quickSort(
                Comparator.comparing(Appointment::getAppointmentTime)
        );

        Appointment nextAppointment = upcomingAppointments.getFirst();

        Duration duration = Duration.between(now, nextAppointment.getAppointmentTime());
        int totalHours = (int) duration.toHours();
        int days = totalHours / 24;
        int hours = totalHours % 24;

        // Convert to AppointmentInfo if needed
        return new AppointmentInfo(nextAppointment, days, hours);

    }

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

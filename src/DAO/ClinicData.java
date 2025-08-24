/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ADT.DynamicList;
import ADT.MyList;
import Control.DiagnosisManagement;
import Control.PatientManagement;
import Control.QueueControl;
import Entity.Consultation;
import Entity.Diagnosis;
import Entity.Patient;
import Entity.QueueEntry;
import Utility.UtilityClass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author user
 */
public class ClinicData {

    public static void run() {
        addSamplePatients();
        addSampleConsultations();
        addSampleQueueData();
    }

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

            PatientManagement.add(p1);
            PatientManagement.add(p2);
            PatientManagement.add(p3);
            PatientManagement.add(p4);
            PatientManagement.add(p5);
            PatientManagement.add(p6);
            PatientManagement.add(p7);
            PatientManagement.add(p8);
            PatientManagement.add(p9);
            PatientManagement.add(p10);
            PatientManagement.add(p11);
            PatientManagement.add(p12);
            PatientManagement.add(p13);
            PatientManagement.add(p14);
            PatientManagement.add(p15);
            PatientManagement.add(p16);
            PatientManagement.add(p17);
            PatientManagement.add(p18);
            PatientManagement.add(p19);
            PatientManagement.add(p20);
            PatientManagement.add(p21);
            PatientManagement.add(p22);
            PatientManagement.add(p23);
            PatientManagement.add(p24);
            PatientManagement.add(p25);
            PatientManagement.add(p26);
            PatientManagement.add(p27);
            PatientManagement.add(p28);
            PatientManagement.add(p29);
            PatientManagement.add(p30);

        } catch (ParseException e) {
            System.out.println("Error parsing date in sample data.");
        }
    }

    public static void addSampleConsultations() {
        try {
            Consultation[] samples = new Consultation[10];

            samples[0] = new Consultation("C001", "P1001", "D001", "Cough");
            samples[0].setStartTime(LocalDateTime.of(2025, 8, 20, 9, 0));
            samples[0].setEndTime(LocalDateTime.of(2025, 8, 20, 9, 30));

            samples[1] = new Consultation("C002", "P1002", "D002", "Fever");
            samples[1].setStartTime(LocalDateTime.of(2025, 8, 20, 10, 0));
            samples[1].setEndTime(LocalDateTime.of(2025, 8, 20, 10, 20));

            samples[2] = new Consultation("C003", "P1003", "D003", "Headache");
            samples[2].setStartTime(LocalDateTime.of(2025, 8, 21, 11, 0));
            samples[2].setEndTime(LocalDateTime.of(2025, 8, 21, 11, 40));

            samples[3] = new Consultation("C004", "P1004", "D004", "Back Pain");
            samples[3].setStartTime(LocalDateTime.of(2025, 8, 21, 14, 0));
            samples[3].setEndTime(LocalDateTime.of(2025, 8, 21, 14, 25));

            samples[4] = new Consultation("C005", "P1005", "D005", "Stomach Ache");
            samples[4].setStartTime(LocalDateTime.of(2025, 8, 22, 9, 15));
            samples[4].setEndTime(LocalDateTime.of(2025, 8, 22, 9, 50));

            samples[5] = new Consultation("C006", "P1006", "D006", "Sore Throat");
            samples[5].setStartTime(LocalDateTime.of(2025, 8, 22, 10, 30));
            samples[5].setEndTime(LocalDateTime.of(2025, 8, 22, 11, 0));

            samples[6] = new Consultation("C007", "P1007", "D007", "Allergy");
            samples[6].setStartTime(LocalDateTime.of(2025, 8, 23, 8, 45));
            samples[6].setEndTime(LocalDateTime.of(2025, 8, 23, 9, 15));

            samples[7] = new Consultation("C008", "P1008", "D008", "Flu");
            samples[7].setStartTime(LocalDateTime.of(2025, 8, 23, 10, 15));
            samples[7].setEndTime(LocalDateTime.of(2025, 8, 23, 10, 50));

            samples[8] = new Consultation("C009", "P1009", "D009", "Fatigue");
            samples[8].setStartTime(LocalDateTime.of(2025, 8, 24, 13, 0));
            samples[8].setEndTime(LocalDateTime.of(2025, 8, 24, 13, 45));

            samples[9] = new Consultation("C010", "P1010", "D010", "Dizziness");
            samples[9].setStartTime(LocalDateTime.of(2025, 8, 24, 14, 15));
            samples[9].setEndTime(LocalDateTime.of(2025, 8, 24, 14, 55));

            for (Consultation c : samples) {
                Consultation.getCompletedConsultations().add(c);
            }

            System.out.println("Added 10 sample consultations with LocalDateTime.");
        } catch (Exception e) {
            System.out.println("Error adding sample consultations: " + e.getMessage());
        }
    }

    // adding the sample diagnosis based on the sample consultations above
    public static void addSampleDiagnosis() {
        try {
            MyList<Diagnosis> diagnosisList = DiagnosisManagement.getDiagnosisList();

            // Adding 10 sample diagnoses
            Diagnosis d1 = new Diagnosis("P001", "D001", new Date(),
                    getSymptoms("D001"), "Common Cold", "Low",
                    "Drink warm fluids and rest for 3 days.",
                    "Patient reported mild cough and nasal congestion.");
            Diagnosis d2 = new Diagnosis("P002", "D002", new Date(),
                    getSymptoms("D002"), "Influenza", "Medium",
                    "Take prescribed antiviral medication and monitor temperature.",
                    "High fever and body aches reported.");
            Diagnosis d3 = new Diagnosis("P003", "D003", new Date(),
                    getSymptoms("D003"), "Migraine", "High",
                    "Avoid bright lights and take prescribed painkillers.",
                    "Severe headache localized to the right side.");
            Diagnosis d4 = new Diagnosis("P004", "D004", new Date(),
                    getSymptoms("D004"), "Muscle Strain", "Medium",
                    "Apply ice packs and attend physical therapy sessions.",
                    "Patient experienced muscle pain after heavy lifting.");
            Diagnosis d5 = new Diagnosis("P005", "D005", new Date(),
                    getSymptoms("D005"), "Gastritis", "Medium",
                    "Avoid spicy foods and eat smaller, frequent meals.",
                    "Patient reported stomach pain after meals.");
            Diagnosis d6 = new Diagnosis("P006", "D006", new Date(),
                    getSymptoms("D006"), "Pharyngitis", "Low",
                    "Complete the full course of antibiotics.",
                    "Sore throat and difficulty swallowing.");
            Diagnosis d7 = new Diagnosis("P007", "D007", new Date(),
                    getSymptoms("D007"), "Allergic Rhinitis", "Low",
                    "Take antihistamines and avoid allergens.",
                    "Patient reported sneezing and itchy eyes.");
            Diagnosis d8 = new Diagnosis("P008", "D008", new Date(),
                    getSymptoms("D008"), "Seasonal Flu", "Medium",
                    "Stay hydrated and rest for 5 days.",
                    "Fever and fatigue reported during flu season.");
            Diagnosis d9 = new Diagnosis("P009", "D009", new Date(),
                    getSymptoms("D009"), "Chronic Fatigue", "High",
                    "Adopt a regular sleep schedule and reduce stress.",
                    "Patient reported persistent fatigue for over a month.");
            Diagnosis d10 = new Diagnosis("P010", "D010", new Date(),
                    getSymptoms("D010"), "Vertigo", "Critical",
                    "Attend vestibular therapy sessions.",
                    "Patient reported dizziness and balance issues.");

            // Adding the diagnoses to the diagnosis list
            diagnosisList.add(d1);
            diagnosisList.add(d2);
            diagnosisList.add(d3);
            diagnosisList.add(d4);
            diagnosisList.add(d5);
            diagnosisList.add(d6);
            diagnosisList.add(d7);
            diagnosisList.add(d8);
            diagnosisList.add(d9);
            diagnosisList.add(d10);

            System.out.println("Added 10 sample diagnoses with auto-generated IDs.");

        } catch (Exception e) {
            System.out.println("Error adding sample diagnoses: " + e.getMessage());
        }
    }

    private static MyList<String> getSymptoms(String diagnosisId) {
        MyList<String> symptoms = new DynamicList<>();

        switch (diagnosisId) {
            case "D001":
                symptoms.add("Cough");
                symptoms.add("Nasal congestion");
                symptoms.add("Mild fever");
            case "D002":
                symptoms.add("High fever");
                symptoms.add("Body aches");
                symptoms.add("Fatigue");
            case "D003":
                symptoms.add("Severe headache");
                symptoms.add("Sensitivity to light");
                symptoms.add("Nausea");
            case "D004":
                symptoms.add("Muscle pain");
                symptoms.add("Stiffness");
                symptoms.add("Weakness");
            case "D005":
                symptoms.add("Stomach pain");
                symptoms.add("Nausea");
                symptoms.add("Bloating");
                break;
            case "D006":
                symptoms.add("Sore throat");
                symptoms.add("Difficulty swallowing");
                symptoms.add("Swollen lymph nodes");
            case "D007":
                symptoms.add("Sneezing");
                symptoms.add("Itchy eyes");
                symptoms.add("Runny nose");
            case "D008":
                symptoms.add("Fever");
                symptoms.add("Chills");
                symptoms.add("Fatigue");
            case "D009":
                symptoms.add("Persistent fatigue");
                symptoms.add("Muscle aches");
                symptoms.add("Memory issues");
            case "D010":
                symptoms.add("Dizziness");
                symptoms.add("Balance issues");
                symptoms.add("Nausea");
            default:
                return new DynamicList<>();
        }
        return symptoms;
    }

    public static void addSampleQueueData() {
        try {
            MyList<Patient> patientList = PatientManagement.getPatientList();

            Calendar cal = Calendar.getInstance();

            cal.setTime(new Date());

            createQueueEntry(patientList.get(0), cal, 8, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(1), cal, 9, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(2), cal, 9, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(3), cal, 10, 20, UtilityClass.statusConsulting);
            createQueueEntry(patientList.get(4), cal, 11, 10, UtilityClass.statusConsulting);
            createQueueEntry(patientList.get(5), cal, 11, 50, UtilityClass.statusWaiting);

            createQueueEntry(patientList.get(6), cal, 14, 15, UtilityClass.statusWaiting);
            createQueueEntry(patientList.get(7), cal, 14, 45, UtilityClass.statusWaiting);
            createQueueEntry(patientList.get(8), cal, 15, 30, UtilityClass.statusWaiting);
            createQueueEntry(patientList.get(9), cal, 16, 0, UtilityClass.statusWaiting);
            createQueueEntry(patientList.get(10), cal, 16, 30, UtilityClass.statusWaiting);
            createQueueEntry(patientList.get(11), cal, 17, 0, UtilityClass.statusWaiting);

            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -1);

            createQueueEntry(patientList.get(12), cal, 8, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(13), cal, 8, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(14), cal, 9, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(15), cal, 9, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(16), cal, 10, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(17), cal, 10, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(18), cal, 11, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(19), cal, 11, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(20), cal, 14, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(21), cal, 14, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(22), cal, 15, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(23), cal, 15, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(24), cal, 16, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(25), cal, 16, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(26), cal, 17, 0, UtilityClass.statusCompleted);

            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -2);

            createQueueEntry(patientList.get(0), cal, 8, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(1), cal, 9, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(2), cal, 9, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(3), cal, 10, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(4), cal, 11, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(5), cal, 14, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(6), cal, 15, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(7), cal, 16, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(8), cal, 16, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(9), cal, 17, 30, UtilityClass.statusCompleted);

            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -3);

            createQueueEntry(patientList.get(10), cal, 8, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(11), cal, 9, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(12), cal, 10, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(13), cal, 11, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(14), cal, 14, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(15), cal, 15, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(16), cal, 16, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(17), cal, 17, 0, UtilityClass.statusCompleted);

            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -4);

            createQueueEntry(patientList.get(18), cal, 8, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(19), cal, 8, 20, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(20), cal, 8, 40, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(21), cal, 9, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(22), cal, 9, 20, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(23), cal, 9, 40, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(24), cal, 10, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(25), cal, 10, 20, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(26), cal, 10, 40, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(27), cal, 11, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(28), cal, 11, 20, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(29), cal, 11, 40, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(0), cal, 14, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(1), cal, 14, 20, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(2), cal, 14, 40, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(3), cal, 15, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(4), cal, 15, 20, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(5), cal, 15, 40, UtilityClass.statusCompleted);

            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -5);

            createQueueEntry(patientList.get(6), cal, 8, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(7), cal, 9, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(8), cal, 10, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(9), cal, 10, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(10), cal, 11, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(11), cal, 14, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(12), cal, 15, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(13), cal, 15, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(14), cal, 16, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(15), cal, 17, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(16), cal, 17, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(17), cal, 18, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(18), cal, 18, 30, UtilityClass.statusCompleted);

            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -6);

            createQueueEntry(patientList.get(19), cal, 8, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(20), cal, 9, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(21), cal, 10, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(22), cal, 11, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(23), cal, 11, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(24), cal, 14, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(25), cal, 15, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(26), cal, 16, 0, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(27), cal, 16, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(28), cal, 17, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(29), cal, 18, 15, UtilityClass.statusCompleted);

        } catch (Exception e) {
            System.out.println("Error adding sample queue data: " + e.getMessage());
        }
    }

    private static void createQueueEntry(Patient patient, Calendar cal, int hour, int minute, String status) {
        QueueEntry queueEntry = new QueueEntry(patient.getPatientID());

        // Set the specific time
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        queueEntry.setCheckInTime(cal.getTime());
        queueEntry.setStatus(status);

        // Add to queue list
        QueueControl.getQueueList().add(queueEntry);
    }
}

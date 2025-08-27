/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ADT.DynamicList;
import ADT.MyList;
import Control.AppointmentManagement;
import Control.ConsultationManagement;
import Control.DiagnosisManagement;
import Control.DoctorManagement;
import Control.LeaveManagement;
import Control.MedicalTreatmentManagement;
import Control.PatientManagement;
import Control.PharmacyManagement;
import Control.QueueControl;
import Control.ScheduleManagement;
import Entity.Appointment;
import Entity.Consultation;
import Entity.Diagnosis;
import Entity.Doctor;
import Entity.DoctorLeave;
import Entity.MedicalTreatment;
import Entity.MedicalTreatmentItem;
import Entity.Medicine;
import Entity.Patient;
import Entity.Prescription;
import Entity.QueueEntry;
import Entity.Schedule;
import Entity.StockRequest;
import Utility.UtilityClass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        addSampleAppointments();
        addSampleQueueData();
        addSampleMedicine();
        ConsultationManagement.initializeConsultationCounter();
        addSampleLeaves();
        addSampleSchedules();
        addSampleDoctor();
        addSamplePrescriptions();
        addSampleStockRequests();
        addSampleDiagnosis();
        addSampleMedicalTreatment();
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

            samples[0] = new Consultation("C1001", "P1001", "D001", "Cough");
            samples[0].setStartTime(LocalDateTime.of(2025, 8, 20, 9, 0));
            samples[0].setEndTime(LocalDateTime.of(2025, 8, 20, 9, 30));

            samples[1] = new Consultation("C1002", "P1002", "D002", "Fever");
            samples[1].setStartTime(LocalDateTime.of(2025, 8, 20, 10, 0));
            samples[1].setEndTime(LocalDateTime.of(2025, 8, 20, 10, 20));

            samples[2] = new Consultation("C1003", "P1003", "D003", "Headache");
            samples[2].setStartTime(LocalDateTime.of(2025, 8, 21, 11, 0));
            samples[2].setEndTime(LocalDateTime.of(2025, 8, 21, 11, 40));

            samples[3] = new Consultation("C1004", "P1004", "D004", "Back Pain");
            samples[3].setStartTime(LocalDateTime.of(2025, 8, 21, 14, 0));
            samples[3].setEndTime(LocalDateTime.of(2025, 8, 21, 14, 25));

            samples[4] = new Consultation("C1005", "P1005", "D005", "Stomach Ache");
            samples[4].setStartTime(LocalDateTime.of(2025, 8, 22, 9, 15));
            samples[4].setEndTime(LocalDateTime.of(2025, 8, 22, 9, 50));

            samples[5] = new Consultation("C1006", "P1006", "D006", "Sore Throat");
            samples[5].setStartTime(LocalDateTime.of(2025, 8, 22, 10, 30));
            samples[5].setEndTime(LocalDateTime.of(2025, 8, 22, 11, 0));

            samples[6] = new Consultation("C1007", "P1007", "D007", "Allergy");
            samples[6].setStartTime(LocalDateTime.of(2025, 8, 23, 8, 45));
            samples[6].setEndTime(LocalDateTime.of(2025, 8, 23, 9, 15));

            samples[7] = new Consultation("C1008", "P1008", "D008", "Flu");
            samples[7].setStartTime(LocalDateTime.of(2025, 8, 23, 10, 15));
            samples[7].setEndTime(LocalDateTime.of(2025, 8, 23, 10, 50));

            samples[8] = new Consultation("C1009", "P1009", "D009", "Fatigue");
            samples[8].setStartTime(LocalDateTime.of(2025, 8, 24, 13, 0));
            samples[8].setEndTime(LocalDateTime.of(2025, 8, 24, 13, 45));

            samples[9] = new Consultation("C1010", "P1010", "D010", "Dizziness");
            samples[9].setStartTime(LocalDateTime.of(2025, 8, 24, 14, 15));
            samples[9].setEndTime(LocalDateTime.of(2025, 8, 24, 14, 55));

            // Added to ConsultationManagement's completedConsultations
            DynamicList<Consultation> completedList = ConsultationManagement.getCompletedConsultations();
            for (Consultation c : samples) {
                completedList.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error adding sample consultations: " + e.getMessage());
        }
    }

    public static void addSampleAppointments() {
        try {
            Appointment[] samples = new Appointment[10];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            samples[0] = new Appointment("A1001", "P1001", "D001",
                    LocalDateTime.of(2025, 8, 20, 9, 0), "General Checkup");
            samples[1] = new Appointment("A1002", "P1002", "D002",
                    LocalDateTime.of(2025, 8, 20, 10, 30), "Fever");
            samples[2] = new Appointment("A1003", "P1003", "D003",
                    LocalDateTime.of(2025, 8, 21, 11, 15), "Headache");
            samples[3] = new Appointment("A1004", "P1004", "D004",
                    LocalDateTime.of(2025, 8, 21, 14, 45), "Back Pain");
            samples[4] = new Appointment("A1005", "P1005", "D005",
                    LocalDateTime.of(2025, 8, 22, 9, 20), "Stomach Ache");
            samples[5] = new Appointment("A1006", "P1006", "D006",
                    LocalDateTime.of(2025, 8, 22, 10, 40), "Sore Throat");
            samples[6] = new Appointment("A1007", "P1007", "D007",
                    LocalDateTime.of(2025, 8, 23, 8, 50), "Allergy");
            samples[7] = new Appointment("A1008", "P1008", "D008",
                    LocalDateTime.of(2025, 8, 23, 10, 10), "Flu");
            samples[8] = new Appointment("A1009", "P1009", "D009",
                    LocalDateTime.of(2025, 8, 24, 13, 30), "Fatigue");
            samples[9] = new Appointment("A1010", "P1010", "D010",
                    LocalDateTime.of(2025, 8, 24, 14, 20), "Dizziness");

            for (Appointment a : samples) {
                AppointmentManagement.addScheduledAppointment(a);
            }

        } catch (Exception e) {
            System.out.println("Error adding sample appointments: " + e.getMessage());
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
                break;
            case "D002":
                symptoms.add("High fever");
                symptoms.add("Body aches");
                symptoms.add("Fatigue");
                break;
            case "D003":
                symptoms.add("Severe headache");
                symptoms.add("Sensitivity to light");
                symptoms.add("Nausea");
                break;
            case "D004":
                symptoms.add("Muscle pain");
                symptoms.add("Stiffness");
                symptoms.add("Weakness");
                break;
            case "D005":
                symptoms.add("Stomach pain");
                symptoms.add("Nausea");
                symptoms.add("Bloating");
                break;
            case "D006":
                symptoms.add("Sore throat");
                symptoms.add("Difficulty swallowing");
                symptoms.add("Swollen lymph nodes");
                break;
            case "D007":
                symptoms.add("Sneezing");
                symptoms.add("Itchy eyes");
                symptoms.add("Runny nose");
                break;
            case "D008":
                symptoms.add("Fever");
                symptoms.add("Chills");
                symptoms.add("Fatigue");
                break;
            case "D009":
                symptoms.add("Persistent fatigue");
                symptoms.add("Muscle aches");
                symptoms.add("Memory issues");
                break;
            case "D010":
                symptoms.add("Dizziness");
                symptoms.add("Balance issues");
                symptoms.add("Nausea");
                break;
            case "DIAG1011":
                symptoms.add("Fatigue");
                symptoms.add("Weakness");
                symptoms.add("Pale skin");
                break;
            case "DIAG1012":
                symptoms.add("Frequent urination");
                symptoms.add("Burning sensation");
                symptoms.add("Urgency to urinate");
                break;
            case "DIAG1013":
                symptoms.add("Increased thirst");
                symptoms.add("Weight loss");
                symptoms.add("Fatigue");
                break;
            case "DIAG1014":
                symptoms.add("Persistent sadness");
                symptoms.add("Lack of interest");
                symptoms.add("Difficulty concentrating");
                break;
            case "DIAG1015":
                symptoms.add("Shortness of breath");
                symptoms.add("Wheezing");
                symptoms.add("Chest tightness");
                break;
            default:
                return new DynamicList<>();
        }

        return symptoms;
    }

    public static void addSampleMedicalTreatment() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");

        try {
        MyList<MedicalTreatment> treatmentList = MedicalTreatmentManagement.getMedicalTreatmentList();

        // Example medicines
        MyList<MedicalTreatmentItem> meds1 = new DynamicList<>();
        meds1.add(new MedicalTreatmentItem("Paracetamol", "500mg", "Twice daily", "5 days", "Oral"));
        meds1.add(new MedicalTreatmentItem("Antihistamine", "10mg", "Once daily", "3 days", "Oral"));

        MyList<MedicalTreatmentItem> meds2 = new DynamicList<>();
        meds2.add(new MedicalTreatmentItem("Oseltamivir", "75mg", "Twice daily", "5 days", "Oral"));

        MyList<MedicalTreatmentItem> meds3 = new DynamicList<>();
        meds3.add(new MedicalTreatmentItem("Sumatriptan", "50mg", "As needed", "7 days", "Oral"));

        MyList<MedicalTreatmentItem> meds4 = new DynamicList<>();
        meds4.add(new MedicalTreatmentItem("Ibuprofen", "400mg", "Thrice daily", "7 days", "Oral"));

        MyList<MedicalTreatmentItem> meds5 = new DynamicList<>();
        meds5.add(new MedicalTreatmentItem("Omeprazole", "20mg", "Once daily", "14 days", "Oral"));

        // Sample treatments
        MedicalTreatment t1 = new MedicalTreatment("TRMT1001", "P1001", "D001",
                new Date(), null, "Active", "Failed",
                "Rest and hydration advised", "Patient stable", meds1);

        MedicalTreatment t2 = new MedicalTreatment("TRMT1002", "P1002", "D002",
                new Date(), null, "Active", "Failed",
                "Antiviral medication prescribed", "Monitor temperature", meds2);

        MedicalTreatment t3 = new MedicalTreatment("TRMT1003", "P1003", "D003",
                new Date(), null, "Completed", "Follow up",
                "Avoid triggers like bright light", "Headache improving", meds3);

        MedicalTreatment t4 = new MedicalTreatment("TRMT1004", "P1004", "D004",
                new Date(), null, "Active", "Follow up",
                "Physical therapy scheduled", "Muscle pain reduced slightly", meds4);

        MedicalTreatment t5 = new MedicalTreatment("TRMT1005", "P1005", "D005",
                new Date(), null, "Completed", "Successful",
                "Diet modifications suggested", "Patient responding well", meds5);

        MedicalTreatment t6 = new MedicalTreatment("TRMT1006", "P1006", "D006",
                new Date(), null, "Active", "Ongoing",
                "Antibiotics prescribed", "Throat pain persists", new DynamicList<>());

        MedicalTreatment t7 = new MedicalTreatment("TRMT1007", "P1007", "D007",
                new Date(), null, "Completed", "Successful",
                "Advised to avoid allergens", "No sneezing reported", new DynamicList<>());

        MedicalTreatment t8 = new MedicalTreatment("TRMT1008", "P1008", "D008",
                new Date(), null, "Active", "Ongoing",
                "Rest and fluids advised", "Fever persists", new DynamicList<>());

        MedicalTreatment t9 = new MedicalTreatment("TRMT1009", "P1009", "D009",
                new Date(), null, "Active", "Follow up",
                "Stress management counseling", "Fatigue continues", new DynamicList<>());

        MedicalTreatment t10 = new MedicalTreatment("TRMT1010", "P1010", "D010",
                new Date(), null, "Active", "Ongoing",
                "Vestibular therapy planned", "Patient still dizzy", new DynamicList<>());
            
        Date treatmentDate = sdf.parse("04/01/2023");
        Date followUpDate = sdf.parse("04/12/2024");

        MedicalTreatment t11 = new MedicalTreatment("TRMT1011", "P1011", "D011",
                treatmentDate, followUpDate, "Completed", "Failed",
                "Patient advised to avoid caffeine", "Patient still experiencing symptoms", new DynamicList<>());

        MedicalTreatment t12 = new MedicalTreatment("TRMT1012", "P1012", "D012",
                treatmentDate, followUpDate, "Completed", "Successful",
                "Patient advised to avoid spicy foods", "Patient no longer experiencing symptoms", new DynamicList<>());

        MedicalTreatment t13 = new MedicalTreatment("TRMT1013", "P1013", "D013",
                treatmentDate, followUpDate, "Completed", "Successful",
                "Patient advised to avoid alcohol", "Patient still experiencing symptoms", new DynamicList<>());

        MedicalTreatment t14 = new MedicalTreatment("TRMT1014", "P1014", "D014",
                treatmentDate, followUpDate, "Completed", "Follow up",
                "Patient advised to avoid dairy products", "Patient no longer experiencing symptoms", new DynamicList<>());

        MedicalTreatment t15 = new MedicalTreatment("TRMT1015", "P1015", "D015",
                treatmentDate, followUpDate, "Completed", "Successful",
                "Patient advised to avoid gluten", "Patient still experiencing symptoms", new DynamicList<>());

        // add them into treatmentList
        treatmentList.add(t1);
        treatmentList.add(t2);
        treatmentList.add(t3);
        treatmentList.add(t4);
        treatmentList.add(t5);
        treatmentList.add(t6);
        treatmentList.add(t7);
        treatmentList.add(t8);
        treatmentList.add(t9);
        treatmentList.add(t10);
        treatmentList.add(t11);
        treatmentList.add(t12);
        treatmentList.add(t13);
        treatmentList.add(t14);
        treatmentList.add(t15);

        } catch (Exception e) {
                System.out.println("Error adding sample treatments: " + e.getMessage());
        }
    }

    public static void addSampleQueueData() {
        try {
            MyList<Patient> patientList = PatientManagement.getPatientList();

            Calendar cal = Calendar.getInstance();

            cal.setTime(new Date());

            createQueueEntry(patientList.get(0), cal, 8, 30, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(1), cal, 9, 15, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(2), cal, 9, 45, UtilityClass.statusCompleted);
            createQueueEntry(patientList.get(3), cal, 10, 20, UtilityClass.statusWaiting);
            createQueueEntry(patientList.get(4), cal, 11, 10, UtilityClass.statusWaiting);
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

    public static void addSamplePrescriptions() {
        try {
            // Sample Prescription 1: Common Cold Treatment
            Prescription prescription1 = new Prescription("RX001", "P1100", "DR001");
            prescription1.addMedicineItem("Paracetamol", "1 tablet", "3 times daily after meals", "5 days", "Oral");
            prescription1.addMedicineItem("Vitamin C", "2 tablets", "Once daily", "7 days", "Oral");
            PharmacyManagement.addToQueue(prescription1);

            // Sample Prescription 2: Infection Treatment
            Prescription prescription2 = new Prescription("RX002", "P1101", "DR002");
            prescription2.addMedicineItem("Amoxicillin", "1 capsule", "3 times daily", "7 days", "Oral");
            prescription2.addMedicineItem("Benadryl Cough Syrup", "10ml", "2 times daily", "5 days", "Oral");
            PharmacyManagement.addToQueue(prescription2);

            // Sample Prescription 3: Stomach Issues
            Prescription prescription3 = new Prescription("RX003", "P1102", "DR003");
            prescription3.addMedicineItem("Omeprazole", "1 capsule", "Once daily before breakfast", "14 days", "Oral");
            prescription3.addMedicineItem("ORS Sachet", "1 sachet", "After each loose stool", "As needed", "Dissolve in water");
            PharmacyManagement.addToQueue(prescription3);

            // Sample Prescription 4: Skin Treatment
            Prescription prescription4 = new Prescription("RX004", "P1103", "DR004");
            prescription4.addMedicineItem("Hydrocortisone Cream", "Apply thin layer", "2 times daily", "10 days", "Topical");
            prescription4.addMedicineItem("Aspirin", "1 capsule", "Once daily", "7 days", "Oral");
            PharmacyManagement.addToQueue(prescription4);

            // Sample Prescription 5: Chronic Condition Management
            Prescription prescription5 = new Prescription("RX005", "P1104", "DR005");
            prescription5.addMedicineItem("Paracetamol", "2 tablets", "4 times daily as needed for pain", "10 days", "Oral");
            prescription5.addMedicineItem("Vitamin C", "2 tablets", "Twice daily", "30 days", "Oral");
            prescription5.addMedicineItem("Omeprazole", "1 capsule", "Once daily", "30 days", "Oral");
            PharmacyManagement.addToQueue(prescription5);

            // Sample Prescription 6: Respiratory Issues
            Prescription prescription6 = new Prescription("RX006", "P1105", "DR001");
            prescription6.addMedicineItem("Benadryl Cough Syrup", "5ml", "3 times daily", "7 days", "Oral");
            prescription6.addMedicineItem("Paracetamol", "1 tablet", "As needed for fever", "5 days", "Oral");
            PharmacyManagement.addToQueue(prescription6);

            // Sample Prescription 7: Pain Management
            Prescription prescription7 = new Prescription("RX007", "P1106", "DR002");
            prescription7.addMedicineItem("Aspirin", "2 capsules", "Twice daily with food", "14 days", "Oral");
            prescription7.addMedicineItem("Hydrocortisone Cream", "Apply as needed", "Up to 3 times daily", "7 days", "Topical");
            PharmacyManagement.addToQueue(prescription7);

            // Sample Prescription 8: Nutritional Support
            Prescription prescription8 = new Prescription("RX008", "P1107", "DR003");
            prescription8.addMedicineItem("Vitamin C", "1 tablet", "Twice daily", "30 days", "Oral");
            prescription8.addMedicineItem("ORS Sachet", "1 sachet", "Once daily", "10 days", "Dissolve in water");
            PharmacyManagement.addToQueue(prescription8);

        } catch (Exception e) {
            System.err.println("Error adding sample prescriptions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addSampleMedicine() {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);

        try {
            // Initialize medicine inventory with only dosage form
            PharmacyManagement.addMedicine(new Medicine("M001", "Paracetamol", 100, "Analgesic", 0.25, "China",
                    sdf.parse("31/12/2030"), "tablet"));
            PharmacyManagement.addMedicine(new Medicine("M002", "Aspirin", 50, "Analgesic", 1.00, "Bayer",
                    sdf.parse("15/11/2029"), "tablet"));
            PharmacyManagement.addMedicine(new Medicine("M003", "Amoxicillin", 200, "Antibiotic", 1.50, "Pfizer",
                    sdf.parse("01/07/2031"), "capsule"));
            PharmacyManagement.addMedicine(new Medicine("M004", "Vitamin C", 150, "Supplement", 0.8, "Blackmores",
                    sdf.parse("10/05/2028"), "tablet"));
            PharmacyManagement.addMedicine(new Medicine("M005", "Benadryl Cough Syrup", 80, "Cold & Flu", 0.30, "Johnson",
                    sdf.parse("20/03/2032"), "ml"));
            PharmacyManagement.addMedicine(new Medicine("M006", "ORS Sachet", 300, "Electrolyte", 1.20, "Cipla",
                    sdf.parse("15/08/2029"), "sachet"));
            PharmacyManagement.addMedicine(new Medicine("M007", "Hydrocortisone Cream", 40, "Topical", 4.00, "GSK",
                    sdf.parse("30/06/2030"), "cream"));
            PharmacyManagement.addMedicine(new Medicine("M008", "Omeprazole", 100, "Antacid", 1.75, "AstraZeneca",
                    sdf.parse("12/09/2031"), "capsule"));
            PharmacyManagement.addMedicine(new Medicine("M009", "Loratadine", 8, "Antihistamine", 0.95, "Sun Pharma",
                    sdf.parse("15/04/2026"), "tablet"));
            PharmacyManagement.addMedicine(new Medicine("M010", "Ibuprofen", 15, "Analgesic", 0.50, "Abbott",
                    sdf.parse("05/10/2025"), "tablet"));
            PharmacyManagement.addMedicine(new Medicine("M011", "Metformin", 220, "Antidiabetic", 0.70, "Novartis",
                    sdf.parse("17/01/2030"), "tablet"));
            PharmacyManagement.addMedicine(new Medicine("M012", "Azithromycin", 5, "Antibiotic", 2.00, "Cipla",
                    sdf.parse("20/07/2024"), "capsule"));
            PharmacyManagement.addMedicine(new Medicine("M013", "Cetirizine Syrup", 60, "Allergy", 0.40, "Ranbaxy",
                    sdf.parse("25/12/2027"), "ml"));
            PharmacyManagement.addMedicine(new Medicine("M014", "Warfarin", 12, "Anticoagulant", 3.50, "Bristol-Myers Squibb",
                    sdf.parse("09/09/2025"), "tablet"));
            PharmacyManagement.addMedicine(new Medicine("M015", "Insulin Injection", 7, "Hormone", 25.00, "Novo Nordisk",
                    sdf.parse("01/03/2025"), "ml"));
            PharmacyManagement.addMedicine(new Medicine("M016", "Salbutamol Inhaler", 90, "Respiratory", 6.00, "GSK",
                    sdf.parse("19/06/2030"), "inhaler"));
            PharmacyManagement.addMedicine(new Medicine("M017", "Prednisolone", 25, "Corticosteroid", 2.20, "Pfizer",
                    sdf.parse("11/11/2028"), "tablet"));
            PharmacyManagement.addMedicine(new Medicine("M018", "Folic Acid", 300, "Supplement", 0.15, "Merck",
                    sdf.parse("02/02/2032"), "tablet"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addSampleStockRequests() {
        Calendar cal = Calendar.getInstance();

        try {
            // === COMPLETED/APPROVED STOCK REQUESTS (History) ===

            // Request 1: Paracetamol restocked 2 weeks ago
            cal.add(Calendar.DAY_OF_MONTH, -14);
            StockRequest request1 = new StockRequest("REQ001", "M001", "Paracetamol", 200, cal.getTime(), "COMPLETED");
            PharmacyManagement.getAllStockRequests().add(request1);

            // Request 2: Vitamin C restocked 10 days ago
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -10);
            StockRequest request2 = new StockRequest("REQ002", "M004", "Vitamin C", 100, cal.getTime(), "COMPLETED");
            PharmacyManagement.getAllStockRequests().add(request2);

            // Request 3: Aspirin restocked 1 week ago
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -7);
            StockRequest request3 = new StockRequest("REQ003", "M002", "Aspirin", 150, cal.getTime(), "COMPLETED");
            PharmacyManagement.getAllStockRequests().add(request3);

            // Request 4: Benadryl rejected 5 days ago (supplier issue)
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -5);
            StockRequest request4 = new StockRequest("REQ004", "M005", "Benadryl Cough Syrup", 50, cal.getTime(), "REJECTED");
            PharmacyManagement.getAllStockRequests().add(request4);

            // Request 5: Amoxicillin restocked 3 days ago
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -3);
            StockRequest request5 = new StockRequest("REQ005", "M003", "Amoxicillin", 100, cal.getTime(), "COMPLETED");
            PharmacyManagement.getAllStockRequests().add(request5);

            // === PENDING STOCK REQUESTS (Current) ===
            // Request 6: Hydrocortisone Cream - Low stock, requested yesterday
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -1);
            StockRequest request6 = new StockRequest("REQ006", "M007", "Hydrocortisone Cream", 80, cal.getTime(), "PENDING");
            PharmacyManagement.getAllStockRequests().add(request6);

            // Request 7: ORS Sachet - High demand, requested today
            cal = Calendar.getInstance();
            StockRequest request7 = new StockRequest("REQ007", "M006", "ORS Sachet", 200, cal.getTime(), "PENDING");
            PharmacyManagement.getAllStockRequests().add(request7);

            // Request 8: Omeprazole - Chronic condition patients, requested today
            cal = Calendar.getInstance();
            StockRequest request8 = new StockRequest("REQ008", "M008", "Omeprazole", 120, cal.getTime(), "PENDING");
            PharmacyManagement.getAllStockRequests().add(request8);

            // Request 9: Benadryl Cough Syrup - Retry after previous rejection, requested 2 hours ago
            cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -2);
            StockRequest request9 = new StockRequest("REQ009", "M005", "Benadryl Cough Syrup", 100, cal.getTime(), "PENDING");
            PharmacyManagement.getAllStockRequests().add(request9);

            // Request 10: Paracetamol - Popular item, requested 1 hour ago
            cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -1);
            StockRequest request10 = new StockRequest("REQ010", "M001", "Paracetamol", 250, cal.getTime(), "PENDING");
            PharmacyManagement.getAllStockRequests().add(request10);
        } catch (Exception e) {
            System.err.println("❌ Error adding sample stock requests: " + e.getMessage());
        }
    }
    
    public static void addSampleLeaves() {
        LeaveManagement.addLeave(new DoctorLeave(
                "L001", // leaveID
                "D001", // doctorID
                LocalDate.of(2025, 8, 14), // dateFrom
                LocalDate.of(2025, 8, 30), // dateTo (same day leave)
                "Medical conference" // reason
        ));

        LeaveManagement.addLeave(new DoctorLeave(
                "L002",
                "D002",
                LocalDate.of(2025, 8, 13), // multi-day leave
                LocalDate.of(2025, 8, 16),
                "Family vacation"
        ));

        LeaveManagement.addLeave(new DoctorLeave(
                "L003",
                "D004",
                LocalDate.of(2025, 8, 13), // multi-day leave
                LocalDate.of(2025, 8, 25),
                "Family vacation"
        ));

        LeaveManagement.addLeave(new DoctorLeave(
                "L004",
                "D004",
                LocalDate.of(2025, 9, 15), // multi-day leave
                LocalDate.of(2025, 9, 25),
                "Family vacation"
        ));
    }
    
    public static void addSampleSchedules() {
        ScheduleManagement.addSchedule(new Schedule("S001", "D001", DayOfWeek.MONDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 0)));
        ScheduleManagement.addSchedule(new Schedule("S002", "D002", DayOfWeek.MONDAY,
                LocalTime.of(9, 0), LocalTime.of(23, 0)));
        ScheduleManagement.addSchedule(new Schedule("S003", "D003", DayOfWeek.MONDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 0)));
        ScheduleManagement.addSchedule(new Schedule("S004", "D004", DayOfWeek.MONDAY,
                LocalTime.of(13, 0), LocalTime.of(18, 0)));
        ScheduleManagement.addSchedule(new Schedule("S005", "D005", DayOfWeek.MONDAY,
                LocalTime.of(13, 0), LocalTime.of(18, 0)));
        ScheduleManagement.addSchedule(new Schedule("S006", "D001", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        ScheduleManagement.addSchedule(new Schedule("S007", "D003", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        ScheduleManagement.addSchedule(new Schedule("S008", "D005", DayOfWeek.TUESDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 0)));
        ScheduleManagement.addSchedule(new Schedule("S009", "D004", DayOfWeek.TUESDAY,
                LocalTime.of(13, 0), LocalTime.of(15, 0)));
        ScheduleManagement.addSchedule(new Schedule("S010", "D005", DayOfWeek.TUESDAY,
                LocalTime.of(15, 0), LocalTime.of(18, 0)));
        ScheduleManagement.addSchedule(new Schedule("S011", "D005", DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        ScheduleManagement.addSchedule(new Schedule("S012", "D003", DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        ScheduleManagement.addSchedule(new Schedule("S013", "D001", DayOfWeek.THURSDAY,
                LocalTime.of(9, 0), LocalTime.of(12, 0)));
        ScheduleManagement.addSchedule(new Schedule("S014", "D001", DayOfWeek.THURSDAY,
                LocalTime.of(13, 0), LocalTime.of(18, 0)));
        ScheduleManagement.addSchedule(new Schedule("S020", "D002", DayOfWeek.FRIDAY,
                LocalTime.of(9, 0), LocalTime.of(13, 30)));
    }

    public static void addSampleDoctor() {
        
        MyList<Doctor> doctorList = DoctorManagement.getAllDoctors();
        
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
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

            DoctorManagement.add(d1);
            DoctorManagement.add(d2);
            DoctorManagement.add(d3);
            DoctorManagement.add(d4);
            DoctorManagement.add(d5);
//            System.out.println("Doctors loaded: " + doctorList.size()); // DEBUG

            // 🔹 Once doctors are added, update each doctor's working status
            for (int i = 0; i < doctorList.size(); i++) {
                DoctorManagement.updateWorkingStatus(doctorList.get(i));
            }

        } catch (ParseException e) {
            System.out.println("Error parsing date in sample data.");
        }
    }
}

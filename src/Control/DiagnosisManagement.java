/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import DAO.CurrentServingDAO;
import Entity.Diagnosis;
import java.util.Date;
import java.util.Iterator;


/**
 *
 * @author User
 */
public class DiagnosisManagement {
    
    //list to store medical treatment details
    private static final MyList<Diagnosis> diagnosisList = new DynamicList<>();
    private static MyList<CurrentServingDAO> currentServingList = new DynamicList<>();
    
    // Create a new symptom list for the specific diagnosis or patient
    public static MyList<String> addSymptoms(MyList<String> symptomInput) {
        MyList<String> symptoms = new DynamicList<>();
        if (symptomInput != null && !symptomInput.isEmpty()) {
            for (int i = 0; i < symptomInput.size(); i++) {
                symptoms.add(symptomInput.get(i));
            }
        }
        return symptoms;
    }

    // Create a new diagnosis
    public static boolean addDiagnosis(Diagnosis diagnosis) {
        if (diagnosis != null) {
            diagnosisList.add(diagnosis);
            return true;
        }
        return false;
    }

    // get the diagnosis list by ID
    public static Diagnosis getDiagnosisListById(String diagnosisId) {
        return diagnosisList.findFirst(d -> d.getDiagnosisId().equals(diagnosisId));
    }
    
    //display the diagnosis list using the iterator
    public static void displayDiagnosisList() {
        Iterator<Diagnosis> iterator = diagnosisList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    //find diagnosis by diagnosis ID
    public static Diagnosis findDiagnosisById(String diagnosisId) {
        return diagnosisList.findFirst(d -> d.getDiagnosisId().equals(diagnosisId));
    }

    //update diagnosis details using ADT method
    public static boolean   updateDiagnosisDetails(String diagnosisId, Diagnosis newDiagnosis) {
        int index  = diagnosisList.findIndex(p -> p.getDiagnosisId().equals(diagnosisId));
        if(index != -1) {

            diagnosisList.replace(index, newDiagnosis);

            return true;
        }
        return false;
    }

    //remove a diagnosis by ID
    public static boolean removeDiagnosisById(String diagnosisId) {
        return diagnosisList.removeIf(d -> d.getDiagnosisId().equals(diagnosisId));
    }

    // display the all diagnosis list based on diagnosis ID
    public static MyList<Diagnosis> getDiagnosisList() {
        return diagnosisList;
    }

    public static String getSeverityDisplay(String severityLevel) {
        if (severityLevel == null) return "N/A";
        
        switch (severityLevel.toUpperCase()) {
            case "LOW":
                return severityLevel + " !";
            case "MEDIUM":
                return severityLevel + " !!";
            case "HIGH":
                return severityLevel + " !!!";
            case "CRITICAL":
                return severityLevel + " !!!!";
            default:
                return severityLevel;
        }
    }

    // get the diagnosis list by year and month
    public static MyList<Diagnosis> getDiagnosesByYearAndMonth(int year, int month) {
    MyList<Diagnosis> filteredDiagnoses = new DynamicList<>();
        for (int i = 0; i < diagnosisList.size(); i++) {
            Diagnosis diagnosis = diagnosisList.get(i);
            Date diagnosisDate = diagnosis.getDiagnosisDate();
            if (diagnosisDate != null) {
                int diagnosisYear = diagnosisDate.getYear() + 1900;
                int diagnosisMonth = diagnosisDate.getMonth() + 1;
                if (diagnosisYear == year && diagnosisMonth == month) {
                    filteredDiagnoses.add(diagnosis);
                }
            }
        }
        return filteredDiagnoses;
    }

    // get the diagnosis list by year only
    public static MyList<Diagnosis> getDiagnosesByYear(int year) {
        MyList<Diagnosis> filteredDiagnoses = new DynamicList<>();
        for (int i = 0; i < diagnosisList.size(); i++) {
            Diagnosis diagnosis = diagnosisList.get(i);
            Date diagnosisDate = diagnosis.getDiagnosisDate();
            if (diagnosisDate != null) {
                int diagnosisYear = diagnosisDate.getYear() + 1900;
                if (diagnosisYear == year) {
                    filteredDiagnoses.add(diagnosis);
                }
            }
        }
        return filteredDiagnoses;
    }

    public static MyList<Diagnosis> getDiagnosisListBySeverityLevelAndYear(String severity, int year) {
        MyList<Diagnosis> filteredDiagnoses = new DynamicList<>();
        for (int i = 0; i < diagnosisList.size(); i++) {
            Diagnosis diagnosis = diagnosisList.get(i);
            Date diagnosisDate = diagnosis.getDiagnosisDate();
            if (diagnosisDate != null) {
                int diagnosisYear = diagnosisDate.getYear() + 1900;
                if (diagnosisYear == year && diagnosis.getSeverityLevel().equalsIgnoreCase(severity)) {
                    filteredDiagnoses.add(diagnosis);
                }
            }
        }
        return filteredDiagnoses;
    }

    // print the diagnosis list based on the severity level
    public static MyList<Diagnosis> filterDiagnosisBySeverityLevel(String severityLevel) {
        if (severityLevel == null || severityLevel.isEmpty()) {
            System.out.println("Invalid severity level. Please try again.");
            return new DynamicList<>();
        }

        if(diagnosisList == null || diagnosisList.isEmpty()) {
            System.out.println("No diagnoses available to filter.");
            return new DynamicList<>();
        }
        
        return DiagnosisManagement.getDiagnosisList().findAll(d -> d.getSeverityLevel().equalsIgnoreCase(severityLevel));
    }

    // get the first patientID from the queue list
    public static String getCurrentServingPatient() {
        CurrentServingDAO latest = ConsultationManagement.getLatestCurrentConsulting();
        if (latest != null) {
            return latest.getPatientId();
        }
        return null;
    }

    // get the related doctor ID from the current serving list
    public static String getCurrentServingDoctor() {
        CurrentServingDAO latest = ConsultationManagement.getLatestCurrentConsulting();
        if (latest != null) {
            return latest.getDoctorId();
        }
        return null; 
    }

    // get the current diagnosis ID from the diagnosis list
    public static String getCurrentDiagnosisId() {
        if (!diagnosisList.isEmpty()) {
            return diagnosisList.get(diagnosisList.size() - 1).getDiagnosisId();
        }
        return null;
    }

    public static String getMedicineForSymptom(String symptom) {
        switch (symptom.toLowerCase()) {
            case "cough":
                return "Cough Syrup";
            case "nasal congestion":
                return "Decongestant Spray";
            case "mild fever":
            case "high fever":
            case "fever":
            case "chills":
                return "Paracetamol";
            case "body aches":
            case "muscle pain":
            case "stiffness":
            case "muscle aches":
                return "Pain Reliever (Ibuprofen)";
            case "fatigue":
            case "persistent fatigue":
            case "weakness":
                return "Vitamin Supplements";
            case "severe headache":
            case "sensitivity to light":
            case "headache":
                return "Pain Reliever (Aspirin)";
            case "nausea":
            case "bloating":
                return "Antimetic Tablets";
            case "stomach pain":
                return "Antacid";
            case "sore throat":
            case "difficulty swallowing":
                return "Lozenges / Antibiotics if persistent";
            case "swollen lymph nodes":
                return "Anti-inflammatory Medicine";
            case "sneezing":
            case "runny nose":
            case "itchy eyes":
                return "Antihistamine";
            case "dizziness":
            case "balance issues":
                return "Vertigo Medicine";
            case "memory issues":
            case "difficulty concentrating":
                return "Cognitive Support Supplement";
            case "pale skin":
                return "Iron Supplements";
            case "frequent urination":
            case "burning sensation":
            case "urgency to urinate":
                return "Antibiotics (for UTI)";
            case "increased thirst":
            case "weight loss":
                return "Insulin / Diabetes Medicine";
            case "persistent sadness":
            case "lack of interest":
                return "Antidepressants";
            case "shortness of breath":
            case "wheezing":
            case "chest tightness":
                return "Inhaler (Bronchodilator)";
            default:
                return "General Checkup Needed";
        }
    }
}
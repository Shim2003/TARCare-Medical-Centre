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
    private static MyList<String> symptoms = new DynamicList<>();
    
    //collect the list of symptoms
    public static void startSymptomCollection() {
        symptoms = new DynamicList<>();
    }

    // add one symptom string into the list
    public static void addSymptom(String symptomInput) {
        if (symptomInput != null && !symptomInput.trim().isEmpty()) {
            symptoms.add(symptomInput);
        }
    }

    public static int getCollectedSymptomCount() {
        return symptoms.size();
    }

    public static String getCollectedSymptomByIndex(int index) {
        if (index >= 0 && index < symptoms.size()) {
            return symptoms.get(index);
        }
        return null;
    }

    public static MyList<String> getAllCollectedSymptoms() {
        return symptoms;
    }

    public static class SymptomCount {
    public final String symptom;
    public final int count;

    public SymptomCount(String symptom, int count) {
        this.symptom = symptom;
        this.count = count;
        }
    }

            public static MyList<SymptomCount> getTopSymptoms(int topN) {
            MyList<SymptomCount> topSymptoms = new DynamicList<>();
            int[] counts = new int[symptoms.size()];

            // Count each symptom
            DynamicList<String> uniqueSymptoms = new DynamicList<>();
            for (int i = 0; i < symptoms.size(); i++) {
                String s = symptoms.get(i);
                boolean found = false;
                for (int j = 0; j < uniqueSymptoms.size(); j++) {
                    if (uniqueSymptoms.get(j).equalsIgnoreCase(s)) {
                        counts[j]++;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    uniqueSymptoms.add(s);
                    counts[uniqueSymptoms.size() - 1] = 1;
                }
            }

            // Sort by count descending
            for (int i = 0; i < uniqueSymptoms.size() - 1; i++) {
                for (int j = i + 1; j < uniqueSymptoms.size(); j++) {
                    if (counts[j] > counts[i]) {
                        int tempCount = counts[i];
                        counts[i] = counts[j];
                        counts[j] = tempCount;

                        String tempSymptom = uniqueSymptoms.get(i);
                        uniqueSymptoms.replace(i, uniqueSymptoms.get(j));
                        uniqueSymptoms.replace(j, tempSymptom);
                    }
                }
            }

            // Populate topSymptoms
            int limit = Math.min(topN, uniqueSymptoms.size());
            for (int i = 0; i < limit; i++) {
                topSymptoms.add(new SymptomCount(uniqueSymptoms.get(i), counts[i]));
            }

            return topSymptoms;
        }

    // Return the number of top symptoms calculated
    public static int getTopSymptomSize(MyList<SymptomCount> topSymptoms) {
        if (topSymptoms != null) {
            return topSymptoms.size();
        }
        return 0;
    }

    // Return the occurrence count of a symptom at a certain index
    public static int getTopSymptomCount(MyList<SymptomCount> topSymptoms, int index) {
        if (topSymptoms != null && index >= 0 && index < topSymptoms.size()) {
            return topSymptoms.get(index).count;
        }
        return 0;
    }

    // Return the symptom name at a certain index
    public static String getTopSymptomName(MyList<SymptomCount> topSymptoms, int index) {
        if (topSymptoms != null && index >= 0 && index < topSymptoms.size()) {
            return topSymptoms.get(index).symptom;
        }
        return null;
    }

    // Return how many recommended medicines exist
    public static int getRecommendSize(MyList<SymptomCount> topSymptoms) {
        return (topSymptoms != null) ? topSymptoms.size() : 0;
    }

    // Return a single SymptomCount object by index
    public static SymptomCount getRecommendListItem(MyList<SymptomCount> topSymptoms, int index) {
        if (topSymptoms == null || index < 0 || index >= topSymptoms.size()) {
            return null;
        }
        return topSymptoms.get(index);
    }

    // return the collected list when done
    public static MyList<String> getSymptoms() {
        return symptoms;
    }

    // check if the symptom list is empty
    public static boolean isSymptomEmpty() {
        return symptoms.isEmpty();
    }
    
    // return the symptom list size
    public static int getSymptomSize(MyList<String> symptoms) {
        return symptoms.size();
    }
    public static int getSymptomSize(Diagnosis diagnosis) {
        return diagnosis.getSymptoms().size();
    }

    // Check if a diagnosis has symptoms
    public static boolean hasSymptoms(Diagnosis diagnosis) {
        return diagnosis != null 
        && diagnosis.getSymptoms() != null 
        && diagnosis.getSymptoms().size() > 0;
    }
    
    // get the symptom in the certain id and certain symptom list
    public static String getSymptomById(String diagnosisId, int index) {
        Diagnosis diagnosis = findDiagnosisById(diagnosisId);
        if (diagnosis != null) {
            MyList<String> symptoms = diagnosis.getSymptoms();
            if (index >= 0 && index < symptoms.size()) {
                return symptoms.get(index);
            }
        }
        return null;
    }

    // get the symptom in the certain index
    public static String getSymptomByIndex(int index) {
        if (index >= 0 && index < symptoms.size()) {
            return symptoms.get(index);
        }
        return null;
    }
    public static String getSymptomByIndex(MyList<String> symptom, int index) {
        if (index >= 0 && index < symptom.size()) {
            return symptom.get(index);
        }
        return null;
    }



    // check diagnosis list is empty by passing as parameter
    public static boolean isDiagnosisEmpty(MyList<Diagnosis> diagnosisList) {
        return diagnosisList.isEmpty();
    }

    // Create a new diagnosis
    public static boolean addDiagnosis(Diagnosis diagnosis) {
        if (diagnosis != null) {
            diagnosisList.add(diagnosis);
            return true;
        }
        return false;
    }

    // get the size of certain diagnosis by parameter
    public static int getDiagnosisSize(MyList<Diagnosis> diagnosisList) {
        return diagnosisList.size();
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

    // get the diagnosis list by index
    public static Diagnosis getDiagnosisListByIndex(int index) {
        return diagnosisList.get(index);
    }

    // get the diagnosis list by index and list
    public static Diagnosis getDiagnosisListByIndex(MyList<Diagnosis> diagnosisList, int index) {
        return diagnosisList.get(index);
    }

    // check if the diagnosis is empty
    public static boolean isDiagnosisEmpty(Diagnosis diagnosis) {
        return diagnosis == null;
    }

    // check if the id input is empty
    public static boolean isIdEmpty(String id) {
        return id == null || id.trim().isEmpty();
    }

    //find diagnosis by diagnosis ID
    public static Diagnosis findDiagnosisById(String diagnosisId) {
        return diagnosisList.findFirst(d -> d.getDiagnosisId().equals(diagnosisId));
    }

    //update diagnosis details using ADT method
    public static boolean updateDiagnosisDetails(String diagnosisId, Diagnosis newDiagnosis) {
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
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.Diagnosis;
import java.util.Date;
import java.util.Iterator;


/**
 *
 * @author User
 */
public class DiagnosisManagement {
    
    //list to store medical treatment details
    private static final DynamicList<Diagnosis> diagnosisList = new DynamicList<>();
    
    // Create a new symptom list for the specific diagnosis or patient
    public static DynamicList<String> addSymptoms(DynamicList<String> symptomInput) {
        DynamicList<String> symptoms = new DynamicList<>();
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

    //update diagnosis details
    public static boolean updateDiagnosisDetails(String diagnosisId, Diagnosis newDiagnosis) {
        Diagnosis existingDiagnosis = findDiagnosisById(diagnosisId);
        if (existingDiagnosis != null) {
            existingDiagnosis.setPatientId(newDiagnosis.getPatientId());
            existingDiagnosis.setDoctorId(newDiagnosis.getDoctorId());
            existingDiagnosis.setDiagnosisDate(newDiagnosis.getDiagnosisDate());
            existingDiagnosis.setSymptoms(newDiagnosis.getSymptoms());
            existingDiagnosis.setDiagnosisDescription(newDiagnosis.getDiagnosisDescription());
            existingDiagnosis.setSeverityLevel(newDiagnosis.getSeverityLevel());
            existingDiagnosis.setRecommendations(newDiagnosis.getRecommendations());
            existingDiagnosis.setNotes(newDiagnosis.getNotes());
            return true;
        }
        return false;
    }

    //remove a diagnosis by ID
    public static boolean removeDiagnosisById(String diagnosisId) {
        return diagnosisList.removeIf(d -> d.getDiagnosisId().equals(diagnosisId));
    }

    // display the all diagnosis list based on diagnosis ID
    public static DynamicList<Diagnosis> getDiagnosisList() {
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

    public static void appendWrappedText(StringBuilder sb, String text) {
        if (text == null || text.trim().isEmpty()) {
            sb.append(">   N/A                                                         <\n");
            return;
        }
        
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder(">   ");
        
        for (String word : words) {
            if (line.length() + word.length() + 1 > 63) {
                sb.append(String.format("%-63s <\n", line.toString()));
                line = new StringBuilder(">   ");
            }
            line.append(word).append(" ");
        }
        
        if (line.length() > 4) {
            sb.append(String.format("%-63s <\n", line.toString().trim()));
        }
    }

    public static DynamicList<Diagnosis> getDiagnosesByYearAndMonth(int year, int month) {
    DynamicList<Diagnosis> filteredDiagnoses = new DynamicList<>();
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
}

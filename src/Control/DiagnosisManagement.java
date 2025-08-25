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
    public static boolean   updateDiagnosisDetails(String diagnosisId, Diagnosis newDiagnosis) {
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

    // update the diagnosis list
    public static boolean updateDiagnosisList(Diagnosis updatedDiagnosis) {
        if (updatedDiagnosis == null || updatedDiagnosis.getDiagnosisId() == null) {
            return false;
        }

        for (int i = 0; i < diagnosisList.size(); i++) {
            Diagnosis existingDiagnosis = diagnosisList.get(i);
            if (existingDiagnosis.getDiagnosisId().equals(updatedDiagnosis.getDiagnosisId())) {
                diagnosisList.replace(i, updatedDiagnosis);
                return true;
            }
        }
        return false;
    }
}
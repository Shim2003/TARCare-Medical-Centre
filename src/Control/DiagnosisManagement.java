/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.Diagnosis;
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
}

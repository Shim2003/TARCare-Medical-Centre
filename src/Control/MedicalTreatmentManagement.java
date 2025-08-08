/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.Diagnosis;
import Entity.MedicalTreatment;
import Entity.MedicalTreatmentItem;
import Entity.TreatmentHistory;

/**
* 
* @author User
*/

public class MedicalTreatmentManagement {
    
    //list to store medical treatment details
    private static final DynamicList<Diagnosis> diagnosisList = new DynamicList<>();
    private static final DynamicList<MedicalTreatment> treatmentList = new DynamicList<>();
    private static final DynamicList<TreatmentHistory> treatmentHistoryList = new DynamicList<>();
    private static final DynamicList<MedicalTreatmentItem> medicineList = new DynamicList<>();
    
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

    //create a new medical treatment
    public static boolean addMedicalTreatment(MedicalTreatment treatment) {
        if(treatment != null) {
            treatmentList.add(treatment);
            return true;
        }
        return false;
    }

    public static boolean addTreatmentHistory(TreatmentHistory history) {
        if(history != null) {
            treatmentHistoryList.add(history);
            return true;
        }
        return false;
    }

    // find the treatment history by ID
    public static TreatmentHistory getTreatmentHistoryByPatientId(String patientId) {
        return treatmentHistoryList.findFirst(th -> th.getPatientId().equals(patientId));
    }

    public static TreatmentHistory getTreatmentHistoryById(String treatmentId) {
        TreatmentHistory result = treatmentHistoryList.findFirst(th -> th.getTreatmentId().equals(treatmentId));
        if (result == null) {
            System.out.println("Treatment history not found for ID: " + treatmentId);
        } else {
            System.out.println("Treatment history found for ID: " + treatmentId);
        }
        return result;
    }

    public static boolean addMedicineList(String medicineName, String dosage, String frequency, 
                                           String duration, String method) {
        if (medicineName != null && !medicineName.isEmpty() && dosage != null && !dosage.isEmpty() &&
            frequency != null && !frequency.isEmpty() && duration != null && !duration.isEmpty() &&
            method != null && !method.isEmpty()) {

            MedicalTreatmentItem item = new MedicalTreatmentItem(medicineName, dosage, frequency, 
                                                                 duration, method);
            medicineList.add(item);
            System.out.println("Medicine item added successfully");
            return true;
        }
        return false;
    }
}
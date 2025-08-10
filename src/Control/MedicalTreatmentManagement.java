/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.MedicalTreatment;
import Entity.TreatmentHistory;

/**
* 
* @author User
*/

public class MedicalTreatmentManagement {
    
    //list to store medical treatment details
    private static final DynamicList<MedicalTreatment> treatmentList = new DynamicList<>();
    private static final DynamicList<TreatmentHistory> treatmentHistoryList = new DynamicList<>();

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

    // display treatment history by patient ID
    public static DynamicList<TreatmentHistory> getTreatmentHistoryByPatientIdList(String patientId) {
        return treatmentHistoryList.findAll(th -> th.getPatientId().equals(patientId));
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

    public static DynamicList<MedicalTreatment> getTreatmentList() {
        return treatmentList;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.TreatmentHistory;

/**
 *
 * @author User
 */
public class TreatmentHistoryManagement {
    
    //list to store medical treatment details
    private static final DynamicList<TreatmentHistory> treatmentHistoryList = new DynamicList<>();
    
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

    //get the consultation ID from the treatment history
    public static String getConsultationIdFromHistory(String treatmentId) {
        TreatmentHistory history = getTreatmentHistoryById(treatmentId);
        if (history != null) {
            return history.getConsultationId();
        }
        return null;
    }
    
}

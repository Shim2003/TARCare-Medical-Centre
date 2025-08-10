/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.MedicalTreatment;

/**
* 
* @author User
*/

public class MedicalTreatmentManagement {
    
    //list to store medical treatment details
    private static final DynamicList<MedicalTreatment> treatmentList = new DynamicList<>();

    //create a new medical treatment
    public static boolean addMedicalTreatment(MedicalTreatment treatment) {
        if(treatment != null) {
            treatmentList.add(treatment);
            return true;
        }
        return false;
    }

    

    public static DynamicList<MedicalTreatment> getMedicalTreatmentList() {
        return treatmentList;
    }

    // Find treatment by ID
    public static MedicalTreatment findTreatmentById(String treatmentId) {
        return treatmentList.findFirst(treatment -> treatment.getTreatmentId().equals(treatmentId));
    }
    
    public static DynamicList<MedicalTreatment> getTreatmentList() {
        return treatmentList;
    }
}
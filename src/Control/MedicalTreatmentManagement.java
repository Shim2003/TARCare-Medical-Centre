/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import Entity.MedicalTreatment;
import java.util.Date;;

/**
* 
* @author User
*/

public class MedicalTreatmentManagement {
    
    //list to store medical treatment details
    private static final MyList<MedicalTreatment> treatmentList = new DynamicList<>();

    //create a new medical treatment
    public static boolean addMedicalTreatment(MedicalTreatment treatment) {
        if(treatment != null) {
            treatmentList.add(treatment);
            return true;
        }
        return false;
    }

    public static MyList<MedicalTreatment> getMedicalTreatmentList() {
        return treatmentList;
    }

    // Find treatment by ID
    public static MedicalTreatment findTreatmentById(String treatmentId) {
        return treatmentList.findFirst(treatment -> treatment.getTreatmentId().equals(treatmentId));
    }
    
    public static MyList<MedicalTreatment> getTreatmentList() {
        return treatmentList;
    }
    
    // find the treatment history by ID
    public static MedicalTreatment getTreatmentHistoryByPatientId(String patientId) {
        return treatmentList.findFirst(th -> th.getPatientId().equals(patientId));
    }

    // display treatment history by patient ID
    public static MyList<MedicalTreatment> getTreatmentHistoryByPatientIdList(String patientId) {
        return treatmentList.findAll(th -> th.getPatientId().equals(patientId));
    }

    public static MedicalTreatment getTreatmentHistoryById(String treatmentId) {
        MedicalTreatment result = treatmentList.findFirst(th -> th.getTreatmentId().equals(treatmentId));
        if (result == null) {
            System.out.println("Treatment history not found for ID: " + treatmentId);
        } else {
            System.out.println("Treatment history found for ID: " + treatmentId);
        }
        return result;
    }

    //get the consultation ID from the treatment history
    public static String getConsultationIdFromHistory(String treatmentId) {
        MedicalTreatment history = getTreatmentHistoryById(treatmentId);
        if (history != null) {
            return history.getConsultationId();
        }
        return null;
    }
    
    // remove treatment history by ID
    public static boolean removeTreatmentHistoryById(String treatmentId) {
        return treatmentList.removeIf(th -> th.getTreatmentId().equals(treatmentId));
    }

    //generate monthly report
    public static MyList<MedicalTreatment> generateMonthlyReport(int month) {
        return treatmentList.findAll(th -> th.getMonth().equals(month));
    }

    // retrieve the existing treatment history by year
    public static MyList<Integer> getAvailableYears() {
        DynamicList<Integer> availableYears = new DynamicList<>();
        for (int i = 0; i < MedicalTreatmentManagement.getTreatmentList().size(); i++) {
            MedicalTreatment treatment = MedicalTreatmentManagement.getTreatmentList().get(i);
            int year = treatment.getTreatmentDate().getYear() + 1900;
            if (!availableYears.contains(year)) {
                availableYears.add(year);
            }
        }
        return availableYears;
    }

    public static MyList<MedicalTreatment> getMonthlyTreatments(int year, int month) {
        MyList<MedicalTreatment> monthlyTreatments = new DynamicList<>();
        MyList<MedicalTreatment> allTreatmentsHistory = MedicalTreatmentManagement.getTreatmentList();

        for (int i = 0; i < allTreatmentsHistory.size(); i++) {
            MedicalTreatment treatHistory = allTreatmentsHistory.get(i);
            Date treatmentDate = treatHistory.getTreatmentDate();

            if(treatmentDate != null) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(treatmentDate);

                int treatmentYear = cal.get(java.util.Calendar.YEAR);
                int treatmentMonth = cal.get(java.util.Calendar.MONTH) + 1; // the month value start form 0

                if (treatmentYear == year && treatmentMonth == month) {
                    monthlyTreatments.add(treatHistory);
                }

            }
        }
        return monthlyTreatments;
    }

    // get the current treatment ID
    public static String getCurrentTreatmentId() {
        if (treatmentList.isEmpty()) {
            return null;
        }
        return treatmentList.get(treatmentList.size() - 1).getTreatmentId();
    }
}
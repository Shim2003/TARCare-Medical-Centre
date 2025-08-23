/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import Entity.MedicalTreatment;
import Entity.TreatmentHistory;
import java.util.Date;

/**
* 
* @author User
*/

public class MedicalTreatmentManagement {
    
    //list to store medical treatment details
    private static final MyList<MedicalTreatment> treatmentList = new DynamicList<>();
    private static final MyList<TreatmentHistory> treatmentHistoryList = new DynamicList<>();

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

    public static MyList<TreatmentHistory> getTreatmentHistoryList() {
        return treatmentHistoryList;
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
    public static MyList<TreatmentHistory> getTreatmentHistoryByPatientIdList(String patientId) {
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
    
    // remove treatment history by ID
    public static boolean removeTreatmentHistoryById(String treatmentId) {
        return treatmentHistoryList.removeIf(th -> th.getTreatmentId().equals(treatmentId));
    }

    //generate monthly report
    public static MyList<TreatmentHistory> generateMonthlyReport(int month) {
        return treatmentHistoryList.findAll(th -> th.getMonth().equals(month));
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

    public static MyList<TreatmentHistory> getMonthlyTreatments(int year, int month) {
        MyList<TreatmentHistory> monthlyTreatments = new DynamicList<>();
        MyList<TreatmentHistory> allTreatmentsHistory = MedicalTreatmentManagement.getTreatmentHistoryList();

        for (int i = 0; i < allTreatmentsHistory.size(); i++) {
            TreatmentHistory treatHisotry = allTreatmentsHistory.get(i);
            Date treatmentDate = treatHisotry.getTreatmentDate();

            if(treatmentDate != null) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(treatmentDate);

                int treatmentYear = cal.get(java.util.Calendar.YEAR);
                int treatmentMonth = cal.get(java.util.Calendar.MONTH) + 1; // the month value start form 0

                if (treatmentYear == year && treatmentMonth == month) {
                    monthlyTreatments.add(treatHisotry);
                }

            }
        }
        return monthlyTreatments;
    }

    // method to call in order to set the treatment history status
    public static void setDistributionStatusToCompleted(String treatmentId) {
        if (distributionStatus(treatmentId, "Completed")) {
            System.out.println("Distribution status set to Completed for treatment ID: " + treatmentId);
        } else {
            System.out.println("Failed to set distribution status for treatment ID: " + treatmentId);
        }
    }

    // set the distribution status to completed
    private static boolean distributionStatus(String treatmentId, String status) {
        TreatmentHistory history = getTreatmentHistoryById(treatmentId);
        if (history != null) {
            history.setDistributionStatus(status);
            return true;
        }
        return false;
    }
}
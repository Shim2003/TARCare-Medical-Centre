/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import Entity.MedicalTreatment;
import Entity.MedicalTreatmentItem;
import java.util.Date;

/**
* 
* @author User
*/

public class MedicalTreatmentManagement {
    
    //list to store medical treatment details
    private static MyList<MedicalTreatment> treatmentList = new DynamicList<>();
    private static final MyList<MedicalTreatmentItem> medicineList = new DynamicList<>();
    private static MyList<MedicalTreatment> backupList = null; // store backup before deletion

    // initialize the medical treatment list
    public static void initMedicalTreatmentList() {
        treatmentList.clear();
    }

    // add the medicine to list
    public static void addMedicine(MedicalTreatmentItem item) {
        medicineList.add(item);
    }

    // get the medicine list
    public static MyList<MedicalTreatmentItem> getMedicineList() {
        return medicineList;
    }

    // get the medicine list size
    public static int getMedicineListSize() {
        return medicineList.size();
    }

    // check if the medicine list is empty
    public static boolean isMedicineListEmpty() {
        return medicineList.isEmpty();
    }

    public static MyList<MedicalTreatmentItem> getMedicineList(MedicalTreatment treatment) {
        if (treatment != null && treatment.getMedicineList() != null) {
            return treatment.getMedicineList();
        }
        return new DynamicList<>(); // never return null
    }

    //create a new medical treatment
    public static boolean addMedicalTreatment(MedicalTreatment treatment) {
        if(treatment != null && !isTreatmentExists(treatment.getTreatmentId())) {
            treatmentList.add(treatment);
            return true;
        }
        System.out.println("Failed to add treatment. Treatment may already exist or is null.");
        return false;
    }

    // get the treatment by predefined list and index
    public static MedicalTreatment getTreatment(MyList<MedicalTreatment> list, int index) {
        return list.get(index);
    }

    //check the list whether it is empty by passing a list as parameter
    public static boolean isTreatmentListEmpty(MyList<MedicalTreatment> list) {
        return list.isEmpty();
    }

    // Check if a treatment has any prescribed medicines
    public static boolean hasMedicines(MedicalTreatment treatment) {
        return treatment != null 
            && treatment.getMedicineList() != null 
            && !treatment.getMedicineList().isEmpty();
    }

    // Get the number of medicines in a treatment
    public static int getMedicineCount(MedicalTreatment treatment) {
        if (treatment != null && treatment.getMedicineList() != null) {
            return treatment.getMedicineList().size();
        }
        return 0;
    }

    // Get a medicine by index
    public static MedicalTreatmentItem getMedicineByIndex(MedicalTreatment treatment, int index) {
        if (treatment != null && treatment.getMedicineList() != null) {
            if (index >= 0 && index < treatment.getMedicineList().size()) {
                return treatment.getMedicineList().get(index);
            }
        }
        return null;
    }

    // get size with certain list in the parameter
    public static int getTreatmentSize(MyList<MedicalTreatment> list) {
        return list.size();
    }

    // get the treatment list by a filtered list and index
    public static MedicalTreatment getTreatmentByIndex(MyList<MedicalTreatment> list, int index) {
        return list.get(index);
    }

    // get all the medical treatment list
    public static MyList<MedicalTreatment> getMedicalTreatmentList() {
        return treatmentList;
    }

    // Find treatment by ID
    public static MedicalTreatment findTreatmentById(String treatmentId) {
        return treatmentList.findFirst(treatment -> treatment.getTreatmentId().equals(treatmentId));
    }

    //check the capacity of the treatment list
    public static int getTreatmentListSize() {
        return treatmentList.size();
    }    
    
    // find the treatment history by ID
    public static MedicalTreatment getTreatmentHistoryByPatientId(String patientId) {
        return treatmentList.findFirst(th -> th.getPatientId().equals(patientId));
    }

    // display treatment history by patient ID
    public static MyList<MedicalTreatment> getTreatmentHistoryByPatientIdList(String patientId) {
        return treatmentList.findAll(th -> th.getPatientId().equals(patientId));
    }

    // get the treatment history by ID
    public static MedicalTreatment getTreatmentHistoryById(String treatmentId) {
        MedicalTreatment result = treatmentList.findFirst(th -> th.getTreatmentId().equals(treatmentId));
        if (result == null) {
            System.out.println("Treatment history not found for ID: " + treatmentId);
        } else {
            System.out.println("Treatment history found for ID: " + treatmentId);
        }
        return result;
    }
    
    // remove treatment history by ID
    public static boolean removeTreatmentHistoryById(String treatmentId) {
        return treatmentList.removeIf(th -> th.getTreatmentId().equals(treatmentId));
    }

    // generate monthly report
    public static MyList<MedicalTreatment> generateMonthlyReport(int month) {
        return treatmentList.findAll(th -> th.getMonth().equals(month));
    }

    // find the treatment list based on index
    public static boolean isTreatmentExists(String treatmentId) {
        int index = treatmentList.findIndex(t -> t.getTreatmentId().equals(treatmentId));
        return index != -1; // it will return true if treatment exists
    }

    //check if the treatment list is empty
    public static boolean isTreatmentListEmpty() {
        return treatmentList.isEmpty();
    }

    // retrieve the existing treatment history by year
    public static MyList<Integer> getAvailableYears() {
        DynamicList<Integer> availableYears = new DynamicList<>();
        for (int i = 0; i < MedicalTreatmentManagement.getMedicalTreatmentList().size(); i++) {
            MedicalTreatment treatment = MedicalTreatmentManagement.getMedicalTreatmentList().get(i);
            int year = treatment.getTreatmentDate().getYear() + 1900;
            if (!availableYears.contains(year)) {
                availableYears.add(year);
            }
        }
        return availableYears;
    }

    // being called at UI
    public static boolean hasTreatmentHistory() {
        return !getAvailableYears().isEmpty();
    }

    // retrieve the existing treatment history by month and year
    public static MyList<MedicalTreatment> getMonthlyTreatments(int year, int month) {
        MyList<MedicalTreatment> monthlyTreatments = new DynamicList<>();
        MyList<MedicalTreatment> allTreatmentsHistory = MedicalTreatmentManagement.getMedicalTreatmentList();

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

    //delete treatment by ID
    public static boolean deleteTreatmentById(String treatmentId) {
        return treatmentList.removeIf(t -> t.getTreatmentId().equals(treatmentId));
    }

    // delete all treatment history by patient ID
    public static boolean deleteAllTreatmentsByPatientId(String patientId) {
        return treatmentList.removeIf(t -> t.getPatientId().equals(patientId));
    }

    // delete all treatment history
    public static void deleteAllTreatments() {
        treatmentList.clear();
    }

    //display the treatment history that is successful in the outcome based on list received
    public static MyList<MedicalTreatment> getSuccessfulTreatmentHistory(int year, int month) {
        MyList<MedicalTreatment> treatmentHistory = getMonthlyTreatments(year, month);
        MyList<MedicalTreatment> successfulList = new DynamicList<>();
        for (int i = 0; i < treatmentHistory.size(); i++) {
            MedicalTreatment treatment = treatmentHistory.get(i);
            if (treatment.getTreatmentOutcome().equalsIgnoreCase("Successful")) {
                successfulList.add(treatment);
            }
        }
        return successfulList;
    }

    //display the treatment history that needs follow up in the outcome.
    public static MyList<MedicalTreatment> getFollowUpTreatmentHistory(int year, int month) {
        MyList<MedicalTreatment> treatmentHistory = getMonthlyTreatments(year, month);
        MyList<MedicalTreatment> followUpList = new DynamicList<>();
        for (int i = 0; i < treatmentHistory.size(); i++) {
            MedicalTreatment treatment = treatmentHistory.get(i);
            if (treatment.getTreatmentOutcome().equalsIgnoreCase("Follow up")) {
                followUpList.add(treatment);
            }
        }
        return followUpList;
    }

    //display the treatment history that is failed in the outcome.
    public static MyList<MedicalTreatment> getFailedTreatmentHistory(int year, int month) {
        MyList<MedicalTreatment> treatmentHistory = getMonthlyTreatments(year, month);
        MyList<MedicalTreatment> failList = new DynamicList<>();
        for (int i = 0; i < treatmentHistory.size(); i++) {
            MedicalTreatment treatment = treatmentHistory.get(i);
            if (treatment.getTreatmentOutcome().equalsIgnoreCase("Failed")) {
                failList.add(treatment);
            }
        }
        return failList;
    }

    //display the treatment history that is ongoing in the outcome.
    public static MyList<MedicalTreatment> getOngoingTreatmentHistory(int year, int month) {
        MyList<MedicalTreatment> treatmentHistory = getMonthlyTreatments(year, month);
        MyList<MedicalTreatment> ongoingList = new DynamicList<>();
        for (int i = 0; i < treatmentHistory.size(); i++) {
            MedicalTreatment treatment = treatmentHistory.get(i);
            if (treatment.getTreatmentOutcome().equalsIgnoreCase("Ongoing")) {
                ongoingList.add(treatment);
            }
        }
        return ongoingList;
    }

    //calculate the percentage of outcome by month and year
    public static double calculatePercentageOfSuccessOutcome(int year, int month) {
        MyList<MedicalTreatment> treatmentHistory = getMonthlyTreatments(year, month);
        int count = 0;

        for (int i = 0; i < treatmentHistory.size(); i++) {
            MedicalTreatment treatment = treatmentHistory.get(i);
            if (treatment.getTreatmentOutcome().equalsIgnoreCase("Successful")) {
                count++;
            }
        }
        int total = treatmentHistory.size();
        double percentage = total == 0 ? 0 : ((double) count / total) * 100;
        return roundTwoDecimals(percentage);
    }

    //calculate the percentage of outcome by month and year
    public static double calculatePercentageOfFollowUpOutcome(int year, int month) {
        MyList<MedicalTreatment> treatmentHistory = getMonthlyTreatments(year, month);
        int count = 0;

        for (int i = 0; i < treatmentHistory.size(); i++) {
            MedicalTreatment treatment = treatmentHistory.get(i);
            if (treatment.getTreatmentOutcome().equalsIgnoreCase("Follow up")) {
                count++;
            }
        }
        int total = treatmentHistory.size();
        double percentage = total == 0 ? 0 : ((double) count / total) * 100;
        return roundTwoDecimals(percentage);
    }

    //calculate the percentage of outcome by month and year
    public static double calculatePercentageOfOngoingOutcome(int year, int month) {
        MyList<MedicalTreatment> treatmentHistory = getMonthlyTreatments(year, month);
        int count = 0;

        for (int i = 0; i < treatmentHistory.size(); i++) {
            MedicalTreatment treatment = treatmentHistory.get(i);
            if (treatment.getTreatmentOutcome().equalsIgnoreCase("Ongoing")) {
                count++;
            }
        }
        int total = treatmentHistory.size();
        double percentage = total == 0 ? 0 : ((double) count / total) * 100;
        return roundTwoDecimals(percentage);
    }

    //calculate the percentage of outcome by month and year
    public static double calculatePercentageOfFailOutcome(int year, int month) {
        MyList<MedicalTreatment> treatmentHistory = getMonthlyTreatments(year, month);
        int count = 0;

        for (int i = 0; i < treatmentHistory.size(); i++) {
            MedicalTreatment treatment = treatmentHistory.get(i);
            if (treatment.getTreatmentOutcome().equalsIgnoreCase("Failed")) {
                count++;
            }
        }
        int total = treatmentHistory.size();
        double percentage = total == 0 ? 0 : ((double) count / total) * 100;
        return roundTwoDecimals(percentage);
    }

    // round to 2 decimal places
    public static double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }


    // Get all medical treatment list that need to follow up
    public static MyList<MedicalTreatment> getFollowUpList() {
        MyList<MedicalTreatment> treatmentHistory = getMedicalTreatmentList();
        MyList<MedicalTreatment> followUpList = new DynamicList<>();
        if (treatmentHistory != null) {
            for (int i = 0; i < treatmentHistory.size(); i++) {
                MedicalTreatment treatment = treatmentHistory.get(i);
                if ("Follow up".equalsIgnoreCase(treatment.getTreatmentOutcome())) {
                    followUpList.add(treatment);
                }
            }
        }
        return followUpList; // returns empty list if none found
    }

    // Get all medical treatment list that succeeded
    public static MyList<MedicalTreatment> getSuccessList() {
        MyList<MedicalTreatment> treatmentHistory = getMedicalTreatmentList();
        MyList<MedicalTreatment> successList = new DynamicList<>();
        if (treatmentHistory != null) {
            for (int i = 0; i < treatmentHistory.size(); i++) {
                MedicalTreatment treatment = treatmentHistory.get(i);
                if ("Successful".equalsIgnoreCase(treatment.getTreatmentOutcome())) {
                    successList.add(treatment);
                }
            }
        }
        return successList;
    }

    // Get all medical treatment list that are ongoing
    public static MyList<MedicalTreatment> getOngoingList() {
        MyList<MedicalTreatment> treatmentHistory = getMedicalTreatmentList();
        MyList<MedicalTreatment> ongoingList = new DynamicList<>();
        if (treatmentHistory != null) {
            for (int i = 0; i < treatmentHistory.size(); i++) {
                MedicalTreatment treatment = treatmentHistory.get(i);
                if ("Ongoing".equalsIgnoreCase(treatment.getTreatmentOutcome())) {
                    ongoingList.add(treatment);
                }
            }
        }
        return ongoingList;
    }

    // Get all medical treatment list that failed
    public static MyList<MedicalTreatment> getFailedList() {
        MyList<MedicalTreatment> treatmentHistory = getMedicalTreatmentList();
        MyList<MedicalTreatment> failedList = new DynamicList<>();
        if (treatmentHistory != null) {
            for (int i = 0; i < treatmentHistory.size(); i++) {
                MedicalTreatment treatment = treatmentHistory.get(i);
                if ("Failed".equalsIgnoreCase(treatment.getTreatmentOutcome())) {
                    failedList.add(treatment);
                }
            }
        }
        return failedList;
    }

    public static MyList<MedicalTreatment> getTreatmentHistoryByYearAndMonth(int year, int month) {
        MyList<MedicalTreatment> allTreatments = getMedicalTreatmentList();
        MyList<MedicalTreatment> filteredList = new DynamicList<>();

        if (allTreatments != null) {
            for (int i = 0; i < allTreatments.size(); i++) {
                MedicalTreatment t = allTreatments.get(i);
                if (t.getTreatmentDate() != null) {
                    int treatmentYear = t.getTreatmentDate().getYear() + 1900;
                    int treatmentMonth = t.getTreatmentDate().getMonth() + 1;
                    if (treatmentYear == year && treatmentMonth == month) {
                        filteredList.add(t);
                    }
                }
            }
        }

        // Sort by day of the month ascending
        for (int i = 0; i < filteredList.size() - 1; i++) {
            for (int j = i + 1; j < filteredList.size(); j++) {
                if (filteredList.get(i).getTreatmentDate().getDate() > filteredList.get(j).getTreatmentDate().getDate()) {
                    MedicalTreatment temp = filteredList.get(i);
                    filteredList.replace(i, filteredList.get(j));
                    filteredList.replace(j, temp);
                }
            }
        }

        return filteredList; // empty list if none found
    }

    // set the follow up date for a treatment
    public static void setFollowUpDate(MedicalTreatment treatment, Date followUpDate) {
        treatment.setFollowUpDate(followUpDate);
    }
    
    // set the treatment outcome for a treatment
    public static void setTreatmentOutcome(MedicalTreatment treatment, String outcome) {
        treatment.setTreatmentOutcome(outcome);
    }

    // set the treatment status for a treatment
    public static void setTreatmentStatus(MedicalTreatment treatment, String status) {
        treatment.setTreatmentStatus(status);
    }

    // set the treatment status for a treatment
    public static void setTreatmentNote(MedicalTreatment treatment, String note) {
        treatment.setNotes(note);
    }

    // clone the treatment list
    public static void cloneTreatmentList() {
        backupList = treatmentList.clone();
    }

    // restore the treatment list
    public static void restoreTreatmentList() {
        if (backupList != null) {
            treatmentList = backupList.clone(); // restore
            System.out.println(">> Treatment history has been restored from backup.");
        } else {
            System.out.println(">> No backup available to restore.");
        }
    }   
}
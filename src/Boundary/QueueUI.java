/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.MyList;
import Control.PatientManagement;
import Control.QueueControl;
import DAO.QueueOperationResult;
import DAO.NextPatientResult;
import DAO.RemovalResult;
import Entity.Patient;
import Entity.QueueEntry;
import Utility.UtilityClass;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class QueueUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void staffQueueMenu() {
        while (true) {
            System.out.println("\n--- Staff Queue Management Menu ---");
            QueueUI.displayCurrentQueue();
            System.out.println("1. Queue for Consultation");
            System.out.println("2. Get Next Queue Patient");
            System.out.println("3. Display Queue By Status");
            System.out.println("4. Remove Queue Records");
            System.out.println("5. Generate Queue Reports");
            System.out.println("6. Back to Staff Main Menu");

            System.out.print("Enter your choice (1-6): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    startQueue();
                    break;
                case "2":
                    getNextInQueue();
                    break;
                case "3":
                    displayQueueByStatus();
                    break;
                case "4":
                    removeQueueRecord();
                    break;
                case "5":
                    generateQueueReports();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-6.");
            }
        }
    }

    public static void patientQueueMenu() {
        while (true) {
            System.out.println("\n--- Patient Queue Management Menu ---");
            QueueUI.displayCurrentQueue();
            System.out.println("1. Queue for Consultation");
            System.out.println("2. Cancel My Queue");
            System.out.println("3. Back to Patient Main Menu");

            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    QueueUI.startQueue();
                    break;
                case "2":
                    QueueUI.cancelQueue();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    public static void startQueue() {
        System.out.println("\n=== Join Consultation Queue ===");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();

        if (patientId.isEmpty()) {
            System.out.println("Error: Patient ID cannot be empty.");
            UtilityClass.pressEnterToContinue();
            return;
        }

        QueueOperationResult result = QueueControl.addInQueue(patientId);

        if (result.isSuccess()) {
            QueueEntry newQueue = result.getQueueEntry();
            // Get patient name for display
            Patient patient = PatientManagement.findPatientById(patientId);
            String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";

            System.out.println("\n--- Queue Success ---");
            System.out.println("Queue Number: " + newQueue.getQueueNumber());
            System.out.println("Patient: " + patientName + " (" + patientId + ")");
            System.out.println("Status: " + newQueue.getStatus());
            System.out.println("Check-in: " + UtilityClass.formatDate(newQueue.getCheckInTime()));

            // Show position in queue
            MyList<QueueEntry> waitingList = QueueControl.getQueueListByStatus(UtilityClass.statusWaiting);
            int waitingCount = QueueControl.getWaitingPatientsCount();
            System.out.println("Patients waiting: " + waitingCount);

        } else {
            System.out.println("\nFailed to join queue.");
            System.out.println("Reason: " + result.getMessage());
        }

        UtilityClass.pressEnterToContinue();
    }

    public static void getNextInQueue() {
        System.out.println("\n========================================");
        System.out.println("         SERVE NEXT PATIENT");
        System.out.println("========================================");

        if ((QueueControl.getReadyToConsultCount() + QueueControl.getCurrentServingPatientCount()) >= 3) {
            System.out.println("STATUS: Full Consultation");
            UtilityClass.pressEnterToContinue();
            return;
        }

        // Get waiting patients
        MyList<QueueEntry> waitingPatients = Control.QueueControl.getQueueListByStatus(
                Utility.UtilityClass.statusWaiting
        );

        if (QueueControl.isQueueListByStatusEmpty(Utility.UtilityClass.statusWaiting)) {
            System.out.println("STATUS: No patients waiting in queue");
            UtilityClass.pressEnterToContinue();
            return;
        }

        // Display current queue
        int waitingPatientsCount = QueueControl.getWaitingPatientsCount();
        System.out.println("\n+--------------------------------------+");
        System.out.println("|     CURRENT WAITING LIST             |");
        System.out.println("|     (" + waitingPatientsCount + " patients)                     |");
        System.out.println("+--------------------------------------+");
        int index = 1;
        for (QueueEntry entry : waitingPatients) {
            System.out.printf("| %2d. %-32s |%n", index++, entry.toString());
        }
        System.out.println("+--------------------------------------+");

        // Get and serve next patient
        NextPatientResult result = Control.QueueControl.getNextInQueue();

        if (result.isSuccess()) {
            QueueEntry nextPatient = result.getNextPatient();
            System.out.println("\n+--------------------------------------+");
            System.out.println("|            NOW SERVING               |");
            System.out.println("+--------------------------------------+");
            System.out.printf("| %-36s |%n", nextPatient.toString());
            System.out.println("+--------------------------------------+");
            System.out.println("| Patient called for consultation      |");
            System.out.println("+--------------------------------------+");
        } else {
            System.out.println("\nERROR: " + result.getMessage());
        }

        System.out.println("\n========================================");
        UtilityClass.pressEnterToContinue();
    }

    public static void displayCurrentQueue() {
        System.out.println("---      Current Queue Served      ---");

        MyList<QueueEntry> servingList = QueueControl.getQueueListByStatus(Utility.UtilityClass.statusReadyToConsult);

        if (QueueControl.isQueueListByStatusEmpty(Utility.UtilityClass.statusReadyToConsult)) {
            String msg = "No patients are currently being served.";
            int width = msg.length();
            String border = "+" + "-".repeat(width + 2) + "+";

            System.out.println(border);
            System.out.printf("| %s |\n", msg);
            System.out.println(border);
            return;
        }

        String header = "+-----------------------------------+";
        System.out.println(header);
        System.out.printf("| %-15s | %-15s |\n", "Queue ID", "Patient Name");
        System.out.println(header);

        int index = 1;
        for (QueueEntry cs : servingList) {
            String patientName = PatientManagement.getPatientNameById(cs.getPatientId());
            System.out.printf("| %-15s | %-15s |\n", cs.getQueueNumber(), patientName);
            index++;
        }
        System.out.println(header);
    }

    public static void displayQueueByStatus() {
        System.out.println("\n\n=== Display Queue By Status ===");
        System.out.println("Select Status : ");
        System.out.println("1. " + Utility.UtilityClass.statusWaiting);
        System.out.println("2. " + Utility.UtilityClass.statusReadyToConsult);
        System.out.println("3. " + Utility.UtilityClass.statusConsulting);
        System.out.println("4. " + Utility.UtilityClass.statusCompleted);
        System.out.print("Enter your choice (1-3): ");

        String choice = scanner.nextLine();
        String selectedStatus = null;

        switch (choice) {
            case "1":
                selectedStatus = Utility.UtilityClass.statusWaiting;
                break;
            case "2":
                selectedStatus = Utility.UtilityClass.statusReadyToConsult;
                break;
            case "3":
                selectedStatus = Utility.UtilityClass.statusConsulting;
                break;
            case "4":
                selectedStatus = Utility.UtilityClass.statusCompleted;
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                UtilityClass.pressEnterToContinue();
                return;
        }

        MyList<QueueEntry> filteredList = Control.QueueControl.getQueueListByStatus(selectedStatus);

        if (QueueControl.isQueueListByStatusEmpty(selectedStatus)) {
            System.out.println("No queue entries found with status: " + selectedStatus);
        } else {
            int filteredCount = QueueControl.getQueueListByStatusCount(selectedStatus);
            System.out.println("\nQueue Entries with status: " + selectedStatus);
            System.out.println("Total entries: " + filteredCount);
            System.out.println("-".repeat(50));

            int index = 1;
            for (QueueEntry entry : filteredList) {
                System.out.printf("%2d. %s%n", index++, entry.toString());
            }
        }

        UtilityClass.pressEnterToContinue();
    }

    public static void cancelQueue() {
        System.out.print("Enter your Patient ID to cancel your queue entry: ");
        String patientId = scanner.nextLine();

        RemovalResult result = Control.QueueControl.removeQueueByPatientId(patientId);

        if (result.isSuccess()) {
            System.out.println("Your queue entry has been successfully cancelled.");
        } else {
            System.out.println("Cancellation Failed: " + result.getMessage());
        }

        UtilityClass.pressEnterToContinue();
    }

    public static void removeQueueRecord() {
        int choice = -1;

        while (choice < 1 || choice > 4) {
            System.out.println("\n--- Remove Queue Record ---");
            System.out.println("1. Remove Specific Queue Record");
            System.out.println("2. Remove Queue Records by Status");
            System.out.println("3. Remove All Queue Records");
            System.out.println("4. Cancel");

            System.out.print("Enter your choice (1-4): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice. Please select a number between 1 and 4.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        switch (choice) {
            case 1:
                removeSpecificQueueRecord();
                break;
            case 2:
                removeQueueRecordsByStatus();
                break;
            case 3:
                removeAllQueueRecords();
                break;
            case 4:
                System.out.println("Operation cancelled.");
                UtilityClass.pressEnterToContinue();
                break;
        }
    }

    private static void removeSpecificQueueRecord() {
        System.out.print("Enter Queue ID: ");
        String queueId = scanner.nextLine();

        RemovalResult result = QueueControl.removeQueueById(queueId);

        if (result.isSuccess()) {
            System.out.println("Remove Success: " + result.getMessage());
        } else {
            System.out.println("Removal Failed: " + result.getMessage());
        }

        UtilityClass.pressEnterToContinue();
    }

    private static void removeQueueRecordsByStatus() {
        System.out.println("\n--- Remove Queue Records by Status ---");
        System.out.println("Select Status to Remove:");
        System.out.println("1. " + Utility.UtilityClass.statusWaiting);
        System.out.println("2. " + Utility.UtilityClass.statusConsulting);
        System.out.println("3. " + Utility.UtilityClass.statusCompleted);
        System.out.print("Enter your choice (1-3): ");

        String choice = scanner.nextLine();
        String selectedStatus = null;

        switch (choice) {
            case "1":
                selectedStatus = Utility.UtilityClass.statusWaiting;
                break;
            case "2":
                selectedStatus = Utility.UtilityClass.statusConsulting;
                break;
            case "3":
                selectedStatus = Utility.UtilityClass.statusCompleted;
                break;
            default:
                System.out.println("Invalid choice. Operation cancelled.");
                UtilityClass.pressEnterToContinue();
                return;
        }

        RemovalResult result = QueueControl.removeByStatus(selectedStatus);

        if (result.isSuccess()) {
            System.out.println("Successfully Removed: " + result.getMessage());
        } else {
            System.out.println("Removal Failed: " + result.getMessage());
        }

        UtilityClass.pressEnterToContinue();
    }

    private static void removeAllQueueRecords() {

        if (QueueControl.isQueueEmpty()) {
            System.out.println("No queue records to remove.");
            UtilityClass.pressEnterToContinue();
            return;
        }

        int totalRecords = QueueControl.getTotalQueueCount();

        System.out.println("\n--- Remove All Queue Records ---");
        System.out.println("Total queue records: " + totalRecords);
        System.out.print("Are you sure you want to remove ALL queue records? This action cannot be undone. (Y/N): ");

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("No input provided. Removal cancelled.");
            UtilityClass.pressEnterToContinue();
            return;
        }

        char confirm = input.toUpperCase().charAt(0);

        if (confirm == 'Y') {
            System.out.print("Please type 'CONFIRM' to proceed with removing all records: ");
            String confirmation = scanner.nextLine();

            if ("CONFIRM".equals(confirmation)) {
                QueueControl.clearAllQueueRecords();
                System.out.println("Successfully removed all " + totalRecords + " queue records.");
            } else {
                System.out.println("Confirmation failed. Removal cancelled.");
            }
        } else {
            System.out.println("Removal of all queue records cancelled.");
        }

        UtilityClass.pressEnterToContinue();
    }

    public static void generateQueueReports() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           QUEUE REPORTING SYSTEM");
            System.out.println("=".repeat(50));
            System.out.println("1. Daily Queue Summary");
            System.out.println("2. Historical Queue Data");
            System.out.println("3. Back to Queue Menu");

            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    generateDailyQueueSummary();
                    break;
                case "2":
                    generateHistoricalQueueData();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    private static void generateDailyQueueSummary() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                  DAILY QUEUE SUMMARY");
        System.out.println("=".repeat(70));

        DAO.DailyQueueStats todayStats = QueueControl.getDailyQueueStats(new Date());

        if (todayStats.totalPatients == 0) {
            System.out.println("No queue entries found for today.");
            System.out.println("=".repeat(70));
            UtilityClass.pressEnterToContinue();
            return;
        }

        System.out.printf("Date: %s\n", UtilityClass.formatDate(new Date()));
        System.out.printf("Total Queue Entries: %d\n", todayStats.totalPatients);
        System.out.println("-".repeat(70));

        System.out.println("STATUS DISTRIBUTION:");
        System.out.printf("|- Waiting: %d (%.1f%%)\n", todayStats.waitingPatients,
                (double) todayStats.waitingPatients / todayStats.totalPatients * 100);
        System.out.printf("|- Ready To Consult: %d (%.1f%%)\n", todayStats.readyToConsultingPatients,
                (double) todayStats.readyToConsultingPatients / todayStats.totalPatients * 100);
        System.out.printf("|- Consulting: %d (%.1f%%)\n", todayStats.consultingPatients,
                (double) todayStats.consultingPatients / todayStats.totalPatients * 100);
        System.out.printf("|- Completed: %d (%.1f%%)\n", todayStats.completedPatients,
                todayStats.completionRate);

        // Visual representation
        System.out.println("\nVISUAL BREAKDOWN:");
        displayStatusBar("Waiting", todayStats.waitingPatients, todayStats.totalPatients);
        displayStatusBar("Ready To Consult", todayStats.readyToConsultingPatients, todayStats.totalPatients);
        displayStatusBar("Consulting", todayStats.consultingPatients, todayStats.totalPatients);
        displayStatusBar("Completed", todayStats.completedPatients, todayStats.totalPatients);

        // Performance indicator
        System.out.println("\nPERFORMANCE INDICATOR:");
        if (todayStats.completionRate >= 80) {
            System.out.println("Excellent - High completion rate");
        } else if (todayStats.completionRate >= 60) {
            System.out.println("Good - Moderate completion rate");
        } else if (todayStats.completionRate >= 40) {
            System.out.println("Fair - Consider optimizing workflow");
        } else {
            System.out.println("Needs Attention - Low completion rate");
        }

        System.out.println("=".repeat(70));
        UtilityClass.pressEnterToContinue();
    }

    public static void generateHistoricalQueueData() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                    HISTORICAL QUEUE DATA");
        System.out.println("=".repeat(80));

        System.out.println("LAST 7 DAYS SUMMARY:");
        System.out.printf("%-12s | %-6s | %-7s | %-10s | %-9s  | %-10s\n",
                "Date", "Total", "Waiting", "Consulting", "Completed", "Rate");
        System.out.println("-".repeat(80));

        Calendar cal = Calendar.getInstance();

        for (int i = 6; i >= 0; i--) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -i);
            Date targetDate = cal.getTime();

            DAO.DailyQueueStats dayStats = QueueControl.getDailyQueueStats(targetDate);

            if (dayStats.totalPatients > 0) {
                System.out.printf("%-12s | %-6d | %-7d | %-10d | %-10d | %-9.1f%%\n",
                        UtilityClass.formatDate(targetDate),
                        dayStats.totalPatients,
                        dayStats.waitingPatients,
                        dayStats.consultingPatients,
                        dayStats.completedPatients,
                        dayStats.completionRate);
            } else {
                System.out.printf("%-12s | %-6s | %-7s | %-10s | %-8s | %-12s\n",
                        UtilityClass.formatDate(targetDate),
                        "0", "0", "0", "0", "N/A");
            }
        }

        // Weekly summary
        System.out.println("-".repeat(80));

        // Calculate weekly totals
        int weeklyTotal = 0, weeklyCompleted = 0;
        for (int i = 6; i >= 0; i--) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -i);
            DAO.DailyQueueStats dayStats = QueueControl.getDailyQueueStats(cal.getTime());
            weeklyTotal += dayStats.totalPatients;
            weeklyCompleted += dayStats.completedPatients;
        }

        double weeklyRate = weeklyTotal > 0 ? (double) weeklyCompleted / weeklyTotal * 100 : 0;

        System.out.println("WEEKLY SUMMARY:");
        System.out.printf("Total Patients (7 days): %d\n", weeklyTotal);
        System.out.printf("Completed Patients: %d\n", weeklyCompleted);
        System.out.printf("Weekly Completion Rate: %.1f%%\n", weeklyRate);

        System.out.println("=".repeat(80));
        UtilityClass.pressEnterToContinue();
    }

    private static void displayStatusBar(String status, int count, int total) {
        if (total == 0) {
            return;
        }

        double percentage = (double) count / total * 100;
        int barLength = (int) (percentage / 2);

        System.out.printf("%-12s [", status);
        for (int i = 0; i < barLength; i++) {
            System.out.print("#");
        }
        for (int i = barLength; i < 50; i++) {
            System.out.print(" ");
        }
        System.out.printf("] %d (%.1f%%)\n", count, percentage);
    }

    // Additional helper methods for queue status management
    public static void markPatientAsCompleted(String patientId) {
        boolean success = QueueControl.markAsCompleted(patientId);

        if (success) {
            System.out.println("Patient ID : " + patientId + " marked as COMPLETED.");
        } else {
            System.out.println("No consulting patient found with ID: " + patientId);
        }
    }

    public static void updateQueueStatus(String patientId) {
        boolean success = QueueControl.updateQueueStatus(patientId);

        if (success) {
            System.out.println("Queue status updated successfully for patient: " + patientId);
        } else {
            System.out.println("Invalid patient ID or patient not found in queue.");
        }
    }

}

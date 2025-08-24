/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.MyList;
import Control.QueueControl;
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

    public static void adminQueueMenu() {
        while (true) {
            System.out.println("\n--- Admin Queue Management Menu ---");
            QueueUI.displayCurrentQueue();
            System.out.println("1. Queue for Consultation");
            System.out.println("2. Get Next Queue Patient");
            System.out.println("3. Display Queue By Status");
            System.out.println("4. Remove Queue Records");
            System.out.println("5. Generate Queue Reports");
            System.out.println("6. Back to Admin Main Menu");

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
                    System.out.println("Invalid choice. Please enter 1-4.");
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

        System.out.print("Enter your Patient ID to join the queue: ");
        String patientId = scanner.nextLine();

        if (patientId == null || patientId.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a valid Identity Number.");
            return;
        }

        QueueEntry newQueue = QueueControl.addInQueue(patientId);

        if (newQueue != null) {
            System.out.println("You have successfully joined the queue.");
            System.out.println("Queue Details");
            System.out.println(newQueue.toString());
        } else {
            System.out.println("You have unsuccessfully joined the queue.");
        }

        UtilityClass.pressEnterToContinue();

    }

    public static void getNextInQueue() {
        System.out.println("\n========================================");
        System.out.println("         SERVE NEXT PATIENT");
        System.out.println("========================================");

        // Check if doctor is available
        if (Control.QueueControl.isFullConsulting()) {
            System.out.println("STATUS: Doctor is currently busy with other patients");
            System.out.println("        Please wait and try again later");
            UtilityClass.pressEnterToContinue();
            return;
        }

        // Get waiting patients
        MyList<QueueEntry> waitingPatients = Control.QueueControl.getQueueListByStatus(
                Utility.UtilityClass.statusWaiting
        );

        if (waitingPatients.isEmpty()) {
            System.out.println("STATUS: No patients waiting in queue");
            UtilityClass.pressEnterToContinue();
            return;
        }

        // Display current queue
        System.out.println("\n+--------------------------------------+");
        System.out.println("|     CURRENT WAITING LIST             |");
        System.out.println("|     (" + waitingPatients.size() + " patients)                     |");
        System.out.println("+--------------------------------------+");
        for (int i = 0; i < waitingPatients.size(); i++) {
            System.out.printf("| %2d. %-32s |%n", (i + 1), waitingPatients.get(i).toString());
        }
        System.out.println("+--------------------------------------+");

        // Get and serve next patient
        QueueEntry nextPatient = Control.QueueControl.getNextInQueue();

        if (nextPatient != null) {
            System.out.println("\n+--------------------------------------+");
            System.out.println("|            NOW SERVING               |");
            System.out.println("+--------------------------------------+");
            System.out.printf("| %-36s |%n", nextPatient.toString());
            System.out.println("+--------------------------------------+");
            System.out.println("| Patient called for consultation      |");
            System.out.println("+--------------------------------------+");
        } else {
            System.out.println("\nERROR: Unable to retrieve next patient");
        }

        System.out.println("\n========================================");
        UtilityClass.pressEnterToContinue();
    }

    public static void displayCurrentQueue() {
        System.out.println("--- Current Patient Being Served ---");

        QueueEntry current = QueueControl.currentConsulting();

        if (current != null) {
            String queueNum = "Current Queue Number: " + current.getQueueNumber();
            int width = queueNum.length();
            String border = "+" + "-".repeat(width + 2) + "+";

            System.out.println(border);
            System.out.printf("| %s |\n", queueNum);
            System.out.println(border);
        } else {
            String msg = "No patient is currently being served.";
            int width = msg.length();
            String border = "+" + "-".repeat(width + 2) + "+";

            System.out.println(border);
            System.out.printf("| %s |\n", msg);
            System.out.println(border);
        }
    }

    public static void displayQueueByStatus() {

        System.out.println("\n\n=== Display Queue By Status ===");
        System.out.println("Select Status : ");
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
                System.out.println("Invalid choice. Returning to main menu.");
                UtilityClass.pressEnterToContinue();
                return;
        }

        MyList<QueueEntry> filteredList = Control.QueueControl.getQueueListByStatus(selectedStatus);

        if (filteredList.isEmpty()) {
            System.out.println("No queue entries found with status: " + selectedStatus);
        } else {
            System.out.println("\nQueue Entries with status: " + selectedStatus);
            for (int i = 0; i < filteredList.size(); i++) {
                System.out.println(filteredList.get(i).toString());
            }
        }

        UtilityClass.pressEnterToContinue();

    }

    public static void cancelQueue() {

        System.out.print("Enter your Queue ID to cancel your queue entry: ");
        String queueId = scanner.nextLine();

        boolean removed = Control.QueueControl.removeFromQueue(queueId);

        if (removed) {
            System.out.println("Your queue entry has been successfully cancelled.");
        } else {
            System.out.println("No queue entry found with the given Identity Number.");
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

        boolean success = QueueControl.removeFromQueue(queueId);

        if (success) {
            System.out.println("Remove Success.");
        } else {
            System.out.println("Removal cancelled. Please check on the Patient Id");
            return;
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
                return;
        }

        if (QueueControl.removeByStatus(selectedStatus)) {
            System.out.println("Successfully cancelled.");
        } else {
            System.out.println("No queue entry found with the given Identity Number.");
        }

        UtilityClass.pressEnterToContinue();

    }

    private static void removeAllQueueRecords() {
        MyList<QueueEntry> allRecords = QueueControl.getQueueList();

        if (allRecords.isEmpty()) {
            System.out.println("No queue records to remove.");
            return;
        }

        System.out.println("\n--- Remove All Queue Records ---");
        System.out.println("Total queue records: " + allRecords.size());
        System.out.print("Are you sure you want to remove ALL queue records? This action cannot be undone. (Y/N): ");

        char confirm = scanner.nextLine().toUpperCase().charAt(0);

        if (confirm == 'Y') {
            System.out.print("Please type 'CONFIRM' to proceed with removing all records: ");
            String confirmation = scanner.nextLine();

            if ("CONFIRM".equals(confirmation)) {
                int totalRecords = allRecords.size();
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
        System.out.printf("|- Consulting: %d (%.1f%%)\n", todayStats.consultingPatients,
                (double) todayStats.consultingPatients / todayStats.totalPatients * 100);
        System.out.printf("|- Completed: %d (%.1f%%)\n", todayStats.completedPatients,
                todayStats.completionRate);

        // Visual representation
        System.out.println("\nVISUAL BREAKDOWN:");
        displayStatusBar("Waiting", todayStats.waitingPatients, todayStats.totalPatients);
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
        System.out.printf("%-12s | %-6s | %-7s | %-10s | %-8s | %-10s\n",
                "Date", "Total", "Waiting", "Consulting", "Completed", "Rate");
        System.out.println("-".repeat(80));

        Calendar cal = Calendar.getInstance();

        for (int i = 6; i >= 0; i--) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -i);
            Date targetDate = cal.getTime();

            DAO.DailyQueueStats dayStats = QueueControl.getDailyQueueStats(targetDate);

            if (dayStats.totalPatients > 0) {
                System.out.printf("%-12s | %-6d | %-7d | %-10d | %-8d | %-9.1f%%\n",
                        UtilityClass.formatDate(targetDate),
                        dayStats.totalPatients,
                        dayStats.waitingPatients,
                        dayStats.consultingPatients,
                        dayStats.completedPatients,
                        dayStats.completionRate);
            } else {
                System.out.printf("%-12s | %-6s | %-7s | %-10s | %-8s | %-10s\n",
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

}

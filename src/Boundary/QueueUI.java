/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.QueueControl;
import Entity.QueueEntry;
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
            System.out.println("1. Get Next Queue Patient");
            System.out.println("2. Display Queue By Status");
            System.out.println("3. Remove Queue Records");
            System.out.println("4. Back to Admin Main Menu");

            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    QueueUI.getNextInQueue();
                    break;
                case "2":
                    QueueUI.displayQueueByStatus();
                    break;
                case "3":
                    QueueUI.removeQueueRecord();
                    break;
                case "4":
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

    }

    public static void getNextInQueue() {

        if (Control.QueueControl.isFullConsulting()) {
            System.out.println("\n\n-- Current Consulting Doctor is FULL");
        } else {
            System.out.println("\n\n--- Serve Next Patient ---");
            QueueEntry next = Control.QueueControl.getNextInQueue();

            if (next == null) {
                System.out.println("No patient is queuing");
            } else {
                System.out.println("Patient Serving :");
                System.out.println("-----------------");
                System.out.println(next.toString());
            }
        }

        System.out.println("Enter any key to continue...");
        scanner.nextLine();

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
                return;
        }

        DynamicList<QueueEntry> filteredList = Control.QueueControl.getQueueListByStatus(selectedStatus);

        if (filteredList.isEmpty()) {
            System.out.println("No queue entries found with status: " + selectedStatus);
        } else {
            System.out.println("\nQueue Entries with status: " + selectedStatus);
            for (int i = 0; i < filteredList.size(); i++) {
                System.out.println(filteredList.get(i).toString());
            }
        }

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

    }

    private static void removeAllQueueRecords() {
        DynamicList<QueueEntry> allRecords = QueueControl.getQueueList();

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
        
    }
}

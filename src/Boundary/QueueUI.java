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

    public static void startQueue() {

        System.out.print("Enter your Identity Number to join the queue: ");
        String identityNumber = scanner.nextLine();

        if (identityNumber == null || identityNumber.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a valid Identity Number.");
            return;
        }

        QueueEntry newQueue = QueueControl.addInQueue(identityNumber);

        if (newQueue != null) {
            System.out.println("You have successfully joined the queue. Your queue number is: " + newQueue.getQueueNumber());
        } else {
            System.out.println("You have unsuccessfully joined the queue.");
        }

        DynamicList<QueueEntry> queueList = QueueControl.getQueueList();
        System.out.println("You have successfully joined the queue. Your queue number is : " + queueList.getLast().getQueueNumber());
        System.out.println(queueList.getLast().toString());

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
        System.out.println("\n--- Current Patient Being Served ---");

        QueueEntry current = QueueControl.currentQueue();

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

}

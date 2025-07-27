/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import Control.QueueControl;
import Entity.QueueEntry;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class QueueUI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final QueueControl queueControl = new QueueControl();

    public static void startQueue() {

        System.out.print("Enter your Identity Number to join the queue: ");
        String identityNumber = scanner.nextLine();

        if (identityNumber == null || identityNumber.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a valid Identity Number.");
            return;
        }

        QueueEntry newQueue = queueControl.startQueue(identityNumber);
        
        if (newQueue != null){
            System.out.println("You have successfully joined the queue. Your queue number is: " + newQueue.getQueueNumber());
        }else{
            System.out.println("You have unsuccessfully joined the queue.");
        }
        
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import Entity.Patient;
import Entity.Consultation;
import Control.ConsultationManagement;

import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author leekeezhan
 */
public class ConsultationUI {
    private ConsultationManagement consultationControl = new ConsultationManagement();
    private Scanner sc = new Scanner(System.in);

    public void run() {
        int choice;
        do {
            System.out.println("\n--- Consultation Management Menu ---");
            System.out.println("1. Add Patient to Queue");
            System.out.println("2. View Next Patient");
            System.out.println("3. Start Consultation");
            System.out.println("4. End Consultation");
            System.out.println("5. View Consultation History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewNextPatient();
                    break;
                case 3:
                    startConsultation();
                    break;
                case 4:
                    endConsultation();
                    break;
                case 5:
                    consultationControl.printConsultationHistory();
                    break;
                case 0:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private void addPatient() {
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("IC Number: ");
        String ic = sc.nextLine();
        System.out.print("Gender (M/F): ");
        char gender = sc.nextLine().charAt(0);
        System.out.print("Contact Number: ");
        String contact = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        System.out.print("Emergency Contact: ");
        String emergency = sc.nextLine();

        Patient p = new Patient(name, ic, new Date(), gender, contact, email, address, emergency, new Date());
        consultationControl.enqueuePatient(p);
        System.out.println("Patient added to queue with ID: " + p.getPatientID());
    }

    private void viewNextPatient() {
        Patient p = consultationControl.getNextPatient();
        if (p == null) {
            System.out.println("Queue is empty.");
        } else {
            System.out.println("Next in queue: " + p.getPatientID() + " - " + p.getFullName());
        }
    }

    private void startConsultation() {
        System.out.print("Doctor ID: ");
        String doctorId = sc.nextLine();
        System.out.print("Symptoms: ");
        String symptoms = sc.nextLine();

        Consultation c = consultationControl.startConsultation(doctorId, symptoms);
        if (c != null) {
            System.out.println("Consultation started for patient " + c.getPatientId());
            System.out.println("Consultation ID: " + c.getConsultationId());
        } else {
            System.out.println("No patients in queue.");
        }
    }

    private void endConsultation() {
        System.out.print("Consultation ID: ");
        String consultId = sc.nextLine();
        System.out.print("Diagnosis: ");
        String diagnosis = sc.nextLine();

        boolean success = consultationControl.endConsultation(consultId, diagnosis);
        if (success) {
            System.out.println("Consultation ended and diagnosis recorded.");
        } else {
            System.out.println("Consultation ID not found.");
        }
    }
}

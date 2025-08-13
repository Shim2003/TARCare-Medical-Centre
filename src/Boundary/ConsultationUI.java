/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Control.ConsultationManagement;
import java.util.Scanner;

public class ConsultationUI {

    private final ConsultationManagement consultationManagement = new ConsultationManagement();
    private final Scanner sc = new Scanner(System.in);

    public void run() {
    int choice;
    do {
        System.out.println("\n=== Consultation Module ===");
        System.out.println("1. Add Patient to Queue");
        System.out.println("2. View Queue");
        System.out.println("3. Start Next Consultation");
        System.out.println("4. View Current Consulting Patients");
        System.out.println("5. End Consultation");
        System.out.println("6. View Completed Consultations");
        System.out.println("7. Schedule Appointment");
        System.out.println("8. View All Appointments");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        choice = sc.nextInt();
        sc.nextLine(); // 清除换行符

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Patient ID: ");
                String patientId = sc.nextLine();
                consultationManagement.addPatientToQueue(patientId);
            }
            case 2 -> consultationManagement.viewQueue();
            case 3 -> consultationManagement.startNextConsultation();
            case 4 -> consultationManagement.viewCurrentConsulting();
            case 5 -> {
                System.out.print("Enter Patient ID to end consultation: ");
                String id = sc.nextLine();
                consultationManagement.endConsultation(id);
            }
            case 6 -> consultationManagement.viewCompletedPatients();  // 新增调用
            case 7 -> {
                System.out.print("Enter Patient ID: ");
                String patientId = sc.nextLine();
                System.out.print("Enter Doctor ID: ");
                String doctorId = sc.nextLine();
                System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
                String dateTimeStr = sc.nextLine();
                System.out.print("Enter symptoms (optional): ");
                String symptoms = sc.nextLine();

                consultationManagement.scheduleNextAppointment(patientId, doctorId, dateTimeStr, symptoms);
            }
            case 8 -> consultationManagement.viewScheduledAppointments();
            case 0 -> System.out.println("Returning to Main Menu...");
            default -> System.out.println("Invalid choice.");
        }
    } while (choice != 0);
}
    public static void main(String[] args) {
        ScheduleManagement.addSampleSchedules();
        DoctorManagement.addSampleDoctor();
        // TODO code application logic here
        ConsultationUI ui = new ConsultationUI();
        ui.run();
    }
}

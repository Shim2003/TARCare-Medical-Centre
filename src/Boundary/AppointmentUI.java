/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import Control.AppointmentManagement;
import Control.PatientManagement;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Control.LeaveManagement;
import DAO.ClinicData;
import java.util.Scanner;
/**
 *
 * @author leekeezhan
 */
public class AppointmentUI {

    private final Scanner sc = new Scanner(System.in);

    public void run() {
        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("           Appointment Module         ");
            System.out.println("======================================");
            System.out.println(" 1. Schedule Appointment");
            System.out.println(" 2. View Scheduled Appointments");
            System.out.println(" 3. View Appointments with Names");
            System.out.println(" 4. Delete Appointment");
            System.out.println(" 5. Modify Appointment");
            System.out.println(" 0. Back to Main Menu");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // 清除换行符

            switch (choice) {
                case 1 -> { // Schedule Appointment
                    System.out.print("Enter Patient ID: ");
                    String patientId = sc.nextLine();
                    System.out.print("Enter Doctor ID: ");
                    String doctorId = sc.nextLine();
                    System.out.print("Enter appointment date and time (dd-MM-yyyy HH:mm): ");
                    String dateTimeStr = sc.nextLine();
                    System.out.print("Enter reason: ");
                    String reason = sc.nextLine();

                    AppointmentManagement.scheduleNextAppointment(patientId, doctorId, dateTimeStr, reason);
                }
                case 2 -> AppointmentManagement.viewScheduledAppointments();
                case 3 -> AppointmentManagement.viewAppointmentsWithNames();
                case 4 -> { // Delete Appointment
                    System.out.print("Enter Appointment ID to delete: ");
                    String appointmentId = sc.nextLine();
                    AppointmentManagement.deleteAppointmentById(appointmentId);
                }
                case 5 -> { // Modify Appointment
                    System.out.print("Enter Appointment ID to modify: ");
                    String appointmentId = sc.nextLine();
                    AppointmentManagement.modifyAppointment(appointmentId);
                }
                case 0 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    public static void main(String[] args) {
        // 初始化样本数据
        DoctorManagement.addSampleDoctor();
        ClinicData.run();
        LeaveManagement.addSampleLeaves();
        ScheduleManagement.addSampleSchedules();

        AppointmentUI ui = new AppointmentUI();
        ui.run();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Control.ConsultationManagement;
import Control.LeaveManagement;
import Control.PatientManagement;
import DAO.ClinicData;
import java.util.Scanner;

public class ConsultationUI {

    private final ConsultationManagement consultationManagement = new ConsultationManagement();
    private final Scanner sc = new Scanner(System.in);

    public void run() {
        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("          Consultation Module        ");
            System.out.println("======================================");
            System.out.println(" 1. Add Patient to Queue");
            System.out.println(" 2. View Queue");
            System.out.println(" 3. Start Next Consultation");
            System.out.println(" 4. View Current Consulting Patients");
            System.out.println(" 5. End Consultation");
            System.out.println(" 6. View Patients Who Finished Consultation");
            System.out.println(" 7. View Consultation Report");
            System.out.println(" 8. Delete Consultation");
            System.out.println(" 9. View All Doctors Status");
            System.out.println("10. Show Completed Patients");
            System.out.println("11. Show All Completed Consultations");
            System.out.println("12. Show Ongoing Consultations (raw list)");
            System.out.println("--------------------------------------");
            System.out.println(" 0. Back to Main Menu");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {  
                System.out.print("Invalid input! Please enter a number: ");
                sc.next(); // 丢弃错误输入
            }

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
                case 6 -> consultationManagement.viewCompletedPatients();
                case 7 -> {   // ✅ View Consultation Report
                    System.out.print("Enter Patient ID to view report: ");
                    String patientId = sc.nextLine();
                    consultationManagement.viewConsultationReport(patientId);
                }
                case 8 -> {   // Delete Consultation
                    System.out.print("Enter Consultation ID to delete: ");
                    String consultationId = sc.nextLine();
                    consultationManagement.deleteConsultationById(consultationId);
                }
                case 9 -> consultationManagement.printAllDoctorsStatus("=== Current Doctors Status ===");
                case 10 -> consultationManagement.showCompletedPatients();
                case 11 -> consultationManagement.showCompletedConsultations();
                case 12 -> consultationManagement.showOngoingConsultations();
                case 0 -> System.out.println("  Returning to Main Menu...");
                default -> System.out.println("  Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    
    public static void main(String[] args) {
        LeaveManagement.addSampleLeaves();
        ScheduleManagement.addSampleSchedules();
        DoctorManagement.addSampleDoctor();
        ClinicData.run();
        
        // TODO code application logic here
        ConsultationUI ui = new ConsultationUI();
        ui.run();
    }
}

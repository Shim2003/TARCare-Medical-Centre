/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.MyList;
import Control.AppointmentManagement;
import Control.PatientManagement;
import Control.DoctorManagement;
import DAO.AppointmentReportResult;
import DAO.ModifyAppointmentResult;
import DAO.ScheduleAppointmentResult;
import Entity.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author leekeezhan
 */
public class AppointmentUI {

    private static final Scanner sc = new Scanner(System.in);

    public static void run1() {
        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("           Appointment Module         ");
            System.out.println("======================================");
            System.out.println(" 1. MAIN FUNCTIONS (Daily Use)");
            System.out.println(" 2. MANAGEMENT (Admin/IT Use)");
            System.out.println(" 3. ANALYTICS & UTILITIES");
            System.out.println("--------------------------------------");
            System.out.println(" 0. Back to Main Menu");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showMainFunctionsMenu();
                case 2 -> showManagementMenu();
                case 3 -> showAnalyticsMenu();
                case 0 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // MAIN FUNCTIONS MENU
    private static void showMainFunctionsMenu() {
        int choice;
        do {
            System.out.println("\n====== MAIN FUNCTIONS (Daily Use) ======");
            System.out.println(" 1. Schedule Appointment");
            System.out.println(" 2. View Appointments by ID");
            System.out.println(" 3. View Appointments by Time");
            System.out.println(" 0. Back");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> scheduleAppointmentUI();
                case 2 -> viewAppointmentsByIdUI();
                case 3 -> displayAppointmentsByTimeUI();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // MANAGEMENT MENU
    private static void showManagementMenu() {
        int choice;
        do {
            System.out.println("\n====== MANAGEMENT (Admin/IT Use) ======");
            System.out.println(" 1. Delete Appointment");
            System.out.println(" 2. Modify Appointment");
            System.out.println(" 3. Clone Appointment List");
            System.out.println(" 0. Back");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> deleteAppointmentUI();
                case 2 -> modifyAppointmentUI();
                case 3 -> demoCloneUI();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // ANALYTICS MENU
    private static void showAnalyticsMenu() {
        int choice;
        do {
            System.out.println("\n====== ANALYTICS & UTILITIES ======");
            System.out.println(" 1. View Appointment Reports");
            System.out.println(" 2. Check Doctor Appointments Next Week");
            System.out.println(" 3. Doctor Statistics");
            System.out.println(" 0. Back");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewAppointmentsByIdUI2();
                case 2 -> checkDoctorNextWeekUI();
                case 3 -> doctorStatisticsUI();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
    
    private static void scheduleAppointmentUI() {
        ScheduleUI.DisplayAllTimetable();
        System.out.print("Enter Patient ID: ");
        String patientId = sc.nextLine();
        System.out.print("Enter Doctor ID: ");
        String doctorId = sc.nextLine();
        System.out.print("Enter appointment date and time (dd-MM-yyyy HH:mm): ");
        String dateTimeStr = sc.nextLine();
        System.out.print("Enter reason: ");
        String reason = sc.nextLine();

        ScheduleAppointmentResult result = AppointmentManagement.scheduleNextAppointment(patientId, doctorId, dateTimeStr, reason);

        if (result.hasErrors()) {
            System.out.println("Failed to schedule appointment:");
            for (String error : result.getErrorMessages()) {
                System.out.println("- " + error);
            }
        } else {
            Appointment appt = result.getAppointment();
            System.out.println("Appointment scheduled successfully!");
            System.out.println("Appointment ID: " + appt.getAppointmentId());
            Patient patient = PatientManagement.findPatientById(appt.getPatientId());
            Doctor doctor = DoctorManagement.findDoctorById(appt.getDoctorId());
            System.out.println("Patient: " + (patient != null ? patient.getFullName() : appt.getPatientId())
                               + " (" + appt.getPatientId() + ")");
            System.out.println("Doctor: " + (doctor != null ? doctor.getName() : appt.getDoctorId())
                               + " (" + appt.getDoctorId() + ")");
            System.out.println("Date/Time: " + appt.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            System.out.println("Reason: " + appt.getReason());
        }
    }
    
    private static void viewAppointmentsByIdUI() {
        System.out.print("Enter Appointment / Patient / Doctor ID: ");
        String id = sc.nextLine().trim();

        MyList<String> report = AppointmentManagement.getAppointmentsReportByIdSingleLine(id);

        AppointmentManagement.printReport(report);
    }

    private static void viewAppointmentsByIdUI2() {
        System.out.print("Enter Appointment, Patient, or Doctor ID: ");
        String id = sc.nextLine().trim();

        AppointmentReportResult result = AppointmentManagement.getConsultationReportById(id);

        if (result.hasErrors()) {
            for (String error : result.getErrors()) {
                System.out.println("Error: " + error);
            }
        } else {
            for (String line : result.getReportLines()) {
                System.out.println(line);
            }
        }
    }
    
    private static void displayAppointmentsByTimeUI() {
        MyList<Appointment> sortedList = AppointmentManagement.getAppointmentsByTimeReport();
        LocalDateTime now = LocalDateTime.now();
        String format = "%-15s %-20s %-20s %-10s %-10s %-20s %-20s%n";

        System.out.println("\n--- Future Appointments ---");
        System.out.printf(format, "Appointment ID", "Patient", "Doctor", "Patient ID", "Doctor ID", "Date/Time", "Reason");
        boolean hasFuture = false;

        for (Appointment a : sortedList) {
            if (a.getAppointmentTime().isAfter(now)) {
                hasFuture = true;
                Patient patient = PatientManagement.findPatientById(a.getPatientId());
                String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";
                Doctor doctor = DoctorManagement.findDoctorById(a.getDoctorId());
                String doctorName = (doctor != null) ? doctor.getName() : "Unknown Doctor";

                System.out.printf(format,
                        a.getAppointmentId(),
                        patientName,
                        doctorName,
                        a.getPatientId(),
                        a.getDoctorId(),
                        a.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                        a.getReason()
                );
            }
        }
        if (!hasFuture) System.out.println("No future appointments.");

        System.out.println("\n--- Past Appointments ---");
        System.out.printf(format, "Appointment ID", "Patient", "Doctor", "Patient ID", "Doctor ID", "Date/Time", "Reason");
        boolean hasPast = false;

        for (Appointment a : sortedList) {
            if (!a.getAppointmentTime().isAfter(now)) {
                hasPast = true;
                Patient patient = PatientManagement.findPatientById(a.getPatientId());
                String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";
                Doctor doctor = DoctorManagement.findDoctorById(a.getDoctorId());
                String doctorName = (doctor != null) ? doctor.getName() : "Unknown Doctor";

                System.out.printf(format,
                        a.getAppointmentId(),
                        patientName,
                        doctorName,
                        a.getPatientId(),
                        a.getDoctorId(),
                        a.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                        a.getReason()
                );
            }
        }
        if (!hasPast) System.out.println("No past appointments.");
    }

    private static void deleteAppointmentUI() {
        System.out.print("Enter Appointment ID to delete: ");
        String appointmentId = sc.nextLine();
        boolean success = AppointmentManagement.deleteAppointment(appointmentId);
        if (success) {
            System.out.println("Appointment deleted successfully.");
        } else {
            System.out.println("Appointment ID not found.");
        }
    }
    
    private static void modifyAppointmentUI() {
        System.out.print("Enter Appointment ID to modify: ");
        String appointmentId = sc.nextLine().trim();

        AppointmentReportResult result = AppointmentManagement.getConsultationReportById(appointmentId);

        if (result.hasErrors()) {
            for (String err : result.getErrors()) {
                System.out.println(err);
            }
            return;
        }

        if (result.getReportLines() == null || result.getReportLines().iterator().hasNext() == false) {
            System.out.println("No consultation report found for ID: " + appointmentId);
            return;
        }

        for (String line : result.getReportLines()) {
            System.out.println(line);
        }

        System.out.println("\n--- Modifying Appointment " + appointmentId + " ---");

        System.out.print("Enter new Patient ID (or press Enter to keep current): ");
        String newPatientId = sc.nextLine().trim();
        if (newPatientId.length() == 0) newPatientId = null;

        System.out.print("Enter new Doctor ID (or press Enter to keep current): ");
        String newDoctorId = sc.nextLine().trim();
        if (newDoctorId.length() == 0) newDoctorId = null;

        System.out.print("Enter new date/time (dd-MM-yyyy HH:mm) (or press Enter to keep current): ");
        String dateTimeStr = sc.nextLine().trim();
        LocalDateTime newDateTime = null;
        if (dateTimeStr.length() > 0) { // 不用 isEmpty()
            try {
                newDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            } catch (Exception e) {
                System.out.println("Invalid date/time format. Keeping original.");
            }
        }

        System.out.print("Enter new reason (or press Enter to keep current): ");
        String newReason = sc.nextLine().trim();
        if (newReason.length() == 0) newReason = null;

        ModifyAppointmentResult modifyResult = AppointmentManagement.modifyAppointment(
            appointmentId,
            newPatientId,
            newDoctorId,
            newDateTime,
            newReason
        );

        if (modifyResult.hasErrors()) {
            System.out.println("Modification completed with issues:");
            for (String err : modifyResult.getErrors()) {
                System.out.println("- " + err);
            }
        } else {
            System.out.println("Appointment modified successfully.");
        }
    }

    
    private static void demoCloneUI() {
        MyList<Appointment> report = AppointmentManagement.demoCloneInfo();

        int count = 0;
        for (Appointment ignored : report) count++;
        System.out.println("Cloned appointment list, total size: " + count);

        System.out.println("=== Demo Clone Content ===");

        for (Appointment a : report) {
            System.out.printf("%s | %s | %s | %s | %s%n",
                a.getAppointmentId(),
                a.getPatientId(),
                a.getDoctorId(),
                a.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                a.getReason()
            );
        }

        System.out.println("==========================");
    }

    private static void checkDoctorNextWeekUI() {
        System.out.print("Enter Doctor ID: ");
        String doctorId = sc.nextLine();
        boolean hasAppointments = AppointmentManagement.hasDoctorAppointmentsNextWeek(doctorId);
        if (hasAppointments) {
            System.out.println("Doctor " + doctorId + " has appointments next week.");
        } else {
            System.out.println("Doctor " + doctorId + " has no appointments next week.");
        }
    }

    private static void doctorStatisticsUI() {
        System.out.print("Enter Doctor ID: ");
        String doctorId = sc.nextLine();

        MyList<String> stats = AppointmentManagement.getDoctorStatistics(doctorId);

        for (String line : stats) {
            System.out.println(line);
        }
    }

}

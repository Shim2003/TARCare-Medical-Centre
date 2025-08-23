/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Entity.Appointment;
import Entity.Patient;
import Entity.Doctor;
import Control.PatientManagement;
import Control.DoctorManagement;
import Entity.Schedule;
import java.time.DayOfWeek;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author leekeezhan
 */
public class AppointmentManagement {
    private static DynamicList<Appointment> scheduledAppointments = new DynamicList<>();
    private static int appointmentCounter = 1001;

    private String generateNextAppointmentId() {
        return "A" + appointmentCounter++;
    }

    public void scheduleNextAppointment(String patientId, String doctorId, String dateTimeStr, String reason) {
        // 检查病人是否存在
        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found. Cannot schedule appointment.");
            return;
        }

        // 检查医生是否存在
        Doctor doctor = DoctorManagement.findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found. Cannot schedule appointment.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime appointmentTime = LocalDateTime.parse(dateTimeStr, formatter);

            // 检查预约时间必须是未来
            if (appointmentTime.isBefore(LocalDateTime.now())) {
                System.out.println("Invalid appointment time. The appointment must be in the future.");
                return;
            }

            // 检查医生在预约那天是否有排班
            DayOfWeek dayOfWeek = appointmentTime.getDayOfWeek();
            DynamicList<Schedule> doctorSchedules = ScheduleManagement.findSchedulesByDoctorId(doctorId);
            boolean isAvailable = false;
            for (int i = 0; i < doctorSchedules.size(); i++) {
                Schedule s = doctorSchedules.get(i);
                if (s.getDayOfWeek() == dayOfWeek) {
                    LocalTime start = s.getStartTime();
                    LocalTime end = s.getEndTime();
                    LocalTime appTime = appointmentTime.toLocalTime();
                    if (!appTime.isBefore(start) && !appTime.isAfter(end)) {
                        isAvailable = true;
                        break;
                    }
                }
            }

            if (!isAvailable) {
                System.out.println("Doctor " + doctorId + " is not available on " + dayOfWeek + " at " + appointmentTime.toLocalTime());
                return;
            }

            String appointmentId = generateNextAppointmentId();

            Appointment newAppointment = new Appointment(
                    appointmentId,
                    patientId,
                    doctorId,
                    appointmentTime,
                    reason
            );

            scheduledAppointments.add(newAppointment);
            System.out.println("Appointment scheduled: " + appointmentId + " for Patient " + patientId + " at " + dateTimeStr);
        } catch (Exception e) {
            System.out.println("Invalid date/time format. Please use 'dd-MM-yyyy HH:mm'.");
        }
    }
    
    // 查看所有预约consultations
    public void viewScheduledAppointments() {
        if (scheduledAppointments.isEmpty()) {
            System.out.println("No scheduled appointments.");
            return;
        }
        System.out.println("\n--- Scheduled Consultations ---");
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            System.out.println((i + 1) + ". " + scheduledAppointments.get(i).toString());
        }
    }

    // ✅ 查看所有预约，并显示 Patient 名字和 Doctor 名字
    public void viewAppointmentsWithNames() {
        if (scheduledAppointments.isEmpty()) {
            System.out.println("No scheduled appointments.");
            return;
        }

        System.out.println("\n--- Scheduled Appointments (With Names) ---");
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);

            // 获取 Patient 名字
            Patient patient = PatientManagement.findPatientById(a.getPatientId());
            String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";

            // 获取 Doctor 名字
            Doctor doctor = DoctorManagement.findDoctorById(a.getDoctorId());
            String doctorName = (doctor != null) ? doctor.getName() : "Unknown Doctor";

            System.out.printf("%d. Appointment ID: %s | Patient: %s (%s) | Doctor: %s (%s) | Date/Time: %s | Reason: %s\n",
                    i + 1,
                    a.getAppointmentId(),
                    patientName,
                    a.getPatientId(),
                    doctorName,
                    a.getDoctorId(),
                    a.getAppointmentTime().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    a.getReason()
            );
        }
        System.out.println("-------------------------------------------\n");
    }

    // ✅ 删除 Appointment（通过 Appointment ID）
    public void deleteAppointmentById(String appointmentId) {
        if (scheduledAppointments.isEmpty()) {
            System.out.println("No appointments available to delete.");
            return;
        }

        boolean deleted = false;
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);
            if (a.getAppointmentId().equals(appointmentId)) {
                scheduledAppointments.remove(i);
                deleted = true;
                System.out.println("Appointment " + appointmentId + " has been deleted.");
                break;
            }
        }

        if (!deleted) {
            System.out.println("Appointment ID " + appointmentId + " not found.");
        }
    }

    // ✅ 修改 Appointment
    public void modifyAppointment(String appointmentId) {
        Appointment appointment = null;
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            if (scheduledAppointments.get(i).getAppointmentId().equals(appointmentId)) {
                appointment = scheduledAppointments.get(i);
                break;
            }
        }

        if (appointment == null) {
            System.out.println("Appointment ID " + appointmentId + " not found.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Modifying Appointment " + appointmentId + " ---");

        // 修改 Patient
        System.out.print("Enter new Patient ID (or press Enter to keep " + appointment.getPatientId() + "): ");
        String newPatientId = sc.nextLine().trim();
        if (!newPatientId.isEmpty()) {
            Patient patient = PatientManagement.findPatientById(newPatientId);
            if (patient != null) {
                appointment.setPatientId(newPatientId);
                System.out.println("Patient updated to " + patient.getFullName());
            } else {
                System.out.println("Patient ID not found. Keeping original.");
            }
        }

        // 修改 Doctor
        System.out.print("Enter new Doctor ID (or press Enter to keep " + appointment.getDoctorId() + "): ");
        String newDoctorId = sc.nextLine().trim();
        if (!newDoctorId.isEmpty()) {
            Doctor doctor = DoctorManagement.findDoctorById(newDoctorId);
            if (doctor != null) {
                appointment.setDoctorId(newDoctorId);
                System.out.println("Doctor updated to " + doctor.getName());
            } else {
                System.out.println("Doctor ID not found. Keeping original.");
            }
        }

        // 修改 Date/Time
        System.out.print("Enter new date and time (dd-MM-yyyy HH:mm) (or press Enter to keep " 
                         + appointment.getAppointmentTime().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + "): ");
        String newDateTimeStr = sc.nextLine().trim();
        if (!newDateTimeStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                LocalDateTime newDateTime = LocalDateTime.parse(newDateTimeStr, formatter);
                if (newDateTime.isAfter(LocalDateTime.now())) {
                    appointment.setAppointmentTime(newDateTime);
                    System.out.println("Appointment date/time updated.");
                } else {
                    System.out.println("Invalid date/time. Keeping original.");
                }
            } catch (Exception e) {
                System.out.println("Invalid format. Keeping original.");
            }
        }

        // 修改 Reason
        System.out.print("Enter new reason (or press Enter to keep \"" + appointment.getReason() + "\"): ");
        String newReason = sc.nextLine().trim();
        if (!newReason.isEmpty()) {
            appointment.setReason(newReason);
            System.out.println("Reason updated.");
        }

        System.out.println("Appointment modification complete.\n");
    }

    public DynamicList<Appointment> getScheduledAppointments() {
        return scheduledAppointments;
    }
}

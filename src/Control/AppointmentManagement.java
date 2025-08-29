/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import java.util.Comparator;
import Entity.Appointment;
import Entity.Patient;
import Entity.Doctor;
import Control.PatientManagement;
import Control.DoctorManagement;
import Entity.Schedule;
import Entity.ScheduleAppointmentResult;
import Utility.UtilityClass;
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
    // List of all scheduled appointments
    private static MyList<Appointment> scheduledAppointments = new DynamicList<>();
    
    // Counter
    private static int appointmentCounter = 1001; // A1001

    // Set the list of scheduled appointments (used for data initialization)
    public static void setScheduledAppointments(MyList<Appointment> list) {
        scheduledAppointments = list;
    }
    
    // Generate the next unique appointment ID
    public static String generateNextAppointmentId() {
        String id = "A" + appointmentCounter;
        while (appointmentIdExists(id)) {
            appointmentCounter++; // appointmentCounter++
            id = "A" + appointmentCounter;
        }
        return id;
    }

    // Check if the appointment ID exists
    private static boolean appointmentIdExists(String id) {
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            if (scheduledAppointments.get(i).getAppointmentId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
    
    // Add a new appointment to the scheduled list
    public static void addScheduledAppointment(Appointment a) {
        scheduledAppointments.add(scheduledAppointments.size(), a);
    }
    
    // Peek the next available appointment ID without incrementing the counter
    public static String peekNextAppointmentId() {
        int tempCounter = appointmentCounter; 
        String id = "A" + tempCounter;
        while (appointmentIdExists(id)) {
            id = "A" + (++tempCounter);
        }
        return id; // Do not modify appointmentCounter
    }

    // Schedule a new appointment.
    public static ScheduleAppointmentResult scheduleNextAppointment(String patientId, String doctorId, String dateTimeStr, String reason) {
        MyList<String> errors = new DynamicList<>();
        LocalDateTime appointmentTime = null;
        Appointment newAppointment = null;

        // Check patient
        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient == null) errors.add("Patient ID '" + patientId + "' not found.");

        // Check doctor
        Doctor doctor = DoctorManagement.findDoctorById(doctorId);
        if (doctor == null) errors.add("Doctor ID '" + doctorId + "' not found.");

        // Parse date/time
        if (errors.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                appointmentTime = LocalDateTime.parse(dateTimeStr, formatter);
                if (appointmentTime.isBefore(LocalDateTime.now())) errors.add("Appointment must be in the future.");
            } catch (Exception e) {
                errors.add("Invalid date/time format.");
            }
        }

        // Check schedule/conflicts
        if (errors.isEmpty()) {
            DayOfWeek dayOfWeek = appointmentTime.getDayOfWeek();
            MyList<Schedule> doctorSchedules = ScheduleManagement.findSchedulesByDoctorId(doctorId);
            boolean isAvailable = false;
            for (int i = 0; i < doctorSchedules.size(); i++) {
                Schedule s = doctorSchedules.get(i);
                LocalTime start = s.getStartTime();
                LocalTime end = s.getEndTime();
                LocalTime appTime = appointmentTime.toLocalTime();
                if (s.getDayOfWeek() == dayOfWeek && !appTime.isBefore(start) && !appTime.isAfter(end)) {
                    isAvailable = true;
                    break;
                }
            }
            if (!isAvailable) errors.add("Doctor " + doctorId + " is not available on " + dayOfWeek + " at " + appointmentTime.toLocalTime());

            for (int i = 0; i < scheduledAppointments.size(); i++) {
                Appointment existing = scheduledAppointments.get(i);
                if (existing.getDoctorId().equals(doctorId) &&
                    existing.getAppointmentTime().equals(appointmentTime)) {
                    errors.add("Conflict: Doctor already has an appointment at this time.");
                    break;
                }
            }
        }

        // Schedule appointment
        if (errors.isEmpty()) {
            String appointmentId = generateNextAppointmentId();
            newAppointment = new Appointment(appointmentId, patientId, doctorId, appointmentTime, reason);
            scheduledAppointments.add(newAppointment);
        }

        return new ScheduleAppointmentResult(errors, newAppointment);
    }
 
    // find Appointment by ID automatically
    public static MyList<Appointment> findAppointmentsById(String id) {
        boolean isPatient = (PatientManagement.findPatientById(id) != null);
        boolean isDoctor = (DoctorManagement.findDoctorById(id) != null);

        MyList<Appointment> result = new DynamicList<>();

        if (!isPatient && !isDoctor) {
            return result; // Return empty result
        }

        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);
            if ((isPatient && a.getPatientId().equals(id)) ||
                (isDoctor && a.getDoctorId().equals(id))) {
                result.add(result.size(), a);
            }
        }
        return result;
    }

    // Get a formatted appointment report
    public static MyList<String> getAppointmentsReportByIdSingleLine(String id) {
        MyList<String> report = new DynamicList<>();

        Appointment found = null;
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);
            if (a.getAppointmentId().equalsIgnoreCase(id)) {
                found = a;
                break;
            }
        }

        if (found != null) {
            report.add("--- Appointment Detail for ID: " + found.getAppointmentId() + " ---");
            report.add(formatAppointmentDisplaySingleLine(found));
            report.add("-------------------------------------------");
            return report;
        }

        // Patient / Doctor case
        boolean isPatient = (PatientManagement.findPatientById(id) != null);
        boolean isDoctor = (DoctorManagement.findDoctorById(id) != null);

        if (!isPatient && !isDoctor) {
            report.add("No appointments found for ID: " + id);
            return report;
        }

        MyList<Appointment> appointments = findAppointmentsById(id);
        if (appointments.isEmpty()) {
            report.add("No appointments found for ID: " + id);
            return report;
        }

        report.add("--- Appointment Detail for ID: " + id + " ---");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            report.add(formatAppointmentDisplaySingleLine(a));
        }
        report.add("-------------------------------------------");

        return report;
    }
    
    // Print a report (list of strings)
    public static void printReport(MyList<String> report) {
        for (String line : report) {
            System.out.println(line);
        }
    }
    
    // Single-line display version
    public static String formatAppointmentDisplaySingleLine(Appointment a) {
        Patient patient = PatientManagement.findPatientById(a.getPatientId());
        String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";

        Doctor doctor = DoctorManagement.findDoctorById(a.getDoctorId());
        String doctorName = (doctor != null) ? doctor.getName() : "Unknown Doctor";

        return String.format(
            "Appointment ID: %s | Patient: %s (%s) | Doctor: %s (%s) | Date/Time: %s | Reason: %s",
            a.getAppointmentId(),
            patientName,
            a.getPatientId(),
            doctorName,
            a.getDoctorId(),
            a.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
            a.getReason()
        );
    }

    // Helper: add all lines from one list to another
    private static void addAll(MyList<String> target, MyList<String> source) {
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }

    // Delete Appointment by Appointment ID
    public static boolean deleteAppointment(String appointmentId) {
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            if (scheduledAppointments.get(i).getAppointmentId().equalsIgnoreCase(appointmentId)) {
                scheduledAppointments.remove(i);
                return true;
            }
        }
        return false;
    }
    
    // Private: check if patient exists
    private static Appointment findAppointmentOrReportError(String appointmentId, MyList<String> errors) {
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);
            if (a.getAppointmentId().equalsIgnoreCase(appointmentId)) {
                return a;
            }
        }
        errors.add("Appointment ID " + appointmentId + " not found.");
        return null;
    }
    
    // Private: check if doctor exists
    private static boolean checkPatientExists(String patientId, MyList<String> errors) {
        if (PatientManagement.findPatientById(patientId) == null) {
            errors.add("Patient ID " + patientId + " not found.");
            return false;
        }
        return true;
    }

    // Modify an existing appointment
    private static boolean checkDoctorExists(String doctorId, MyList<String> errors) {
        if (DoctorManagement.findDoctorById(doctorId) == null) {
            errors.add("Doctor ID " + doctorId + " not found.");
            return false;
        }
        return true;
    }

    // Modify Appointment
    public static MyList<String> modifyAppointment(String appointmentId, String newPatientId, String newDoctorId, LocalDateTime newDateTime, String newReason) {
        
        MyList<String> errors = new DynamicList<>();
        Appointment appointment = findAppointmentOrReportError(appointmentId, errors);
        if (appointment == null) return errors;

        if (newPatientId != null && !newPatientId.isEmpty()) {
            if (checkPatientExists(newPatientId, errors)) {
                appointment.setPatientId(newPatientId);
            }
        }

        if (newDoctorId != null && !newDoctorId.isEmpty()) {
            if (checkDoctorExists(newDoctorId, errors)) {
                appointment.setDoctorId(newDoctorId);
            }
        }
        
        if (newDateTime != null) {
            if (newDateTime.isAfter(LocalDateTime.now())) {
                
                boolean available = false;
                DayOfWeek dayOfWeek = newDateTime.getDayOfWeek();
                MyList<Schedule> doctorSchedules = ScheduleManagement.findSchedulesByDoctorId(appointment.getDoctorId());
                LocalTime appTime = newDateTime.toLocalTime();
                for (int i = 0; i < doctorSchedules.size(); i++) {
                    Schedule s = doctorSchedules.get(i);
                    if (s.getDayOfWeek() == dayOfWeek &&
                        !appTime.isBefore(s.getStartTime()) &&
                        !appTime.isAfter(s.getEndTime())) {
                        available = true;
                        break;
                    }
                }
                if (!available) {
                    errors.add("Doctor not available at this new time. Keeping original.");
                } else {
                    
                    boolean conflict = false;
                    for (int i = 0; i < scheduledAppointments.size(); i++) {
                        Appointment existing = scheduledAppointments.get(i);
                        if (!existing.getAppointmentId().equalsIgnoreCase(appointmentId) &&
                            existing.getDoctorId().equals(appointment.getDoctorId()) &&
                            existing.getAppointmentTime().equals(newDateTime)) {
                            conflict = true;
                            break;
                        }
                    }
                    if (conflict) {
                        errors.add("Conflict with another appointment. Keeping original time.");
                    } else {
                        appointment.setAppointmentTime(newDateTime);
                    }
                }
            } else {
                errors.add("Invalid date/time (past). Keeping original.");
            }
        }
        if (newReason != null && !newReason.isEmpty()) {
            appointment.setReason(newReason);
        }
        return errors;
    }

    // Get list of all scheduled appointments
    public static MyList<Appointment> getScheduledAppointments() {
        return scheduledAppointments;
    }
    
    // Get Consultation Report by ID
    public static MyList<String> getConsultationReportById(String id) {
        MyList<String> report = new DynamicList<>();
        LocalDateTime now = LocalDateTime.now();

        // Check Appointment ID
        Appointment found = null;
        for (Appointment a : scheduledAppointments) {
            if (a.getAppointmentId().equalsIgnoreCase(id)) {
                found = a;
                break;
            }
        }

        if (found != null) {
            report.add("--- Consultation Report for Appointment ID: " + id + " ---");
            addAll(report, formatAppointmentDisplay(found));
            report.add("---------------------------------------");
            return report;
        }

        // Determine whether it is a Patient or a Doctor
        boolean isPatient = (PatientManagement.findPatientById(id) != null);
        boolean isDoctor = (DoctorManagement.findDoctorById(id) != null);

        if (!isPatient && !isDoctor) {
            report.add("ID not found in Appointment, Patient, or Doctor records: " + id);
            return report;
        }

        MyList<Appointment> appointments = findAppointmentsById(id);

        if (appointments.isEmpty()) {
            report.add("No appointments found for ID: " + id);
            return report;
        }

        report.add("--- Consultation Report for " + (isPatient ? "Patient" : "Doctor") + " ID: " + id + " ---");

        for (Appointment a : appointments) {
            addAll(report, formatAppointmentDisplay(a));
            report.add("---------------------------------------");
        }

        return report;
    }  
    
    // Format a multi-line appointment display
    public static MyList<String> formatAppointmentDisplay(Appointment a) {
        MyList<String> lines = new DynamicList<>();

        Patient patient = PatientManagement.findPatientById(a.getPatientId());
        String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";

        Doctor doctor = DoctorManagement.findDoctorById(a.getDoctorId());
        String doctorName = (doctor != null) ? doctor.getName() : "Unknown Doctor";

        lines.add("Appointment ID : " + a.getAppointmentId());
        lines.add("Patient        : " + patientName + " (" + a.getPatientId() + ")");
        lines.add("Doctor         : " + doctorName + " (" + a.getDoctorId() + ")");
        lines.add("Date/Time      : " + a.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        lines.add("Reason         : " + a.getReason());

        return lines;
    }

    // Clone all appointments (for demo/testing)
    public static MyList<Appointment> demoCloneInfo() {
        return scheduledAppointments.clone();
    }
    
    // Generate statistics for a specific doctor
    public static MyList<String> getDoctorStatistics(String doctorId) {
        MyList<String> report = new DynamicList<>();

        // 1. Total appointments for the specific doctor
        int doctorTotal = (int) scheduledAppointments
                            .filter(a -> a.getDoctorId().equals(doctorId))
                            .getStatistics(a -> 1).sum;
        report.add("Total appointments for Doctor " + doctorId + ": " + doctorTotal);

        // 2. Find doctor(s) with most total appointments
        DynamicList<String> doctorIds = new DynamicList<>();
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            String dId = scheduledAppointments.get(i).getDoctorId();
            if (!doctorIds.contains(dId)) doctorIds.add(dId);
        }

        int maxAppointments = 0;
        DynamicList<String> topDoctors = new DynamicList<>();
        for (int i = 0; i < doctorIds.size(); i++) {
            String dId = doctorIds.get(i);
            int count = (int) scheduledAppointments
                                .filter(a -> a.getDoctorId().equals(dId))
                                .getStatistics(a -> 1).sum;
            if (count > maxAppointments) {
                maxAppointments = count;
                topDoctors.clear();
                topDoctors.add(dId);
            } else if (count == maxAppointments) {
                topDoctors.add(dId);
            }
        }

        // Append doctor(s) with most appointments
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < topDoctors.size(); i++) {
            sb.append(topDoctors.get(i));
            if (i != topDoctors.size() - 1) sb.append(", ");
        }
        report.add("Doctor(s) with most appointments: " + sb.toString() + " (" + maxAppointments + " appointments)");

        // 3. Check appointments this week
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        boolean anyAppointmentsThisWeek = false;
        for (int i = 0; i < doctorIds.size(); i++) {
            String dId = doctorIds.get(i);
            int count = (int) scheduledAppointments
                                .filter(a -> a.getDoctorId().equals(dId)
                                        && !a.getAppointmentTime().isBefore(startOfWeek)
                                        && !a.getAppointmentTime().isAfter(endOfWeek))
                                .getStatistics(a -> 1).sum;
            if (count > 0) {
                anyAppointmentsThisWeek = true;
                break;
            }
        }

        if (!anyAppointmentsThisWeek) {
            report.add("No appointments for any doctor this week.");
        }

        return report;
    }
    
    // Check if a doctor has appointments in the next week
    public static boolean hasDoctorAppointmentsNextWeek(String doctorId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfNextWeek = now.with(DayOfWeek.MONDAY).plusWeeks(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfNextWeek = startOfNextWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

        return scheduledAppointments.anyMatch(a ->
            a.getDoctorId().equals(doctorId) &&
            !a.getAppointmentTime().isBefore(startOfNextWeek) &&
            !a.getAppointmentTime().isAfter(endOfNextWeek)
        );
    }

    // Get all appointments sorted by time
    public static MyList<Appointment> getAppointmentsByTimeReport() {
        DynamicList<Appointment> sortedList = (DynamicList<Appointment>) scheduledAppointments.clone();
        UtilityClass.quickSort(sortedList, Comparator.comparing(Appointment::getAppointmentTime));
        return sortedList;
    }
}

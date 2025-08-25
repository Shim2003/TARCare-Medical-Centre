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
    private static DynamicList<Appointment> scheduledAppointments = new DynamicList<>();
    
    // Counter
    private static int appointmentCounter = 1001; // A1001

    public static String generateNextAppointmentId() {
        String id;
        do {
            id = "A" + (appointmentCounter++);
        } while (appointmentIdExists(id));  // if exists
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
    
    public static void addScheduledAppointment(Appointment a) {
        scheduledAppointments.add(a);
    }

    public static void scheduleNextAppointment(String patientId, String doctorId, String dateTimeStr, String reason) {
        // Check if the patient exists
        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found. Cannot schedule appointment.");
            return;
        }

        // Check if the doctor exists
        Doctor doctor = DoctorManagement.findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found. Cannot schedule appointment.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime appointmentTime = LocalDateTime.parse(dateTimeStr, formatter);

            // Check that appointment time is in the future
            if (appointmentTime.isBefore(LocalDateTime.now())) {
                System.out.println("Invalid appointment time. The appointment must be in the future.");
                return;
            }

            // Check if doctor is scheduled on the appointment day
            DayOfWeek dayOfWeek = appointmentTime.getDayOfWeek();
            MyList<Schedule> doctorSchedules = ScheduleManagement.findSchedulesByDoctorId(doctorId);
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
            
            // Check for conflicting appointments (same doctor at the same time)
            for (int i = 0; i < scheduledAppointments.size(); i++) {
                Appointment existing = scheduledAppointments.get(i);
                if (existing.getDoctorId().equals(doctorId) &&
                    existing.getAppointmentTime().equals(appointmentTime)) {
                    System.out.println("Conflict detected: Doctor " + doctorId + 
                                       " already has an appointment at " + appointmentTime.format(formatter));
                    return;
                }
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
            System.out.println("   Appointment scheduled successfully!");
            System.out.println("   Appointment ID: " + appointmentId);
            System.out.println("   Patient: " + patient.getFullName() + " (" + patientId + ")");
            System.out.println("   Doctor: " + doctor.getName() + " (" + doctorId + ")");
            System.out.println("   Date/Time: " + dateTimeStr);
            System.out.println("   Reason: " + reason);
        } catch (Exception e) {
            System.out.println("Invalid date/time format. Please use 'dd-MM-yyyy HH:mm'.");
        }
    }

    // View all appointments with Patient and Doctor names
    public static void viewAppointmentsWithNames() {
        if (scheduledAppointments.isEmpty()) {
            System.out.println("No scheduled appointments.");
            return;
        }

        System.out.println("\n--- Scheduled Appointments (With Names) ---");
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);

            Patient patient = PatientManagement.findPatientById(a.getPatientId());
            String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";

            Doctor doctor = DoctorManagement.findDoctorById(a.getDoctorId());
            String doctorName = (doctor != null) ? doctor.getName() : "Unknown Doctor";

            System.out.printf("%d. Appointment ID: %s | Patient: %s (%s) | Doctor: %s (%s) | Date/Time: %s | Reason: %s\n",
                    i + 1,
                    a.getAppointmentId(),
                    patientName,
                    a.getPatientId(),
                    doctorName,
                    a.getDoctorId(),
                    a.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    a.getReason()
            );
        }
        System.out.println("-------------------------------------------\n");
    }
    
    // General method: find Appointment by ID automatically
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
                result.add(a);
            }
        }
        return result;
    }
    
    // Prompt user and view appointments
    public static void promptAndViewAppointments() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Appointment ID, Patient ID, or Doctor ID to view appointments: ");
        String id = sc.nextLine().trim();
        viewAppointmentsById(id);
    }
    
    // Unified method to display Appointment details
    private static void displayAppointment(Appointment a) {
        Patient patient = PatientManagement.findPatientById(a.getPatientId());
        String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";

        Doctor doctor = DoctorManagement.findDoctorById(a.getDoctorId());
        String doctorName = (doctor != null) ? doctor.getName() : "Unknown Doctor";

        System.out.printf("Appointment ID: %s | Patient: %s (%s) | Doctor: %s (%s) | Date/Time: %s | Reason: %s\n",
                a.getAppointmentId(),
                patientName,
                a.getPatientId(),
                doctorName,
                a.getDoctorId(),
                a.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                a.getReason()
        );
    }
    
    // Automatically determine ID type (Appointment / Patient / Doctor) and display
    public static void viewAppointmentsById(String id) {
        // check if it is an Appointment ID
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);
            if (a.getAppointmentId().equalsIgnoreCase(id)) {
                // Display only future appointments
                if (a.getAppointmentTime().isAfter(java.time.LocalDateTime.now())) {
                    System.out.println("\n--- Appointment Detail for ID: " + id + " ---");
                    displayAppointment(a);
                    System.out.println("-------------------------------------------\n");
                } else {
                    System.out.println("Appointment ID " + id + " has already passed.");
                }
                return; 
            }
        }

        boolean isPatient = (PatientManagement.findPatientById(id) != null);
        boolean isDoctor = (DoctorManagement.findDoctorById(id) != null);

        if (!isPatient && !isDoctor) {
            System.out.println("ID not found in Patient or Doctor records: " + id);
            return;
        }

        MyList<Appointment> appointments = findAppointmentsById(id);
        boolean foundFuture = false;

        if (appointments.isEmpty()) {
            System.out.println("No appointments found for ID: " + id);
            return;
        }

        System.out.printf("\n--- Appointments for %s ID: %s ---\n",
                isPatient ? "Patient" : "Doctor", id);

        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            if (a.getAppointmentTime().isAfter(java.time.LocalDateTime.now())) {
                foundFuture = true;
                System.out.printf("%d. ", i + 1);
                displayAppointment(a);
            }
        }

        if (!foundFuture) {
            System.out.println("No future appointments found for this ID.");
        }

        System.out.println("-------------------------------------------\n");
    }


    // Delete Appointment by Appointment ID
    public static void deleteAppointmentById(String appointmentId) {
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

    // Modify Appointment
    public static void modifyAppointment(String appointmentId) {
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

        System.out.print("Enter new date and time (dd-MM-yyyy HH:mm) (or press Enter to keep " 
                         + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + "): ");
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

        System.out.print("Enter new reason (or press Enter to keep \"" + appointment.getReason() + "\"): ");
        String newReason = sc.nextLine().trim();
        if (!newReason.isEmpty()) {
            appointment.setReason(newReason);
            System.out.println("Reason updated.");
        }

        System.out.println("Appointment modification complete.\n");
    }

    public static DynamicList<Appointment> getScheduledAppointments() {
        return scheduledAppointments;
    }
    
    // View Consultation Report by ID
    public static void viewConsultationReportById(String id) {
        // Check Appointment ID
        Appointment found = null;
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);
            if (a.getAppointmentId().equalsIgnoreCase(id)) {
                found = a;
                break;
            }
        }

        if (found != null) {
            System.out.println("\n--- Consultation Report for Appointment ID: " + id + " ---\n");
            displayAppointment1(found);
            System.out.println("---------------------------------------\n");
            return;
        }

        // Determine whether it is a Patient or a Doctor
        boolean isPatient = (PatientManagement.findPatientById(id) != null);
        boolean isDoctor = (DoctorManagement.findDoctorById(id) != null);

        if (!isPatient && !isDoctor) {
            System.out.println("ID not found in Appointment, Patient, or Doctor records: " + id);
            return;
        }

        MyList<Appointment> appointments = findAppointmentsById(id);

        if (appointments.isEmpty()) {
            System.out.println("No appointments found for ID: " + id);
            return;
        }

        System.out.printf("\n--- Consultation Report for %s ID: %s ---\n\n",
                isPatient ? "Patient" : "Doctor", id);

        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            displayAppointment1(a);
            System.out.println("---------------------------------------");
        }
    }
    
    public static void displayAppointment1(Appointment a) {
        Patient patient = PatientManagement.findPatientById(a.getPatientId());
        String patientName = (patient != null) ? patient.getFullName() : "Unknown Patient";

        Doctor doctor = DoctorManagement.findDoctorById(a.getDoctorId());
        String doctorName = (doctor != null) ? doctor.getName() : "Unknown Doctor";

        System.out.println("Appointment ID : " + a.getAppointmentId());
        System.out.println("Patient        : " + patientName + " (" + a.getPatientId() + ")");
        System.out.println("Doctor         : " + doctorName + " (" + a.getDoctorId() + ")");
        System.out.println("Date/Time      : " + a.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        System.out.println("Reason         : " + a.getReason());
    }

    public static void demoClone() {
        MyList<Appointment> copyList = scheduledAppointments.clone();
        System.out.println("Cloned appointment list, total size: " + copyList.size());
    }
    
    public static void doctorStatistics() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Doctor ID to check total appointments: ");
        String doctorId = sc.nextLine().trim();

        // The total number of appointments for the user's doctor ID
        int doctorTotal = (int) scheduledAppointments
                            .filter(a -> a.getDoctorId().equals(doctorId))
                            .getStatistics(a -> 1).sum;
        System.out.println("Total appointments for Doctor " + doctorId + ": " + doctorTotal);

        // The doctor with the highest appointment volume
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

        if (topDoctors.size() == doctorIds.size()) {
            // All same
            System.out.println("All doctors have the same number of appointments: " + maxAppointments);
        } else {
            // Display the doctor with the most appointments
            System.out.print("Doctor(s) with most appointments: ");
            for (int i = 0; i < topDoctors.size(); i++) {
                System.out.print(topDoctors.get(i));
                if (i != topDoctors.size() - 1) System.out.print(", ");
            }
            System.out.println(" (" + maxAppointments + " appointments)");
        }


        //  The doctor with the most appointments this week
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        String topThisWeek = null;
        int maxThisWeek = 0;
        for (int i = 0; i < doctorIds.size(); i++) {
            String dId = doctorIds.get(i);
            int count = (int) scheduledAppointments
                                .filter(a -> a.getDoctorId().equals(dId)
                                        && !a.getAppointmentTime().isBefore(startOfWeek)
                                        && !a.getAppointmentTime().isAfter(endOfWeek))
                                .getStatistics(a -> 1).sum;
            if (count > maxThisWeek) {
                maxThisWeek = count;
                topThisWeek = dId;
            }
        }
        System.out.println("Doctor with most appointments this week: " + topThisWeek + " (" + maxThisWeek + " appointments)");
    }
    
    public static void checkDoctorNextWeekAppointments() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Doctor ID to check appointments next week: ");
        String doctorId = sc.nextLine().trim();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfNextWeek = now.with(DayOfWeek.MONDAY).plusWeeks(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfNextWeek = startOfNextWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

        boolean hasAppointment = scheduledAppointments.anyMatch(a ->
            a.getDoctorId().equals(doctorId) &&
            !a.getAppointmentTime().isBefore(startOfNextWeek) &&
            !a.getAppointmentTime().isAfter(endOfNextWeek)
        );

        if (hasAppointment) {
            System.out.println("Doctor " + doctorId + " has appointments next week.");
        } else {
            System.out.println("Doctor " + doctorId + " is free next week.");
        }
    }
 

    public static void displayAppointmentsByTime() {
        // Make a copy to prevent modifying the original data
        DynamicList<Appointment> sortedList = (DynamicList<Appointment>) scheduledAppointments.clone();

        // Call the sorting method we used earlier
        UtilityClass.quickSort(sortedList, Comparator.comparing(Appointment::getAppointmentTime));

        LocalDateTime now = LocalDateTime.now();

        System.out.println("\n--- Future Appointments ---");
        for (int i = 0; i < sortedList.size(); i++) {
            Appointment a = sortedList.get(i);
            if (a.getAppointmentTime().isAfter(now)) {
                displayAppointment(a);
            }
        }

        System.out.println("\n--- Past Appointments ---");
        for (int i = 0; i < sortedList.size(); i++) {
            Appointment a = sortedList.get(i);
            if (!a.getAppointmentTime().isAfter(now)) {
                displayAppointment(a);
            }
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
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
    
    // 计数器
    private static int appointmentCounter = 1001; // A1001

    private static String generateNextAppointmentId() {
        return "A" + appointmentCounter++;
    }
    
    public static void addSampleAppointments() {
        try {
            Appointment[] samples = new Appointment[10];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            samples[0] = new Appointment("A1001", "P1001", "D001", 
                    LocalDateTime.of(2025, 8, 20, 9, 0), "General Checkup");
            samples[1] = new Appointment("A1002", "P1002", "D002", 
                    LocalDateTime.of(2025, 8, 20, 10, 30), "Fever");
            samples[2] = new Appointment("A1003", "P1003", "D003", 
                    LocalDateTime.of(2025, 8, 21, 11, 15), "Headache");
            samples[3] = new Appointment("A1004", "P1004", "D004", 
                    LocalDateTime.of(2025, 8, 21, 14, 45), "Back Pain");
            samples[4] = new Appointment("A1005", "P1005", "D005", 
                    LocalDateTime.of(2025, 8, 22, 9, 20), "Stomach Ache");
            samples[5] = new Appointment("A1006", "P1006", "D006", 
                    LocalDateTime.of(2025, 8, 22, 10, 40), "Sore Throat");
            samples[6] = new Appointment("A1007", "P1007", "D007", 
                    LocalDateTime.of(2025, 8, 23, 8, 50), "Allergy");
            samples[7] = new Appointment("A1008", "P1008", "D008", 
                    LocalDateTime.of(2025, 8, 23, 10, 10), "Flu");
            samples[8] = new Appointment("A1009", "P1009", "D009", 
                    LocalDateTime.of(2025, 8, 24, 13, 30), "Fatigue");
            samples[9] = new Appointment("A1010", "P1010", "D010", 
                    LocalDateTime.of(2025, 8, 24, 14, 20), "Dizziness");

            for (Appointment a : samples) {
                scheduledAppointments.add(a);
            }

            System.out.println("Added 10 sample appointments with LocalDateTime.");
        } catch (Exception e) {
            System.out.println("Error adding sample appointments: " + e.getMessage());
        }
    }
    
    public static void addScheduledAppointment(Appointment a) {
        scheduledAppointments.add(a);
    }

    public static void scheduleNextAppointment(String patientId, String doctorId, String dateTimeStr, String reason) {
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
            
            // ✅ 检查是否已有冲突的预约（同一个医生在相同时间）
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

    // ✅ 查看所有预约，并显示 Patient 名字和 Doctor 名字
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
    
    // ✅ 通用方法：根据 ID 自动查找 Appointment
    public static MyList<Appointment> findAppointmentsById(String id) {
        boolean isPatient = (PatientManagement.findPatientById(id) != null);
        boolean isDoctor = (DoctorManagement.findDoctorById(id) != null);

        MyList<Appointment> result = new DynamicList<>();

        if (!isPatient && !isDoctor) {
            return result; // 直接返回空结果
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
    
    public static void promptAndViewAppointments() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Appointment ID, Patient ID, or Doctor ID to view appointments: ");
        String id = sc.nextLine().trim();
        viewAppointmentsById(id);
    }
    
    // ✅ 统一显示 Appointment 详情的方法
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
    
    // ✅ 自动判断 ID 属于 Appointment / Patient / Doctor 并显示
    public static void viewAppointmentsById(String id) {
        // 先检查是否为 Appointment ID
        for (int i = 0; i < scheduledAppointments.size(); i++) {
            Appointment a = scheduledAppointments.get(i);
            if (a.getAppointmentId().equalsIgnoreCase(id)) {
                // 只显示未来的预约
                if (a.getAppointmentTime().isAfter(java.time.LocalDateTime.now())) {
                    System.out.println("\n--- Appointment Detail for ID: " + id + " ---");
                    displayAppointment(a);
                    System.out.println("-------------------------------------------\n");
                } else {
                    System.out.println("Appointment ID " + id + " has already passed.");
                }
                return; // 直接 return
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


    // ✅ 删除 Appointment（通过 Appointment ID）
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

    // ✅ 修改 Appointment
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
    
    public static void viewConsultationReportById(String id) {
        // 先检查是否为 Appointment ID
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
            displayAppointment1(found); // 使用报表风格
            System.out.println("---------------------------------------\n");
            return;
        }

        // 判断是否为 Patient 或 Doctor
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
            displayAppointment1(a); // 保持报表风格
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

        // 1️⃣ 用户输入医生ID的总预约数量
        int doctorTotal = (int) scheduledAppointments
                            .filter(a -> a.getDoctorId().equals(doctorId))
                            .getStatistics(a -> 1).sum;
        System.out.println("Total appointments for Doctor " + doctorId + ": " + doctorTotal);

        // 2️⃣ 预约数量最高的医生（平局显示）
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
            // 全部都一样
            System.out.println("All doctors have the same number of appointments: " + maxAppointments);
        } else {
            // 显示最多预约的医生
            System.out.print("Doctor(s) with most appointments: ");
            for (int i = 0; i < topDoctors.size(); i++) {
                System.out.print(topDoctors.get(i));
                if (i != topDoctors.size() - 1) System.out.print(", ");
            }
            System.out.println(" (" + maxAppointments + " appointments)");
        }


        // 3️⃣ 本周内预约最多的医生
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
}

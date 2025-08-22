/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.util.Scanner;
import java.util.Iterator;
import ADT.DynamicList;
import DAO.CurrentServingDAO;
import Entity.QueueEntry;
import Entity.Patient;
import Entity.Doctor;
import Entity.Consultation;
import Entity.Appointment;
import Entity.Schedule;
import Utility.UtilityClass;
import Control.PatientManagement;
import Control.ScheduleManagement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Duration;
import java.time.DayOfWeek;

/**
 *
 * @author leekeezhan
 */
public class ConsultationManagement {

    private static DynamicList<Patient> completedPatients = new DynamicList<>();
    private static DynamicList<Appointment> scheduledAppointments = new DynamicList<>();
    private static DynamicList<Consultation> ongoingConsultations = new DynamicList<>();
    private static DynamicList<CurrentServingDAO> currentConsulting = QueueControl.getCurrentServingPatient();
    private static DynamicList<Consultation> completedConsultations = new DynamicList<>();
    
    public void showCompletedPatients() {
        System.out.println("--- Completed Patients ---");
        if (completedPatients.isEmpty()) {
            System.out.println("No completed patients.");
        } else {
            for (int i = 0; i < completedPatients.size(); i++) {
                Patient p = completedPatients.get(i);
                System.out.println(p); // 确保 Patient 有 toString()
            }
        }
    }

    public void showCurrentConsulting() {
        System.out.println("--- Current Consulting Patients ---");
        if (currentConsulting.isEmpty()) {
            System.out.println("No patients currently consulting.");
        } else {
            for (int i = 0; i < currentConsulting.size(); i++) {
                CurrentServingDAO c = currentConsulting.get(i);
                System.out.println(c); // 确保 CurrentServingDAO 有 toString()
            }
        }
    }

    public void showCompletedConsultations() {
        System.out.println("--- Completed Consultations ---");
        if (completedConsultations.isEmpty()) {
            System.out.println("No completed consultations.");
        } else {
            for (int i = 0; i < completedConsultations.size(); i++) {
                Consultation c = completedConsultations.get(i);
                System.out.println(c); // 确保 Consultation 有 toString()
            }
        }
    }
    
    public void showOngoingConsultations() {
        System.out.println("===== Ongoing Consultations =====");
        if (ongoingConsultations.isEmpty()) {
            System.out.println("No ongoing consultations.");
        } else {
            for (int i = 0; i < ongoingConsultations.size(); i++) {
                Consultation c = ongoingConsultations.get(i);
                System.out.println("Index " + i + " -> " + c);
            }
        }
        System.out.println("=================================");
    }

    // 计数器
    private static int appointmentCounter = 1001; // A1001
    private static int consultationCounter = 1001; // C1001

    private String generateNextAppointmentId() {
        return "A" + appointmentCounter++;
    }

    private String generateNextConsultationId() {
        return "C" + consultationCounter++;
    }

    public void addPatientToQueue(String patientId) {
        QueueEntry entry = QueueControl.addInQueue(patientId);

        if (entry != null) {
            System.out.println("Patient added to consultation queue: " + patientId);
        } else {
            System.out.println("Failed to add patient to queue.");
        }
    }

    // 查看队列
    public void viewQueue() {
        if (QueueControl.getQueueList().isEmpty()) {
            System.out.println("No patients in queue.");
        } else {
            System.out.println("\n--- Current Queue ---");
            for (int i = 0; i < QueueControl.getQueueList().size(); i++) {
                QueueEntry qe = QueueControl.getQueueList().get(i);
                System.out.println((i + 1) + ". " + qe.getPatientId() + " - " + qe.getStatus());
            }
        }
    }

    public void printAllDoctorsStatus(String header) {
        System.out.println("\n=== " + header + " ===");
        DynamicList<Doctor> allDoctors = DoctorManagement.getAllDoctors();
        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d.getDoctorID() + " - " + d.getName() + " : " + d.getWorkingStatus());
        }
        System.out.println("==========================\n");
    }

    // 开始下一位咨询
    public void startNextConsultation() {

        // ✅ 限制最大咨询数为3
        if (currentConsulting.size() >= 3) {
            System.out.println("Maximum consultations reached (3). Please wait for a consultation to finish.");
            return;
        }

        printAllDoctorsStatus("All Doctors Status Before Assignment");

        // 获取下一个等待的病人
        QueueEntry nextPatient = null;
        DynamicList<QueueEntry> queueList = QueueControl.getQueueList();
        for (int i = 0; i < queueList.size(); i++) {
            QueueEntry qe = queueList.get(i);
            if (qe.getStatus().equals(UtilityClass.statusWaiting)) {
                nextPatient = qe;
                break;
            }
        }

        if (nextPatient == null) {
            System.out.println("No patients waiting or no free doctors available.");
            return;
        }

        // ✅ 手动输入医生ID
        Scanner sc = new Scanner(System.in);
        Doctor assignedDoctor = null;
        while (assignedDoctor == null) {
            System.out.print("Enter Doctor ID to assign for Patient " + nextPatient.getPatientId() + ": ");
            String doctorId = sc.nextLine();
            Doctor d = DoctorManagement.findDoctorById(doctorId);
            if (d == null) {
                System.out.println("Doctor ID not found. Try again.");
            } else if (!d.getWorkingStatus().equals(UtilityClass.statusFree)) {
                System.out.println("Doctor is not free. Please choose another doctor.");
            } else {
                assignedDoctor = d; // 找到空闲医生
            }
        }

        assignedDoctor.setWorkingStatus(UtilityClass.statusConsulting); // 更新医生状态

        // 创建当前咨询记录
        CurrentServingDAO current = new CurrentServingDAO(nextPatient.getPatientId(), assignedDoctor.getDoctorID());
        currentConsulting.add(current); // 添加到 currentConsulting

        // 更新 QueueEntry 状态
        nextPatient.setStatus(UtilityClass.statusConsulting);

        // 创建 Consultation 对象并加入 static ongoingConsultations
        Patient patient = PatientManagement.findPatientById(nextPatient.getPatientId());
        if (patient != null) {
            String consultationId = generateNextConsultationId();
            Consultation consultation = new Consultation(
                    consultationId,
                    patient.getPatientID(),
                    assignedDoctor.getDoctorID(),
                    "" // symptoms 先空着，后面让医生输入
            );

            // ✅ 让医生输入病人的 Symptoms
            System.out.print("Enter Symptoms for Patient " + patient.getFullName() + ": ");
            String symptoms = sc.nextLine();
            consultation.setSymptoms(symptoms);
            consultation.setStartTime(LocalDateTime.now());

            ongoingConsultations.add(consultation);

            System.out.println("\n================ Consultation Started ================");
            System.out.println("Doctor: " + assignedDoctor.getName() + " (ID: " + assignedDoctor.getDoctorID() + ")");
            System.out.println("Patient: " + patient.getFullName() + " (ID: " + patient.getPatientID() + ")");
            System.out.println("Start Time: " + UtilityClass.formatLocalDateTime(consultation.getStartTime()));
            System.out.println("Symptoms: " + (consultation.getSymptoms().isEmpty() ? "-" : consultation.getSymptoms()));
            System.out.println("Doctor Status: " + assignedDoctor.getWorkingStatus());
            System.out.println("======================================================\n");
        } else {
            System.out.println("Patient data not found.");
        }

        // 打印所有医生状态（调试用）
        printAllDoctorsStatus("All Doctors Status After Assignment");
    }
    
    // 计算咨询已经进行多久
    public static String getConsultationDuration(LocalDateTime startTime) {
        if (startTime == null) {
            return "Not started";
        }

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startTime, now);

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    // --- 查看当前咨询 ---
    public void viewCurrentConsulting() {
        if (currentConsulting.size() == 0) {
            System.out.println("No patients currently consulting.");
            return;
        }

        System.out.println("\n--- Consulting Patients ---");
        for (int i = 0; i < currentConsulting.size(); i++) {
            CurrentServingDAO cs = currentConsulting.get(i);
            Patient p = PatientManagement.findPatientById(cs.getPatientId());
            Doctor d = DoctorManagement.findDoctorById(cs.getDoctorId());

            String patientName = (p != null) ? p.getFullName() : "Unknown Patient";
            String doctorName = (d != null) ? d.getName() : "Unknown Doctor";

            // 找对应的 Consultation 并计算时长
            Consultation consultation = null;
            for (int j = 0; j < ongoingConsultations.size(); j++) {
                Consultation c = ongoingConsultations.get(j);
                if (c.getPatientId().equals(cs.getPatientId())) {
                    consultation = c;
                    break;
                }
            }

            String duration = "N/A";
            if (consultation != null && consultation.getStartTime() != null) {
                LocalDateTime startTime = consultation.getStartTime();
                LocalDateTime now = LocalDateTime.now();
                Duration dur = Duration.between(startTime, now);
                long hours = dur.toHours();
                long minutes = dur.toMinutesPart();
                long seconds = dur.toSecondsPart();
                duration = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            }

            System.out.println("Patient: " + patientName + " (ID: " + cs.getPatientId() + ")"
                    + " | Doctor: " + doctorName + " (ID: " + cs.getDoctorId() + ")"
                    + " | Duration: " + duration);
        }
    }

    // 打印所有 currentConsulting 的内容
    public void printAllCurrentConsulting() {
        System.out.println("===== Current Consulting List =====");
        if (currentConsulting.isEmpty()) {
            System.out.println("No patients currently in consultation.");
        } else {
            for (int i = 0; i < currentConsulting.size(); i++) {
                CurrentServingDAO cs = currentConsulting.get(i);
                System.out.println(
                        "Index " + i
                        + " -> PatientId: " + cs.getPatientId()
                        + ", DoctorId: " + cs.getDoctorId()
                );
            }
        }
        System.out.println("===================================");
    }

    // 结束咨询并保存病人信息
    public void endConsultation(String patientId) {
        // 找队列中的病人
        QueueEntry queueEntry = null;
        DynamicList<QueueEntry> queueList = QueueControl.getQueueList();
        for (int i = 0; i < queueList.size(); i++) {
            QueueEntry qe = queueList.get(i);
            if (qe.getPatientId().equals(patientId) && qe.getStatus().equals(UtilityClass.statusConsulting)) {
                queueEntry = qe;
                break;
            }
        }

        if (queueEntry == null) {
            System.out.println("No ongoing consultation found for Patient ID: " + patientId);
            return;
        }

        queueEntry.setStatus(UtilityClass.statusCompleted);
        System.out.println("Consultation ended for Patient ID: " + patientId);

        // 从 ongoingConsultations 找对应 consultation
        Consultation consultation = null;
        int consultationIndex = -1;
        for (int i = 0; i < ongoingConsultations.size(); i++) {
            Consultation c = ongoingConsultations.get(i);
            if (c.getPatientId().equals(patientId)) {
                consultation = c;
                consultationIndex = i;
                break;
            }
        }

        if (consultation != null) {
            consultation.setEndTime(LocalDateTime.now());

            long totalSeconds = Duration.between(consultation.getStartTime(), consultation.getEndTime()).getSeconds();
            long hours = totalSeconds / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;

            String formattedDuration = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
            System.out.println("Consultation Duration: " + formattedDuration);

            completedConsultations.add(consultation);
            if (consultationIndex != -1) {
                ongoingConsultations.remove(consultationIndex); // ✅ 用索引移除
            }
        }

        // 保存病人信息
        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient != null) {
            completedPatients.add(patient);
            System.out.println("Patient info saved to completed consultations.");
        } else {
            System.out.println("Warning: Patient data not found to save.");
        }

        // 用 currentConsulting 管理医生状态
        int currentIndex = -1;
        for (int i = 0; i < currentConsulting.size(); i++) {
            if (currentConsulting.get(i).getPatientId().equals(patientId)) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex != -1) {
            CurrentServingDAO current = currentConsulting.get(currentIndex);
            Doctor doctor = DoctorManagement.findDoctorById(current.getDoctorId());
            if (doctor != null) {
                doctor.setWorkingStatus(UtilityClass.statusFree);
                System.out.println("Doctor " + doctor.getName() + " is now free.");
            }
            currentConsulting.remove(currentIndex); // ✅ 用索引移除
        }
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


    // 你可以加个方法查看所有完成咨询的病人
    public void viewCompletedPatients() {
        DynamicList<String> addedPatientIds = new DynamicList<>();

        System.out.println("--- Completed Patients ---");
        for (int i = 0; i < completedConsultations.size(); i++) {
            Consultation c = completedConsultations.get(i);
            String patientId = c.getPatientId();
            if (!addedPatientIds.contains(patientId)) {
                addedPatientIds.add(patientId);
                Patient p = PatientManagement.findPatientById(patientId);
                String name = (p != null) ? p.getFullName() : "Unknown";
                System.out.println(patientId + " - " + name);
            }
        }

        if (addedPatientIds.isEmpty()) {
            System.out.println("No completed patients.");
        }
    }


    // ✅ 新增方法：查看病人所有咨询记录（美化版）
    public void viewConsultationReport(String patientId) {
        boolean found = false;
        System.out.println("\n============================================");
        System.out.println("       CONSULTATION REPORT FOR PATIENT       ");
        System.out.println("          Patient ID: " + patientId);
        System.out.println("============================================");

        int count = 1;
        for (int i = 0; i < completedConsultations.size(); i++) {
            Consultation c = completedConsultations.get(i);
            if (c.getPatientId().equals(patientId)) {
                found = true;
                System.out.println("Consultation #" + count++);
                System.out.println("--------------------------------------------");
                System.out.printf("%-15s: %s\n", "Consultation ID", c.getConsultationId());
                System.out.printf("%-15s: %s\n", "Doctor ID", c.getDoctorId());
                System.out.printf("%-15s: %s\n", "Start Time", 
                    c.getStartTime() != null ? c.getStartTime().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : "-");
                System.out.printf("%-15s: %s\n", "End Time", 
                    c.getEndTime() != null ? c.getEndTime().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : "-");
                System.out.printf("%-15s: %s\n", "Duration", 
                    c.getEndTime() != null ? formatDuration(c.getDurationSeconds()) : "-");
                System.out.printf("%-15s: %s\n", "Symptoms", c.getSymptoms().isEmpty() ? "-" : c.getSymptoms());
                System.out.println("--------------------------------------------\n");
            }
        }

        if (!found) {
            System.out.println("No consultation records found for this patient.\n");
        }
        System.out.println("============================================\n");
    }

    // 辅助方法：格式化持续时间
    private String formatDuration(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
    }

    
    // ✅ 删除 Consultation 记录（通过 Consultation ID）
    public void deleteConsultationById(String consultationId) {
        if (completedConsultations.isEmpty()) {
            System.out.println("No consultations available to delete.");
            return;
        }

        boolean deleted = false;
        for (int i = 0; i < completedConsultations.size(); i++) {
            Consultation c = completedConsultations.get(i);
            if (c.getConsultationId().equals(consultationId)) {
                completedConsultations.remove(i);
                deleted = true;
                System.out.println("Consultation " + consultationId + " has been deleted.");
                break;
            }
        }

        if (!deleted) {
            System.out.println("Consultation ID " + consultationId + " not found.");
        }
    }

    public DynamicList<Appointment> getScheduledAppointments() {
        return scheduledAppointments;
    }
}

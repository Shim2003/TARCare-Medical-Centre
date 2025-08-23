/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.util.Scanner;
import java.util.Iterator;
import ADT.DynamicList;
import ADT.MyList;
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
    private static DynamicList<Consultation> ongoingConsultations = new DynamicList<>();
    private static MyList<CurrentServingDAO> currentConsulting = QueueControl.getCurrentServingPatient();
    private static DynamicList<Consultation> completedConsultations = new DynamicList<>();
    
    // 计数器
    private static int consultationCounter = 1001; // C1001
    
    private static String generateNextConsultationId() {
        return "C" + consultationCounter++;
    }
    
    private static Scanner sc = new Scanner(System.in);
    
    public static void showCompletedPatients() {
        System.out.println("--- Completed Patients ---");
        if (completedPatients.isEmpty()) {
            System.out.println("No completed patients.");
        } else {
            for (int i = 0; i < completedPatients.size(); i++) {
                System.out.println(completedPatients.get(i));
            }
        }
    }

    public static void showCurrentConsulting() {
        System.out.println("--- Current Consulting Patients ---");
        if (currentConsulting.isEmpty()) {
            System.out.println("No patients currently consulting.");
        } else {
            for (int i = 0; i < currentConsulting.size(); i++) {
                System.out.println(currentConsulting.get(i));
            }
        }
    }

    public static void showCompletedConsultations() {
        System.out.println("--- Completed Consultations ---");
        if (completedConsultations.isEmpty()) {
            System.out.println("No completed consultations.");
        } else {
            for (int i = 0; i < completedConsultations.size(); i++) {
                System.out.println(completedConsultations.get(i));
            }
        }
    }
    
    public static void showOngoingConsultations() {
        System.out.println("===== Ongoing Consultations =====");
        if (ongoingConsultations.isEmpty()) {
            System.out.println("No ongoing consultations.");
        } else {
            for (int i = 0; i < ongoingConsultations.size(); i++) {
                System.out.println("Index " + i + " -> " + ongoingConsultations.get(i));
            }
        }
        System.out.println("=================================");
    }

    public static void addPatientToQueue(String patientId) {
        QueueEntry entry = QueueControl.addInQueue(patientId);
        if (entry != null) {
            System.out.println("Patient added to consultation queue: " + patientId);
        } else {
            System.out.println("Failed to add patient to queue.");
        }
    }

    // 查看队列
    public static void viewQueue() {
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

    public static void printAllDoctorsStatus(String header) {
        System.out.println("\n=== " + header + " ===");
        MyList<Doctor> allDoctors = DoctorManagement.getAllDoctors();
        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d.getDoctorID() + " - " + d.getName() + " : " + d.getWorkingStatus());
        }
        System.out.println("==========================\n");
    }

    // 开始下一位咨询
    public static void startNextConsultation() {

        // ✅ 限制最大咨询数为3
        if (currentConsulting.size() >= 3) {
            System.out.println("Maximum consultations reached (3). Please wait for a consultation to finish.");
            return;
        }

        printAllDoctorsStatus("All Doctors Status Before Assignment");

        // 获取下一个等待的病人
        QueueEntry nextPatient = null;
        MyList<QueueEntry> queueList = QueueControl.getQueueList();
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
    public static void viewCurrentConsulting() {
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
                duration = getConsultationDuration(consultation.getStartTime());
            }

            System.out.println("Patient: " + patientName + " (ID: " + cs.getPatientId() + ")"
                    + " | Doctor: " + doctorName + " (ID: " + cs.getDoctorId() + ")"
                    + " | Duration: " + duration);
        }
    }

    // 打印所有 currentConsulting 的内容
    public static void printAllCurrentConsulting() {
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
    public static void endConsultation(String patientId) {
        // 找队列中的病人
        QueueEntry queueEntry = null;
        MyList<QueueEntry> queueList = QueueControl.getQueueList();
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
    
    // 你可以加个方法查看所有完成咨询的病人
    public static void viewCompletedPatients() {
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
    public static void viewConsultationReport(String patientId) {
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
                System.out.printf("%-15s: %s\n", "Start Time", c.getStartTime() != null
                        ? c.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : "-");
                System.out.printf("%-15s: %s\n", "End Time", c.getEndTime() != null
                        ? c.getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : "-");
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
    private static String formatDuration(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
    }

    
    // ✅ 删除 Consultation 记录（通过 Consultation ID）
    public static void deleteConsultationById(String consultationId) {
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
}

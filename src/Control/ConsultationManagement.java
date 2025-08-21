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
import java.time.DayOfWeek;

/**
 *
 * @author leekeezhan
 */
public class ConsultationManagement {

    private static DynamicList<Patient> completedPatients = new DynamicList<>();
    private DynamicList<Appointment> scheduledAppointments = new DynamicList<>();
    DynamicList<CurrentServingDAO> currentConsulting = QueueControl.getCurrentServingPatient();
    private DynamicList<Consultation> completedConsultations = new DynamicList<>();

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
        if (Consultation.getCurrentConsultationList().size() >= 3) {
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
        QueueControl.getCurrentServingPatient().add(current); // 添加到当前咨询列表

        // 更新 QueueEntry 状态
        nextPatient.setStatus(UtilityClass.statusConsulting);

        // 创建 Consultation 对象
        Patient patient = PatientManagement.findPatientById(nextPatient.getPatientId());
        if (patient != null) {
            String consultationId = generateNextConsultationId();
            Consultation consultation = new Consultation(
                consultationId,
                patient.getPatientID(),
                assignedDoctor.getDoctorID(),
                "", // symptoms
                ""  // diagnosis
            );
            
            // ✅ 让医生输入病人的 Symptoms
            System.out.print("Enter Symptoms for Patient " + patient.getFullName() + ": ");
            String symptoms = sc.nextLine();
            consultation.setSymptoms(symptoms);
            
            consultation.setStartTime(LocalDateTime.now());
            Consultation.setCurrentConsultation(consultation);

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

            System.out.println("Patient: " + patientName + " (ID: " + cs.getPatientId() + ")"
                    + " | Doctor: " + doctorName + " (ID: " + cs.getDoctorId() + ")");
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
                    "Index " + i + 
                    " -> PatientId: " + cs.getPatientId() + 
                    ", DoctorId: " + cs.getDoctorId()
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

        Consultation consultation = Consultation.getCurrentConsultation();
        if (consultation != null && consultation.getPatientId().equals(patientId)) {
            consultation.setEndTime(LocalDateTime.now());
            System.out.println("Consultation End Time: " + UtilityClass.formatLocalDateTime(consultation.getEndTime()));
            // ✅ 保存到 completedConsultations
            completedConsultations.add(consultation);
        }

        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient != null) {
            completedPatients.add(patient);
            System.out.println("Patient info saved to completed consultations.");
        } else {
            System.out.println("Warning: Patient data not found to save.");
        }

        // 用 currentConsulting 管理医生状态
        CurrentServingDAO current = null;
        for (int i = 0; i < currentConsulting.size(); i++) {
            CurrentServingDAO cs = currentConsulting.get(i);
            if (cs.getPatientId().equals(patientId)) {
                current = cs;
                break;
            }
        }

        if (current != null) {
            Doctor doctor = DoctorManagement.findDoctorById(current.getDoctorId());
            if (doctor != null) {
                doctor.setWorkingStatus(UtilityClass.statusFree);
                System.out.println("Doctor " + doctor.getName() + " is now free.");
            }
            int removeIndex = -1;
            for (int i = 0; i < currentConsulting.size(); i++) {
                if (currentConsulting.get(i).getPatientId().equals(patientId)) {
                    removeIndex = i;
                    break;
                }
            }

            if (removeIndex != -1) {
                currentConsulting.remove(removeIndex);
            }
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

    // 你可以加个方法查看所有完成咨询的病人
    public void viewCompletedPatients() {
        if (completedPatients.isEmpty()) {
            System.out.println("No patients have completed consultation yet.");
            return;
        }
        
        System.out.println("\n--- Completed Consultation Patients ---");
        for (int i = 0; i < completedPatients.size(); i++) {
            Patient p = completedPatients.get(i);
            System.out.println((i + 1) + ". " + p.getPatientID() + " - " + p.getFullName());
        }
    }
    
    // ✅ 新增方法：查看病人所有咨询记录
    public void viewConsultationReport(String patientId) {
        boolean found = false;
        System.out.println("\n=== Consultation Report for Patient ID: " + patientId + " ===\n");
        for (int i = 0; i < completedConsultations.size(); i++) {
            Consultation c = completedConsultations.get(i);
            if (c.getPatientId().equals(patientId)) {
                System.out.println(c.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No consultation records found for this patient.");
        }
        System.out.println("============================================\n");
    }
}
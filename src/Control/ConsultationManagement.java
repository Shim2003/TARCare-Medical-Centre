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
    private DynamicList<CurrentServingDAO> currentConsulting = new DynamicList<>();

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
    
    private void printAllDoctorsStatus(String header) {
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

    // 分配空闲医生
    DynamicList<Doctor> freeDoctors = DoctorManagement.getFreeDoctors();
    if (freeDoctors.isEmpty()) {
        System.out.println("No free doctors available. Please wait.");
        return;
    }

    Doctor assignedDoctor = freeDoctors.get(0); // 取第一个空闲医生
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
        consultation.setStartTime(LocalDateTime.now());
        Consultation.setCurrentConsultation(consultation);

        System.out.println("Doctor ID: " + assignedDoctor.getDoctorID() 
            + " (" + assignedDoctor.getName() + ")\n" 
            + " started consultation for Patient ID: " 
            + patient.getPatientID() 
            + ", Name: " + patient.getFullName());
        System.out.println("Consultation Start Time: " + consultation.getStartTime());
        System.out.println("Updated Doctor Status: " + assignedDoctor.getName() 
            + " is now " + assignedDoctor.getWorkingStatus());
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


            
    public void scheduleNextAppointment(String patientId, String doctorId, String dateTimeStr, String symptoms) {
        // 检查病人是否存在
        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found. Cannot schedule appointment.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime appointmentTime = LocalDateTime.parse(dateTimeStr, formatter);
            String appointmentId = generateNextAppointmentId();

            Appointment newAppointment = new Appointment(
                    appointmentId,
                    patientId,
                    doctorId,
                    appointmentTime,
                    symptoms
            );

            scheduledAppointments.add(newAppointment);
            System.out.println("Appointment scheduled: " + appointmentId + " for Patient " + patientId + " at " + dateTimeStr);
        } catch (Exception e) {
            System.out.println("Invalid date/time format. Please use 'yyyy-MM-dd HH:mm'.");
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
}
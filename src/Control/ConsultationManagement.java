/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.util.Scanner;
import ADT.DynamicList;
import Entity.QueueEntry;
import Entity.Patient;
import Entity.Doctor;
import Entity.Consultation;
import Entity.Appointment;
import Utility.UtilityClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author leekeezhan
 */
public class ConsultationManagement {
    
    private static DynamicList<Patient> completedPatients = new DynamicList<>();
    private DynamicList<Appointment> scheduledAppointments = new DynamicList<>();
    
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

    // 开始下一位咨询
    public void startNextConsultation() {
        
        // 打印所有医生状态（调试用）
        System.out.println("\n=== All Doctors Status Before Assignment ===");
        DynamicList<Doctor> allDoctors = DoctorManagement.getAllDoctors();
        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d.getDoctorID() + " - " + d.getName() 
                + " : " + d.getWorkingStatus());
        }
        System.out.println("==========================\n");

        if (!QueueControl.isFullConsulting()) {
            DynamicList<Doctor> freeDoctors = DoctorManagement.getFreeDoctors();

            if (freeDoctors.isEmpty()) {
                System.out.println("No free doctors available.");
                return;
            }
            
            // 直接分配第一个空闲医生
            Doctor assignedDoctor = freeDoctors.get(0);
            QueueEntry next = QueueControl.getNextInQueue();

            if (next != null) {
                next.setDoctorId(assignedDoctor.getDoctorID());
                Patient patient = PatientManagement.findPatientById(next.getPatientId());

                if (patient != null) {
                    
                    // 生成实际 consultation ID
                    String consultationId = generateNextConsultationId();

                    Consultation consultation = new Consultation(
                        consultationId,
                        patient.getPatientID(),
                        assignedDoctor.getDoctorID(),
                        "", // symptoms
                        ""  // diagnosis
                    );
                    consultation.setStartTime(LocalDateTime.now()); // 记录开始咨询时间
                    Consultation.setCurrentConsultation(consultation);

                    System.out.println("Doctor ID: " + assignedDoctor.getDoctorID() 
                        + " (" + assignedDoctor.getName() + ")\n" 
                        + " started consultation for Patient ID: " 
                        + patient.getPatientID() 
                        + ", Name: " + patient.getFullName());

                    System.out.println("Consultation Start Time: " + consultation.getStartTime());

                    // 更新医生状态
                    assignedDoctor.setWorkingStatus(UtilityClass.statusConsulting);
                    System.out.println("Updated Doctor Status: " 
                        + assignedDoctor.getName() 
                        + " is now " 
                        + assignedDoctor.getWorkingStatus());

                    // 更新 QueueEntry 状态
                    next.setStatus("Consulting");

                } else {
                    System.out.println("Patient data not found.");
                }
            } else {
                System.out.println("No patients waiting in queue.");
            }
        } else {
            System.out.println("Maximum number of consultations reached.");
        }

        // 打印所有医生状态（调试用）
        System.out.println("\n=== All Doctors Status After Assignment ===");
        allDoctors = DoctorManagement.getAllDoctors();
        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d.getDoctorID() + " - " + d.getName() 
                + " : " + d.getWorkingStatus());
        }
        System.out.println("==========================\n");
    }



    public void viewCurrentConsulting() {
        var consultingList = QueueControl.getQueueListByStatus(UtilityClass.statusConsulting);
        if (consultingList.isEmpty()) {
            System.out.println("No patients currently consulting.");
        } else {
            System.out.println("\n--- Consulting Patients ---");
            for (int i = 0; i < consultingList.size(); i++) {
                QueueEntry qe = consultingList.get(i);
                Patient p = PatientManagement.findPatientById(qe.getPatientId());
                Doctor d = DoctorManagement.findDoctorById(qe.getDoctorId());

                String patientId = (p != null) ? p.getPatientID() : "Unknown ID";
                String patientName = (p != null) ? p.getFullName() : "Unknown Patient";
                String doctorId = (d != null) ? d.getDoctorID() : "Unknown ID";
                String doctorName = (d != null) ? d.getName() : "Unknown Doctor";

                System.out.printf("%d. Patient: %s (%s) - Doctor: %s (%s)%n",
                        i + 1, patientId, patientName, doctorId, doctorName);
            }
        }
    }


    // 结束咨询并保存病人信息
    public void endConsultation(String patientId) {
        QueueEntry queueEntry = QueueControl.getQueueEntryByPatientId(patientId);
        if (queueEntry != null && queueEntry.getStatus().equals(UtilityClass.statusConsulting)) {
            queueEntry.setStatus(UtilityClass.statusCompleted); // 更新队列状态
            System.out.println("Consultation ended for Patient ID: " + patientId);

            Consultation consultation = Consultation.getCurrentConsultation();
            if (consultation != null && consultation.getPatientId().equals(patientId)) {
                consultation.setEndTime(LocalDateTime.now()); // 记录结束时间
                System.out.println("Consultation End Time: " + 
                    UtilityClass.formatLocalDateTime(consultation.getEndTime()));
            }

            // 保存病人到完成列表
            Patient patient = PatientManagement.findPatientById(patientId);
            if (patient != null) {
                completedPatients.add(patient);
                System.out.println("Patient info saved to completed consultations.");
            } else {
                System.out.println("Warning: Patient data not found to save.");
            }

            // 更新医生状态为空闲
            Doctor doctor = DoctorManagement.findDoctorById(queueEntry.getDoctorId());
            if (doctor != null) {
                doctor.setWorkingStatus(UtilityClass.statusFree);
                System.out.println("Doctor " + doctor.getName() + " is now free.");
            }

        } else {
            System.out.println("No ongoing consultation found for Patient ID: " + patientId);
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
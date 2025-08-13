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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author leekeezhan
 */
public class ConsultationManagement {
    
    private static DynamicList<Patient> completedPatients = new DynamicList<>();
    private DynamicList<Consultation> scheduledConsultations = new DynamicList<>();
    
    
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
    Scanner scanner = new Scanner(System.in);

    if (!QueueControl.isFullConsulting()) {
        System.out.print("Please enter Doctor ID: ");
        String doctorId = scanner.nextLine().trim();

        QueueEntry next = QueueControl.getNextInQueue();
        if (next != null) {
            Patient patient = PatientManagement.findPatientById(next.getPatientId());
            if (patient != null) {
                System.out.println("Doctor ID: " + doctorId + " started consultation for Patient ID: " + next.getPatientId() + ", Name: " + patient.getFullName());
            } else {
                System.out.println("Patient data not found.");
            }
        } else {
            System.out.println("No patients waiting in queue.");
        }
    } else {
        System.out.println("Maximum number of consultations reached.");
    }
}


    public void viewCurrentConsulting() {
        var consultingList = QueueControl.getQueueListByStatus(Utility.UtilityClass.statusConsulting);
        if (consultingList.isEmpty()) {
            System.out.println("No patients currently consulting.");
        } else {
            System.out.println("\n--- Consulting Patients ---");
            for (int i = 0; i < consultingList.size(); i++) {
                QueueEntry qe = consultingList.get(i);
                System.out.println((i + 1) + ". " + qe.getPatientId());
            }
        }
    }

    // 结束咨询并保存病人信息
     public void endConsultation(String patientId) {
        if (QueueControl.markAsCompleted(patientId)) {
            System.out.println("Consultation ended for Patient ID: " + patientId);

            Patient patient = PatientManagement.findPatientById(patientId);
            if (patient != null) {
                completedPatients.add(patient);
                System.out.println("Patient info saved to completed consultations.");
            } else {
                System.out.println("Warning: Patient data not found to save.");
            }
        }
    }
    
    // 预约下一次咨询
    public void scheduleNextConsultation(String consultationId, String patientId, String doctorId, String dateTimeStr, String symptoms) {
        // 检查病人是否存在
        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found. Cannot schedule consultation.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

            Consultation newConsultation = new Consultation(
                consultationId,
                patientId,
                doctorId,
                dateTime,
                symptoms,
                ""  // 诊断可以先空着，之后填写
            );

            scheduledConsultations.add(newConsultation);
            System.out.println("Consultation scheduled for Patient " + patientId + " at " + dateTimeStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format. Please use 'yyyy-MM-dd HH:mm'.");
        }
    }
    
    // 查看所有预约consultations
    public void viewScheduledConsultations() {
        if (scheduledConsultations.isEmpty()) {
            System.out.println("No scheduled consultations.");
            return;
        }
        System.out.println("\n--- Scheduled Consultations ---");
        for (int i = 0; i < scheduledConsultations.size(); i++) {
            System.out.println((i + 1) + ". " + scheduledConsultations.get(i).toString());
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
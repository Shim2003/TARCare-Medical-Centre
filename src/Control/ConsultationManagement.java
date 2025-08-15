/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.util.Scanner;
import ADT.DynamicList;
import DAO.CurrentServingDAO;
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
    public void startNextConsultation(String doctorId) {

        DynamicList<CurrentServingDAO> currentServingPatient = QueueControl.getCurrentServingPatient();
        CurrentServingDAO patientBeingServed = currentServingPatient.findFirst(csp -> csp.getDoctorId().equals(doctorId));

        if (patientBeingServed == null) {
            System.out.println("No patient served by " + doctorId);
            return;
        }

        Patient patient = PatientManagement.findPatientById(patientBeingServed.getPatientId());
        Doctor doctor = DoctorManagement.findDoctorById(doctorId);

        if ((patient == null) && (doctor == null)) {
            System.out.println("No patient and doctor");
            return;
        }

        // 打印所有医生状态（调试用）
        System.out.println("\n=== Consult Details ===");
        System.out.println("Consulting Patient : ");
        System.out.println("ID : " + patient.getPatientID());
        System.out.println("Name : " + patient.getFullName());

        String consultationId = generateNextConsultationId();

        Consultation consultation = new Consultation(
                consultationId,
                patient.getPatientID(),
                doctor.getDoctorID(),
                "", // symptoms
                "" // diagnosis
        );
        consultation.setStartTime(LocalDateTime.now()); // 记录开始咨询时间
        Consultation.setCurrentConsultation(consultation);

        System.out.println("Doctor ID: " + doctor.getDoctorID()
                + " (" + doctor.getName() + ")\n"
                + " started consultation for Patient ID: "
                + patient.getPatientID()
                + ", Name: " + patient.getFullName());

        System.out.println("Consultation Start Time: " + consultation.getStartTime());

        // 更新医生状态
        System.out.println("Updated Doctor Status: " 
                + doctor.getName()
                + " is now "
                + doctor.getWorkingStatus());

        // 打印所有医生状态（调试用）
        System.out.println("\n=== All Doctors Status After Assignment ===");
        DynamicList<Doctor> allDoctors = DoctorManagement.getAllDoctors();
        for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d.getDoctorID() + " - " + d.getName()
                    + " : " + d.getWorkingStatus());
        }
        System.out.println("==========================\n");
    }

    public void viewCurrentConsulting() {
        var consultingList = QueueControl.getCurrentServingPatient();
        if (consultingList.isEmpty()) {
            System.out.println("No patients currently consulting.");
        } else {
            System.out.println("\n--- Consulting Patients ---");
            for (int i = 0; i < consultingList.size(); i++) {
                CurrentServingDAO cs = consultingList.get(i);
                Patient p = PatientManagement.findPatientById(cs.getPatientId());
                Doctor d = DoctorManagement.findDoctorById(cs.getDoctorId());

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
    public void endConsultation(String doctorId) {

        DynamicList<CurrentServingDAO> cs = QueueControl.getCurrentServingPatient();
        CurrentServingDAO serving = cs.findFirst(c -> c.getDoctorId().equals(doctorId));

        if (serving == null) {
            System.out.println("Doctor ID not found in current serving list.");
            return;
        }

        Patient patient = PatientManagement.findPatientById(serving.getPatientId());
        Doctor doctor = DoctorManagement.findDoctorById(doctorId);

        if (patient == null) {
            System.out.println("Patient data not found for ID: " + serving.getPatientId());
            return;
        }
        if (doctor == null) {
            System.out.println("Doctor data not found for ID: " + doctorId);
            return;
        }

        QueueControl.updateQueueStatus(patient.getPatientID());
        System.out.println("Consultation ended for Patient ID: " + patient.getPatientID());

        Consultation consultation = Consultation.getCurrentConsultation();
        if (consultation != null && consultation.getPatientId().equals(patient.getPatientID())) {
            consultation.setEndTime(LocalDateTime.now());
            System.out.println("Consultation End Time: "
                    + UtilityClass.formatLocalDateTime(consultation.getEndTime()));
        }

        completedPatients.add(patient);
        System.out.println("Patient info saved to completed consultations.");

        doctor.setWorkingStatus(UtilityClass.statusFree);
        System.out.println("Doctor " + doctor.getName() + " is now free.");

        cs.removeIf(c -> c.getDoctorId().equals(doctorId));
        System.out.println("Doctor-patient record removed from current serving list.");
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

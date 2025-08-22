/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author leekeezhan
 */
import ADT.DynamicList;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consultation {
    private String consultationId;
    private String patientId;
    private String doctorId;
    private LocalDateTime startTime;  
    private LocalDateTime endTime;    
    private String symptoms;
    private long durationSeconds;
    private static Consultation currentConsultation;
    private static DynamicList<Consultation> currentConsultationList = new DynamicList<>();

    public Consultation(String consultationId, String patientId, String doctorId, String symptoms) {
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.symptoms = symptoms;
    }

    public String getConsultationId() { return consultationId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getSymptoms() { return symptoms; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public static Consultation getCurrentConsultation() { return currentConsultation; }
    public long getDurationSeconds() { return durationSeconds; }

    public void setConsultationId(String consultationId) { this.consultationId = consultationId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        if (this.startTime != null && this.endTime != null) {
            Duration duration = Duration.between(this.startTime, this.endTime);
            this.durationSeconds = duration.getSeconds();
        }
    } 
    
    public static DynamicList<Consultation> getCurrentConsultationList() {
        return currentConsultationList;
    }

    public static void setCurrentConsultation(Consultation consultation) {
        currentConsultation = consultation;
        currentConsultationList.add(consultation);
    }
    
    public static void removeCurrentConsultation(Consultation consultation) {
        for (int i = 0; i < currentConsultationList.size(); i++) {
            if (currentConsultationList.get(i).getConsultationId().equals(consultation.getConsultationId())) {
                currentConsultationList.remove(i);
                break;
            }
        }
        if (currentConsultation == consultation) {
            currentConsultation = null;
        }
    }
    
    private String formatDuration(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Consultation ID: " + consultationId +
               "\nPatient ID: " + patientId +
               "\nDoctor ID: " + doctorId +
               "\nStart Time: " + (startTime != null ? startTime.format(formatter) : "-") +
               "\nEnd Time: " + (endTime != null ? endTime.format(formatter) : "-") +
               "\nDuration: " + (endTime != null ? formatDuration(durationSeconds) : "-") +
               "\nSymptoms: " + symptoms + "\n";
    }
}

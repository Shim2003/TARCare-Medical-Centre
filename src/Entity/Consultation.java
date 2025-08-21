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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consultation {
    private String consultationId;
    private String patientId;
    private String doctorId;
    private LocalDateTime startTime;  // 新增开始时间
    private LocalDateTime endTime;    // 新增结束时间
    private String symptoms;
    private String diagnosis;
    private static Consultation currentConsultation;
    private static DynamicList<Consultation> currentConsultationList = new DynamicList<>();

    public Consultation(String consultationId, String patientId, String doctorId,
                        String symptoms, String diagnosis) {
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
    }

    public String getConsultationId() { return consultationId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getSymptoms() { return symptoms; }
    public String getDiagnosis() { return diagnosis; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public static Consultation getCurrentConsultation() { return currentConsultation; }
    
    public static DynamicList<Consultation> getCurrentConsultationList() {
        return currentConsultationList;
    }

    public void setConsultationId(String consultationId) { this.consultationId = consultationId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public static void setCurrentConsultation(Consultation consultation) {
        currentConsultation = consultation;
        currentConsultationList.add(consultation); // 加入列表，追踪所有进行中的咨询
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


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Consultation ID: " + consultationId +
               "\nPatient ID: " + patientId +
               "\nDoctor ID: " + doctorId +
               "\nStart Time: " + startTime.format(formatter)  +
               "\nEnd Time: " + endTime.format(formatter)  +
               "\nSymptoms: " + symptoms +
               "\nDiagnosis: " + diagnosis + "\n";
    }
}

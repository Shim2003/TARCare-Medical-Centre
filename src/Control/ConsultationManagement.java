/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import DAO.CurrentServingDAO;
import Entity.*;
import Utility.UtilityClass;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author leekeezhan
 */
public class ConsultationManagement {

    private static MyList<Patient> completedPatients = new DynamicList<>();
    private static MyList<Consultation> ongoingConsultations = new DynamicList<>();
    private static MyList<CurrentServingDAO> currentConsulting = QueueControl.getCurrentServingPatient();
    private static MyList<Consultation> completedConsultations = new DynamicList<>();
    private static MyList<Consultation> backupConsultations = new DynamicList<>();
    
    // Get all consultations (completed) from Consultation entity
    public static DynamicList<Consultation> getAllConsultations() {
        return Consultation.getCompletedConsultations();
    }
    
    // Counter
    private static int consultationCounter = 1001; // C1001
    
    // Initialize the consultation ID counter based on the highest existing consultation ID
    public static void initializeConsultationCounter() {
        int maxId = 1000;
        MyList<Consultation> completedList = getCompletedConsultations();

        for (int i = 0; i < completedList.size(); i++) {
            String id = completedList.get(i).getConsultationId();
            if (id != null && id.length() > 1) {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxId) maxId = num;
            }
        }

        consultationCounter = maxId + 1;
    }

    // Generate the next unique consultation ID
    private static String generateNextConsultationId() {
        return "C" + (consultationCounter++);
    }
    
    // Get the list of ongoing consultations
    public static MyList<Consultation> getOngoingConsultationsList() {
        return ongoingConsultations;
    }

    // Get the list of completed consultations
    public static MyList<Consultation> getCompletedConsultationsList() {
        return completedConsultations;
    }
    
    // Get ongoing consultations list
    public static MyList<Consultation> getOngoingConsultations() {
        return ongoingConsultations;
    }
    
    // Convert ongoing consultations into a display-friendly string array
    public static String[] getOngoingConsultationsForDisplayArray() {
        MyList<Consultation> ongoingList = getOngoingConsultationsList();

        if (ongoingList.isEmpty()) return new String[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String[] displayArr = new String[ongoingList.size()];

        for (int i = 0; i < ongoingList.size(); i++) {
            Consultation c = ongoingList.get(i);
            String startTime = (c.getStartTime() != null) ? c.getStartTime().format(dtf) : "-";
            String endTime = (c.getEndTime() != null) ? c.getEndTime().format(dtf) : "-";
            String duration = (c.getStartTime() != null && c.getEndTime() != null)
                    ? formatDuration(Duration.between(c.getStartTime(), c.getEndTime()).getSeconds())
                    : "-";
            String symptoms = (c.getSymptoms() != null && !c.getSymptoms().isEmpty()) ? c.getSymptoms() : "-";

            displayArr[i] = "Index " + i + " -> Consultation ID: " + c.getConsultationId() + "\n"
                    + "Patient ID: " + c.getPatientId() + "\n"
                    + "Doctor ID: " + c.getDoctorId() + "\n"
                    + "Start Time: " + startTime + "\n"
                    + "End Time: " + endTime + "\n"
                    + "Duration: " + duration + "\n"
                    + "Symptoms: " + symptoms;
        }

        return displayArr;
    }

    public static DynamicList<Consultation> getConsultationReportList(String id) {
        MyList<Consultation> list = getConsultationReport(id);

        DynamicList<Consultation> result = new DynamicList<>();
        for (Consultation c : list) {
            result.add(c);
        }

        return result;
    }

    // Get a list of all doctors
    public static MyList<Doctor> getAllDoctors() {
        return DoctorManagement.getAllDoctors();
    }
    
    public static MyList<Doctor> getAllDoctorsStatus() {
        return DoctorManagement.getAllDoctors();
    }
    
    // Get next waiting patient
    public static QueueEntry getNextWaitingPatient() {
        MyList<QueueEntry> queueList = QueueControl.getQueueList();
        for (int i = 0; i < queueList.size(); i++) {
            QueueEntry qe = queueList.get(i);
            if (qe.getStatus().equals(UtilityClass.statusReadyToConsult)) {
                return qe;
            }
        }
        return null; // No waiting patient
    }
    
    // Get latest CurrentServingDAO (recently assigned patient & doctor)
    public static CurrentServingDAO getLatestCurrentConsulting() {
        if (currentConsulting.isEmpty()) {
            return null; // No ongoing consultation
        }
        return currentConsulting.get(currentConsulting.size() - 1); 
    }
    
    // Get the most recent current consulting record
    public static MyList<CurrentServingDAO> getCurrentConsulting() {
        return currentConsulting;
    }
    
    // Get the list of current consulting records
    public static boolean canStartConsultation() {
        return currentConsulting.size() < 3;
    }

    // Start next consultation
    public static Consultation startNextConsultation(String doctorId, String symptoms) {

        // Limit maximum concurrent consultations to 3
        if (currentConsulting.size() >= 3) {
            return null;
        }

        // Get next waiting patient
        QueueEntry nextPatient = getNextWaitingPatient();
        if (nextPatient == null) {
            return null;
        }

        // Manually input doctor ID
        Doctor assignedDoctor = DoctorManagement.findDoctorById(doctorId);
        if (assignedDoctor == null || !assignedDoctor.getWorkingStatus().equals(UtilityClass.statusFree)) {
            return null; // doctor can no used
        }

        assignedDoctor.setWorkingStatus(UtilityClass.statusConsulting); // Update doctor status

        // Create current consultation record
        CurrentServingDAO current = new CurrentServingDAO(nextPatient.getPatientId(), assignedDoctor.getDoctorID());
        currentConsulting.add(current); // Add to currentConsulting

        // Update QueueEntry status
        nextPatient.setStatus(UtilityClass.statusConsulting);

        // Create Consultation object and add to ongoingConsultations
        Patient patient = PatientManagement.findPatientById(nextPatient.getPatientId());
        if (patient == null) {
            return null;
        }

        String consultationId = generateNextConsultationId();
        Consultation consultation = new Consultation(
            consultationId,
            patient.getPatientID(),
            assignedDoctor.getDoctorID(),
            symptoms // symptoms left empty, doctor will input
        );
        
        consultation.setStartTime(LocalDateTime.now());
        ongoingConsultations.add(consultation);

        return consultation;
    }
    
    // Calculate consultation duration
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
    
    // Get display list of all completed consultations
    public static DynamicList<String> getCompletedConsultationsDisplayList() {
        DynamicList<String> displayList = new DynamicList<>();

        for (int i = 0; i < completedConsultations.size(); i++) {
            Consultation c = completedConsultations.get(i);

            String startTime = (c.getStartTime() != null) 
                ? UtilityClass.formatLocalDateTime(c.getStartTime()) 
                : "-";
            String endTime = (c.getEndTime() != null) 
                ? UtilityClass.formatLocalDateTime(c.getEndTime()) 
                : "-";
            String duration = (c.getStartTime() != null && c.getEndTime() != null) 
                ? formatDuration(Duration.between(c.getStartTime(), c.getEndTime()).getSeconds()) 
                : "-";
            String symptoms = (c.getSymptoms() != null && !c.getSymptoms().isEmpty()) 
                ? c.getSymptoms() 
                : "-";

            String display = String.format(
                "Consultation ID: %s%nPatient ID: %s%nDoctor ID: %s%nStart Time: %s%nEnd Time: %s%nDuration: %s%nSymptoms: %s%n",
                c.getConsultationId(),
                c.getPatientId(),
                c.getDoctorId(),
                startTime,
                endTime,
                duration,
                symptoms
            );

            displayList.add(display);
        }

        return displayList;
    }

    // View current consulting patients
    public static DynamicList<String> getCurrentConsultingInfo() {
        DynamicList<String> infoList = new DynamicList<>();

        for (int i = 0; i < currentConsulting.size(); i++) {
            CurrentServingDAO cs = currentConsulting.get(i);
            Patient p = PatientManagement.findPatientById(cs.getPatientId());
            Doctor d = DoctorManagement.findDoctorById(cs.getDoctorId());

            String patientName = (p != null) ? p.getFullName() : "Unknown Patient";
            String doctorName = (d != null) ? d.getName() : "Unknown Doctor";

            Consultation consultation = null;
            for (int j = 0; j < ongoingConsultations.size(); j++) {
                Consultation c = ongoingConsultations.get(j);
                if (c.getPatientId().equals(cs.getPatientId())) {
                    consultation = c;
                    break;
                }
            }

            String duration = (consultation != null && consultation.getStartTime() != null)
                              ? getConsultationDuration(consultation.getStartTime())
                              : "N/A";

            String line = "Patient: " + patientName + " (ID: " + cs.getPatientId() + ")"
                        + " | Doctor: " + doctorName + " (ID: " + cs.getDoctorId() + ")"
                        + " | Duration: " + duration;

            infoList.add(line);
        }

        return infoList;
    }

    // End consultation and save patient info
    public static EndConsultationResult endConsultation(String patientId) {
        EndConsultationResult result = new EndConsultationResult();
        result.success = false;
        
        // Find patient in queue
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
            return result;
        }

        queueEntry.setStatus(UtilityClass.statusCompleted);

        // Find corresponding consultation in ongoingConsultations
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
            result.consultationId = consultation.getConsultationId();
            result.duration = getConsultationDuration(consultation.getStartTime());
            completedConsultations.add(consultation);
            if (consultationIndex != -1) ongoingConsultations.remove(consultationIndex);
        }

        // Save patient info
        Patient patient = PatientManagement.findPatientById(patientId);
        if (patient != null) {
            completedPatients.add(patient);
            result.patientSavedMsg = "Patient info saved to completed consultations.";
        }

        // Update doctor status in currentConsulting
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
                result.doctorStatusMsg = "Doctor " + doctor.getName() + " is now free.";
            }
            currentConsulting.remove(currentIndex); // Remove by index
        }
        result.success = true;
        result.patientId = patientId;
        return result;
    }
    
    // ConsultationManagement.java
    public static boolean hasCurrentConsulting() {
        return !currentConsulting.isEmpty();
    }

    // Print current consulting information to console
    public static void printCurrentConsultingInfo() {
        for (int i = 0; i < currentConsulting.size(); i++) {
            CurrentServingDAO cs = currentConsulting.get(i);
            Patient p = PatientManagement.findPatientById(cs.getPatientId());
            Doctor d = DoctorManagement.findDoctorById(cs.getDoctorId());
            String patientName = (p != null) ? p.getFullName() : "Unknown Patient";
            String doctorName = (d != null) ? d.getName() : "Unknown Doctor";

            Consultation consultation = null;
            for (int j = 0; j < ongoingConsultations.size(); j++) {
                Consultation c = ongoingConsultations.get(j);
                if (c.getPatientId().equals(cs.getPatientId())) {
                    consultation = c;
                    break;
                }
            }

            String duration = (consultation != null && consultation.getStartTime() != null)
                              ? getConsultationDuration(consultation.getStartTime())
                              : "N/A";

            System.out.println("Patient: " + patientName + " (ID: " + cs.getPatientId() + ")"
                             + " | Doctor: " + doctorName + " (ID: " + cs.getDoctorId() + ")"
                             + " | Duration: " + duration);
        }
    }

    // Print all completed patients information to console
    public static void printCompletedPatientsInfo() {
        DynamicList<String> addedPatientIds = new DynamicList<>();
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
    }

    // Print all doctors' working status with a header
    public static MyList<String> getAllDoctorsStatus(String header) {
        MyList<String> statusList = new DynamicList<>();
        MyList<Doctor> doctors = DoctorManagement.getAllDoctors();

        statusList.add("=== " + header + " ===");
        for (int i = 0; i < doctors.size(); i++) {
            Doctor d = doctors.get(i);
            statusList.add(d.getDoctorID() + " - " + d.getName() + " : " + d.getWorkingStatus());
        }

        return statusList;
    }
    
    // Method to view all completed patients
    public static DynamicList<String> getCompletedPatientsInfo() {
        DynamicList<String> result = new DynamicList<>();
        DynamicList<String> addedPatientIds = new DynamicList<>();

        for (int i = 0; i < completedConsultations.size(); i++) {
            Consultation c = completedConsultations.get(i);
            String patientId = c.getPatientId();
            if (!addedPatientIds.contains(patientId)) {
                addedPatientIds.add(patientId);
                Patient p = PatientManagement.findPatientById(patientId);
                String name = (p != null) ? p.getFullName() : "Unknown";
                result.add(patientId + " - " + name);
            }
        }
        return result;
    }

    // View consultation report
    public static DynamicList<Consultation> getConsultationReport(String id) {
        DynamicList<Consultation> result = new DynamicList<>();
        boolean searchByConsultationId = isConsultationId(id);

        for (int i = 0; i < completedConsultations.size(); i++) {
            Consultation c = completedConsultations.get(i);
            if (c.getPatientId().equalsIgnoreCase(id) || c.getConsultationId().equalsIgnoreCase(id)) {
                result.add(c);
                if (searchByConsultationId) break;
            }
        }
        return result;
    }

    // Helper method: check if it's a ConsultationID
    private static boolean isConsultationId(String id) {
        return id != null && id.toUpperCase().startsWith("C");
    }

    // Helper method: format duration
    public static String formatDuration(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
    }
    
    // Delete Consultation record by Consultation ID
    public static boolean deleteConsultationById(String consultationId) {
        return completedConsultations.removeIf(
            c -> c.getConsultationId().equals(consultationId)
        );
    }
    
    // Insert a consultation into ongoing consultations at a specific index
    public static boolean insertConsultationAt(int index, Consultation consultation) {
        if (index < 0 || index > ongoingConsultations.size()) {
            return false;
        }
        ongoingConsultations.add(index, consultation);
        return true;
    }
    
    // Export backup consultations to an array
    public static Consultation[] exportConsultationsToArray() {
        if (backupConsultations == null) return new Consultation[0];

        Object[] objArr = backupConsultations.toArray();
        Consultation[] arr = new Consultation[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            arr[i] = (Consultation) objArr[i];
        }
        return arr; // UI decides how to display
    }

    // Get statistical data for consultation durations
    public static ConsultationStats getConsultationDurationStats() {
        if (completedConsultations.isEmpty()) return null;

        DynamicList.ListStatistics<Consultation> stats = completedConsultations.getStatistics(
            c -> {
                if (c.getStartTime() != null && c.getEndTime() != null) {
                    return Duration.between(c.getStartTime(), c.getEndTime()).toMinutes();
                } else {
                    return 0L;
                }
            }
        );

        return new ConsultationStats(
        stats.count,
        stats.average,
        (long) stats.max,
        (long) stats.min,
        stats.standardDeviation
        );
    }

    // Backup completed consultations into backupConsultations list
    public static void backupConsultations() {
        backupConsultations.clear();
        for (int i = 0; i < completedConsultations.size(); i++) {
            backupConsultations.add(completedConsultations.get(i));
        }
    }
    
    // Get backup consultations
    public static MyList<Consultation> getBackup() {
        return backupConsultations;
    }
    
    // Get backup consultations in a display-friendly format
    public static DynamicList<String> getBackupDisplayList() {
        DynamicList<String> displayList = new DynamicList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        for (int i = 0; i < backupConsultations.size(); i++) {
            Consultation c = backupConsultations.get(i);

            String startTime = (c.getStartTime() != null) ? UtilityClass.formatLocalDateTime(c.getStartTime()) : "-";
            String endTime = (c.getEndTime() != null) ? UtilityClass.formatLocalDateTime(c.getEndTime()) : "-";
            String duration = (c.getStartTime() != null && c.getEndTime() != null)
                    ? formatDuration(Duration.between(c.getStartTime(), c.getEndTime()).getSeconds())
                    : "-";
            String symptoms = (c.getSymptoms() != null && !c.getSymptoms().isEmpty()) ? c.getSymptoms() : "-";

            displayList.add("Consultation ID: " + c.getConsultationId());
            displayList.add("Patient ID: " + c.getPatientId());
            displayList.add("Doctor ID: " + c.getDoctorId());
            displayList.add("Start Time: " + startTime);
            displayList.add("End Time: " + endTime);
            displayList.add("Duration: " + duration);
            displayList.add("Symptoms: " + symptoms);
            displayList.add("");
        }

        return displayList;
    }
    
    // Getter for completedConsultations
    public static MyList<Consultation> getCompletedConsultations() {
        return completedConsultations;
    }
    
    // Synchronize current consulting records to ongoing consultations
    public static void syncCurrentConsultingToOngoing() {
        for (int i = 0; i < currentConsulting.size(); i++) {
            CurrentServingDAO cs = currentConsulting.get(i);

            // Check if this patient is already in the ongoingConsultations list
            boolean exists = ongoingConsultations.filter(
                c -> c.getPatientId().equals(cs.getPatientId()) &&
                     c.getDoctorId().equals(cs.getDoctorId())
            ).size() > 0;

            if (!exists) {
                Patient patient = PatientManagement.findPatientById(cs.getPatientId());
                Doctor doctor = DoctorManagement.findDoctorById(cs.getDoctorId());
                if (patient != null && doctor != null) {
                    String consultationId = "C" + (consultationCounter++);
                    Consultation consultation = new Consultation(
                        consultationId,
                        patient.getPatientID(),
                        doctor.getDoctorID(),
                        "" // Symptoms can be left blank or filled in by the doctor.
                    );
                    consultation.setStartTime(LocalDateTime.now()); // The current time can be used
                    ongoingConsultations.add(consultation);
                }
            }
        }
    }
}

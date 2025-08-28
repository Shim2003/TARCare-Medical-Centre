package Control;

import ADT.DynamicList;
import ADT.MyList;
import DAO.CurrentServingDAO;
import DAO.DailyQueueStats;
import DAO.QueueOperationResult;
import DAO.NextPatientResult;
import DAO.RemovalResult;
import Entity.Doctor;
import Entity.Patient;
import Entity.QueueEntry;
import Utility.UtilityClass;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Lee Wei Hao
 */
public class QueueControl {

    private static MyList<QueueEntry> queueList = new DynamicList<>();
    private static MyList<CurrentServingDAO> currentServingPatient = new DynamicList<>();

    public static QueueOperationResult addInQueue(String patientId) {
        if (patientId == null) {
            return new QueueOperationResult(false, "Patient ID cannot be null");
        }

        if (!PatientManagement.isPatientExists(patientId)) {
            return new QueueOperationResult(false, "Patient ID not found... \nPlease register first...");
        }

        Patient p = PatientManagement.findPatientById(patientId);

        if (p == null) {
            return new QueueOperationResult(false, "Error: Patient could not be found.");
        }

        if (queueList.anyMatch(qe -> qe.getPatientId().equals(p.getPatientID())
                && (qe.getStatus().equals(UtilityClass.statusWaiting)
                || qe.getStatus().equals(UtilityClass.statusConsulting)))) {
            return new QueueOperationResult(false, "You are already in the queue or currently consulting.");
        }

        QueueEntry newQueue = new QueueEntry(p.getPatientID());
        queueList.add(newQueue);

        return new QueueOperationResult(true, "Successfully added to queue", newQueue);
    }

    public static NextPatientResult getNextInQueue() {
        // Get all waiting patients
        MyList<QueueEntry> waitingPatients = queueList.findAll(qe
                -> qe.getStatus().equals(Utility.UtilityClass.statusWaiting));

        if (waitingPatients.isEmpty()) {
            return new NextPatientResult(false, "No patients waiting in queue");
        }

        if ((getQueueListByStatus(Utility.UtilityClass.statusReadyToConsult).size() + currentServingPatient.size()) >= 3) {
            return new NextPatientResult(false, "Full Consulting");
        }

        // Find the patient with the lowest queue number (next in sequence)
        QueueEntry nextPatient = waitingPatients.getFirst();
        for (int i = 1; i < waitingPatients.size(); i++) {
            QueueEntry current = waitingPatients.get(i);
            if (current.getQueueNumber() < nextPatient.getQueueNumber()) {
                nextPatient = current;
            }
        }

        nextPatient.setStatus(Utility.UtilityClass.statusReadyToConsult);
        return new NextPatientResult(true, "Patient called for consultation", nextPatient);
    }

    public static MyList<CurrentServingDAO> getCurrentServingPatient() {
        return currentServingPatient;
    }

    public static boolean updateQueueStatus(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            return false;
        }

        QueueEntry target = queueList.findFirst(qe -> qe.getPatientId().equals(patientId));
        if (target != null) {
            target.setStatus(UtilityClass.statusCompleted);
            return true;
        }
        return false;
    }

    public static boolean isFullConsulting() {
        MyList<QueueEntry> consulting = queueList.findAll(qe
                -> qe.getStatus().equals(Utility.UtilityClass.statusConsulting));

        // Full
        return consulting.size() >= 3;
    }

    public static MyList<QueueEntry> getQueueList() {
        return queueList;
    }

    public static boolean markAsCompleted(String patientId) {
        QueueEntry target = queueList.findFirst(qe -> qe.getPatientId().equals(patientId)
                && qe.getStatus().equals(Utility.UtilityClass.statusConsulting));

        if (target != null) {
            target.setStatus(Utility.UtilityClass.statusCompleted);
            return true;
        }
        return false;
    }

    public static MyList<QueueEntry> getQueueListByStatus(String status) {
        return queueList.findAll(entry -> entry.getStatus().equalsIgnoreCase(status));
    }

    public static RemovalResult removeFromQueue(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            return new RemovalResult(false, "Invalid input. Please enter a valid Patient ID.");
        }

        int index = queueList.findIndex(queue -> queue.getPatientId().equals(patientId));

        try {

            if (index != -1) {
                queueList.remove(index);
                return new RemovalResult(true, "Queue record removed successfully", 1);
            }
        } catch (NumberFormatException e) {
            return new RemovalResult(false, "Invalid Queue ID format. Please enter a valid number.");
        }

        return new RemovalResult(false, "No queue entry found with the given Queue ID.");
    }

    public static RemovalResult removeQueueById(String queueId) {
        if (queueId == null || queueId.trim().isEmpty()) {
            return new RemovalResult(false, "Invalid input. Please enter a valid Queue ID.");
        }

        try {
            int id = Integer.parseInt(queueId);
            int index = queueList.findIndex(queue -> queue.getQueueNumber() == id);

            if (index != -1) {
                queueList.remove(index);
                return new RemovalResult(true, "Queue record removed successfully", 1);
            }
        } catch (NumberFormatException e) {
            return new RemovalResult(false, "Invalid Queue ID format. Please enter a valid number.");
        }

        return new RemovalResult(false, "No queue entry found with the given Queue ID.");
    }

    public static RemovalResult removeByStatus(String selectedStatus) {
        MyList<QueueEntry> recordsToRemove = QueueControl.getQueueListByStatus(selectedStatus);

        if (recordsToRemove.isEmpty()) {
            return new RemovalResult(false, "No queue records found with status: " + selectedStatus);
        }

        int removedCount = recordsToRemove.size();
        boolean success = queueList.removeIf(entry -> entry.getStatus().equalsIgnoreCase(selectedStatus));

        if (success) {
            return new RemovalResult(true, "Successfully removed " + removedCount + " queue records", removedCount);
        } else {
            return new RemovalResult(false, "Failed to remove queue records");
        }
    }

    public static void clearAllQueueRecords() {
        queueList.clear();
    }

    public static MyList<QueueEntry> getQueueByDate(Date date) {
        Calendar targetCal = Calendar.getInstance();
        targetCal.setTime(date);

        return queueList.findAll(entry -> {
            Calendar entryCal = Calendar.getInstance();
            entryCal.setTime(entry.getCheckInTime());

            return targetCal.get(Calendar.YEAR) == entryCal.get(Calendar.YEAR)
                    && targetCal.get(Calendar.DAY_OF_YEAR) == entryCal.get(Calendar.DAY_OF_YEAR);
        });
    }

    public static DAO.DailyQueueStats getDailyQueueStats(Date date) {
        MyList<QueueEntry> dayEntries = getQueueByDate(date);

        if (dayEntries.isEmpty()) {
            return new DAO.DailyQueueStats(date, 0, 0, 0, 0, 0.0);
        }

        int total = dayEntries.size();
        int waiting = dayEntries.findAll(e -> e.getStatus().equals(UtilityClass.statusWaiting)).size();
        int consulting = dayEntries.findAll(e -> e.getStatus().equals(UtilityClass.statusConsulting)).size();
        int completed = dayEntries.findAll(e -> e.getStatus().equals(UtilityClass.statusCompleted)).size();

        double completionRate = total > 0 ? (double) completed / total * 100 : 0;

        return new DailyQueueStats(date, total, waiting, consulting, completed, completionRate);
    }

    public static boolean removeFromCurrentServing(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            return false;
        }

        // Find index of the patient in the serving list
        int index = currentServingPatient.findIndex(cs -> cs.getPatientId().equals(patientId));

        if (index != -1) {
            currentServingPatient.remove(index);

            // Also update the doctor's status back to "Free"
            Doctor doctor = DoctorManagement.findDoctorById(
                    currentServingPatient.get(index).getDoctorId()
            );
            if (doctor != null) {
                doctor.setWorkingStatus(UtilityClass.statusFree);
            }

            return true;
        }

        return false;
    }

}

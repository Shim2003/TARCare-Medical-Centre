/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import Boundary.PatientUI;
import Entity.Patient;
import Entity.QueueEntry;

/**
 *
 * @author Lee Wei Hao
 */
public class QueueControl {

    private static DynamicList<QueueEntry> queueList = new DynamicList<>();

    public static QueueEntry addInQueue(String patientId) {

        if (patientId != null) {

            if (!PatientManagement.isPatientExists(patientId)) {
                System.out.println("Patient ID not found... Please register first...");
                return null;
            }

            Patient p = PatientManagement.findPatientById(patientId);

            if (p == null) {
                System.out.println("Error: Patient could not be found.");
                return null;
            }

            if (queueList.anyMatch(qe -> qe.getPatientId().equals(p.getPatientID()))) {
                System.out.println("You are already in the queue.");
                return null;
            }

            QueueEntry newQueue = new QueueEntry(p.getPatientID());
            queueList.add(newQueue);

            return newQueue;
        }

        return null;

    }

    public static QueueEntry getNextInQueue() {
        // Get all waiting patients
        DynamicList<QueueEntry> waitingPatients = queueList.findAll(qe
                -> qe.getStatus().equals(Utility.UtilityClass.statusWaiting));

        if (waitingPatients.isEmpty()) {
            return null;
        }

        // Find the patient with the lowest queue number (next in sequence)
        QueueEntry nextPatient = waitingPatients.get(0);
        for (int i = 1; i < waitingPatients.size(); i++) {
            QueueEntry current = waitingPatients.get(i);
            if (current.getQueueNumber() < nextPatient.getQueueNumber()) {
                nextPatient = current;
            }
        }

        // Change status to consulting
        nextPatient.setStatus(Utility.UtilityClass.statusConsulting);
        return nextPatient;
    }

    public static boolean isFullConsulting() {
        DynamicList<QueueEntry> consulting = queueList.findAll(qe
                -> qe.getStatus().equals(Utility.UtilityClass.statusConsulting));

        // Full
        if (consulting.size() >= 3) {
            return true;
        }
        return false;
    }

    public static DynamicList<QueueEntry> getQueueList() {
        return queueList;
    }

    public static QueueEntry currentConsulting() {
        return queueList.findFirst(qe
                -> qe.getStatus().equals(Utility.UtilityClass.statusConsulting));
    }

    public static boolean markAsCompleted(String patientId) {

        QueueEntry target = queueList.findFirst(qe -> qe.getPatientId().equals(patientId)
                && qe.getStatus().equals(Utility.UtilityClass.statusConsulting));

        if (target != null) {
            target.setStatus(Utility.UtilityClass.statusCompleted);
            System.out.println("Patient ID : " + patientId + " marked as COMPLETED.");
            return true;
        } else {
            System.out.println("No consulting patient found with ID: " + patientId);
            return false;
        }

    }

    public static DynamicList<QueueEntry> getQueueListByStatus(String status) {
        return queueList.findAll(entry -> entry.getStatus().equalsIgnoreCase(status));
    }

    public static boolean removeFromQueue(String queueId) {

        if (queueId == null || queueId.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a valid Patient ID.");
            return false;
        }

        int id = Integer.parseInt(queueId);
        int index = queueList.findIndex(queue -> queue.getQueueNumber() == id);

        if (index != -1) {
            queueList.remove(index);
            return true;
        }

        return false;
    }

    public static boolean removeByStatus(String selectedStatus) {

        DynamicList<QueueEntry> recordsToRemove = QueueControl.getQueueListByStatus(selectedStatus);

        if (recordsToRemove.isEmpty()) {
            System.out.println("No queue records found with status: " + selectedStatus);
            return false;
        }

        return queueList.removeIf(entry -> entry.getStatus().equalsIgnoreCase(selectedStatus));

    }

    public static void clearAllQueueRecords() {
        queueList.clear();
    }

}

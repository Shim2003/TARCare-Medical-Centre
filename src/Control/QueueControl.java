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
    private static final PatientManagement patientControl = new PatientManagement();
    private static final PatientUI patientUi = new PatientUI();

    public static QueueEntry addInQueue(String patientId) {

        if (patientId != null) {

            if (!patientControl.isPatientExists(patientId)) {
                patientUi.addPatient();
            }

            Patient p = patientControl.findPatientById(patientId);

            if (p == null) {
                System.out.println("Error: Patient could not be found.");
                return null;
            }

            for (int i = 0; i < queueList.size(); i++) {
                if (queueList.get(i).getPatientId().equals(p.getPatientID())) {
                    System.out.println("You are already in the queue.");
                    return null;
                }
            }

            QueueEntry newQueue = new QueueEntry(p.getPatientID());
            queueList.add(newQueue);

            return queueList.getLast();
        }

        return null;

    }

    public static QueueEntry getNextInQueue() {

        for (int i = 0; i < queueList.size(); i++) {
            QueueEntry qe = queueList.get(i);
            if (qe.getStatus().equals(Utility.UtilityClass.statusWaiting)) {
                qe.setStatus(Utility.UtilityClass.statusConsulting);
                return qe;
            }
        }

        return null;
    }

    public static boolean isFullConsulting() {
        int consultingCount = 0;
        DynamicList<QueueEntry> queueList = Control.QueueControl.getQueueList();

        for (int i = 0; i < queueList.size(); i++) {
            if (queueList.get(i).getStatus().equals(Utility.UtilityClass.statusConsulting)) {
                consultingCount++;
            }
        }

        if (consultingCount >= 3) {
            System.out.println("Maximum number of patients being served at the same time is reached.");
            return true;
        } else {
            return false;
        }
    }

    public static DynamicList<QueueEntry> getQueueList() {
        return queueList;
    }

    public static QueueEntry currentConsulting() {
        for (int i = 0; i < queueList.size(); i++) {
            QueueEntry qe = queueList.get(i);
            if (qe.getStatus().equals(Utility.UtilityClass.statusConsulting)) {
                return qe; // Return the one being served now
            }
        }
        return null; // No one is being served
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

    public static boolean removeFromQueue(String patientId) {
        boolean isSuccessful = false;

        if (patientId == null || patientId.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a valid Patient ID.");
            return isSuccessful;
        }

        for (int i = 0; i < queueList.size(); i++) {

            QueueEntry queue = queueList.get(i);

            if (queue.getPatientId().equalsIgnoreCase(patientId)) {

                queueList.remove(i);
                isSuccessful = true;
            }

        }

        return isSuccessful;
    }

}

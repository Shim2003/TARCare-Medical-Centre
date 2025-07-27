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

    public static QueueEntry startQueue(String identityNumber) {

        if (identityNumber != null) {

            if (!patientControl.isIdentityNumberExists(identityNumber)) {
                patientUi.addPatient();
            }

            Patient p = patientControl.findPatientByIdentity(identityNumber);

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

}

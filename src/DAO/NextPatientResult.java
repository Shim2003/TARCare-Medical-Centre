/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.QueueEntry;

/**
 *
 * @author Lee Wei Hao
 */
public class NextPatientResult {
        private final boolean success;
        private final String message;
        private final QueueEntry nextPatient;

        public NextPatientResult(boolean success, String message, QueueEntry nextPatient) {
            this.success = success;
            this.message = message;
            this.nextPatient = nextPatient;
        }

        public NextPatientResult(boolean success, String message) {
            this(success, message, null);
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public QueueEntry getNextPatient() { return nextPatient; }
    }

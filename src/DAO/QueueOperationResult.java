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
public class QueueOperationResult {
        private final boolean success;
        private final String message;
        private final QueueEntry queueEntry;

        public QueueOperationResult(boolean success, String message, QueueEntry queueEntry) {
            this.success = success;
            this.message = message;
            this.queueEntry = queueEntry;
        }

        public QueueOperationResult(boolean success, String message) {
            this(success, message, null);
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public QueueEntry getQueueEntry() { return queueEntry; }
    }

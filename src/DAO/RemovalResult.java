/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Lee Wei Hao
 */
public class RemovalResult {
        private final boolean success;
        private final String message;
        private final int removedCount;

        public RemovalResult(boolean success, String message, int removedCount) {
            this.success = success;
            this.message = message;
            this.removedCount = removedCount;
        }

        public RemovalResult(boolean success, String message) {
            this(success, message, 0);
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public int getRemovedCount() { return removedCount; }
    }


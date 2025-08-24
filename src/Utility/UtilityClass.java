/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import ADT.MyList;
import Entity.Medicine;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class UtilityClass {

    //Constant
    //General
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    
    // LocalDateTime format
    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    //Status
    public static final String statusFree = "Free";
    public static final String statusWaiting = "Waiting";
    public static final String statusConsulting = "Consulting";
    public static final String statusCompleted = "Completed";
    public static final String workingStatusOff = "Off";
    public static final String statusOnLeave = "On Leave";

    //Diagnosis Status
    private static final String lowLevel = "Low";
    private static final String mediumLevel = "Medium";
    private static final String highLevel = "High";
    private static final String criticalLevel = "Critical";

    private static final Scanner scanner = new Scanner(System.in);
    
    // Display the message with a maximum length
    public static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        } else {
            return str.substring(0, maxLength - 3) + "...";
        }
    }

    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("Invalid date format: " + dateStr);
            return null;
        }
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "N/A";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    //get the month name
    public static String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }
    
    // 格式化 LocalDateTime 为字符串 "dd-MM-yyyy HH:mm:ss"
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        return dateTime.format(formatter);
    }

    // 将字符串 "dd-MM-yyyy HH:mm:ss" 解析为 LocalDateTime
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) return null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            System.err.println("Invalid datetime format: " + dateTimeStr);
            return null;
        }
    }
    
    public static final String[] DOSAGE_FORMS = {
        "tablet", "capsule", "ml", "syrup", "cream", "ointment", 
        "gel", "injection", "drops", "sachet", "powder", "lotion"
    };
    
    public static final int LOW_STOCK_THRESHOLD = 20;
    
    public static Date addMonthsToDate(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }
    
    public static int convertMonthsToDays(int months) {
        return months * 30; // Approximate
    }
    
    public static double calculateTotalValue(MyList<Medicine> medicines) {
        double total = 0.0;
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            total += med.getPrice() * med.getQuantity();
        }
        return total;
    }
    
    public static Date addDaysToDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
}


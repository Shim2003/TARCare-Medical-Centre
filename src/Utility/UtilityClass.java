/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import ADT.MyList;
import Entity.MedicalTreatmentItem;
import Entity.Medicine;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
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
    public static final String statusReadyToConsult = "ReadyToConsult";
    public static final String statusConsulting = "Consulting";
    public static final String statusCompleted = "Completed";
    public static final String workingStatusOff = "Off";
    public static final String statusOnLeave = "On Leave";
    
    public static final int DEFAULT_RESTOCK_QUANTITY = 30;

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
        if (dateTime == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        return dateTime.format(formatter);
    }

    // 将字符串 "dd-MM-yyyy HH:mm:ss" 解析为 LocalDateTime
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }
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

    public static <T> void quickSort(MyList<T> list, Comparator<T> comparator) {
        if (list == null || list.isEmpty()) {
            return; // No need to sort an empty or null list
        }
        quickSortHelper(list, 0, list.size() - 1, comparator);
    }

    private static <T> void quickSortHelper(MyList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);
            quickSortHelper(list, low, pivotIndex - 1, comparator);
            quickSortHelper(list, pivotIndex + 1, high, comparator);
        }
    }

    private static <T> int partition(MyList<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static <T> void swap(MyList<T> list, int i, int j) {
        T temp = list.get(i);
        list.replace(i, list.get(j));
        list.replace(j, temp);
    }
    
    // Make this static for utility-like usage
    public static int calculateQuantityNeeded(MedicalTreatmentItem item) {
        try {
            // Extract numeric values from strings
            int dosageAmount = extractNumericValue(item.getDosage());
            int frequencyPerDay = extractFrequencyPerDay(item.getFrequency());
            int durationDays = extractDurationInDays(item.getDuration()); // FIX: was item.getFrequency()
            
            // Calculate total quantity needed
            int totalQuantity = dosageAmount * frequencyPerDay * durationDays;
            
            // Ensure minimum quantity is 1
            return Math.max(totalQuantity, 1);
        } catch (Exception e) {
            // Default to 1 if parsing fails
            System.err.println("Warning: Could not parse dosage information for " + item.getMedicineName() + 
                             ". Using default quantity of 1. Error: " + e.getMessage());
            return 1;
        }
    }
    
    // Helper method to extract numeric values from dosage strings like "1", "2", "10ml"
    private static int extractNumericValue(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 1;
        }
        
        // Clean the text and extract number
        String cleanText = text.trim().toLowerCase();
        
        // Extract first number found
        StringBuilder number = new StringBuilder();
        for (char c : cleanText.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            } else if (number.length() > 0) {
                break; // Stop at first non-digit after finding digits
            }
        }
        
        return number.length() > 0 ? Integer.parseInt(number.toString()) : 1;
    }
    
    // Helper method to extract frequency per day from strings like "3x/day", "2 times daily", "once daily"
    private static int extractFrequencyPerDay(String frequency) {
        if (frequency == null || frequency.trim().isEmpty()) {
            return 1;
        }
        
        String cleanFreq = frequency.toLowerCase().trim();
        
        // Handle common patterns
        if (cleanFreq.contains("once") || cleanFreq.contains("1x") || cleanFreq.equals("1")) {
            return 1;
        } else if (cleanFreq.contains("twice") || cleanFreq.contains("2x")) {
            return 2;
        } else if (cleanFreq.contains("thrice") || cleanFreq.contains("3x")) {
            return 3;
        } else if (cleanFreq.contains("4x")) {
            return 4;
        }
        
        // Extract number before "x/day", "/day", "times"
        String[] patterns = {"x/day", "/day", "times", "x"};
        for (String pattern : patterns) {
            int index = cleanFreq.indexOf(pattern);
            if (index > 0) {
                String beforePattern = cleanFreq.substring(0, index).trim();
                try {
                    return Integer.parseInt(beforePattern);
                } catch (NumberFormatException e) {
                    // Continue to next pattern
                }
            }
        }
        
        // Try to extract any number from the frequency string
        StringBuilder number = new StringBuilder();
        for (char c : cleanFreq.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            } else if (number.length() > 0) {
                break;
            }
        }
        
        return number.length() > 0 ? Integer.parseInt(number.toString()) : 1;
    }
    
    // Helper method to extract duration in days from strings like "5 days", "1 week", "2 weeks"
    private static int extractDurationInDays(String duration) {
        if (duration == null || duration.trim().isEmpty()) {
            return 1;
        }
        
        String cleanDuration = duration.toLowerCase().trim();
        
        // Handle special cases
        if (cleanDuration.contains("as needed") || cleanDuration.contains("prn")) {
            return 1; // Assume 1 day for as-needed medications
        }
        
        // Extract number
        int number = extractNumericValue(cleanDuration);
        
        // Check for time units
        if (cleanDuration.contains("week")) {
            return number * 7; // Convert weeks to days
        } else if (cleanDuration.contains("month")) {
            return number * 30; // Convert months to days (approximate)
        } else if (cleanDuration.contains("day") || cleanDuration.matches("\\d+")) {
            return number;
        }
        
        // Default to treating the number as days
        return number;
    }
    
    // Method to get complete dosage description with form (for display purposes)
    public static String getCompleteDosageDescription(MedicalTreatmentItem item, String dosageForm) {
        if (item.getDosage() == null || item.getDosage().trim().isEmpty()) {
            return "1 " + (dosageForm != null ? dosageForm : "unit");
        }
        
        // If dosage already contains the form, return as is
        String lowerDosage = item.getDosage().toLowerCase();
        if (lowerDosage.contains("tablet") || lowerDosage.contains("capsule") || 
            lowerDosage.contains("ml") || lowerDosage.contains("mg")) {
            return item.getDosage();
        }
        
        // Otherwise, append the dosage form
        return item.getDosage().trim() + " " + (dosageForm != null ? dosageForm : "unit");
    }

}
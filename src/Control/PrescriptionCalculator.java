/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import ADT.DynamicList;
import ADT.MyList;
import Entity.MedicalTreatmentItem;
import Entity.Prescription;

public class PrescriptionCalculator {

    MyList<Prescription> prescriptionList = new DynamicList<>();
    
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

    // check the prescription list ids, if the list is null then create id RX001 if not empty then check the last 3 digit value and increment by 1
    public static String generateNewPrescriptionId() {
        MyList<Prescription> prescriptionList = new DynamicList<>();
        if (prescriptionList.isEmpty()) {
            return "RX001";
        } else {
            String lastId = prescriptionList.get(prescriptionList.size() - 1).getPrescriptionID();
            int numericPart = Integer.parseInt(lastId.substring(2));
            numericPart++;
            return String.format("RX%03d", numericPart);
        }
    }

    //add prescription to the list through object
    public static void addPrescription(Prescription prescription) {
        MyList<Prescription> prescriptionList = new DynamicList<>();
        prescriptionList.add(prescription);
    }
}
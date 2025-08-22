/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

public class MedicalTreatmentItem {
    private String medicineName;
    private String dosage;      // Now just the number/amount (e.g., "1", "2", "10ml")
    private String frequency;
    private String duration;
    private String method;
    
    public MedicalTreatmentItem() {}
    
    public MedicalTreatmentItem(String medicineName, String dosage, String frequency, 
                              String duration, String method) {
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.method = method;
    }
    
    // Method to calculate quantity needed based on dosage, frequency, and duration
    public int calculateQuantityNeeded() {
        try {
            // Extract numeric values from strings
            int dosageAmount = extractNumericValue(dosage);
            int frequencyPerDay = extractFrequencyPerDay(frequency);
            int durationDays = extractDurationInDays(duration);
            
            // Calculate total quantity needed
            int totalQuantity = dosageAmount * frequencyPerDay * durationDays;
            
            // Ensure minimum quantity is 1
            return Math.max(totalQuantity, 1);
        } catch (Exception e) {
            // Default to 1 if parsing fails
            System.err.println("Warning: Could not parse dosage information for " + medicineName + 
                             ". Using default quantity of 1. Error: " + e.getMessage());
            return 1;
        }
    }
    
    // Helper method to extract numeric values from dosage strings like "1", "2", "10ml"
    private int extractNumericValue(String text) {
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
    private int extractFrequencyPerDay(String frequency) {
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
    private int extractDurationInDays(String duration) {
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
    // This would need access to the Medicine entity to get the dosage form
    public String getCompleteDosageDescription(String dosageForm) {
        if (dosage == null || dosage.trim().isEmpty()) {
            return "1 " + (dosageForm != null ? dosageForm : "unit");
        }
        
        // If dosage already contains the form, return as is
        String lowerDosage = dosage.toLowerCase();
        if (lowerDosage.contains("tablet") || lowerDosage.contains("capsule") || 
            lowerDosage.contains("ml") || lowerDosage.contains("mg")) {
            return dosage;
        }
        
        // Otherwise, append the dosage form
        return dosage.trim() + " " + (dosageForm != null ? dosageForm : "unit");
    }
    
    // Getters and Setters
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    
    @Override
    public String toString() {
        return "MedicalTreatmentItem{" +
               "medicineName='" + medicineName + '\'' +
               ", dosage='" + dosage + '\'' +
               ", frequency='" + frequency + '\'' +
               ", duration='" + duration + '\'' +
               ", method='" + method + '\'' +
               ", calculatedQuantity=" + calculateQuantityNeeded() +
               '}';
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

public class MedicalTreatmentItem {
    private String medicineName;
    private String dosage;
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
            int frequencyPerDay = extractNumericValue(frequency);
            int durationDays = extractNumericValue(duration);
            
            // Calculate total quantity needed
            return dosageAmount * frequencyPerDay * durationDays;
        } catch (Exception e) {
            // Default to 1 if parsing fails
            System.err.println("Warning: Could not parse dosage information for " + medicineName + 
                             ". Using default quantity of 1.");
            return 1;
        }
    }
    
    // Helper method to extract numeric values from strings like "2 tablets", "3x/day", "7 days"
    private int extractNumericValue(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 1;
        }
        
        // Remove common text and extract first number found
        String cleanText = text.toLowerCase()
                              .replace("tablets", "")
                              .replace("tablet", "")
                              .replace("capsules", "")
                              .replace("capsule", "")
                              .replace("ml", "")
                              .replace("x/day", "")
                              .replace("/day", "")
                              .replace("times", "")
                              .replace("day", "")
                              .replace("days", "")
                              .trim();
        
        // Extract first number
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
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
    private int quantityNeeded;
    
    public MedicalTreatmentItem() {}
    
    public MedicalTreatmentItem(String medicineName, String dosage, String frequency, 
                              String duration, String method, int quantityNeeded) {
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.method = method;
        this.quantityNeeded = quantityNeeded;
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
    
    public int getQuantityNeeded() { return quantityNeeded; }
    public void setQuantityNeeded(int quantityNeeded) { this.quantityNeeded = quantityNeeded; }
    
    @Override
    public String toString() {
        return "MedicalTreatmentItem{" +
               "medicineName='" + medicineName + '\'' +
               ", dosage='" + dosage + '\'' +
               ", frequency='" + frequency + '\'' +
               ", duration='" + duration + '\'' +
               ", method='" + method + '\'' +
               ", quantityNeeded=" + quantityNeeded +
               '}';
    }
}

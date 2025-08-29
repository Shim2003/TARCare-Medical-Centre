/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author leekeezhan
 */
public class EndConsultationResult {
    public boolean success;         
    public String patientId;         
    public String consultationId;  
    public String duration;        
    public String patientSavedMsg;  
    public String doctorStatusMsg;  

    public EndConsultationResult() {}

    public EndConsultationResult(boolean success) {
        this.success = success;
    }
    
}

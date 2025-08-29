/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author leekeezhan
 */
import ADT.MyList;
import Entity.Appointment;

public class ScheduleAppointmentResult {
    private MyList<String> errors;
    private Appointment appointment;

    public ScheduleAppointmentResult(MyList<String> errors, Appointment appointment) {
        this.errors = errors;
        this.appointment = appointment;
    }
    
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
    
    public Iterable<String> getErrorMessages() {
        return errors;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}

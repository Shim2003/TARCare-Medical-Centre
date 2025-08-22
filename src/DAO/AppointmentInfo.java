/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Appointment;

/**
 *
 * @author User
 */
public class AppointmentInfo {
    
    private Appointment appointment;
    private int dayLeft;
    private int hoursLef;

    public AppointmentInfo(Appointment appointment, int dayLeft, int hoursLef) {
        this.appointment = appointment;
        this.dayLeft = dayLeft;
        this.hoursLef = hoursLef;
    }
    
    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public int getDayLeft() {
        return dayLeft;
    }

    public void setDayLeft(int dayLeft) {
        this.dayLeft = dayLeft;
    }

    public int getHoursLeft() {
        return hoursLef;
    }

    public void setHoursLeft(int hoursLef) {
        this.hoursLef = hoursLef;
    }
    
}

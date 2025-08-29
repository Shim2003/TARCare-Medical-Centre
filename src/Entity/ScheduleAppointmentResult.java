/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author leekeezhan
 */
import ADT.MyList;

public class ScheduleAppointmentResult {
    private MyList<String> errors;
    private Appointment appointment; // 新建的 Appointment 对象，如果成功

    public ScheduleAppointmentResult(MyList<String> errors, Appointment appointment) {
        this.errors = errors;
        this.appointment = appointment;
    }

    public MyList<String> getErrors() {
        return errors;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}

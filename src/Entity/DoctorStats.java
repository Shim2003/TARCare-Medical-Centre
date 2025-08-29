/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author leekeezhan
 */
public class DoctorStats {
    private String doctorId;
    private int totalAppointments;
    private int appointmentsThisWeek;

    // Constructor
    public DoctorStats(String doctorId, int totalAppointments, int appointmentsThisWeek) {
        this.doctorId = doctorId;
        this.totalAppointments = totalAppointments;
        this.appointmentsThisWeek = appointmentsThisWeek;
    }

    // Getters and Setters
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public int getAppointmentsThisWeek() {
        return appointmentsThisWeek;
    }

    public void setAppointmentsThisWeek(int appointmentsThisWeek) {
        this.appointmentsThisWeek = appointmentsThisWeek;
    }

    // Optional: a toString for debugging
    @Override
    public String toString() {
        return "DoctorStats{" +
                "doctorId='" + doctorId + '\'' +
                ", totalAppointments=" + totalAppointments +
                ", appointmentsThisWeek=" + appointmentsThisWeek +
                '}';
    }
}

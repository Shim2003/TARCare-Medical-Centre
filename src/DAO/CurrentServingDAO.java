/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Lee Wei Hao
 */
public class CurrentServingDAO {

    private String PatientId;
    private String DoctorId;

    public String getPatientId() {
        return PatientId;
    }

    public void setPatientId(String PatientId) {
        this.PatientId = PatientId;
    }

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String DoctorId) {
        this.DoctorId = DoctorId;
    }

    public CurrentServingDAO(String PatientId, String DoctorId) {
        this.PatientId = PatientId;
        this.DoctorId = DoctorId;
    }

    @Override
    public String toString() {
        return "CurrentServingDAO{" + "PatientId=" + PatientId + ", DoctorId=" + DoctorId + '}';
    }

}

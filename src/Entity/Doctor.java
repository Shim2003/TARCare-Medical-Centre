/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class Doctor {
    
    private String doctorID;
    private String name;
    private String specialistID;
    private String specialistName;
    private Date dateOfBirth;
    private char gender;
    private String contactNumber;
    private String email;
    private String workingStatus;

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialistID() {
        return specialistID;
    }

    public void setSpecialistID(String specialistID) {
        this.specialistID = specialistID;
    }


    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

    public Doctor(String doctorID, String name, String specialistID, String specialistName, Date dateOfBirth, char gender, String contactNumber, String email, String workingStatus) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialistID = specialistID;
        this.specialistName = specialistName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.workingStatus = workingStatus;
    }
    
    @Override
    public String toString() {
        return "Doctor{" + "doctorID=" + doctorID + ", name=" + name + ", specialistID=" + specialistID + ", specialistName=" + specialistName + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", contactNumber=" + contactNumber + ", email=" + email + ", workingStatus=" + workingStatus + '}';
    }
}

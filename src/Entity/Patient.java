/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author user
 */
public class Patient {

    private String patientID;
    private String fullName;
    private Date dateOfBirth;
    private String gender;
    private String contactNumber;
    private String email;
    private String address;
    private String emergencyContact;
    private Date registrationDate;
    private int queueNumber;
    private String status;

    // Constructor
    public Patient(String patientID, String fullName, Date dateOfBirth, String gender,
            String contactNumber, String email, String address, String emergencyContact,
            Date registrationDate, int queueNumber, String status) {
        this.patientID = patientID;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
        this.queueNumber = queueNumber;
        this.status = status;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
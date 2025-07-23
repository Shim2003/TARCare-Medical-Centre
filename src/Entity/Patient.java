/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Lee Wei Hao
 */
public class Patient {

    private static int idCounter = 1001;
    private static int queueCounter = 1001;

    private String patientID;
    private String fullName;
    private String identityNumber;
    private Date dateOfBirth;
    private char gender;
    private String contactNumber;
    private String email;
    private String address;
    private String emergencyContact;
    private Date registrationDate;
    private int queueNumber;
    private String queueStatus;
    
    // Constructor
    public Patient(){
        this.patientID = "P" + idCounter++;
    }
    
    public Patient(String fullName,String identityNumber, Date dateOfBirth, char gender,
            String contactNumber, String email, String address, String emergencyContact,
            Date registrationDate) {
        this.patientID = "P" + idCounter++;
        this.fullName = fullName;
        this.identityNumber = identityNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
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

    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Patient{" + "patientID=" + patientID + ", fullName=" + fullName + ", identityNumber=" + identityNumber + 
                ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", contactNumber=" + contactNumber + 
                ", email=" + email + ", address=" + address + ", emergencyContact=" + emergencyContact +
                ", registrationDate=" + registrationDate + ", queueNumber=" + queueNumber + ", Queue status=" + queueStatus + '}';
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

    public String getIdentityNumber() {
        return identityNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public String getQueueStatus() {
        return queueStatus;
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

    public void setGender(char gender) {
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

    public void setQueueStatus(String queueStatus) {
        this.queueStatus = queueStatus;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }
}

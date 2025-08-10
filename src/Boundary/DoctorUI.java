/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Entity.Doctor;
import Entity.Schedule;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ACER
 */
public class DoctorUI {

    public static void main(String[] args) {
        
        //
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DayOfWeek currentDay = today.getDayOfWeek();
        //
        
        ScheduleManagement.addSampleSchedules();
        DoctorManagement.addSampleDoctor();

//        System.out.println("Doctors available on 12/08/2025:");
//        ScheduleManagement.findAvailableDoctors(DayOfWeek.TUESDAY);
        DynamicList<Schedule> schedulesList = ScheduleManagement.findSchedulesByDoctorId("D005");
//        DynamicList<Doctor> availableDoctors = ScheduleManagement.findAvailableDoctors(DayOfWeek.TUESDAY);
        DynamicList<Doctor> freeDoctors = DoctorManagement.getFreeDoctors();
        boolean allBusy = DoctorManagement.areAllDoctorsBusy();
        DynamicList<Doctor> allDoctors = DoctorManagement.getAllDoctors();


        for (int i = 0; i < schedulesList.size(); i++) {
            Schedule s = schedulesList.get(i);
            System.out.println(s);
        }
//        
//        for (int i = 0; i < availableDoctors.size(); i++) {
//            Doctor d = availableDoctors.get(i);
//            System.out.println(d);
//        }

//        for (int i = 0; i < freeDoctors.size(); i++) {
//            Doctor d = freeDoctors.get(i);
//            System.out.println(d);
//        }
//        
//        System.out.println(allBusy);
       
         for (int i = 0; i < allDoctors.size(); i++) {
            Doctor d = allDoctors.get(i);
            System.out.println(d);
        }
        
         System.out.println("Today: " + currentDay + " " + currentTime);

    }
}

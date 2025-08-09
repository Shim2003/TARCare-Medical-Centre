/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import ADT.DynamicList;
import Control.DoctorManagement;
import Control.ScheduleManagement;
import Entity.Schedule;
import java.time.DayOfWeek;

/**
 *
 * @author ACER
 */
public class DoctorUI {

    public static void main(String[] args) {
        DoctorManagement.addSampleDoctor();
        ScheduleManagement.addSampleSchedules();

//        System.out.println("Doctors available on 12/08/2025:");
//        ScheduleManagement.findAvailableDoctors(DayOfWeek.TUESDAY);
        DynamicList<Schedule> schedulesList = ScheduleManagement.findSchedulesByDoctorId("D001");

        for (int i = 0; i < schedulesList.size(); i++) {
            Schedule s = schedulesList.get(i);
            System.out.println(s);
        }

    }
}

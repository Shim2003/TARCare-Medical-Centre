/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ACER
 */
public class DoctorWorkSummary {
    private String id, name;
    private int totalHours;

    public DoctorWorkSummary(String id, String name, int totalHours) {
        this.id = id;
        this.name = name;
        this.totalHours = totalHours;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalHours() {
        return totalHours;
    }
}

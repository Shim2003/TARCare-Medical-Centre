/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

/**
 *
 * @author user
 */
public class UtilityClass {

    //Constant
    //General
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    
    //Status
    public static final String statusWaiting = "Waiting";
    public static final String statusReady = "Ready";
    public static final String statusCompleted = "Completed";
    
    public static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        } else {
            return str.substring(0, maxLength - 3) + "...";
        }
    }

}

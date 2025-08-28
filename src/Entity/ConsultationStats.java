/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author leekeezhan
 */

public class ConsultationStats {
    public final long count;
    public final double average;
    public final long max;
    public final long min;
    public final double standardDeviation;

    public ConsultationStats(long count, double average, long max, long min, double stdDev) {
        this.count = count;
        this.average = average;
        this.max = max;
        this.min = min;
        this.standardDeviation = stdDev;
    }
}
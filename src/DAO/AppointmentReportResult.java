/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ADT.DynamicList;
import ADT.MyList;

/**
 *
 * @author leekeezhan
 */
public class AppointmentReportResult {
    private MyList<String> reportLines;
    private MyList<String> errors;

    public AppointmentReportResult() {
        this.reportLines = new DynamicList<>();
        this.errors = new DynamicList<>();
    }

    public AppointmentReportResult(MyList<String> errors, MyList<String> reportLines) {
        this.errors = errors;
        this.reportLines = reportLines;
    }

    public void addLine(String line) {
        reportLines.add(line);
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public MyList<String> getErrors() {
        return errors;
    }

    public MyList<String> getReportLines() {
        return reportLines;
    }
}
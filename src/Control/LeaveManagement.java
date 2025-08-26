/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.DynamicList;
import ADT.MyList;
import Entity.Doctor;
import Entity.DoctorLeave;
import Entity.Schedule;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

/**
 *
 * @author ACER
 */
public class LeaveManagement {

    private static MyList<DoctorLeave> leaveList = new DynamicList<>();

    public static boolean hasLeaveConflict(DoctorLeave newLeave) {
        for (int i = 0; i < leaveList.size(); i++) {
            DoctorLeave existing = leaveList.get(i);

            if (existing.getDoctorID().equals(newLeave.getDoctorID())) {
                // Check overlap: (start1 <= end2) && (end1 >= start2)
                if (!newLeave.getDateFrom().isAfter(existing.getDateTo())
                        && !newLeave.getDateTo().isBefore(existing.getDateFrom())) {
//                    System.out.println("❌ Conflict: Doctor already has leave from "
//                            + existing.getDateFrom() + " to " + existing.getDateTo());
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean addLeave(DoctorLeave l) {
        if (l != null) {
            // ✅ Use helper for conflict check
            if (hasLeaveConflict(l)) {
                return false;
            }

            leaveList.add(l);

            // Update doctor's working status
            Doctor doctor = DoctorManagement.findDoctorById(l.getDoctorID());
            if (doctor != null) {
                DoctorManagement.updateWorkingStatus(doctor);
            }

            return true;
        }
        return false;
    }

    //Read
    public static MyList<DoctorLeave> getAllLeaves() {
        return leaveList;
    }

    public static String generateNextLeaveId() {
        int max = 0;

        MyList<DoctorLeave> leaves = getAllLeaves();
        for (int i = 0; i < leaves.size(); i++) {
            String id = leaves.get(i).getLeaveID(); // e.g., "L001"
            if (id != null && id.startsWith("L")) {
                try {
                    int num = Integer.parseInt(id.substring(1)); // take part after "L"
                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException e) {
                    // ignore invalid leave IDs
                }
            }
        }

        // format with leading zeros (L001, L002, etc.)
        return String.format("L%03d", max + 1);
    }

    public static DoctorLeave findLeaveById(String leaveID) {
        for (int i = 0; i < leaveList.size(); i++) {
            DoctorLeave leave = leaveList.get(i);
            if (leave.getLeaveID().equals(leaveID)) {
                return leave;
            }
        }
        return null;
    }

    public static DynamicList<DoctorLeave> findLeavesByDoctorId(String doctorID) {
        DynamicList<DoctorLeave> result = new DynamicList<>();
        for (int i = 0; i < leaveList.size(); i++) {
            DoctorLeave leave = leaveList.get(i);
            if (leave.getDoctorID().equals(doctorID)) {
                result.add(leave);
            }
        }
        return result;
    }

    public static boolean isDoctorOnLeave(String doctorID, LocalDate date) {
        for (int i = 0; i < leaveList.size(); i++) {
            DoctorLeave leave = leaveList.get(i);
            if (leave.getDoctorID().equals(doctorID) && leave.coversDate(date)) {
                return true;
            }
        }
        return false;
    }

    public static boolean removeLeave(String leaveID) {
        for (int i = 0; i < leaveList.size(); i++) {
            DoctorLeave leave = leaveList.get(i);
            if (leave.getLeaveID().equals(leaveID)) {
                leaveList.remove(i);

                // ✅ Update doctor's working status
                Doctor doctor = DoctorManagement.findDoctorById(leave.getDoctorID());
                if (doctor != null) {
                    DoctorManagement.updateWorkingStatus(doctor);
                }

                return true;
            }
        }
        return false;
    }

    public static boolean removeLeaveByDoctorId(String doctorID) {
        boolean removed = leaveList.removeIf(s -> s.getDoctorID().equalsIgnoreCase(doctorID.trim()));

        if (removed) {
            // ✅ Update doctor's working status
            Doctor doctor = DoctorManagement.findDoctorById(doctorID);
            if (doctor != null) {
                DoctorManagement.updateWorkingStatus(doctor);
            }
        }

        return removed;
    }

    // number of leave object in a month
    public static int countLeaveRecordsInMonth(String doctorID, YearMonth month) {
        MyList<DoctorLeave> leaves = getAllLeaves();
        LocalDate monthStart = month.atDay(1);
        LocalDate monthEnd = month.atEndOfMonth();

        int count = 0;
        for (int i = 0; i < leaves.size(); i++) {
            DoctorLeave leave = leaves.get(i);
            if (leave.getDoctorID().equals(doctorID)) {
                LocalDate from = leave.getDateFrom();
                LocalDate to = leave.getDateTo();
                // overlaps this month?
                if (!to.isBefore(monthStart) && !from.isAfter(monthEnd)) {
                    count++;
                }
            }
        }
        return count;
    }

    //total leave taken regardless of shift per month
    public static int countLeaveDaysInMonth(String doctorID, YearMonth month) {
        MyList<DoctorLeave> leaves = getAllLeaves();
        LocalDate monthStart = month.atDay(1);
        LocalDate monthEnd = month.atEndOfMonth();

        int leaveDays = 0;

        for (int i = 0; i < leaves.size(); i++) {
            DoctorLeave leave = leaves.get(i);
            if (leave.getDoctorID().equals(doctorID)) {
                LocalDate from = leave.getDateFrom();
                LocalDate to = leave.getDateTo();

                // clip range to current month
                if (to.isBefore(monthStart) || from.isAfter(monthEnd)) {
                    continue; // skip if completely outside
                }
                if (from.isBefore(monthStart)) {
                    from = monthStart;
                }
                if (to.isAfter(monthEnd)) {
                    to = monthEnd;
                }

                while (!from.isAfter(to)) {
                    leaveDays++;
                    from = from.plusDays(1);
                }
            }
        }

        return leaveDays;
    }

    //actual leave days (according to schedule)
    public static int countLeaveDaysForDoctor(String doctorID, YearMonth month,
            MyList<DoctorLeave> leaves, MyList<Schedule> schedules) {
        LocalDate monthStart = month.atDay(1);
        LocalDate monthEnd = month.atEndOfMonth();

        // First get all working days of the doctor (Mon, Fri etc.)
        MyList<DayOfWeek> workingDays = new DynamicList<>();
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            if (s.getDoctorID().equals(doctorID) && !workingDays.contains(s.getDayOfWeek())) {
                workingDays.add(s.getDayOfWeek());
            }
        }

        int leaveDays = 0;
        for (int i = 0; i < leaves.size(); i++) {
            DoctorLeave leave = leaves.get(i);
            if (leave.getDoctorID().equals(doctorID)) {
                LocalDate start = leave.getDateFrom().isBefore(monthStart) ? monthStart : leave.getDateFrom();
                LocalDate end = leave.getDateTo().isAfter(monthEnd) ? monthEnd : leave.getDateTo();

                for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
                    // count only if this leave date is a scheduled working day
                    for (int j = 0; j < workingDays.size(); j++) {
                        if (d.getDayOfWeek().equals(workingDays.get(j))) {
                            leaveDays++;
                            break;
                        }
                    }
                }
            }
        }
        return leaveDays;
    }

    //helper method
    public static MyList<LocalDate> getLeaveDaysForDoctorInMonth(String doctorId, YearMonth month) {
        MyList<DoctorLeave> allLeaves = getAllLeaves(); // however you store leaves
        MyList<LocalDate> leaveDays = new DynamicList<>();

        int year = month.getYear();
        int monthValue = month.getMonthValue();

        for (int i = 0; i < allLeaves.size(); i++) {
            DoctorLeave leave = allLeaves.get(i);

            if (!leave.getDoctorID().equals(doctorId)) {
                continue; // skip other doctors
            }

            // leave period
            LocalDate from = leave.getDateFrom();
            LocalDate to = leave.getDateTo();

            // iterate all days in the leave range
            LocalDate d = from;
            while (!d.isAfter(to)) {
                // only include if same year & month
                if (d.getYear() == year && d.getMonthValue() == monthValue) {
                    leaveDays.add(d);
                }
                d = d.plusDays(1);
            }
        }

        return leaveDays;
    }

}

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

    public static void addSampleLeaves() {
        addLeave(new DoctorLeave(
                "L001", // leaveID
                "D001", // doctorID
                LocalDate.of(2025, 8, 14), // dateFrom
                LocalDate.of(2025, 8, 30), // dateTo (same day leave)
                "Medical conference" // reason
        ));

        addLeave(new DoctorLeave(
                "L002",
                "D002",
                LocalDate.of(2025, 8, 13), // multi-day leave
                LocalDate.of(2025, 8, 16),
                "Family vacation"
        ));

        addLeave(new DoctorLeave(
                "L003",
                "D004",
                LocalDate.of(2025, 8, 13), // multi-day leave
                LocalDate.of(2025, 8, 25),
                "Family vacation"
        ));

        addLeave(new DoctorLeave(
                "L004",
                "D004",
                LocalDate.of(2025, 9, 15), // multi-day leave
                LocalDate.of(2025, 9, 25),
                "Family vacation"
        ));
    }

    // Add leaves
    public static boolean addLeave(DoctorLeave l) {
        if (l != null) {
            leaveList.add(l);

            // ✅ Update doctor's working status
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

//    public static boolean updateLeave(String leaveID, LocalDate newDateFrom, LocalDate newDateTo, String newReason) {
//        for (int i = 0; i < leaveList.size(); i++) {
//            DoctorLeave leave = leaveList.get(i);
//            if (leave.getLeaveID().equals(leaveID)) {
//                leave.setDateFrom(newDateFrom);
//                leave.setDateTo(newDateTo);
//                leave.setReason(newReason);
//                leaveList.replace(i, leave);
//
//                // ✅ Update doctor's working status
//                Doctor doctor = DoctorManagement.findDoctorById(leave.getDoctorID());
//                if (doctor != null) {
//                    DoctorManagement.updateWorkingStatus(doctor);
//                }
//
//                return true;
//            }
//        }
//        return false;
//    }
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

    public static int countLeavesForMonth(String doctorID, int year, int month) {
        int daysCount = 0;
        for (int i = 0; i < leaveList.size(); i++) {
            DoctorLeave leave = leaveList.get(i);
            if (leave.getDoctorID().equals(doctorID)) {
                LocalDate current = leave.getDateFrom();
                while (!current.isAfter(leave.getDateTo())) {
                    if (current.getYear() == year && current.getMonthValue() == month) {
                        daysCount++;
                    }
                    current = current.plusDays(1);
                }
            }
        }
        return daysCount;
    }

    //total leave taken regardless of shift
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


}

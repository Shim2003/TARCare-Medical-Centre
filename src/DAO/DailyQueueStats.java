package DAO;

import Utility.UtilityClass;
import java.util.Date;

/**
 *
 * @author Lee Wei Hao
 */
public class DailyQueueStats {

    public final Date date;
    public final int totalPatients;
    public final int waitingPatients;
    public final int consultingPatients;
    public final int completedPatients;
    public final double completionRate;

    public DailyQueueStats(Date date, int total, int waiting, int consulting,
            int completed, double completionRate) {
        this.date = date;
        this.totalPatients = total;
        this.waitingPatients = waiting;
        this.consultingPatients = consulting;
        this.completedPatients = completed;
        this.completionRate = completionRate;
    }

    @Override
    public String toString() {
        return String.format("Date: %s | Total: %d | Waiting: %d | Consulting: %d | Completed: %d | Rate: %.1f%%",
                UtilityClass.formatDate(date), totalPatients, waitingPatients,
                consultingPatients, completedPatients, completionRate);
    }
}

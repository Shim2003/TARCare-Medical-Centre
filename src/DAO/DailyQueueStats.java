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
    public final int readyToConsultingPatients;
    public final int consultingPatients;
    public final int completedPatients;
    public final double completionRate;

    public DailyQueueStats(Date date, int total, int waiting, int ready, int consulting,
            int completed, double completionRate) {
        this.date = date;
        this.totalPatients = total;
        this.waitingPatients = waiting;
        this.readyToConsultingPatients = ready;
        this.consultingPatients = consulting;
        this.completedPatients = completed;
        this.completionRate = completionRate;
    }
}

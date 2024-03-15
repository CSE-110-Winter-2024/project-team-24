package edu.ucsd.cse110.successorator.lib.domain.recurring;

import java.util.Calendar;
import java.util.Date;

public class DailyRecurring extends RecurringType {
    @Override
    public Boolean checkIfToday(Date currDate, Date startDate) {
        if (currDate.before(startDate)) return false;

        return true;
    }

    @Override
    public Boolean checkIfTomorrow(Date currDate, Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        cal.add(Calendar.DATE, 1);

        if (cal.getTime().before(startDate)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "DailyRecurring-";
    }
}

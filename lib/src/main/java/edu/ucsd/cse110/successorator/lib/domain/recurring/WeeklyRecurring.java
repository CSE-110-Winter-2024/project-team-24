package edu.ucsd.cse110.successorator.lib.domain.recurring;

import java.util.Calendar;
import java.util.Date;

public class WeeklyRecurring extends RecurringType {
    int dayOfWeek;

    public WeeklyRecurring(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public Boolean checkIfToday(Date currDate, Date startDate) {
        if (currDate.before(startDate)) return false;

        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        return cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
    }

    @Override
    public Boolean checkIfTomorrow(Date currDate, Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        cal.add(Calendar.DATE, 1);

        if (cal.getTime().before(startDate)) return false;

        return cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
    }

    @Override
    public String toString() {
        return "WeeklyRecurring-" + dayOfWeek;
    }
}

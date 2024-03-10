package edu.ucsd.cse110.successorator.lib.domain.recurring;

import java.util.Calendar;
import java.util.Date;

public class WeeklyRecurring extends RecurringType {
    int dayOfWeek;

    public WeeklyRecurring(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public Boolean checkIfToday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
    }

    @Override
    public String toString() {
        return "WeeklyRecurring-" + dayOfWeek;
    }
}

package edu.ucsd.cse110.successorator.lib.domain.recurring;

import java.util.Calendar;
import java.util.Date;

public class MonthlyRecurring extends RecurringType {
    int weekOfMonth;
    int dayOfWeek;

    public MonthlyRecurring(int weekOfMonth, int dayOfWeek) {
        this.weekOfMonth = weekOfMonth;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public Boolean checkIfToday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH) == weekOfMonth && cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
    }

    @Override
    public Boolean checkIfTomorrow(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.get(Calendar.WEEK_OF_MONTH) == weekOfMonth && cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
    }

    @Override
    public String toString() {
        return "MonthlyRecurring-" + weekOfMonth + "-" + dayOfWeek;
    }
}

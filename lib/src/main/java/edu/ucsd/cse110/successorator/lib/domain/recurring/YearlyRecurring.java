package edu.ucsd.cse110.successorator.lib.domain.recurring;

import java.util.Calendar;
import java.util.Date;

public class YearlyRecurring extends RecurringType {
    int month;
    int date;

    public YearlyRecurring(int month, int date) {
        this.month = month;
        this.date = date;
    }

    @Override
    public Boolean checkIfToday(Date currDate, Date startDate) {
        if (currDate.before(startDate)) return false;

        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        return cal.get(Calendar.DAY_OF_MONTH) == this.date && cal.get(Calendar.MONTH) == month;
    }

    @Override
    public Boolean checkIfTomorrow(Date currDate, Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        cal.add(Calendar.DATE, 1);

        if (cal.getTime().before(startDate)) return false;

        return cal.get(Calendar.DAY_OF_MONTH) == this.date && cal.get(Calendar.MONTH) == month;
    }

    @Override
    public String toString() {
        return "YearlyRecurring-" + month + "-" + date;
    }
}

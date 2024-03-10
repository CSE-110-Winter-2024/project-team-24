package edu.ucsd.cse110.successorator.lib.domain;

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
    public Boolean checkIfToday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH) == this.date && cal.get(Calendar.MONTH) == month;
    }

    @Override
    public String toString() {
        return "YearlyRecurring-" + month + "-" + date;
    }
}

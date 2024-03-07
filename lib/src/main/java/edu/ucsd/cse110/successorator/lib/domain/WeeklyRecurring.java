package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class WeeklyRecurring extends RecurringType {
    Date currDate;
    int dayOfWeek;
    public WeeklyRecurring(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public Boolean checkIfToday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        return cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
    }

    @Override
    public void onChanged(@Nullable Date value) {
        currDate = value;
    }

    @Override
    public String toString() {
        return "WeeklyRecurring-" +
                "dayOfWeek=" + dayOfWeek;
    }
}

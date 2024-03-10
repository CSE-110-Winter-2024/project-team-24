package edu.ucsd.cse110.successorator.lib.domain.recurring;

import java.util.Date;

public class DailyRecurring extends RecurringType {
    @Override
    public Boolean checkIfToday(Date date) {
        return true;
    }

    @Override
    public String toString() {
        return "DailyRecurring-";
    }
}

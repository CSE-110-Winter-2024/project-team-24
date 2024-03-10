package edu.ucsd.cse110.successorator.lib.domain.recurring;

import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;

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

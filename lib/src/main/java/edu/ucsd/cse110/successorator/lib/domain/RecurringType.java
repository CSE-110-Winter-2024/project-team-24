package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;
import java.util.regex.Pattern;

import edu.ucsd.cse110.successorator.lib.util.Observer;

public abstract class RecurringType implements Observer<Date> {
    public abstract Boolean checkIfToday();
    public static RecurringType valueOf(String recurringTypeString) {
        String[] strSplit = recurringTypeString.split("-");

        switch (strSplit[0]) {
            case "DailyRecurring":
                return new DailyRecurring();
            case "WeeklyRecurring":
                return new WeeklyRecurring(Integer.parseInt(strSplit[1]));
        }

        return null;
    }
}

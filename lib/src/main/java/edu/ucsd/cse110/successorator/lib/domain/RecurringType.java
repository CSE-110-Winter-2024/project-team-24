package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;

public abstract class RecurringType {
    public abstract Boolean checkIfToday(Date date);

    public static RecurringType valueOf(String recurringTypeString) {
        String[] strSplit = recurringTypeString.split("-");

        switch (strSplit[0]) {
            case "DailyRecurring":
                return new DailyRecurring();
            case "WeeklyRecurring":
                return new WeeklyRecurring(Integer.parseInt(strSplit[1]));
            case "MonthlyRecurring":
                return new MonthlyRecurring(Integer.parseInt(strSplit[1]), Integer.parseInt(strSplit[2]));
            case "YearlyRecurring":
                return new YearlyRecurring(Integer.parseInt(strSplit[1]), Integer.parseInt(strSplit[2]));
        }

        return null;
    }
}

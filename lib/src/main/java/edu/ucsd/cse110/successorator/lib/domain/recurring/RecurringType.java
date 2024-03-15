package edu.ucsd.cse110.successorator.lib.domain.recurring;

import java.util.Date;

public abstract class RecurringType {
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

    public abstract Boolean checkIfToday(Date currDate, Date startDate);

    public abstract Boolean checkIfTomorrow(Date currDate, Date startDate);
}

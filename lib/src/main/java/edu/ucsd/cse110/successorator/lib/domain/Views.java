package edu.ucsd.cse110.successorator.lib.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Views implements Serializable {
    public static String getViewTitle(Date date, ViewEnum view) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE M/d", Locale.getDefault());
        String formattedDate = dateFormat.format(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String tomorrowDate = dateFormat.format(calendar.getTime());

        switch (view) {
            case TOMORROW:
                return "Tomorrow, " + tomorrowDate + " ▼";
            case RECURRING:
                return "Recurring ▼";
            case PENDING:
                return "Pending ▼";
            default:
            case TODAY:
                return "Today, " + formattedDate + " ▼";
        }
    }

    public enum ViewEnum {
        TODAY,
        TOMORROW,
        RECURRING,
        PENDING,
    }
}
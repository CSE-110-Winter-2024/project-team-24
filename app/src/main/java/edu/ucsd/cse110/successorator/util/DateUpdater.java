package edu.ucsd.cse110.successorator.util;

import java.util.Calendar;
import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.util.Observer;

public class DateUpdater implements Observer<Date> {
    private ITasksRepository roomTasksRepository;
    private Date date;

    public DateUpdater(ITasksRepository roomTasksRepository, Date date) {
        this.roomTasksRepository = roomTasksRepository;
        this.date = date;
    }

    @Override
    public void onChanged(Date value) {
        System.out.println("DateSubject: " + date.toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(date);
        int prevMinute = calendar.get(Calendar.MINUTE);
        int prevHour = calendar.get(Calendar.HOUR_OF_DAY);
        int prevDay = calendar.get(Calendar.DAY_OF_MONTH);

//        if (day >= prevDay && prevHour < 2 && hour >= 2) {
//            roomTasksRepository.dateAdvanced();
//        }

        if (day >= prevDay && prevMinute < 42 && minute >= 42) {
            roomTasksRepository.dateAdvanced();
        }
    }
}

package edu.ucsd.cse110.successorator.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(date);
        int prevMinute = calendar.get(Calendar.MINUTE);
        int prevHour = calendar.get(Calendar.HOUR_OF_DAY);
        int prevDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (day > prevDay || (day >= prevDay && prevHour < 2 && hour >= 2)) {
            roomTasksRepository.dateAdvanced(date);
        }
        this.date = value;

        List<Task> tasks = roomTasksRepository.findAll();
        if (tasks == null) return;

        for (Task task : tasks) {
            if (task.isRecurring() && task.getRecurringType().checkIfToday(date)) {
                roomTasksRepository.appendToEndOfUnfinishedTasks(task.withCheckOff(false));
            }
        }
    }
}

package edu.ucsd.cse110.successorator;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.TasksRepository;
import edu.ucsd.cse110.successorator.lib.util.DateSubject;

public class SuccessoratorApplication extends Application {
    private TasksRepository tasksRepository;
    private DateSubject dateSubject;

    @Override
    public void onCreate() {
        super.onCreate();

        InMemoryDataSource dataSource = InMemoryDataSource.fromDefault();
        this.tasksRepository = new TasksRepository(dataSource);
        this.dateSubject = new DateSubject();
    }

    public TasksRepository getTasksRepository() {
        return tasksRepository;
    }

    public DateSubject getDateSubject() {
        return dateSubject;
    }
}

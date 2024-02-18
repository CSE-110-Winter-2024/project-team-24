package edu.ucsd.cse110.successorator;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

import java.util.Date;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.RoomTasksRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.util.DateSubject;

public class SuccessoratorApplication extends Application {
    private ITasksRepository tasksRepository;
    private DateSubject dateSubject;

    @Override
    public void onCreate() {
        super.onCreate();
    
        this.dateSubject = new DateSubject();

        var database = Room.databaseBuilder(
                getApplicationContext(),
                SuccessoratorDatabase.class,
                "successorator-database"
        ).allowMainThreadQueries().build();
        this.tasksRepository = new RoomTasksRepository(database.taskDao());
        var sharedPreferences = getSharedPreferences("successorator", MODE_PRIVATE);
        var isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        if (isFirstRun && database.taskDao().count() == 0) {
            tasksRepository.save(InMemoryDataSource.DEFAULT_CARDS);

            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        }

    }

    public ITasksRepository getTasksRepository() {
        return tasksRepository;
    }

    public DateSubject getDateSubject() {
        return dateSubject;
    }
}

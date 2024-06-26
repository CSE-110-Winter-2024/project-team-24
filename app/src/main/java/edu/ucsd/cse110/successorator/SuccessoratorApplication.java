package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.RoomTasksRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.util.DateSubject;
import edu.ucsd.cse110.successorator.util.DateUpdater;
import edu.ucsd.cse110.successorator.util.FocusModeSubject;
import edu.ucsd.cse110.successorator.util.TaskViewSubject;

public class SuccessoratorApplication extends Application {
    private ITasksRepository tasksRepository;
    private DateSubject dateSubject;
    private TaskViewSubject taskView;
    private FocusModeSubject focusModeSubject;

    @Override
    public void onCreate() {
        super.onCreate();

        this.dateSubject = new DateSubject();
        this.taskView = new TaskViewSubject();
        this.focusModeSubject = new FocusModeSubject();

        var database = Room.databaseBuilder(
                getApplicationContext(),
                SuccessoratorDatabase.class,
                "successorator-database"
        ).allowMainThreadQueries().build();
        this.tasksRepository = new RoomTasksRepository(database.taskDao());

        var sharedPreferences = getSharedPreferences("successorator", MODE_PRIVATE);
        var isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        if (isFirstRun && database.taskDao().count() == 0) {
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        }

        this.dateSubject.setSharedPreferences(sharedPreferences);
        this.dateSubject.loadDate();

        this.dateSubject.observe(new DateUpdater(tasksRepository, dateSubject.getItem()));
    }

    public ITasksRepository getTasksRepository() {
        return tasksRepository;
    }

    public DateSubject getDateSubject() {
        return dateSubject;
    }


    public TaskViewSubject getTaskView() {
        return taskView;
    }

    public TaskViewSubject getTaskViewSubject() {
        return taskView;
    }


    public FocusModeSubject getFocusModeSubject() {
        return focusModeSubject;

    }
}

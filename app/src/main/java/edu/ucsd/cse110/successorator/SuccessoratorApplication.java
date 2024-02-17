package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.RoomTasksRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;

public class SuccessoratorApplication extends Application {
    private ITasksRepository tasksRepository;

    @Override
    public void onCreate() {
        super.onCreate();

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
}

package edu.ucsd.cse110.successorator;

import android.app.Application;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.TasksRepository;

public class SuccessoratorApplication extends Application {
    private TasksRepository tasksRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        InMemoryDataSource dataSource = InMemoryDataSource.fromDefault();
        this.tasksRepository = new TasksRepository(dataSource);
    }

    public TasksRepository getTasksRepository() {
        return tasksRepository;
    }
}

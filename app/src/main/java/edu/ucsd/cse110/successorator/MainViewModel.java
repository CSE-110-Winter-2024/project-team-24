package edu.ucsd.cse110.successorator;


import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    ITasksRepository TasksRepository;
    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getTasksRepository());
                    });

    public MainViewModel(ITasksRepository tasksrepository) {
        this.TasksRepository = tasksrepository;
    }

    public void append(Task task) {
        TasksRepository.appendToEndOfUnfinishedTasks(task);
    }


}

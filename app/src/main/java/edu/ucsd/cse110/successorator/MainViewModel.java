package edu.ucsd.cse110.successorator;


import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TasksRepository;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    TasksRepository TasksRepository;
    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getTasksRepository());
                    });

    public MainViewModel(TasksRepository tasksrepository) {
        this.TasksRepository = tasksrepository;
    }

    public void append(Task task) {
        TasksRepository.appendToEndOfUnfinishedTasks(task, task.getCheckOff());
    }


}

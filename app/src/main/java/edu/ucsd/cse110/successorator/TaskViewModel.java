package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class TaskViewModel extends ViewModel {
    private final ITasksRepository tasksRepository;
    private final MutableSubject<Task> topTask;
    private final MutableSubject<List<Task>> orderedTasks;

    public static final ViewModelInitializer<TaskViewModel> initializer =
            new ViewModelInitializer<>(
                    TaskViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new TaskViewModel(app.getTasksRepository());
                    });

    public TaskViewModel(ITasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;

        // Create the observable subjects.
        this.orderedTasks = new SimpleSubject<>();
        this.topTask = new SimpleSubject<>();

        // When the list of cards changes (or is first loaded), reset the ordering:
        tasksRepository.findAllAsLiveData().observe(cards -> {
            if (cards == null) return;

            var newOrderedCards = cards.stream().sorted(Comparator.comparingInt(Task::sortOrder)).collect(Collectors.toList());
            orderedTasks.setItem(newOrderedCards);
        });

        // When the ordering changes, update the top card:
        orderedTasks.observe(cards -> {
            if (cards == null || cards.size() == 0) return;
            var card = cards.get(0);
            this.topTask.setItem(card);
        });
    }

    public void dateAdvanced() {
        tasksRepository.dateAdvanced();
    }

    public void toggleTaskStrikethrough(Task task) {
        tasksRepository.toggleTaskStrikethrough(task);
    }

    public Subject<List<Task>> getOrderedTasks() {
        return orderedTasks;
    }

    public void append(Task task) {
        tasksRepository.appendToEndOfUnfinishedTasks(task);
    }
}

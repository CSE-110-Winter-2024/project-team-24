package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.DateSubject;

public class TaskViewModel extends ViewModel implements Observer<Date> {
    private final ITasksRepository tasksRepository;
    private final MutableSubject<Task> topTask;
    private final MutableSubject<List<Task>> orderedTasks;
    private DateSubject dateSubject;

    public static final ViewModelInitializer<TaskViewModel> initializer =
            new ViewModelInitializer<>(
                    TaskViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new TaskViewModel(app.getTasksRepository(), app.getDateSubject());
                    });

    public TaskViewModel(ITasksRepository tasksRepository, DateSubject dateSubject) {
        this.tasksRepository = tasksRepository;
        this.dateSubject = dateSubject;

        // Create the observable subjects.
        this.orderedTasks = new SimpleSubject<>();
        this.topTask = new SimpleSubject<>();

        // When the list of cards changes (or is first loaded), reset the ordering:
        tasksRepository.findAllAsLiveData().observe(cards -> {
            if (cards == null) return;

            var newOrderedCards = cards.stream()
                    .filter(card -> !card.isRecurring() || card.getRecurringType().checkIfToday(dateSubject.getItem()))
                    .sorted(Comparator.comparingInt(Task::sortOrder))
                    .collect(Collectors.toList());
            orderedTasks.setItem(newOrderedCards);
        });

        // When the ordering changes, update the top card:
        orderedTasks.observe(cards -> {
            if (cards == null || cards.size() == 0) return;
            var card = cards.get(0);
            this.topTask.setItem(card);
        });
    }

    public void dateAdvanced(Date date) {
        tasksRepository.dateAdvanced(date);
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

    @Override
    public void onChanged(@Nullable Date value) {

    }
}

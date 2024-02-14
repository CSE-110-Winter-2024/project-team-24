package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.TasksRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class TaskViewModel extends ViewModel {
    // Domain state (true "Model" state)
    private final TasksRepository tasksRepository;
    private final MutableSubject<Task> topTask;
    private final MutableSubject<List<Task>> orderedTasks;
    private final MutableSubject<Boolean> isCheckedOff;
    private final MutableSubject<String> displayedText;

    public static final ViewModelInitializer<TaskViewModel> initializer =
            new ViewModelInitializer<>(
                    TaskViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new TaskViewModel(app.getTasksRepository());
                    });

    public TaskViewModel(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;

        // Create the observable subjects.
        this.orderedTasks = new SimpleSubject<>();
        this.isCheckedOff = new SimpleSubject<>();
        this.topTask = new SimpleSubject<>();
        this.displayedText = new SimpleSubject<>();


        isCheckedOff.setValue(false);

        // When the list of cards changes (or is first loaded), reset the ordering:
        tasksRepository.findAll().observe(cards -> {
            if (cards == null) return;

            var newOrderedCards = cards.stream().sorted(Comparator.comparingInt(Task::sortOrder)).collect(Collectors.toList());
            orderedTasks.setValue(newOrderedCards);
        });

        // When the ordering changes, update the top card:
        orderedTasks.observe(cards -> {
            if (cards == null || cards.size() == 0) return;
            var card = cards.get(0);
            this.topTask.setValue(card);
        });

        // When isCheckedOff is true, cross off the card.
        isCheckedOff.observe(isShowingFront -> {
            if (isShowingFront == null) return;

//            var card = topCard.getValue();
//            if (card == null) return;
//
//            var text = isShowingFront ? card.front() : card.back();
//            displayedText.setValue(text);

        });
    }

    public Subject<String> getDisplayedText() {
        return displayedText;
    }

    public Subject<List<Task>> getOrderedCards() {
        return orderedTasks;
    }

    public void crossCard() {
        var isShowingFront = this.isCheckedOff.getValue();
        if (isShowingFront == null) return;

        //Otherwise cross off the card:
    }


    public void append(Task card) {
        tasksRepository.append(card);
    }

    public void prepend(Task card) {
        tasksRepository.prepend(card);
    }

    public void remove(int id) {tasksRepository.remove(id);}
}

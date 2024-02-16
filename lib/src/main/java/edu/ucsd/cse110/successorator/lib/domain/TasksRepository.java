package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class TasksRepository implements ITasksRepository {
    private final InMemoryDataSource dataSource;

    public TasksRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Subject<Task> find(int id) {
        return dataSource.getTaskSubject(id);
    }

    @Override
    public Subject<List<Task>> findAll() {
        return dataSource.getAllTasksSubject();
    }

    @Override
    public void save(Task task) {
        dataSource.putTask(task);
    }

    @Override
    public void save(List<Task> tasks) {
        dataSource.putFlashcards(tasks);
    }

    @Override
    public void remove(int id) {
        dataSource.removeFlashcard(id);
    }

    @Override
    public void append(Task task) {
        dataSource.putTask(task.withSortOrder(dataSource.getMaxSortOrder() + 1));
    }

    @Override
    public void prepend(Task task) {
        dataSource.shiftSortOrders(dataSource.getMinSortOrder(), dataSource.getMaxSortOrder(), 1);
        dataSource.putTask(task.withSortOrder(dataSource.getMinSortOrder() - 1));
    }

    public void appendToEndOfUnfinishedTasks(Task task, boolean newState) {
        int maxSortOrder = dataSource.getMaxSortOrder();
        int newSortOrder = maxSortOrder + 1;

        List<Task> tasks = dataSource.getTasks();
        Optional<Task> firstCheckedOff = tasks.stream()
                .sorted(Comparator.comparing(Task::sortOrder))
                .filter(Task::getCheckOff)
                .findFirst();

        if (firstCheckedOff.isPresent()) {
            newSortOrder = firstCheckedOff.get().sortOrder();
            System.out.println(firstCheckedOff.get().getTask());
            System.out.println(newSortOrder);
        }

        dataSource.shiftSortOrders(newSortOrder, maxSortOrder, 1);
        save(new Task(task.id(), task.getTask(), newSortOrder, newState));
    }

    public void toggleTaskStrikethrough(Task task) {
        boolean newState = !(task.getCheckOff());

        if (!task.getCheckOff()) {
            remove(task.id());
            appendToEndOfUnfinishedTasks(task, newState);
        } else {
            remove(task.id());
            prepend(new Task(task.id(), task.getTask(), task.sortOrder(), newState));
        }
    }

    @Override
    public int size() {
        return dataSource.getTasks().size();
    }
}

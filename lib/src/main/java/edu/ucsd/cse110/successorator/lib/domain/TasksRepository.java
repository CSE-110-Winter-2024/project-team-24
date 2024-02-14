package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class TasksRepository implements ITasksRepository {
    private final InMemoryDataSource dataSource;

    public TasksRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Subject<Task> find(int id) {
        return dataSource.getFlashcardSubject(id);
    }

    @Override
    public Subject<List<Task>> findAll() {
        return dataSource.getAllFlashcardsSubject();
    }

    @Override
    public void save(Task task) {
        dataSource.putFlashcard(task);
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
        dataSource.putFlashcard(task.withSortOrder(dataSource.getMaxSortOrder() + 1));
    }

    public void toggleTaskStrikethrough(Task task) {
        task = task.withCheckOff(!task.getCheckOff());
        save(task);
    }


    @Override
    public void prepend(Task task) {
        dataSource.shiftSortOrders(0, dataSource.getMaxSortOrder(), 1);

        dataSource.putFlashcard(task.withSortOrder(dataSource.getMinSortOrder() - 1));
    }

    @Override
    public int size() {
        return dataSource.getFlashcards().size();
    }
}

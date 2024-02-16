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
        dataSource.putTask(task.withSortOrder(dataSource.getMinSortOrder() - 1));
    }

    public void toggleTaskStrikethrough(Task task) {

        boolean newState = !(task.getCheckOff());

        if (!task.getCheckOff()) {
            remove(task.id());
            append(new Task(task.id(), task.getTask(), task.sortOrder(), newState));
        }
        else {
            remove(task.id());
            prepend(new Task(task.id(), task.getTask(), task.sortOrder(), newState));
        }
        save(task);
    }

    @Override
    public int size() {
        return dataSource.getTasks().size();
    }
}

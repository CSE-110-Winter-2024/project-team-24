package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface ITasksRepository {
    Subject<Task> find(int id);

    Subject<List<Task>> findAll();

    void save(Task task);

    void save(List<Task> tasks);

    void remove(int id);

    void append(Task task);

    void appendToEndOfUnfinishedTasks(Task task);

    void toggleTaskStrikethrough(Task task);

    void prepend(Task task);

    int size();
    void dateAdvanced();
}

package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface ITasksRepository {
    Subject<Task> findAsLiveData(int id);

    Task find(int id);

    List<Task> findAll();

    Subject<List<Task>> findAllAsLiveData();

    void save(Task task);

    void save(List<Task> tasks);

    void remove(int id);

    void append(Task task);

    void appendAll(List<Task> tasks);

    void appendToEndOfUnfinishedTasks(Task task);

    void toggleTaskStrikethrough(Task task);

    void prepend(Task task);

    int size();

    void dateAdvanced(Date date);

    int generateRecurringID();

    void addOnetimeTask(Task task);


    List<Task> filterByValues(List<Task> taskList, Views.ViewEnum view, Contexts.Context context);
}

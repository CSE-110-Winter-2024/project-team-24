package edu.ucsd.cse110.successorator;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.domain.Contexts;
import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskBuilder;
import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.FocusModeSubject;
import edu.ucsd.cse110.successorator.util.TaskViewSubject;

public class TaskViewModelTest {

    ITasksRepository tasksRepository;
    TaskViewModel taskViewModel;
    TaskViewSubject taskViewSubject;
    FocusModeSubject focusModeSubject;

    @Before
    public void setUp() {
        taskViewSubject = new TaskViewSubject();
        focusModeSubject = new FocusModeSubject();
        tasksRepository = new ITasksRepository() {

            List<Task> mockedDataBase = new ArrayList<>();

            @Override
            public Subject<Task> findAsLiveData(int id) {
                return null;
            }

            @Override
            public Task find(int id) {
                return mockedDataBase.get(id);
            }

            @Override
            public List<Task> findAll() {
                return mockedDataBase;
            }

            @Override
            public Subject<List<Task>> findAllAsLiveData() {
                SimpleSubject<List<Task>> output = new SimpleSubject<>();
                output.setItem(mockedDataBase);
                return output;
            }

            @Override
            public void save(Task task) {
                mockedDataBase.add(task);
            }

            @Override
            public void save(List<Task> tasks) {
                mockedDataBase.addAll(tasks);
            }

            @Override
            public void remove(int id) {
                mockedDataBase.remove(id);
            }

            @Override
            public void append(Task task) {
                mockedDataBase.add(task);
            }

            @Override
            public void appendAll(List<Task> tasks) {
                mockedDataBase.addAll(tasks);
            }

            @Override
            public void appendToEndOfUnfinishedTasks(Task task) {
                return;
            }

            @Override
            public void toggleTaskStrikethrough(Task task) {
                return;
            }

            @Override
            public void prepend(Task task) {
                mockedDataBase.add(0, task);
            }

            @Override
            public int size() {
                return mockedDataBase.size();
            }

            @Override
            public void dateAdvanced(Date date) {
                return;
            }

            @Override
            public int generateRecurringID() {
                return -1;
            }

            @Override
            public void addOnetimeTask(Task task) {
                append(task);
            }

            @Override
            public List<Task> filterByValues(List<Task> taskList, Views.ViewEnum view, Contexts.Context context) {
                return null;
            }
        };

        taskViewModel = new TaskViewModel(tasksRepository, taskViewSubject, focusModeSubject);
        tasksRepository.appendAll(List.of(
                new TaskBuilder().withTaskName("Task 1").withSortOrder(0).build(),
                new TaskBuilder().withTaskName("Task 2").withSortOrder(1).build(),
                new TaskBuilder().withTaskName("Task 3").withSortOrder(2).build()
        ));
    }

    @Test
    public void testViewModelGetRepository() {
        assertEquals(List.of(
                new TaskBuilder().withTaskName("Task 1").withSortOrder(0).build(),
                new TaskBuilder().withTaskName("Task 2").withSortOrder(1).build(),
                new TaskBuilder().withTaskName("Task 3").withSortOrder(2).build()
        ), taskViewModel.getTasksRepository().findAll());
    }

    @Test
    public void testViewModelAppend() {
        taskViewModel.append(new TaskBuilder().withTaskName("Task 4").withSortOrder(3).build());
        assertEquals(4, taskViewModel.getTasksRepository().size());
    }
}

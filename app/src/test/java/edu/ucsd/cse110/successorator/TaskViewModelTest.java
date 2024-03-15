package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

            List<Task> mockDatabase = new ArrayList<>(List.of(
                    new TaskBuilder().withTaskName("Task 1").withSortOrder(0).build(),
                    new TaskBuilder().withTaskName("Task 2").withSortOrder(1).build(),
                    new TaskBuilder().withTaskName("Task 3").withSortOrder(2).build()
            ));

            /**
             * @param id
             * @return
             */
            @Override
            public Subject<Task> findAsLiveData(int id) {
                List<Task> tasks_output = mockDatabase;
                Subject<Task> output = new SimpleSubject<>();

                for (Task task : tasks_output) {
                    if (task.id() == id) {
                        ((SimpleSubject<Task>)output).setItem(task);
                        return output;
                    }
                }

                return output;
            }

            /**
             * @param id
             * @return
             */
            @Override
            public Task find(int id) {
                for (Task task : mockDatabase) {
                    if (task.id() == id) {
                        return task;
                    }
                }
                return null;
            }

            /**
             * @return
             */
            @Override
            public List<Task> findAll() {
                return mockDatabase;
            }

            /**
             * @return
             */
            @Override
            public Subject<List<Task>> findAllAsLiveData() {
                List<Task> tasks_output = mockDatabase;
                Subject<List<Task>> output = new SimpleSubject<>();

                ((SimpleSubject<List<Task>>)output).setItem(tasks_output);
                return output;
            }

            /**
             * @param task
             */
            @Override
            public void save(Task task) {
                return;
            }

            /**
             * @param tasks
             */
            @Override
            public void save(List<Task> tasks) {
                return;
            }

            /**
             * @param id
             */
            @Override
            public void remove(int id) {
                for (Task task : mockDatabase) {
                    if (task.id() == id) {
                        mockDatabase.remove(task);
                        return;
                    }
                }
            }

            /**
             * @param task
             */
            @Override
            public void append(Task task) {
                mockDatabase.add(task);
            }

            /**
             * @param tasks
             */
            @Override
            public void appendAll(List<Task> tasks) {
                mockDatabase.addAll(tasks);
            }

            /**
             * @param task
             */
            @Override
            public void appendToEndOfUnfinishedTasks(Task task) {
                return;
            }

            /**
             * @param task
             */
            @Override
            public void toggleTaskStrikethrough(Task task) {
                return;
            }

            /**
             * @param task
             */
            @Override
            public void prepend(Task task) {
                mockDatabase.add(0, task);
            }

            /**
             * @return
             */
            @Override
            public int size() {
                return mockDatabase.size();
            }

            /**
             * @param date
             */
            @Override
            public void dateAdvanced(Date date) {
                return;
            }

            /**
             * @return
             */
            @Override
            public int generateRecurringID() {
                return 0;
            }

            /**
             * @param task
             */
            @Override
            public void addOnetimeTask(Task task) {
                return;
            }

            /**
             * @param taskList
             * @param view
             * @param context
             * @return
             */
            @Override
            public List<Task> filterByValues(List<Task> taskList, Views.ViewEnum view, Contexts.Context context) {
                return null;
            }
        };

        this.taskViewModel = new TaskViewModel(tasksRepository, taskViewSubject, focusModeSubject) {
            @Override
            public void append(Task task) {
                tasksRepository.append(task);
            }
        };
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

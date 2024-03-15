package edu.ucsd.cse110.successorator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Contexts;
import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskBuilder;
import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.FocusModeSubject;
import edu.ucsd.cse110.successorator.util.TaskViewSubject;

public class BddTests {
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
                for (var task : findAll()) {
                    if (task.id() == id) return task;
                }
                return null;
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
                mockedDataBase.remove(this.find(id));
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
                return taskList.stream()
                            .filter(task -> task.getView() == view)
                            .sorted(Comparator.comparingInt(Task::sortOrder))
                            .collect(Collectors.toList());
            }
        };
    }
    @Test
    public void us8_recurringWeeklyFromToday() {
        var newTask = new TaskBuilder()
                .withTaskName("Weekly Task")
                .withRecurringType(RecurringType.valueOf("Weekly-Recurring"))
                .withView(Views.ViewEnum.RECURRING)
                .withCheckedOff(false)
                .build();
        tasksRepository.append(newTask);
        var taskList = tasksRepository.findAll();
        assert taskList.size() == 1;
        var currentTasks = tasksRepository.filterByValues(taskList, Views.ViewEnum.RECURRING, null);
        assert currentTasks.size() == 1;
    }

    @Test
    public void us9_deleteRecurringGoal() {
        var task1 = new TaskBuilder()
                .withTaskName("Task 1")
                .withId(1)
                .withView(Views.ViewEnum.TODAY)
                .withCheckedOff(false)
                .build();
        var task2 = new TaskBuilder()
                .withTaskName("Task 2")
                .withId(2)
                .withRecurringType(RecurringType.valueOf("Weekly-Recurring"))
                .withView(Views.ViewEnum.RECURRING)
                .withCheckedOff(false)
                .build();
        tasksRepository.append(task1);
        tasksRepository.append(task2);
        var taskList = tasksRepository.findAll();
        assert taskList.size() == 2;

        var currentTasks = tasksRepository.filterByValues(taskList, Views.ViewEnum.RECURRING, null);
        for (var task : currentTasks) {
            tasksRepository.remove(task.id());
        }

        assert taskList.size() == 1;
        assert taskList.get(0).id() == 1;
        assert taskList.get(0).getRecurringType() == null;
    }

    @Test
    public void us10a_enterGoalsForTomorrow() {
        var tomorrowTask = new TaskBuilder()
                .withTaskName("Task for Tomorrow")
                .withView(Views.ViewEnum.TOMORROW)
                .withCheckedOff(false)
                .build();
        tasksRepository.append(tomorrowTask);
        var taskList = tasksRepository.findAll();
        var tomorrowTasks = taskList.stream()
                .filter(task -> task.getView() == Views.ViewEnum.TOMORROW)
                .collect(Collectors.toList());

        assert tomorrowTasks.size() == 1;
        assert "Task for Tomorrow".equals(tomorrowTasks.get(0).getTaskName());
    }

    @Test
    public void us10b_treatTomorrowGoalsLikeTodayGoals() {
        var tomorrowTask = new TaskBuilder()
                .withTaskName("Complete project")
                .withView(Views.ViewEnum.TOMORROW)
                .withCheckedOff(false)
                .withId(0)
                .build();
        tasksRepository.append(tomorrowTask);

        // Simulate checking off the task
        var newTomorrowTask = tomorrowTask.withCheckOff(true);
        tasksRepository.remove(0);
        tasksRepository.append(newTomorrowTask);

        assert tasksRepository.find(tomorrowTask.id()).getCheckOff();

        // Simulate removing the task
        tasksRepository.remove(tomorrowTask.id());

        // Verify task is removed
        assert tasksRepository.findAll().size() == 0;
    }

    @Test
    public void us11a_addPendingGoal() {
        var newTask = new TaskBuilder()
                .withTaskName("Pending Task")
                .withView(Views.ViewEnum.PENDING)
                .withCheckedOff(false)
                .build();
        tasksRepository.append(newTask);
        var taskList = tasksRepository.findAll();
        assert taskList.size() == 1;
        var pendingTasks = tasksRepository.filterByValues(taskList, Views.ViewEnum.PENDING, null);
        assert pendingTasks.size() == 1;
        assert "Pending Task".equals(pendingTasks.get(0).getTaskName());
    }

    @Test
    public void us11b_deletePendingGoal() {
        var pendingTask = new TaskBuilder()
                .withTaskName("Delete Pending Task")
                .withView(Views.ViewEnum.PENDING)
                .withCheckedOff(false)
                .withId(1)
                .build();
        tasksRepository.append(pendingTask);
        assert tasksRepository.size() == 1;

        tasksRepository.remove(pendingTask.id());
        assert tasksRepository.size() == 0;
    }

    @Test
    public void us11b_movePendingGoalToTomorrow() {
        var pendingTask = new TaskBuilder()
                .withTaskName("Move to Tomorrow")
                .withView(Views.ViewEnum.PENDING)
                .withCheckedOff(false)
                .build();
        tasksRepository.append(pendingTask);
        var initialTaskList = tasksRepository.findAll();
        assert initialTaskList.size() == 1;

        // Simulate moving the task to tomorrow
        var movedTask = pendingTask.withView(Views.ViewEnum.TOMORROW);
        tasksRepository.save(movedTask);
        var tomorrowTasks = tasksRepository.filterByValues(tasksRepository.findAll(), Views.ViewEnum.TOMORROW, null);
        assert tomorrowTasks.size() == 1;
        assert "Move to Tomorrow".equals(tomorrowTasks.get(0).getTaskName());
    }





}

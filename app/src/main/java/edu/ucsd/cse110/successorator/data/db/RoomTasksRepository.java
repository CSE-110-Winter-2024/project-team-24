package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;

public class RoomTasksRepository implements ITasksRepository {
    private final TaskDao tasksDao;

    public RoomTasksRepository(TaskDao tasksDao) {
        this.tasksDao = tasksDao;
    }

    @Override
    public Subject<Task> findAsLiveData(int id) {
        LiveData<TaskEntity> entityLiveData = tasksDao.findAsLiveData(id);
        LiveData<Task> taskLiveData = Transformations.map(entityLiveData, TaskEntity::toTask);
        return new LiveDataSubjectAdapter<>(taskLiveData);
    }

    @Override
    public Task find(int id) {
        return tasksDao.find(id).toTask();
    }

    public List<Task> findAll() {
        return tasksDao.findAll().stream().map(TaskEntity::toTask).collect(Collectors.toList());
    }

    public Subject<List<Task>> findAllAsLiveData() {
        var entityLiveData = tasksDao.findAllAsLiveData();
        var taskLiveData = Transformations.map(entityLiveData, entities -> {
            return entities.stream().map(TaskEntity::toTask).collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(taskLiveData);
    }

    @Override
    public void save(Task task) {
        tasksDao.insert(TaskEntity.fromTask(task));
    }

    @Override
    public void save(List<Task> tasks) {
        var entities = tasks.stream().map(TaskEntity::fromTask).collect(Collectors.toList());
        tasksDao.insert(entities);
    }

    @Override
    public void append(Task task) {
        tasksDao.append(TaskEntity.fromTask(task));
    }

    @Override
    public void appendToEndOfUnfinishedTasks(Task task) {
        int maxSortOrder = tasksDao.getMaxSortOrder();
        int newSortOrder = maxSortOrder + 1;

        List<Task> tasks = Objects.requireNonNull(tasksDao.findAll().stream().map(TaskEntity::toTask).collect(Collectors.toList()));
        Optional<Task> firstCheckedOff = tasks.stream().sorted(Comparator.comparing(Task::sortOrder)).filter(Task::getCheckOff).findFirst();

        if (firstCheckedOff.isPresent()) {
            newSortOrder = firstCheckedOff.get().sortOrder();
        }

        tasksDao.shiftSortOrders(newSortOrder, maxSortOrder, 1);
        save(task.withSortOrder(newSortOrder));
    }

    @Override
    public void toggleTaskStrikethrough(Task task) {
        boolean newState = !(task.getCheckOff());
        remove(task.id());

        if (!task.getCheckOff()) {
            appendToEndOfUnfinishedTasks(task.withCheckOff(newState));
        } else {
            prepend(task.withCheckOff(newState));
        }
    }

    @Override
    public void prepend(Task task) {
        tasksDao.prepend(TaskEntity.fromTask(task));
    }

    @Override
    public int generateRecurringID() {
        Optional<Task> recurringTasks = findAll().stream().max(Comparator.comparingInt(Task::getRecurringID));

        return recurringTasks.isPresent() ? recurringTasks.get().getRecurringID() + 1 : 1;
    }

    @Override
    public int size() {
        return tasksDao.count();
    }

    @Override
    public void remove(int id) {
        tasksDao.delete(id);
    }

    @Override
    public void dateAdvanced(Date date) {
        // Remove all tasks that are checked off and TODAY
        findAll().stream()
                .filter(task -> task.getView() == Task.IView.TODAY && task.getCheckOff())
                .forEach(task -> remove(task.id()));

        // Move all tasks from TOMORROW to TODAY
        findAll().stream()
                .filter(task -> task.getView() == Task.IView.TOMORROW)
                .forEach(task -> {
                    remove(task.id());
                    addOnetimeTask(task.withView(Task.IView.TODAY));
                });

        findAll().forEach(task -> {
            if (task.isRecurring() && task.getRecurringType().checkIfToday(date)) {
                addOnetimeTask(task.withCheckOff(false).withView(Task.IView.TODAY));
            }

            if (task.isRecurring() && task.getRecurringType().checkIfTomorrow(date)) {
                addOnetimeTask(task.withCheckOff(false).withView(Task.IView.TOMORROW));
            }
        });

        findAll().stream()
                .filter(Task::getCheckOff)
                .forEach(task -> remove(task.id()));
    }

    @Override
    public void addOnetimeTask(Task task) {
        List<Integer> taskRecurringID = findAll().stream().filter(e -> !e.isRecurring() && e.getView() == task.getView()).map(Task::getRecurringID).collect(Collectors.toList());
        if (taskRecurringID.contains(task.getRecurringID())) {
            return;
        }
        appendToEndOfUnfinishedTasks(task.withId(null).withNullRecurringType());
    }

    @Override
    public List<Task> filterByValues(List<Task> taskList, Task.IView view, Task.Context context) {
        return taskList.stream()
                .filter(card -> card.getView() == view)
                .filter(card -> context == null || context == card.getContext())
                .collect(Collectors.groupingBy(Task::getContext))
                .values()
                .stream()
                .flatMap(group -> group.stream().sorted(Comparator.comparingInt(Task::sortOrder)))
                .collect(Collectors.toList());
    }
}


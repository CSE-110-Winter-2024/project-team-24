package edu.ucsd.cse110.successorator.lib.domain;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.views.IView;

public class Task implements Serializable {
    private final @NonNull Integer id;
    private final @NonNull String taskName;
    private final @NonNull Integer sortOrder;
    private final @NonNull Boolean checkedOff;
    private final RecurringType recurringType;
    private final @NonNull Integer recurring_id;
    private final IView view;

    public Task(@NonNull Integer id, @NonNull String taskName, int sortOrder, boolean checkedOff, RecurringType recurringType, Integer recurring_id, IView view) {
        this.id = id;
        this.taskName = taskName;
        this.sortOrder = sortOrder;
        this.checkedOff = checkedOff;
        this.recurringType = recurringType;
        this.recurring_id = recurring_id;
        this.view = view;
    }

    public Integer getRecurringID() {
        return this.recurring_id;
    }

    public Boolean isRecurring() {
        return this.recurringType != null;
    }

    public RecurringType getRecurringType() {
        return this.recurringType;
    }

    public @NonNull Integer id() {
        return id;
    }

    public int sortOrder() {
        return sortOrder;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean getCheckOff() {
        return checkedOff;
    }

    public IView getView() {
        return view;
    }

    public Task withSortOrder(int sortOrder) {
        return new Task(id, taskName, sortOrder, checkedOff, recurringType, recurring_id, view);
    }

    public Task withId(int id) {
        return new Task(id, taskName, sortOrder, checkedOff, recurringType, recurring_id, view);
    }

    public Task withCheckOff(boolean checkOff) {
        return new Task(id, taskName, sortOrder, checkOff, recurringType, recurring_id, view);
    }

    public Task withNullRecurringType() {
        return new Task(id, taskName, sortOrder, checkedOff, null, recurring_id, view);
    }

    public Task withView(IView view) {
        return new Task(id, taskName, sortOrder, checkedOff, recurringType, recurring_id, view);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(taskName, task.taskName) && Objects.equals(sortOrder, task.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, sortOrder);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + taskName + '\'' +
                ", sortOrder=" + sortOrder +
                ", checkedOff=" + checkedOff +
                '}';
    }
}
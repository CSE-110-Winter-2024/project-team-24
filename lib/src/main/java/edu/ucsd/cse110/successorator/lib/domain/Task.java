package edu.ucsd.cse110.successorator.lib.domain;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;

public class Task implements Serializable {
    private final @NonNull Integer id;
    private final @NonNull String taskName;
    private final @NonNull Integer sortOrder;
    private final @NonNull Boolean checkedOff;
    private final RecurringType recurringType;
    private final @NonNull Integer recurring_id;
    private final Views.ViewEnum view;
    private final Contexts.Context context;

    public Task(@NonNull Integer id, @NonNull String taskName, int sortOrder, boolean checkedOff, RecurringType recurringType, Integer recurring_id, Views.ViewEnum view, Contexts.Context context) {
        this.id = id;
        this.taskName = taskName;
        this.sortOrder = sortOrder;
        this.checkedOff = checkedOff;
        this.recurringType = recurringType;
        this.recurring_id = recurring_id;
        this.view = view;
        this.context = context;
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

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    public boolean getCheckOff() {
        return checkedOff;
    }

    public Views.ViewEnum getView() {
        return view;
    }

    public Contexts.Context getContext() {
        return context;
    }

    public Task withSortOrder(int sortOrder) {
        return new Task(id, taskName, sortOrder, checkedOff, recurringType, recurring_id, view, context);
    }

    public Task withId(Integer id) {
        return new Task(id, taskName, sortOrder, checkedOff, recurringType, recurring_id, view, context);
    }

    public Task withCheckOff(boolean checkOff) {
        return new Task(id, taskName, sortOrder, checkOff, recurringType, recurring_id, view, context);
    }

    public Task withNullRecurringType() {
        return new Task(id, taskName, sortOrder, checkedOff, null, recurring_id, view, context);
    }

    public Task withView(Views.ViewEnum view) {
        return new Task(id, taskName, sortOrder, checkedOff, recurringType, recurring_id, view, context);
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
                ", recurringType=" + recurringType +
                ", recurring_id=" + recurring_id +
                ", view=" + view +
                ", context=" + context +
                '}';
    }
}
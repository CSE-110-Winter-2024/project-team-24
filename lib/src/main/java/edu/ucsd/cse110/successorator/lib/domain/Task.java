package edu.ucsd.cse110.successorator.lib.domain;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {
    private final @NonNull Integer id;
    private final @NonNull String task;
    private final @NonNull Integer sortOrder;
    private final @NonNull Boolean checkedOff;

    public Task(@NonNull Integer id, @NonNull String task, int sortOrder, boolean checkedOff) {
        this.id = id;
        this.task = task;
        this.sortOrder = sortOrder;
        this.checkedOff = checkedOff;
    }

    public @NonNull Integer id() {
        return id;
    }

    public int sortOrder() {
        return sortOrder;
    }

    public String getTask() {
        return task;
    }

    public boolean getCheckOff() {
        return checkedOff;
    }

    public Task withSortOrder(int sortOrder) {
        return new Task(this.id, this.task, sortOrder, checkedOff);
    }

    public Task withId(int id) {
        return new Task(id, this.task, this.sortOrder, checkedOff);
    }

    public Task withCheckOff(boolean checkOff) {
        return new Task(id, task, sortOrder, checkOff);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(this.task, task.task) && Objects.equals(sortOrder, task.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, sortOrder);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", sortOrder=" + sortOrder +
                ", checkedOff=" + checkedOff +
                '}';
    }
}
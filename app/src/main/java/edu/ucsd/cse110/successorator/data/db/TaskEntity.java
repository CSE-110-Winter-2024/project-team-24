package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.successorator.lib.domain.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.Task;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "taskName")
    public String taskName;

    @ColumnInfo(name = "checkoff")
    public boolean checkoff;

    @ColumnInfo(name = "sort_order")
    public Integer sortOrder;

    @ColumnInfo(name = "recurring_type")
    public RecurringType recurringType;

    @ColumnInfo(name = "recurring_id")
    public Integer recurring_id;

    TaskEntity(@NonNull String taskName, @NonNull boolean checkoff, Integer sortOrder, RecurringType recurringType, Integer recurring_id) {
        this.taskName = taskName;
        this.checkoff = checkoff;
        this.sortOrder = sortOrder;
        this.recurringType = recurringType;
        this.recurring_id = recurring_id;
    }

    public static TaskEntity fromTask(@NonNull Task task) {
        var card = new TaskEntity(task.getTaskName(), task.getCheckOff(), task.sortOrder(), task.getRecurringType(), task.getRecurringID());
        card.id = task.id();
        return card;
    }

    public @NonNull Task toTask() {
        return new Task(id, taskName, sortOrder, checkoff, recurringType, recurring_id);
    }
}

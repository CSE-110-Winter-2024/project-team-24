package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.successorator.lib.domain.Task;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "task")
    public String task;

    @ColumnInfo(name = "checkoff")
    public boolean checkoff;

    @ColumnInfo(name = "sort_order")
    public Integer sortOrder;

    TaskEntity(@NonNull String task, @NonNull boolean checkoff, Integer sortOrder) {
        this.task = task;
        this.checkoff = checkoff;
        this.sortOrder = sortOrder;
    }

    public static TaskEntity fromTask(@NonNull Task task) {
        var card = new TaskEntity(task.getTask(), task.getCheckOff(), task.sortOrder());
        card.id = task.id();
        return card;
    }

    public @NonNull Task toTask() {
        return new Task(id, task, sortOrder, checkoff);
    }
}

package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.Contexts;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskBuilder;
import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;

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

    @ColumnInfo(name = "view")
    public Views.ViewEnum view;

    @ColumnInfo(name = "context")
    public Contexts.Context context;

    @ColumnInfo(name = "startDate")
    public Date startDate;

    TaskEntity(@NonNull String taskName, @NonNull boolean checkoff, Integer sortOrder, RecurringType recurringType, Integer recurring_id, Views.ViewEnum view, Contexts.Context context, Date startDate) {
        this.taskName = taskName;
        this.checkoff = checkoff;
        this.sortOrder = sortOrder;
        this.recurringType = recurringType;
        this.recurring_id = recurring_id;
        this.view = view;
        this.context = context;
        this.startDate = startDate;
    }

    public static TaskEntity fromTask(@NonNull Task task) {
        var card = new TaskEntity(task.getTaskName(), task.getCheckOff(), task.sortOrder(), task.getRecurringType(), task.getRecurringID(), task.getView(), task.getContext(), task.getStartDate());
        card.id = task.id();
        return card;
    }

    public @NonNull Task toTask() {
        return new TaskBuilder()
                .withId(id)
                .withTaskName(taskName)
                .withSortOrder(sortOrder)
                .withCheckedOff(checkoff)
                .withRecurringType(recurringType)
                .withRecurringId(recurring_id)
                .withView(view)
                .withContext(context)
                .withStartDate(startDate)
                .build();
    }

    public TaskEntity withSortOrder(int sortOrder) {
        return new TaskEntity(taskName, checkoff, sortOrder, recurringType, recurring_id, view, context, startDate);
    }
}

package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;

public class TaskBuilder {
    private Integer id;
    private String taskName;
    private Integer sortOrder;
    private Boolean checkedOff;
    private RecurringType recurringType;
    private Integer recurring_id;
    private Views.ViewEnum view;
    private Contexts.Context context;

    private Date startDate;

    public TaskBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public TaskBuilder withTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public TaskBuilder withSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public TaskBuilder withCheckedOff(Boolean checkedOff) {
        this.checkedOff = checkedOff;
        return this;
    }

    public TaskBuilder withRecurringType(RecurringType recurringType) {
        this.recurringType = recurringType;
        return this;
    }

    public TaskBuilder withRecurringId(Integer recurring_id) {
        this.recurring_id = recurring_id;
        return this;
    }

    public TaskBuilder withView(Views.ViewEnum view) {
        this.view = view;
        return this;
    }

    public TaskBuilder withContext(Contexts.Context context) {
        this.context = context;
        return this;
    }

    public TaskBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Task build() {
        return new Task(id, taskName, sortOrder, checkedOff, recurringType, recurring_id, view, context, startDate);
    }
}

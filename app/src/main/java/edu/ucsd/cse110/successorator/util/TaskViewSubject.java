package edu.ucsd.cse110.successorator.util;

import androidx.annotation.Nullable;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class TaskViewSubject extends SimpleSubject<Task.IView> {
    Task.IView view;

    public TaskViewSubject() {
        this.view = Task.IView.TODAY;
    }

    @Nullable
    @Override
    public Task.IView getItem() {
        return view;
    }

    @Override
    public void setItem(Task.IView value) {
        this.view = value;
    }
}

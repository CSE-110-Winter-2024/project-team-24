package edu.ucsd.cse110.successorator.lib.domain.views;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;

public class PendingView extends IView {

    @Override
    public List<Task> getTasksForView(List<Task> tasks) {
        return null;
    }

    @Override
    public String toString() {
        return "pending";
    }
}

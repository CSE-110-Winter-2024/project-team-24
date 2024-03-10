package edu.ucsd.cse110.successorator.lib.domain.views;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;

public abstract class IView {
    List<Task> getTasksForView(List<Task> tasks);

    public static IView valueOf(String value) {
        switch (value) {
            case "today":
                return new TodayView();
            case "tomorrow":
                return new TomorrowView();
            case "recurring":
                return new RecurringView();
            case "pending":
                return new PendingView();
        }

        return null;
    }
}

package edu.ucsd.cse110.successorator.util;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class TaskViewSubject implements MutableSubject<Task.IView> {
    private final List<Observer<Task.IView>> observers = new ArrayList<>();
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
        this.notifyObservers();
    }

    @Override
    public void removeObserver(Observer<Task.IView> observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer<Task.IView> observer : observers) {
            observer.onChanged(view);
        }
    }

    @Override
    public void observe(Observer<Task.IView> observer) {
        observers.add(observer);
        observer.onChanged(view);
    }
}

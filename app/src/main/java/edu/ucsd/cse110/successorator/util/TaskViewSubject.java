package edu.ucsd.cse110.successorator.util;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;

public class TaskViewSubject implements MutableSubject<Views.ViewEnum> {
    private final List<Observer<Views.ViewEnum>> observers = new ArrayList<>();
    Views.ViewEnum view;

    public TaskViewSubject() {
        this.view = Views.ViewEnum.TODAY;
    }

    @NonNull
    @Override
    public Views.ViewEnum getItem() {
        return view;
    }

    @Override
    public void setItem(Views.ViewEnum value) {
        this.view = value;
        this.notifyObservers();
    }

    @Override
    public void removeObserver(Observer<Views.ViewEnum> observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer<Views.ViewEnum> observer : observers) {
            observer.onChanged(view);
        }
    }

    @Override
    public void observe(Observer<Views.ViewEnum> observer) {
        observers.add(observer);
        observer.onChanged(view);
    }
}

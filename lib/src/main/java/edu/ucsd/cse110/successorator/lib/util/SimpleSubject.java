package edu.ucsd.cse110.successorator.lib.util;

import androidx.annotation.Nullable;

import java.util.List;

public class SimpleSubject<T> implements MutableSubject<T> {
    private final List<Observer<T>> observers = new java.util.ArrayList<>();
    private @Nullable T value = null;

    @Override
    @Nullable
    public T getItem() {
        return value;
    }


    @Override
    public void setItem(T value) {
        this.value = value;
        notifyObservers();
    }

    @Override
    public void observe(Observer<T> observer) {
        observers.add(observer);
        observer.onChanged(value);
    }

    @Override
    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of the subject's new value. Used internally.
     */
    private void notifyObservers() {
        for (var observer : observers) {
            observer.onChanged(value);
        }
    }
}

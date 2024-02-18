package edu.ucsd.cse110.successorator.lib.util;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DateSubject implements MutableSubject<Date> {
    private Date currentDate;
    private final List<Observer<Date>> observers = new ArrayList<>();


    @Override
    public void setValue(Date value) {
        this.currentDate = value;
        notifyObservers();
    }

    @Nullable
    @Override
    public Date getValue() {
        return currentDate;
    }

    @Override
    public void observe(Observer<Date> observer) {
        observers.add(observer);
        observer.onChanged(currentDate);
    }

    @Override
    public void removeObserver(Observer<Date> observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer<Date> observer : observers) {
            observer.onChanged(currentDate);
        }
    }

    /**
     * Advances the date by one day.
     */
    public void advanceDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 1);
        setValue(calendar.getTime());
    }
}

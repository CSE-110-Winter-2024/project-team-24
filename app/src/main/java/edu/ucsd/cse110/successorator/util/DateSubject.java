package edu.ucsd.cse110.successorator.util;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;

public class DateSubject implements MutableSubject<Date> {
    private Date currentDate;
    private final List<Observer<Date>> observers = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    public void setDate(Date value) {
        this.currentDate = value;
        this.saveDate();
        notifyObservers();
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public Date getDate() {
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
        setDate(calendar.getTime());
    }

    public void saveDate() {
        if (sharedPreferences == null) {
            return;
        }
        sharedPreferences.edit().putLong("lastDate", currentDate.getTime()).apply();
    }

    public void loadDate() {
        this.setDate(new Date(sharedPreferences.getLong("lastDate", new Date().getTime())));
    }
}

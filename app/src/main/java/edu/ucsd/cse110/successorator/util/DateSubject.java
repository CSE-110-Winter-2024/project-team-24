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
    private final List<Observer<Date>> observers = new ArrayList<>();
    private Date currentDate;
    private SharedPreferences sharedPreferences;

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public Date getItem() {
        return currentDate;
    }

    @Override
    public void setItem(Date value) {
        this.currentDate = value;
        this.saveDate();
        notifyObservers();
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
        setItem(calendar.getTime());
    }

    public void saveDate() {
        if (sharedPreferences == null) {
            return;
        }
        sharedPreferences.edit().putLong("lastDate", currentDate.getTime()).apply();
    }

    public void loadDate() {
        this.setItem(new Date(sharedPreferences.getLong("lastDate", new Date().getTime())));
    }

    public int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public int getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        return cal.get(Calendar.MONTH);
    }

    public int getWeekOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }
}

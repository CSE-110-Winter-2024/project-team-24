package edu.ucsd.cse110.successorator.ui;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.util.TaskViewSubject;

public class ActionBarUpdater implements Observer<Date> {
    private final AppCompatActivity activity;

    public ActionBarUpdater(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onChanged(Date value) {
        if (value != null) {
            activity.runOnUiThread(() -> {
                TaskViewSubject taskViewSubject = ((SuccessoratorApplication) activity.getApplication()).getTaskViewSubject();
                TextView dateTitle = activity.findViewById(R.id.date_title);
                if (dateTitle != null) {
                    dateTitle.setText(Views.getViewTitle(value, taskViewSubject.getItem()));
                }
            });

        }
    }
}

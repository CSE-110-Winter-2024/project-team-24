package edu.ucsd.cse110.successorator.ui;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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


//             Format is Day, M/DD

            activity.runOnUiThread(() -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE M/d", Locale.getDefault());
                String formattedDate = dateFormat.format(value);
                String tomorrowDate = dateFormat.format(value.getTime() + 24 * 60 * 60 * 1000);
                TaskViewSubject taskViewSubject = ((SuccessoratorApplication) activity.getApplication()).getTaskViewSubject();
                Views.ViewEnum Ivalue = taskViewSubject.getItem();
                TextView dateTitle = activity.findViewById(R.id.date_title);
                if (dateTitle != null) {
                    if (Ivalue == Views.ViewEnum.TODAY) {
                        dateTitle.setText("Today, " + formattedDate + " ▼");
                    } else if (Ivalue == Views.ViewEnum.TOMORROW) {
                        dateTitle.setText("Tomorrow, " + tomorrowDate + " ▼");
                    } else if (Ivalue == Views.ViewEnum.PENDING) {
                        dateTitle.setText("Pending ▼");
                    } else if (Ivalue == Views.ViewEnum.RECURRING) {
                        dateTitle.setText("Recurring ▼");
                    }
                }
            });

        }
    }
}

package edu.ucsd.cse110.successorator.ui;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.util.DateSubject;

public class DateViewUpdater implements Observer<Task.IView> {
    private final AppCompatActivity activity;

    public DateViewUpdater(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onChanged(Task.IView value) {
        if (value != null) {

            activity.runOnUiThread(() -> {
                DateSubject dateSubject = ((SuccessoratorApplication) activity.getApplication()).getDateSubject();
                Date dateVal = dateSubject.getItem();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE M/d", Locale.getDefault());
                String formattedDate = dateFormat.format(dateVal);
                String tomorrowDate = dateFormat.format(dateVal.getTime() + 24 * 60 * 60 * 1000);
                if (value == Task.IView.TODAY) {
                    TextView dateTitle = activity.findViewById(R.id.date_title);
                    if (dateTitle != null) {
                        dateTitle.setText("Today, " + formattedDate + " ▼");
                    }
                } else if (value == Task.IView.TOMORROW) {
                    TextView dateTitle = activity.findViewById(R.id.date_title);
                    if (dateTitle != null) {
                        dateTitle.setText("Tomorrow, " + tomorrowDate + " ▼");
                    }
                } else if (value == Task.IView.PENDING) {
                    TextView dateTitle = activity.findViewById(R.id.date_title);
                    if (dateTitle != null) {
                        dateTitle.setText("Pending ▼");
                    }
                } else if (value == Task.IView.RECURRING) {
                    TextView dateTitle = activity.findViewById(R.id.date_title);
                    if (dateTitle != null) {
                        dateTitle.setText("Recurring ▼");
                    }
                }
            });
        }
    }
    

}

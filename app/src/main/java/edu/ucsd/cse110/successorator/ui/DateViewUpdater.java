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
import edu.ucsd.cse110.successorator.util.DateSubject;

public class DateViewUpdater implements Observer<Views.ViewEnum> {
    private final AppCompatActivity activity;

    public DateViewUpdater(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onChanged(Views.ViewEnum viewValue) {
        if (viewValue != null) {

            activity.runOnUiThread(() -> {
                DateSubject dateSubject = ((SuccessoratorApplication) activity.getApplication()).getDateSubject();

                TextView dateTitle = activity.findViewById(R.id.date_title);
                dateTitle.setText(Views.getViewTitle(dateSubject.getItem(), viewValue));
            });
        }
    }


}

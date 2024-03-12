package edu.ucsd.cse110.successorator.ui;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.util.Observer;

public class ActionBarUpdater implements Observer<Date> {
    private final AppCompatActivity activity;

    public ActionBarUpdater(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onChanged(Date value) {
        if (value != null) {
            // Format is Day, M/DD
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE M/d", Locale.getDefault());
            String formattedDate = dateFormat.format(value);
            activity.runOnUiThread(() -> {
                TextView dateTitle = activity.findViewById(R.id.date_title);
                if (dateTitle != null) {
                    dateTitle.setText(formattedDate + " ▼");
                }
            });
        }
    }
}

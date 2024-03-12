package edu.ucsd.cse110.successorator;    // might need in the future
//    private void onNegativeButtonClick(DialogInterface dialog, int which) {
//        dialog.cancel();
//    }

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Objects;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.ui.ActionBarUpdater;
import edu.ucsd.cse110.successorator.ui.ViewSwitchDialogFragment;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.DailyOrTomorrowTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.PendingTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.RecurringTaskDialogFragment;
import edu.ucsd.cse110.successorator.util.DateSubject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edu.ucsd.cse110.successorator.databinding.ActivityMainBinding view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);

        setContentView(view.getRoot());

        view.fragmentContainer.setOnClickListener(v -> {
            ((SuccessoratorApplication) getApplicationContext()).getDateSubject().setItem(new Date());
        });

        // Hides Android's default ActionBar so we can use our own
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Instantiate the actionBarUpdater
        ActionBarUpdater actionBarUpdater = new ActionBarUpdater(this);

        // Get DateSubject observable from Application, then add ActionBarUpdater as observer
        DateSubject dateSubject = ((SuccessoratorApplication) getApplicationContext()).getDateSubject();
        dateSubject.observe(actionBarUpdater);

        // Create FocusSwitcherListener
        findViewById(R.id.focus_switch).setOnClickListener(this::onFocusSwitchClick);

        // Date Picker Listener
        findViewById(R.id.date_title).setOnClickListener(this::onDateTitleClick);

        // Create AddTaskListener
        findViewById(R.id.add_task).setOnClickListener(this::onAddTaskClick);

    }

    private void onDateTitleClick(View view) {
        ViewSwitchDialogFragment vsd = ViewSwitchDialogFragment.newInstance();
        vsd.show(getSupportFragmentManager(), "ViewSwitchDialogFragment");
        TextView dateTitle = view.findViewById(R.id.date_title);
        if (dateTitle != null) {
            String prevTxt = (String) dateTitle.getText();
            dateTitle.setText(prevTxt.substring(0, prevTxt.length()-2) + " ▲");
        }
//        Toast.makeText(this, "Middle button clicked!", Toast.LENGTH_SHORT).show();
        // Does nothing for now...
    }

    private void onAddTaskClick(View view) {
        Task.IView currentView = ((SuccessoratorApplication) getApplicationContext()).getTaskView();
        switch(currentView) {
            case TODAY:
            case TOMORROW:
                DailyOrTomorrowTaskDialogFragment dialogFragment = DailyOrTomorrowTaskDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "DailyOrTomorrowTaskDialogFragment");
                break;
            case RECURRING:
                RecurringTaskDialogFragment recurringDialogFragment = RecurringTaskDialogFragment.newInstance();
                recurringDialogFragment.show(getSupportFragmentManager(), "RecurringTaskDialogFragment");
                break;
            case PENDING:
                PendingTaskDialogFragment pendingTaskDialogFragment = PendingTaskDialogFragment.newInstance();
                pendingTaskDialogFragment.show(getSupportFragmentManager(), "PendingTaskDialogFragment");
                break;
        }
    }

    private void onFocusSwitchClick(View view) {
        Toast.makeText(this, "Focus Switch button clicked!", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Set to current day, notifies ActionBarUpdater
        DateSubject dateSubject = ((SuccessoratorApplication) getApplicationContext()).getDateSubject();
        dateSubject.setItem(new Date());
        dateSubject.loadDate();
    }
}

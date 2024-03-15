package edu.ucsd.cse110.successorator;    // might need in the future
//    private void onNegativeButtonClick(DialogInterface dialog, int which) {
//        dialog.cancel();
//    }

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Objects;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.ui.ActionBarUpdater;
import edu.ucsd.cse110.successorator.ui.DateViewUpdater;
import edu.ucsd.cse110.successorator.ui.NoTasksFragment;
import edu.ucsd.cse110.successorator.ui.TaskListFragment;
import edu.ucsd.cse110.successorator.ui.ViewSwitchDialogFragment;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.CreateTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.PendingTaskDialogFragment;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.RecurringTaskDialogFragment;
import edu.ucsd.cse110.successorator.util.DateSubject;
import edu.ucsd.cse110.successorator.util.TaskViewSubject;

public class MainActivity extends AppCompatActivity {

    ITasksRepository tasksRepository;
    private long LastDateClick = 0;

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

        tasksRepository = ((SuccessoratorApplication) getApplicationContext()).getTasksRepository();
        tasksRepository.findAllAsLiveData().observe(tasks -> {
            if (tasks == null) return;
            swapFragments();
        });

        // Get DateSubject observable from Application, then add ActionBarUpdater as observer
        DateSubject dateSubject = ((SuccessoratorApplication) getApplicationContext()).getDateSubject();
        dateSubject.observe(actionBarUpdater);

        DateViewUpdater dateViewUpdater = new DateViewUpdater(this);
        TaskViewSubject taskViewSubject = ((SuccessoratorApplication) getApplicationContext()).getTaskViewSubject();
        taskViewSubject.observe(dateViewUpdater);
        // Create FocusSwitcherListener
        findViewById(R.id.focus_switch).setOnClickListener(this::onFocusSwitchClick);

        // Date Picker Listener
        findViewById(R.id.date_title).setOnClickListener(this::onDateTitleClick);

        // Create AddTaskListener
        findViewById(R.id.add_task).setOnClickListener(this::onAddTaskClick);


        findViewById(R.id.advanced_date).setOnClickListener(v -> {
            dateSubject.advanceDate();
        });

    }

    private void onDateTitleClick(View view) {
        ViewSwitchDialogFragment vsd = ViewSwitchDialogFragment.newInstance();
        vsd.show(getSupportFragmentManager(), "ViewSwitchDialogFragment");
        TextView dateTitle = view.findViewById(R.id.date_title);
        if (dateTitle != null) {
            String prevTxt = (String) dateTitle.getText();
            dateTitle.setText(prevTxt.substring(0, prevTxt.length() - 2) + " ▲");
        }
        if (SystemClock.elapsedRealtime() - LastDateClick < 1000) {
            return;
        }
        LastDateClick = SystemClock.elapsedRealtime();
    }

    private void onAddTaskClick(View view) {
        Views.ViewEnum currentView = ((SuccessoratorApplication) getApplicationContext()).getTaskViewSubject().getItem();
        switch (currentView) {
            case TODAY:
                CreateTaskDialogFragment ctdf = CreateTaskDialogFragment.newInstance();
                ctdf.show(getSupportFragmentManager(), "CreateTaskDialogFragment");
                break;
            case TOMORROW:
                CreateTaskDialogFragment ctdf2 = CreateTaskDialogFragment.newInstance();
                ctdf2.show(getSupportFragmentManager(), "CreateTaskDialogFragment");
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


    private void swapFragments() {
        if (this.tasksRepository.findAll().size() == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, NoTasksFragment.newInstance())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, TaskListFragment.newInstance())
                    .commit();
        }
    }

}

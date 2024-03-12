package edu.ucsd.cse110.successorator.ui.taskList.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.databinding.DailyOrTomorrowTaskDialogBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.recurring.DailyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.MonthlyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.recurring.WeeklyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.YearlyRecurring;
import edu.ucsd.cse110.successorator.util.DateSubject;

public class PendingTaskDialogFragment extends DialogFragment {

    private DailyOrTomorrowTaskDialogBinding view;
    private TaskViewModel activityModel;

    PendingTaskDialogFragment() {}

    public static PendingTaskDialogFragment newInstance() {
        var fragment = new PendingTaskDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = DailyOrTomorrowTaskDialogBinding.inflate(getLayoutInflater());

        this.radioSetup();
        return new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
                .create();
    }

    private void radioSetup() {
        view.radioOneTime.toggle();
        view.saveButton.setOnClickListener(v -> addTask(getDialog()));

        SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
        Date today = app.getDateSubject().getItem();
        assert today != null;

        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dateFormatYearly = new SimpleDateFormat("M/d", Locale.getDefault());
        SimpleDateFormat dateNumber = new SimpleDateFormat("d", Locale.getDefault());

        int dayNumber = Integer.parseInt(dateNumber.format(today));
        String dateSuffix;
        if (dayNumber >= 11 && dayNumber <= 13) {
            dateSuffix = "th";
        } else {
            switch (dayNumber % 10) {
                case 1:
                    dateSuffix = "st";
                    break;
                case 2:
                    dateSuffix = "nd";
                    break;
                case 3:
                    dateSuffix = "rd";
                    break;
                default:
                    dateSuffix = "th";
            }
        }
        view.radioWeekly.append(" " + dayOfWeek.format(today));
        view.radioMonthly.append(" " + dayNumber + dateSuffix + " " + dayOfWeek.format(today));
        view.radioYearly.append(" " + dateFormatYearly.format(today));
    }


    private void addTask(DialogInterface dialog) {
        String input = view.addTaskDialog.getText().toString();
        if (input.length() == 0) {
            dialog.dismiss();
            return;
        }
        String frequency = "";

        SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
        DateSubject dateSubject = app.getDateSubject();

        RecurringType recurringType = null;

        if (view.radioOneTime.isChecked()) {
            Log.i("OneTime Add", dialog.toString());
            frequency = " 1";
        } else if (view.radioDaily.isChecked()) {
            Log.i("Daily Add", dialog.toString());
            frequency = " 2";
            recurringType = new DailyRecurring();
        } else if (view.radioWeekly.isChecked()) {
            Log.i("Weekly Add", dialog.toString());
            frequency = " 3";
            recurringType = new WeeklyRecurring(dateSubject.getDayOfWeek());
        } else if (view.radioMonthly.isChecked()) {
            Log.i("Monthly Add", dialog.toString());
            frequency = " 4";
            recurringType = new MonthlyRecurring(dateSubject.getWeekOfMonth(), dateSubject.getDayOfWeek());
        } else if (view.radioYearly.isChecked()) {
            Log.i("Yearly Add", dialog.toString());
            recurringType = new YearlyRecurring(dateSubject.getMonth(), dateSubject.getDayOfMonth());
            frequency = " 5";
        } else {
            throw new IllegalStateException("No Selection Made");
        }

        int recurringID = activityModel.getTasksRepository().generateRecurringID();
        var task = new Task(null, input + frequency, 0, false, recurringType, recurringID, app.getTaskView());

        if (task.isRecurring() && task.getRecurringType().checkIfToday(dateSubject.getItem())) {
            activityModel.getTasksRepository().addOnetimeTask(task);
        }

        activityModel.append(task);
        dialog.dismiss();
    }

    // might need in the future
//    private void onNegativeButtonClick(DialogInterface dialog, int which) {
//        dialog.cancel();
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(TaskViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(TaskViewModel.class);
    }


}

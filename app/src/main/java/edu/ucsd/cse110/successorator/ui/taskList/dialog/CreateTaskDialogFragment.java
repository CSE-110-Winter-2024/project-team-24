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
import edu.ucsd.cse110.successorator.lib.domain.recurring.DailyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.MonthlyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.recurring.WeeklyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.YearlyRecurring;
import edu.ucsd.cse110.successorator.util.DateSubject;

public class CreateTaskDialogFragment extends DialogFragment {

    private DailyOrTomorrowTaskDialogBinding view;
    private TaskViewModel activityModel;

    CreateTaskDialogFragment() {}

    public static CreateTaskDialogFragment newInstance() {
        var fragment = new CreateTaskDialogFragment();
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
        SimpleDateFormat dayOfWeekInMonth = new SimpleDateFormat("F", Locale.getDefault());

        int dayNumber = Integer.parseInt(dateNumber.format(today));
        int dayOfWeekInMonthNumber = Integer.parseInt(dayOfWeekInMonth.format(today));
        String dateSuffix;


//        int dayweek = Integer.parseInt(dayOfWeek.format(today));
//            int ordinal = (dayNumber - 1) / 7 + 1;
//            int dayofWeekoccur = (dayOfWeek < dateNumber % 7) ? ordinal + 1 : ordinal;


        if (dayOfWeekInMonthNumber >= 11 && dayOfWeekInMonthNumber <= 13) {
            dateSuffix = "th";
        } else {
            switch (dayOfWeekInMonthNumber % 10) {
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
        view.radioMonthly.append(" " + dayOfWeekInMonthNumber + dateSuffix + " " + dayOfWeek.format(today));
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
            frequency = " 1" + recurringType;
        } else if (view.radioDaily.isChecked()) {
            Log.i("Daily Add", dialog.toString());
            recurringType = new DailyRecurring();
            frequency = " 2" + recurringType;
        } else if (view.radioWeekly.isChecked()) {
            Log.i("Weekly Add", dialog.toString());
            recurringType = new WeeklyRecurring(dateSubject.getDayOfWeek());
            frequency = " 3";
        } else if (view.radioMonthly.isChecked()) {
            Log.i("Monthly Add", dialog.toString());
            recurringType = new MonthlyRecurring(dateSubject.getWeekOfMonth(), dateSubject.getDayOfWeek());
            frequency = " 4";
        } else if (view.radioYearly.isChecked()) {
            Log.i("Yearly Add", dialog.toString());
            recurringType = new YearlyRecurring(dateSubject.getMonth(), dateSubject.getDayOfMonth());
            frequency = " 5" + recurringType;
        } else {
            throw new IllegalStateException("No Selection Made");
        }

        int recurringID = activityModel.getTasksRepository().generateRecurringID();
        var task = new Task(null, input + frequency, 0, false, recurringType, recurringID, app.getTaskView().getItem());

        if (task.isRecurring() && task.getRecurringType().checkIfToday(dateSubject.getItem())) {
            activityModel.getTasksRepository().addOnetimeTask(task);
        }

        activityModel.append(task);
        dialog.dismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(TaskViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(TaskViewModel.class);
    }


}


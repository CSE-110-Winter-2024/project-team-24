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
import edu.ucsd.cse110.successorator.lib.domain.Contexts;
import edu.ucsd.cse110.successorator.lib.domain.TaskBuilder;
import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.lib.domain.recurring.DailyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.MonthlyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.recurring.WeeklyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.YearlyRecurring;
import edu.ucsd.cse110.successorator.util.DateSubject;
import edu.ucsd.cse110.successorator.util.TaskViewSubject;

public class CreateTaskDialogFragment extends DialogFragment {

    private DailyOrTomorrowTaskDialogBinding view;
    private TaskViewModel activityModel;

    CreateTaskDialogFragment() {
    }

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
        view.saveButton.setOnClickListener(v -> addTask(getDialog()));

        SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
        Date today = app.getDateSubject().getItem();
        assert today != null;

        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dateFormatYearly = new SimpleDateFormat("M/d", Locale.getDefault());
        SimpleDateFormat dayOfWeekInMonth = new SimpleDateFormat("F", Locale.getDefault());
        TaskViewSubject taskViewSubject = app.getTaskViewSubject();
        Views.ViewEnum Ivalue = taskViewSubject.getItem();
        Date date = today;
        if (Ivalue != null) {
            if (Ivalue == Views.ViewEnum.TOMORROW) {
                date = new Date(today.getTime() + 24 * 60 * 60 * 1000);
            }

        }

        int dayOfWeekInMonthNumber = Integer.parseInt(dayOfWeekInMonth.format(date));
        String dateSuffix;

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
        view.radioWeekly.append(" " + dayOfWeek.format(date));
        view.radioMonthly.append(" " + dayOfWeekInMonthNumber + dateSuffix + " " + dayOfWeek.format(date));
        view.radioYearly.append(" " + dateFormatYearly.format(date));
    }


    private void addTask(DialogInterface dialog) {
        String input = view.addTaskDialog.getText().toString();
        if (input.length() == 0) {
            dialog.dismiss();
            return;
        }

        SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
        DateSubject dateSubject = app.getDateSubject();

        RecurringType recurringType = null;

        if (view.radioOneTime.isChecked()) {
            Log.i("OneTime Add", dialog.toString());
        } else if (view.radioDaily.isChecked()) {
            Log.i("Daily Add", dialog.toString());
            recurringType = new DailyRecurring();
        } else if (view.radioWeekly.isChecked()) {
            Log.i("Weekly Add", dialog.toString());
            recurringType = new WeeklyRecurring(dateSubject.getDayOfWeek());
        } else if (view.radioMonthly.isChecked()) {
            Log.i("Monthly Add", dialog.toString());
            recurringType = new MonthlyRecurring(dateSubject.getWeekOfMonth(), dateSubject.getDayOfWeek());
        } else if (view.radioYearly.isChecked()) {
            Log.i("Yearly Add", dialog.toString());
            recurringType = new YearlyRecurring(dateSubject.getMonth(), dateSubject.getDayOfMonth());
        } else {
            throw new IllegalStateException("No Selection Made");
        }

        Contexts.Context context;
        if (view.homeContext.isChecked()) {
            context = Contexts.Context.HOME;
        } else if (view.workContext.isChecked()) {
            context = Contexts.Context.WORK;
        } else if (view.schoolContext.isChecked()) {
            context = Contexts.Context.SCHOOL;
        } else if (view.errandsContext.isChecked()) {
            context = Contexts.Context.ERRANDS;
        } else {
            throw new IllegalStateException("No Selection Made");
        }

        int recurringID = activityModel.getTasksRepository().generateRecurringID();

        var task = new TaskBuilder()
                .withId(null)
                .withTaskName(input)
                .withSortOrder(0)
                .withCheckedOff(false)
                .withRecurringType(recurringType)
                .withRecurringId(recurringID)
                .withView(app.getTaskViewSubject().getItem())
                .withContext(context)
                .withStartDate(dateSubject.getItem())
                .build();

        if (recurringType != null) {
            task = task.withView(Views.ViewEnum.RECURRING);
        }

        if (task.isRecurring() && task.getRecurringType().checkIfToday(dateSubject.getItem(), task.getStartDate())) {
            activityModel.getTasksRepository().addOnetimeTask(task.withView(Views.ViewEnum.TODAY));
        }

        if (task.isRecurring() && task.getRecurringType().checkIfTomorrow(dateSubject.getItem(), task.getStartDate())) {
            activityModel.getTasksRepository().addOnetimeTask(task.withView(Views.ViewEnum.TOMORROW));
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


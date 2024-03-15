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
import java.util.Calendar;

import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.databinding.RecurringTaskDialogBinding;
import edu.ucsd.cse110.successorator.lib.domain.Contexts;
import edu.ucsd.cse110.successorator.lib.domain.TaskBuilder;
import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.lib.domain.recurring.DailyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.MonthlyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.recurring.WeeklyRecurring;
import edu.ucsd.cse110.successorator.lib.domain.recurring.YearlyRecurring;
import edu.ucsd.cse110.successorator.util.DateSubject;

public class RecurringTaskDialogFragment extends DialogFragment {

    private RecurringTaskDialogBinding view;
    private TaskViewModel activityModel;
    private Calendar calendar;

    RecurringTaskDialogFragment() {
    }

    public static RecurringTaskDialogFragment newInstance() {
        var fragment = new RecurringTaskDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        view = RecurringTaskDialogBinding.inflate(getLayoutInflater());

        setCalendar(Calendar.getInstance());
        // Format the date

        view.radioDaily.toggle();
        view.saveButton.setOnClickListener(v -> addTask(getDialog()));
        view.calendarDialogOpener.setOnClickListener(v -> {
            DatePickerFragment datePickerFragment = new DatePickerFragment(this::setCalendar);
            datePickerFragment.show(getParentFragmentManager(), "datePicker");
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
                .create();
    }

    private void setCalendar(Calendar calendar) {
        this.calendar = calendar;

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM, dd, yyyy");
        String formattedDate = sdf.format(calendar.getTime());

        view.calendarDialogOpener.setText(formattedDate);

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

        if (view.radioDaily.isChecked()) {
            Log.i("Daily Add", dialog.toString());
            recurringType = new DailyRecurring();
        } else if (view.radioWeekly.isChecked()) {
            Log.i("Weekly Add", dialog.toString());
            recurringType = new WeeklyRecurring(calendar.get(Calendar.DAY_OF_WEEK));
        } else if (view.radioMonthly.isChecked()) {
            Log.i("Monthly Add", dialog.toString());
            recurringType = new MonthlyRecurring(calendar.get(Calendar.WEEK_OF_MONTH), calendar.get(Calendar.DAY_OF_WEEK));
        } else if (view.radioYearly.isChecked()) {
            Log.i("Yearly Add", dialog.toString());
            recurringType = new YearlyRecurring(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
                .build();


        if (task.isRecurring() && task.getRecurringType().checkIfToday(dateSubject.getItem())) {
            activityModel.getTasksRepository().addOnetimeTask(task.withView(Views.ViewEnum.TODAY));
        }

        if (task.isRecurring() && task.getRecurringType().checkIfTomorrow(dateSubject.getItem())) {
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


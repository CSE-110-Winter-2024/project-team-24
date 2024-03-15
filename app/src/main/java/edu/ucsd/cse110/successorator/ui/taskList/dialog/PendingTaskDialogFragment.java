package edu.ucsd.cse110.successorator.ui.taskList.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.databinding.PendingTaskDialogBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskBuilder;
import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.util.DateSubject;

public class PendingTaskDialogFragment extends DialogFragment {

    private PendingTaskDialogBinding view;
    private TaskViewModel activityModel;

    PendingTaskDialogFragment() {
    }

    public static PendingTaskDialogFragment newInstance() {
        var fragment = new PendingTaskDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = PendingTaskDialogBinding.inflate(getLayoutInflater());

        this.radioSetup();
        return new AlertDialog.Builder(getActivity()).setView(view.getRoot()).create();
    }

    private void radioSetup() {
        view.saveButton.setOnClickListener(v -> addTask(getDialog()));
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

        Task.Context context;
        if (view.homeContext.isChecked()) {
            context = Task.Context.HOME;
        } else if (view.workContext.isChecked()) {
            context = Task.Context.WORK;
        } else if (view.schoolContext.isChecked()) {
            context = Task.Context.SCHOOL;
        } else if (view.errandsContext.isChecked()) {
            context = Task.Context.ERRANDS;
        } else {
            throw new IllegalStateException("No Selection Made");
        }

        int recurringID = activityModel.getTasksRepository().generateRecurringID();
        var task = new TaskBuilder()
                .withId(null)
                .withTaskName(input + frequency)
                .withSortOrder(0)
                .withCheckedOff(false)
                .withRecurringType(recurringType)
                .withRecurringId(recurringID)
                .withView(app.getTaskViewSubject().getItem())
                .withContext(context)
                .build();

        if (task.isRecurring() && task.getRecurringType().checkIfToday(dateSubject.getItem())) {
            activityModel.getTasksRepository().prepend(task);
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


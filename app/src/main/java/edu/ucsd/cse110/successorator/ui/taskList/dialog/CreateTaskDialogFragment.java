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
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateTaskBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;

import android.util.Log;
import java.util.Date;

public class CreateTaskDialogFragment extends DialogFragment {

    private FragmentDialogCreateTaskBinding view;
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
        this.view = FragmentDialogCreateTaskBinding.inflate(getLayoutInflater());
        SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
        Date today = app.getDateSubject().getDate();

        return new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
//                .setTitle("New Task")
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
//                .setMessage("Please provide the new task.")
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        String input = view.addTaskDialog.getText().toString();
        if (input.length() == 0) {
            dialog.dismiss();
            return;
        }

        if (view.radioOneTime.isChecked()) {
            Log.i("OneTime Add", dialog.toString());
        } else if (view.radioDaily.isChecked()) {
            Log.i("Daily Add", dialog.toString());
        } else if (view.radioWeekly.isChecked()) {
            Log.i("Weekly Add", dialog.toString());
        } else if (view.radioMonthly.isChecked()) {
            Log.i("Monthly Add", dialog.toString());
        } else if (view.radioYearly.isChecked()) {
            Log.i("Yearly Add", dialog.toString());
        } else {
            throw new IllegalStateException("No Selection Made");
        }

        var task = new Task(null, input, 0, false);
        activityModel.append(task);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
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


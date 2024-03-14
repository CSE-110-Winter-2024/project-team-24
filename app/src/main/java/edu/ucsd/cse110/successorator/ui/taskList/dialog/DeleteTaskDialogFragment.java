package edu.ucsd.cse110.successorator.ui.taskList.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.DailyOrTomorrowTaskDialogBinding;
import edu.ucsd.cse110.successorator.databinding.RecurringDeleteDialogBinding;

public class DeleteTaskDialogFragment extends DialogFragment {

    RecurringDeleteDialogBinding view;

    private Integer taskId;

    public DeleteTaskDialogFragment(int taskId) {
        // Required empty public constructor
        this.taskId = taskId;
    }

    public static DeleteTaskDialogFragment newInstance(int taskId) {
        var fragment = new DeleteTaskDialogFragment(taskId);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = RecurringDeleteDialogBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
                .create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button deleteTaskButton = view.findViewById(R.id.task_delete);
        deleteTaskButton.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // get task id
                    // call room repo to delete with id


                    // Implement task deletion logic here
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    // Dialog will be dismissed without doing anything
                })
                .show();
    }
}

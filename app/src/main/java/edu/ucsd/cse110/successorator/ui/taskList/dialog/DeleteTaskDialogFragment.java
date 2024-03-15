package edu.ucsd.cse110.successorator.ui.taskList.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.databinding.RecurringDeleteDialogBinding;

public class DeleteTaskDialogFragment extends DialogFragment {

    private final Integer taskId;
    RecurringDeleteDialogBinding view;

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
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = RecurringDeleteDialogBinding.inflate(getLayoutInflater());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
                // Set up the positive button and its click listener to delete the task
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Move the delete task logic here
                    ((SuccessoratorApplication) requireContext().getApplicationContext()).getTasksRepository().remove(taskId);
                    Toast.makeText(getContext(), "Task " + taskId + " deleted", Toast.LENGTH_LONG).show();
                })
                // Set up a negative button for canceling the action
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    // Dismiss the dialog without doing anything
                    dismiss();
                });

        return builder.create();
    }
}

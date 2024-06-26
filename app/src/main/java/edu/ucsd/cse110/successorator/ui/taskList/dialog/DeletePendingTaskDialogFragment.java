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
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.Views;

public class DeletePendingTaskDialogFragment extends DialogFragment {

    private final Integer taskId;
    RecurringDeleteDialogBinding view;

    public DeletePendingTaskDialogFragment(int taskId) {
        // Required empty public constructor
        this.taskId = taskId;
    }

    public static DeletePendingTaskDialogFragment newInstance(int taskId) {
        var fragment = new DeletePendingTaskDialogFragment(taskId);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = RecurringDeleteDialogBinding.inflate(getLayoutInflater());

//        view.getRoot().findViewById()
        SuccessoratorApplication app = (SuccessoratorApplication) requireContext().getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
                // Set up the positive button and its click listener to delete the task
                .setPositiveButton("Delete Task", (dialog, which) -> {
                    // Move the delete task logic here
                    app.getTasksRepository().remove(taskId);
                    Toast.makeText(getContext(), "Task " + taskId + " deleted", Toast.LENGTH_LONG).show();
                })
                // Set up a negative button for canceling the action
                .setNegativeButton("Move Task to Tomorrow", (dialog, which) -> {
                    // Dismiss the dialog without doing anything
                    Task currTask = app.getTasksRepository().find(taskId);

                    assert currTask != null;
                    Task newTask = currTask.withView(Views.ViewEnum.TOMORROW);

                    app.getTasksRepository().remove(taskId);
                    app.getTasksRepository().append(newTask);
                    Toast.makeText(getContext(), "Task " + taskId + " moved to tomorrow!", Toast.LENGTH_LONG).show();

                    dismiss();
                });

        return builder.create();
    }
}

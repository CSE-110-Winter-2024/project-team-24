package edu.ucsd.cse110.successorator.ui.taskList.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateTaskBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;


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
        // Create the radio button list items
        String[] items = {"One-Time", "Daily", "Weekly on Tu", "Monthly 3rd Tu", "Yearly on 2/20"};
        // Set the default checked item (index of items array, in this case 'one-time' is checked by default)
        int checkedItem = 0;

        return new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
                .setTitle("New Task")
                // Replace 'setPositiveButton' with 'setSingleChoiceItems' for radio buttons
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the radio button selection
                    }
                })
                // Change button text to "Save" and handle click
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle the Save button click
                        int selectedPosition = ((AlertDialog)dialogInterface).getListView().getCheckedItemPosition();
                        // Do something with the position
                    }
                })
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .setMessage("Please provide the new task.")
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        String input = view.addTaskDialog.getText().toString();
        if (input.length() == 0) {
            dialog.dismiss();
            return;
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


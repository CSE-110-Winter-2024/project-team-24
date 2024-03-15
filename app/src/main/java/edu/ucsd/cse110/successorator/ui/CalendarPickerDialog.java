package edu.ucsd.cse110.successorator.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.databinding.CalendarPickerDialogBinding;

public class CalendarPickerDialog extends DialogFragment {

    private @NonNull CalendarPickerDialogBinding view;
    private TaskViewModel activityModel;

    public CalendarPickerDialog() {
    }

    public static CalendarPickerDialog newInstance() {
        var fragment = new CalendarPickerDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = CalendarPickerDialogBinding.inflate(getLayoutInflater());

        CalendarView cv = view.calendarView;
        cv.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Note: Month is 0-based, January is 0
            // Do something with the selected date
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth; // Adjusting month for 1-based month
            // For example, show the date in a Toast
            Toast.makeText(view.getContext(), "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view.getRoot())
                .setPositiveButton("OK", (dialog, id) -> {
                    // User clicked OK, so save the selectedDate result somewhere
                    // or return them to the component that opened the dialog
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    // User cancelled the dialog
                    dialog.cancel();
                })
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider.Factory modelFactory = new ViewModelProvider.NewInstanceFactory();
        ViewModelProvider modelProvider = new ViewModelProvider(requireActivity(), modelFactory);
        this.activityModel = modelProvider.get(TaskViewModel.class);
    }
}

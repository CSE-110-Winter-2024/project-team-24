package edu.ucsd.cse110.successorator.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.util.TaskViewSubject;

public class ViewSwitchDialogFragment extends DialogFragment {

    private TaskViewModel activityModel;

    public static ViewSwitchDialogFragment newInstance() {
        return new ViewSwitchDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_switch_dialog, container, false);

        // Set up the buttons with click listeners
        Button todayView = view.findViewById(R.id.today_view);
        Button tomorrowView = view.findViewById(R.id.tomorrow_view);
        Button pendingView = view.findViewById(R.id.pending_view);
        Button recurringView = view.findViewById(R.id.recurring_view);

        todayView.setOnClickListener(v -> {

//            TextView dateTitle = view.getRootView().findViewById(R.id.date_title);
//            Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
//            TextView dateTitle = v.findViewById(R.id.date_title);
            TextView dateTitle = view.getRootView().findViewById(R.id.date_title);
            if(dateTitle == null) {
                Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
            }

            if (dateTitle != null) {
                String prevTxt = (String) dateTitle.getText();
//                dateTitle.setText(prevTxt.substring(0, prevTxt.length()-2) + " ▼");
                Toast.makeText(getContext(), prevTxt, Toast.LENGTH_SHORT).show();
            }

            dismiss();

            TaskViewSubject taskViewSubject = ((SuccessoratorApplication) requireActivity().getApplication()).getTaskView();
            taskViewSubject.setItem(Task.IView.TODAY);
            // Handle "Today" view click
        });

        tomorrowView.setOnClickListener(v -> {
            // Handle "Tomorrow" view click
            TextView dateTitle = view.getRootView().findViewById(R.id.date_title);
            if (dateTitle != null) {
                String prevTxt = (String) dateTitle.getText();
                dateTitle.setText(prevTxt.substring(0, prevTxt.length()-2) + " ▼");
            }
            if (dateTitle != null) {
                String prevTxt = (String) dateTitle.getText();
//                dateTitle.setText(prevTxt.substring(0, prevTxt.length()-2) + " ▼");
                Toast.makeText(getContext(), prevTxt, Toast.LENGTH_SHORT).show();
            }

            dismiss();

            TaskViewSubject taskViewSubject = ((SuccessoratorApplication) requireActivity().getApplication()).getTaskView();
            taskViewSubject.setItem(Task.IView.TOMORROW);
        });

        pendingView.setOnClickListener(v -> {
            // Handle "Pending" view click
            TextView dateTitle = view.getRootView().findViewById(R.id.date_title);
            if (dateTitle != null) {
                String prevTxt = (String) dateTitle.getText();
                dateTitle.setText(prevTxt.substring(0, prevTxt.length()-2) + " ▼");
            }
            if (dateTitle != null) {
                String prevTxt = (String) dateTitle.getText();
//                dateTitle.setText(prevTxt.substring(0, prevTxt.length()-2) + " ▼");
                Toast.makeText(getContext(), prevTxt, Toast.LENGTH_SHORT).show();
            }

            dismiss();

            TaskViewSubject taskViewSubject = ((SuccessoratorApplication) requireActivity().getApplication()).getTaskView();
            taskViewSubject.setItem(Task.IView.PENDING);
        });

        recurringView.setOnClickListener(v -> {
            // Handle "Recurring" view click
            TextView dateTitle = view.getRootView().findViewById(R.id.date_title);
            if (dateTitle != null) {
                String prevTxt = (String) dateTitle.getText();
                dateTitle.setText(prevTxt.substring(0, prevTxt.length()-2) + " ▼");
            }
            if (dateTitle != null) {
                String prevTxt = (String) dateTitle.getText();
//                dateTitle.setText(prevTxt.substring(0, prevTxt.length()-2) + " ▼");
                Toast.makeText(getContext(), prevTxt, Toast.LENGTH_SHORT).show();
            }

            dismiss();

            TaskViewSubject taskViewSubject = ((SuccessoratorApplication) requireActivity().getApplication()).getTaskView();
            taskViewSubject.setItem(Task.IView.RECURRING);
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // Set a title or any other configurations for the dialog
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(TaskViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(TaskViewModel.class);
        return dialog;
    }
}

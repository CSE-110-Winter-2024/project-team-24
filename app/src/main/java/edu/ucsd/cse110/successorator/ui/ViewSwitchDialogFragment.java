package edu.ucsd.cse110.successorator.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
            var itr = this.activityModel.getTasksRepository();
            var filteredCards = itr.filterByView(Task.IView.TODAY);
            for (Task i : filteredCards) {
                System.out.println(i.toString());
            }
            this.activityModel.filterByView(filteredCards);
            // Handle "Today" view click
        });

        tomorrowView.setOnClickListener(v -> {
            // Handle "Tomorrow" view click
            ITasksRepository tasksRepository = ((SuccessoratorApplication) requireActivity().getApplication()).getTasksRepository();
            tasksRepository.filterByView(Task.IView.TOMORROW);
            dismiss();

        });

        pendingView.setOnClickListener(v -> {
            // Handle "Pending" view click
            ITasksRepository tasksRepository = ((SuccessoratorApplication) requireActivity().getApplication()).getTasksRepository();
            tasksRepository.filterByView(Task.IView.PENDING);
            dismiss();
        });

        recurringView.setOnClickListener(v -> {
            // Handle "Recurring" view click
            ITasksRepository tasksRepository = ((SuccessoratorApplication) requireActivity().getApplication()).getTasksRepository();
            tasksRepository.filterByView(Task.IView.RECURRING);
            dismiss();
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

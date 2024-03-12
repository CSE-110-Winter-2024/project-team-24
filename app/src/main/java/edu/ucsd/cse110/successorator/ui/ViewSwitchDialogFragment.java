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

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.lib.domain.ITasksRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class ViewSwitchDialogFragment extends DialogFragment {

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

            //call the command filter by view
//            Task.IView viewT = Task.IView.TODAY;
//            var newOrderedCards = cards.stream().filter(e -> e.getView().equals(view)).4

            ITasksRepository tasksRepository = ((SuccessoratorApplication) requireActivity().getApplication()).getTasksRepository();
            var newOrderedCards = tasksRepository.filterByView(Task.IView.TODAY);
                    // rest of code

            dismiss();

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
        dialog.setTitle("View Switch");
        return dialog;
    }
}

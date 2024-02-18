package edu.ucsd.cse110.successorator.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentTaskListBinding;
import edu.ucsd.cse110.successorator.lib.util.DateSubject;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;


public class TaskListFragment extends Fragment {
    private TaskViewModel activityModel;
    private FragmentTaskListBinding view;
    private TaskListAdapter adapter;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(TaskViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(TaskViewModel.class);

        // Initialize the Adapter (with an empty list for now)
        this.adapter = new TaskListAdapter(requireContext(), List.of(), activityModel::toggleTaskStrikethrough);
        activityModel.getOrderedTasks().observe(tasks -> {
            if (tasks == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(tasks)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentTaskListBinding.inflate(inflater, container, false);

        // Set the adapter on the ListView
        view.cardList.setAdapter(adapter);
        view.button2.setOnClickListener(v -> {
            SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
            DateSubject dateSubject = app.getDateSubject();
            dateSubject.advanceDate();
            activityModel.dateAdvanced();
        });

        return view.getRoot();
    }
}

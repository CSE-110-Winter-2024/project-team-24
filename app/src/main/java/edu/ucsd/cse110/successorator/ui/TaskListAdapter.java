package edu.ucsd.cse110.successorator.ui;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.ListItemTaskBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.Views;
import edu.ucsd.cse110.successorator.util.TaskViewSubject;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private final Consumer<Task> consumer;
    private final TaskViewSubject view;

    public TaskListAdapter(Context context, List<Task> tasks, Consumer<Task> consumer, TaskViewSubject view) {
        super(context, 0, new ArrayList<>(tasks));
        this.consumer = consumer;
        this.view = view;
    }

    private static void updateTextView(TextView textView, Task task) {
        if (task.getCheckOff()) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        var task = getItem(position);
        assert task != null;

        ListItemTaskBinding binding;

        if (convertView != null) {
            binding = ListItemTaskBinding.bind(convertView);
        } else {
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemTaskBinding.inflate(layoutInflater, parent, false);
        }

        binding.taskText.setText(task.getTaskName());

        switch (task.getContext()) {
            case HOME:
                binding.taskContext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.home_background));
                binding.taskContext.setText("H");
                break;
            case WORK:
                binding.taskContext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.work_background));
                binding.taskContext.setText("W");
                break;
            case SCHOOL:
                binding.taskContext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.school_background));
                binding.taskContext.setText("S");
                break;
            case ERRANDS:
                binding.taskContext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.errands_background));
                binding.taskContext.setText("E");
                break;
        }
        updateTextView(binding.taskText, task);

        binding.taskText.setOnClickListener(v -> {
            if (view.getItem() == Views.ViewEnum.TODAY || view.getItem() == Views.ViewEnum.TOMORROW) {
                consumer.accept(task);
            }
        });

        return binding.getRoot();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var task = getItem(position);
        assert task != null;
        return task.id();
    }
}

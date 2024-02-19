package edu.ucsd.cse110.successorator.ui;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.databinding.ListItemTaskBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private final Consumer<Task> consumer;

    public TaskListAdapter(Context context, List<Task> tasks, Consumer<Task> consumer) {
        super(context, 0, new ArrayList<>(tasks));
        this.consumer = consumer;
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

        binding.taskText.setText(task.getTask());
        updateTextView(binding.taskText, task);

        binding.taskText.setOnClickListener(v -> {
//            ((SuccessoratorApplication) getContext().getApplicationContext()).getDateSubject().setDate(new Date());
            consumer.accept(task);
        });

        return binding.getRoot();
    }

    private static void updateTextView(TextView textView, Task task) {
        if (task.getCheckOff()) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
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

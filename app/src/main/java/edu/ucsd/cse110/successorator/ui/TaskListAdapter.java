package edu.ucsd.cse110.successorator.ui;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.databinding.ListItemTaskBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.DeleteTaskDialogFragment;
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
        updateTextView(binding.taskText, task);

        binding.taskText.setOnClickListener(v -> {
            if (view.getItem() == Task.IView.TODAY || view.getItem() == Task.IView.TOMORROW) {
                consumer.accept(task);
            }
        });

        binding.taskText.setOnLongClickListener(v -> {
            switch (view.getItem()) {
                case RECURRING:
                    FragmentActivity activity = (FragmentActivity) getContext();
                    DeleteTaskDialogFragment dialogFragment = DeleteTaskDialogFragment.newInstance();
                    dialogFragment.show(activity.getSupportFragmentManager(), "deleteDialog");
                    break;
                case PENDING:

                    break;
                case TOMORROW:
                case TODAY:
                    break;
            }

            return true;
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

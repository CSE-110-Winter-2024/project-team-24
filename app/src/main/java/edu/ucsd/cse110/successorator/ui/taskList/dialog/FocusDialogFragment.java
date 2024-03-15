package edu.ucsd.cse110.successorator.ui.taskList.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.databinding.FocusModeDialogBinding;
import edu.ucsd.cse110.successorator.databinding.PendingTaskDialogBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.util.DateSubject;

public class FocusDialogFragment extends DialogFragment {

    private FocusModeDialogBinding view;
    private TaskViewModel activityModel;

    FocusDialogFragment() {
    }

    public static FocusDialogFragment newInstance() {
        var fragment = new FocusDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FocusModeDialogBinding.inflate(getLayoutInflater());

        this.radioSetup();
        return new AlertDialog.Builder(getActivity()).setView(view.getRoot()).create();
    }

    private void radioSetup() {
        view.saveButton.setOnClickListener(v -> switchFocus(getDialog()));
    }


    private void switchFocus(DialogInterface dialog) {


        SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
//        DateSubject dateSubject = app.getDateSubject();


        Task.Context context;
        if (view.homeContext.isChecked()) {
            context = Task.Context.HOME;
        } else if (view.workContext.isChecked()) {
            context = Task.Context.WORK;
        } else if (view.schoolContext.isChecked()) {
            context = Task.Context.SCHOOL;
        } else if (view.errandsContext.isChecked()) {
            context = Task.Context.ERRANDS;
        } else {
            throw new IllegalStateException("No Selection Made");
        }

        app.getFocusModeSubject().setItem(context);


        dialog.dismiss();
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


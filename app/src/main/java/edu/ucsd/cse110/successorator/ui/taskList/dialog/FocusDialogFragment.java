package edu.ucsd.cse110.successorator.ui.taskList.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.SuccessoratorApplication;
import edu.ucsd.cse110.successorator.TaskViewModel;
import edu.ucsd.cse110.successorator.databinding.FocusModeDialogBinding;
import edu.ucsd.cse110.successorator.databinding.PendingTaskDialogBinding;
import edu.ucsd.cse110.successorator.lib.domain.Contexts;
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
        view.exitFocus.setOnClickListener(v -> exitFocus(getDialog()));
    }


    private void switchFocus(DialogInterface dialog) {


        SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
//        DateSubject dateSubject = app.getDateSubject();


        Contexts.Context context;
        var color = R.color.white;
        if (view.homeContext.isChecked()) {
            context = Contexts.Context.HOME;
            color = R.color.yellow;
        } else if (view.workContext.isChecked()) {
            context = Contexts.Context.WORK;
            color = R.color.blue;
        } else if (view.schoolContext.isChecked()) {
            context = Contexts.Context.SCHOOL;
            color = R.color.purple;
        } else if (view.errandsContext.isChecked()) {
            context = Contexts.Context.ERRANDS;
            color = R.color.green;
        } else {
            throw new IllegalStateException("No Selection Made");
        }

        app.getFocusModeSubject().setItem(context);
        MaterialButton focusSwitch = requireActivity().findViewById(R.id.focus_switch);
        if (focusSwitch != null) {
            focusSwitch.setIconTint(ContextCompat.getColorStateList(getContext(), color));
        }

        dialog.dismiss();
    }

    public void exitFocus(DialogInterface dialog){
        SuccessoratorApplication app = (SuccessoratorApplication) requireActivity().getApplication();
        app.getFocusModeSubject().setItem(null);
        MaterialButton focusSwitch = requireActivity().findViewById(R.id.focus_switch);
        if (focusSwitch != null) {
            focusSwitch.setIconTint(ContextCompat.getColorStateList(getContext(), R.color.white));
        }
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


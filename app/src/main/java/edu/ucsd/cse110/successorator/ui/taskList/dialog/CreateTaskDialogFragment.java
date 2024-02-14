package edu.ucsd.cse110.successorator.ui.taskList.dialog;

import androidx.fragment.app.DialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.*;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateTaskBinding;


public class CreateTaskDialogFragment extends DialogFragment {

        private FragmentDialogCreateTaskBinding view;
        private MainViewModel activityModel;
        CreateTaskDialogFragment() {}

        public static CreateTaskDialogFragment newInstance() {
            var fragment = new CreateTaskDialogFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            this.view = FragmentDialogCreateTaskBinding.inflate(getLayoutInflater());

            return new AlertDialog.Builder(getActivity())
                    .setTitle("New Task")
                    .setMessage("Please provide the new task.")
                    .setView(view.getRoot())
                    .setPositiveButton("Create", this::onPositiveButtonClick)
                    .setNegativeButton("Cancel", this::onNegativeButtonClick)
                    .create();
        }

        private void onPositiveButtonClick(DialogInterface dialog, int which) {
//            var front = view.cardFrontEditText.getText().toString();
//            var back = view.cardBackEditText.getText().toString();
//
//            var card = new Flashcard(null, front, back, -1);
//
//            if (view.appendRadioBtn.isChecked()) {
//                activityModel.append(card);
//            }
//            else if (view.prependRadioBtn.isChecked()) {
//                activityModel.prepend(card);
//            } else {
//                throw new IllegalStateException("No radio button is checked.");
//            }
            dialog.dismiss();
        }

        private void onNegativeButtonClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            var modelOwner = requireActivity();
            var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
            var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
            this.activityModel = modelProvider.get(MainViewModel.class);
        }


}


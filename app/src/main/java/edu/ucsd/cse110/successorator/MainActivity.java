package edu.ucsd.cse110.successorator;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.databinding.FragmentTaskListBinding;
import edu.ucsd.cse110.successorator.ui.TaskListFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);

        setContentView(view.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

}

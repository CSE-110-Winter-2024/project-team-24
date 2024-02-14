package edu.ucsd.cse110.successorator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.TaskListFragment;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.CreateTaskDialogFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;

//    private boolean isShowingStudy = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);

        setContentView(view.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.action_bar_menu_swap_views) {
            var dialogFragment = CreateTaskDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "CreateTaskDialogFragment");
        }

        return super.onOptionsItemSelected(item);
    }


//    private void swapFragments() {
//        if (isShowingStudy) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, TaskListFragment.newInstance())
//                    .commit();
//        } else {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, StudyFragment.newInstance())
//                    .commit();
//        }
//        isShowingStudy = !isShowingStudy;
//    }



}

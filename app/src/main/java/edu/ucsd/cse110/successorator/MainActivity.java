package edu.ucsd.cse110.successorator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.taskList.dialog.CreateTaskDialogFragment;
import edu.ucsd.cse110.successorator.util.DateSubject;
import edu.ucsd.cse110.successorator.ui.ActionBarUpdater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edu.ucsd.cse110.successorator.databinding.ActivityMainBinding view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);

        setContentView(view.getRoot());

        // Instantiate the actionBarUpdater
        ActionBarUpdater actionBarUpdater = new ActionBarUpdater(this);

        // Get DateSubject observable from Application, then add ActionBarUpdater as observer
        DateSubject dateSubject = ((SuccessoratorApplication) getApplicationContext()).getDateSubject();
        dateSubject.observe(actionBarUpdater);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set to current day, notifies ActionBarUpdater
        DateSubject dateSubject = ((SuccessoratorApplication) getApplicationContext()).getDateSubject();
        dateSubject.setDate(new Date());
        dateSubject.loadDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();

        if (itemId == R.id.add_task) {
            var dialogFragment = CreateTaskDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "CreateTaskDialogFragment");
        }

        return super.onOptionsItemSelected(item);
    }
}

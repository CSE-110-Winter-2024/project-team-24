package edu.ucsd.cse110.successorator;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.successorator.MainActivity; // Adjust this to your actual MainActivity
import edu.ucsd.cse110.successorator.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotEquals;

import android.widget.TextView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RunWith(AndroidJUnit4.class)
public class ActionBarUpdaterTest {
    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }
    @Test
    public void testDateTitleUpdatesForToday() {
        /*
        * Checks if the date has actually advanced.
        * */
        final LocalDate date = LocalDate.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, M/dd");
        String formattedDate = "Today, " + date.format(formatter) + " ▼";
        System.out.println(formattedDate);
        onView(withId(R.id.advanced_date)).perform(click());
        onView(withText(formattedDate)).check(doesNotExist());
    }

    @Test
    public void testDateTitleUpdatesForSpecificDate() {
        // Assuming you have a way to set a specific date for testing
        String expectedTitle = "Mar 10, 2022"; // Use the expected formatted string
        // You might need to interact with the UI to change the date or mock the date change in your test setup

        onView(withId(R.id.date_title))
                .check(matches(withText(expectedTitle)));
    }
}

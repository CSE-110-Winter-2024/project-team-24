package edu.ucsd.cse110.successorator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void addTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Test Goal"), ViewActions.closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withText("Test Goal 1")).check(matches(isDisplayed()));
    }


    @Test
    public void addOneTimeTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.radio_one_time)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("One Time Goal"), ViewActions.closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withText("One Time Goal 1")).check(matches(isDisplayed()));

    }
    @Test
    public void addDailyTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.radio_daily)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Daily Goal"), ViewActions.closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withText("Daily Goal 2")).check(matches(isDisplayed()));
    }

    @Test
    public void addWeeklyTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.radio_weekly)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Weekly Goal"), ViewActions.closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withText("Weekly Goal 3")).check(matches(isDisplayed()));
    }
    @Test
    public void addMonthlyTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.radio_monthly)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Monthly Goal"), ViewActions.closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withText("Monthly Goal 4")).check(matches(isDisplayed()));
    }
    @Test
    public void addYearlyTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.radio_yearly)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Yearly Goal"), ViewActions.closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withText("Yearly Goal 5")).check(matches(isDisplayed()));
    }


    @Test
    public void addEmptyTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText(""), ViewActions.closeSoftKeyboard());
        onView(withText("Save")).perform(click());
        onView(withId(R.id.add_task_dialog)).check(doesNotExist());
    }


}
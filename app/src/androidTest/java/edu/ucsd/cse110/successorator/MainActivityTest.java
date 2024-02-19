package edu.ucsd.cse110.successorator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertNull;

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
        onView(withText("Create")).perform(click());
        onView(withText("Test Goal")).check(matches(isDisplayed()));
    }

    @Test
    public void addEmptyTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText(""), ViewActions.closeSoftKeyboard());
        onView(withText("Create")).perform(click());
        onView(withId(R.id.add_task_dialog)).check(doesNotExist());
    }
}
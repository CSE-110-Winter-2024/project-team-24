package edu.ucsd.cse110.successorator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;

import org.junit.Before;
import org.junit.Test;

public class PendingRecurringTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void addPendingTask() {
        onView(withId(R.id.date_title)).perform(click());
        onView(withId(R.id.pending_view)).perform(click());
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Test Goal"), ViewActions.closeSoftKeyboard());
        onView(withText("H")).perform(click());
        onView(withText("Save")).perform(click());
        onView(withText("Test Goal")).check(matches(isDisplayed()));
        onView(withText("H")).check(matches(isDisplayed()));

        onView(withText("Test Goal")).perform(longClick());
        onView(withText("Move Task to Tomorrow")).perform(click());

        onView(withId(R.id.date_title)).perform(click());
        onView(withId(R.id.tomorrow_view)).perform(click());
        onView(withText("Test Goal")).check(matches(isDisplayed()));
    }

    @Test
    public void deletePendingTask() {
        onView(withId(R.id.date_title)).perform(click());
        onView(withId(R.id.pending_view)).perform(click());
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Test Goal 2"), ViewActions.closeSoftKeyboard());
        onView(withText("H")).perform(click());
        onView(withText("Save")).perform(click());
        onView(withText("Test Goal 2")).check(matches(isDisplayed()));
        onView(withText("H")).check(matches(isDisplayed()));

        onView(withText("Test Goal 2")).perform(longClick());
        onView(withText("Delete Task")).perform(click());

        onView(withText("Test Goal 2")).check(doesNotExist());
    }

    @Test
    public void deleteRecurringTask() {
        onView(withId(R.id.date_title)).perform(click());
        onView(withId(R.id.recurring_view)).perform(click());
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Test Goal 3"), ViewActions.closeSoftKeyboard());
        onView(withText("H")).perform(click());
        onView(withText("Save")).perform(click());
        onView(withText("Test Goal 3")).check(matches(isDisplayed()));
        onView(withText("H")).check(matches(isDisplayed()));

        onView(withText("Test Goal 3")).perform(longClick());
        onView(withText("Delete")).perform(click());

        onView(withText("Test Goal 3")).check(doesNotExist());
    }
}

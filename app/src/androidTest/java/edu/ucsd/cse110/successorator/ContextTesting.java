package edu.ucsd.cse110.successorator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;

import org.junit.Before;
import org.junit.Test;

public class ContextTesting {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void addHomeTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Test Goal"), ViewActions.closeSoftKeyboard());
        onView(withText("H")).perform(click());
        onView(withText("Save")).perform(click());
        onView(withText("Test Goal")).check(matches(isDisplayed()));
        onView(withText("H")).check(matches(isDisplayed()));
    }

    @Test
    public void addWorkTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Test Goal 2"), ViewActions.closeSoftKeyboard());
        onView(withText("W")).perform(click());
        onView(withText("Save")).perform(click());
        onView(withText("Test Goal 2")).check(matches(isDisplayed()));
        onView(withText("W")).check(matches(isDisplayed()));
    }

    @Test
    public void addSchoolErrandsTask() {
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Test Goal 3"), ViewActions.closeSoftKeyboard());
        onView(withText("S")).perform(click());
        onView(withText("Save")).perform(click());
        onView(withText("Test Goal 3")).check(matches(isDisplayed()));
        onView(withText("S")).check(matches(isDisplayed()));

        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_dialog)).perform(typeText("Test Goal 4"), ViewActions.closeSoftKeyboard());
        onView(withText("E")).perform(click());
        onView(withText("Save")).perform(click());
        onView(withText("Test Goal 4")).check(matches(isDisplayed()));
        onView(withText("E")).check(matches(isDisplayed()));
    }
}

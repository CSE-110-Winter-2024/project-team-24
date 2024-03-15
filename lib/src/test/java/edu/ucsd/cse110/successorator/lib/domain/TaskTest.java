package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.ucsd.cse110.successorator.lib.domain.recurring.WeeklyRecurring;

public class TaskTest {

    @Test
    public void sortOrder() {
        Task task = new TaskBuilder()
                .withId(20)
                .withTaskName("Testing Sort Order")
                .withSortOrder(10)
                .withCheckedOff(false)
                .withRecurringId(20)
                .withView(Task.IView.TODAY)
                .withContext(Task.Context.SCHOOL)
                .build();
        int actual = 10;
        assertEquals(actual, task.sortOrder());
    }

    @Test
    public void testWithId() {
        Task actual = new TaskBuilder()
                .withId(1)
                .withTaskName("Drink juice")
                .withSortOrder(0)
                .withCheckedOff(false)
                .withRecurringId(1)
                .withView(Task.IView.PENDING)
                .withContext(Task.Context.SCHOOL)
                .build()
                .withId(42);

        Task expected = new TaskBuilder()
                .withId(42) // Matching the updated ID
                .withTaskName("Drink juice")
                .withSortOrder(0)
                .withCheckedOff(false)
                .withRecurringId(42)
                .withView(Task.IView.PENDING)
                .withContext(Task.Context.SCHOOL)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void withSortOrder() {
        Task actual = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing Sorting Order")
                .withSortOrder(0) // Initial sortOrder
                .withCheckedOff(false)
                .withRecurringId(1)
                .withView(Task.IView.TOMORROW)
                .withContext(Task.Context.SCHOOL)
                .build()
                .withSortOrder(42); // Updated sortOrder to match expected

        Task expected = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing Sorting Order")
                .withSortOrder(42)
                .withCheckedOff(false)
                .withRecurringId(1)
                .withView(Task.IView.TOMORROW)
                .withContext(Task.Context.SCHOOL)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void testWithCheckOff() {
        Task actual = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing checkoff")
                .withSortOrder(0)
                .withCheckedOff(false) // Initial checkedOff state
                .withRecurringId(1)
                .withView(Task.IView.TODAY)
                .withContext(Task.Context.SCHOOL)
                .build()
                .withCheckOff(true); // Updated checkedOff state

        Task expected = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing checkoff")
                .withSortOrder(0)
                .withCheckedOff(true) // Expected state
                .withRecurringId(1)
                .withView(Task.IView.TODAY)
                .withContext(Task.Context.SCHOOL)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void testWithRecurringType() {
        Task actual = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing recurring type")
                .withSortOrder(0)
                .withCheckedOff(false)
                .withRecurringType(new WeeklyRecurring(1)) // Initially set
                .withRecurringId(1)
                .withView(Task.IView.TODAY)
                .withContext(Task.Context.SCHOOL)
                .build()
                .withNullRecurringType(); // Remove recurringType to match expected

        Task expected = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing recurring type")
                .withSortOrder(0)
                .withCheckedOff(false)
                .withRecurringId(1) // No recurringType set, to match the actual's state
                .withView(Task.IView.TODAY)
                .withContext(Task.Context.SCHOOL)
                .build();

        assertEquals(expected, actual);
    }
}
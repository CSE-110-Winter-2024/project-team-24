package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Date;

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
                .withView(Views.ViewEnum.TODAY)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
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
                .withView(Views.ViewEnum.PENDING)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
                .build()
                .withId(42);

        Task expected = new TaskBuilder()
                .withId(42) // Matching the updated ID
                .withTaskName("Drink juice")
                .withSortOrder(0)
                .withCheckedOff(false)
                .withRecurringId(42)
                .withView(Views.ViewEnum.PENDING)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
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
                .withView(Views.ViewEnum.TOMORROW)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
                .build()
                .withSortOrder(42); // Updated sortOrder to match expected

        Task expected = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing Sorting Order")
                .withSortOrder(42)
                .withCheckedOff(false)
                .withRecurringId(1)
                .withView(Views.ViewEnum.TOMORROW)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
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
                .withView(Views.ViewEnum.TODAY)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
                .build()
                .withCheckOff(true); // Updated checkedOff state

        Task expected = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing checkoff")
                .withSortOrder(0)
                .withCheckedOff(true) // Expected state
                .withRecurringId(1)
                .withView(Views.ViewEnum.TODAY)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
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
                .withRecurringType(new WeeklyRecurring(1))
                .withRecurringId(1)
                .withView(Views.ViewEnum.TODAY)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
                .build()
                .withNullRecurringType();

        Task expected = new TaskBuilder()
                .withId(1)
                .withTaskName("Testing recurring type")
                .withSortOrder(0)
                .withCheckedOff(false)
                .withRecurringId(1)
                .withView(Views.ViewEnum.TODAY)
                .withContext(Contexts.Context.SCHOOL)
                .withStartDate(new Date())
                .build();

        assertEquals(expected, actual);
    }
}
package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.ucsd.cse110.successorator.lib.domain.recurring.WeeklyRecurring;

public class TaskTest {

    @Test
    public void sortOrder() {
        var card = new Task(20, "Testing Sort Order", 10, false, null, 20, Views.ViewEnum.TODAY, Contexts.Context.SCHOOL);

        int actual = 10;
        assertEquals(card.sortOrder(), actual);
    }

    @Test
    public void testWithId() {
        var card = new Task(1, "Drink juice", 0, false, null, 1, Views.ViewEnum.PENDING, Contexts.Context.SCHOOL);
        var expected = new Task(42, "Drink juice", 0, false, null, 42, Views.ViewEnum.PENDING, Contexts.Context.SCHOOL);
        var actual = card.withId(42);

        assertEquals(expected, actual);
    }

    @Test
    public void withSortOrder() {
        var card = new Task(1, "Testing Sorting Order", 0, false, null, 1, Views.ViewEnum.TOMORROW, Contexts.Context.SCHOOL);
        var expected = new Task(1, "Testing Sorting Order", 42, false, null, 1, Views.ViewEnum.TOMORROW, Contexts.Context.SCHOOL);
        var actual = card.withSortOrder(42);
        assertEquals(expected, actual);
    }

    @Test
    public void testWithCheckOff() {
        var card = new Task(1, "Testing checkoff", 0, false, null, 1, Views.ViewEnum.TODAY, Contexts.Context.SCHOOL);
        var expected = new Task(1, "Testing checkoff", 0, true, null, 1, Views.ViewEnum.TODAY, Contexts.Context.SCHOOL);

        var actual = card.withCheckOff(true);
        assertEquals(actual, expected);
    }

    @Test
    public void testWithRecurringType() {
        var card = new Task(1, "Testing recurring type", 0, false, new WeeklyRecurring(1), 1, Views.ViewEnum.TODAY, Contexts.Context.SCHOOL);
        var expected = new Task(1, "Testing recurring type", 0, false, null, 1, Views.ViewEnum.TODAY, Contexts.Context.SCHOOL);

        var actual = card.withNullRecurringType();
        assertEquals(actual, expected);
    }
}
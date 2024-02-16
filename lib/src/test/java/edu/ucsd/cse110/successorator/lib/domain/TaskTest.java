package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskTest {

    @Test
    public void sortOrder() {
        var card = new Task(20, "Testing Sort Order", 10, false);

        int actual = 10;
        assertEquals(card.sortOrder(), actual);
    }

    @Test
    public void testWithId() {
        var card = new Task(1, "Drink juice", 0, false);
        var expected = new Task(42, "Drink juice", 0, false);
        var actual = card.withId(42);

        assertEquals(expected, actual);
    }

    @Test
    public void withSortOrder() {
        var card = new Task(1, "Testing Sorting Order", 0, false);
        var expected = new Task(1, "Testing Sorting Order", 42, false);
        var actual = card.withSortOrder(42);
        assertEquals(expected, actual);
    }

    @Test
    public void testWithCheckOff() {
        var card = new Task(1, "Testing checkoff", 0, false);
        var expected = new Task(1, "Testing checkoff", 0, true);

        var actual = card.withCheckOff(true);
        assertEquals(actual, expected);
    }
}
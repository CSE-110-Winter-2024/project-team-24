package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class FlashcardTest {

    @Test
    public void sortOrder() {
        var card = new Flashcard(20, "Testing Sort Order", 10);

        int actual = 10;
        assertEquals(card.sortOrder(), actual);
    }

    @Test
    public void testWithId() {
        var card = new Flashcard(1, "Drink juice", 0);
        var expected = new Flashcard(42, "Drink juice", 0);
        var actual = card.withId(42);

        assertEquals(expected, actual);
    }

    @Test
    public void withSortOrder() {
        var card = new Flashcard(1,"Testing Sorting Order", 0);
        var expected = new Flashcard(1, "Testing Sorting Order", 42);
        var actual = card.withSortOrder(42);
        assertEquals(expected, actual);
    }
}
package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;

public class UnfinishedFlashcardRepositoryTest {

    private InMemoryDataSource dataSource;
    private FinishedFlashcardRepository repository;

    @Before
    public void setUp() {
        dataSource = InMemoryDataSource.fromDefault();
        repository = new FinishedFlashcardRepository(dataSource);
    }

    @Test
    public void testFind() {
        var expected = new Flashcard(0, "Study for Midterm", 0, false);
        var actual = repository.find(0).getValue();
        assertEquals(expected, actual);

        var expected2 = new Flashcard(1, "Play League of Legends", 1, false);
        var actual2 = repository.find(1).getValue();
        assertEquals(expected2, actual2);

        var expected3 = new Flashcard(2, "Do Math homework", 2, true);
        var actual3 = repository.find(2).getValue();
        assertEquals(expected3, actual3);
    }

    @Test
    public void testFindAll() {
        var flashcards = Objects.requireNonNull(repository.findAll().getValue());
        assertEquals(3, flashcards.size());
        var card = new Flashcard(10, "What is a democracy?! RAH", 10, false);
        repository.save(card);
        flashcards = Objects.requireNonNull(repository.findAll().getValue());
        assertEquals(4, flashcards.size());
    }

    @Test
    public void testSave() {
        var card = new Flashcard(10, "What is a democracy?! RAH", 10, false);
        repository.save(card);

        assertEquals(4, repository.size());
        assertEquals(repository.find(10).getValue(), card);

    }

    @Test
    public void testTestSave() {
        List<Flashcard> flashcards = List.of(
                new Flashcard(10, "What is a democracy?! RAH", 10, false),
                new Flashcard(11, "What is a democracy?!?! RAH", 11, false)
        );
        repository.save(flashcards);

        assertEquals(5, repository.size());
        assertEquals(repository.find(10).getValue(), flashcards.get(0));
        assertEquals(repository.find(11).getValue(), flashcards.get(1));
    }

    @Test
    public void testRemove() {
        var card = new Flashcard(10, "What is a democracy?! RAH", 10, false);
        repository.save(card);
        assertEquals(4, repository.size());
        repository.remove(10);
        assertEquals(3, repository.size());
    }

    @Test
    public void testAppend() {
        var card = new Flashcard(10, "What is a democracy?! RAH", -1, false);
        repository.append(card);

        assertEquals(4, repository.size());

        var expected = new Flashcard(10, "What is a democracy?! RAH", 3, false);
        assertEquals(expected, repository.find(10).getValue());
    }

    @Test
    public void testPrepend() {
        var card = new Flashcard(10, "What is a democracy?! RAH", -1, false);
        repository.prepend(card);

        assertEquals(4, repository.size());

        var expected = new Flashcard(10, "What is a democracy?! RAH", 0, false);
        assertEquals(expected, repository.find(10).getValue());
    }

    @Test
    public void testSize() {
        assertEquals(3, repository.size());

        var card = new Flashcard(10, "What is a democracy?! RAH", 10, false);
        repository.save(card);
        assertEquals(4, repository.size());
    }
}
package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;

public class TasksRepositoryTest {

    private InMemoryDataSource dataSource;
    private TasksRepository repository;

    @Before
    public void setUp() {
        dataSource = InMemoryDataSource.fromDefault();
        repository = new TasksRepository(dataSource);
    }
    @Test
    public void find() {
        var expected = new Task(0, "Study for Midterm", 0, false);
        var actual = repository.find(0).getValue();
        assertEquals(expected, actual);

        var expected2 = new Task(1, "Play League of Legends", 1, false);
        var actual2 = repository.find(1).getValue();
        assertEquals(expected2, actual2);

        var expected3 = new Task(2, "Do Math homework", 2, true);
        var actual3 = repository.find(2).getValue();
        assertEquals(expected3, actual3);
    }

    @Test
    public void findAll() {
        var flashcards = Objects.requireNonNull(repository.findAll().getValue());
        assertEquals(3, flashcards.size());

        var card = new Task(10, "What is a democracy?! RAH", 10, false);
        repository.save(card);

        flashcards = Objects.requireNonNull(repository.findAll().getValue());
        assertEquals(4, flashcards.size());
    }

    @Test
    public void save() {
        var card = new Task(10, "What is a democracy?! RAH", 10, false);
        repository.save(card);

        assertEquals(4, repository.size());
        assertEquals(repository.find(10).getValue(), card);
    }

    @Test
    public void testSave() {
        List<Task> flashcards = List.of(
                new Task(10, "What is a democracy?! RAH", 10, false),
                new Task(11, "What is a democracy?!?! RAH", 11, false)
        );
        repository.save(flashcards);

        assertEquals(5, repository.size());
        assertEquals(repository.find(10).getValue(), flashcards.get(0));
        assertEquals(repository.find(11).getValue(), flashcards.get(1));
    }

    @Test
    public void remove() {
        var card = new Task(10, "What is a democracy?! RAH", 10, false);
        repository.save(card);

        assertEquals(4, repository.size());
        repository.remove(10);
        assertEquals(3, repository.size());
    }

    @Test
    public void append() {
        var card = new Task(10, "What is a democracy?! RAH", -1, false);
        repository.append(card);

        assertEquals(4, repository.size());

        var expected = new Task(10, "What is a democracy?! RAH", 3, false);
        assertEquals(expected, repository.find(10).getValue());
    }

    @Test
    public void prepend() {
        var card = new Task(10, "What is a democracy?! RAH", -1, false);
        repository.prepend(card);

        assertEquals(4, repository.size());

        var expected = new Task(10, "What is a democracy?! RAH", 0, false);
        assertEquals(expected, repository.find(10).getValue());
    }

    @Test
    public void size() {
        assertEquals(3, repository.size());

        var card = new Task(10, "What is a democracy?! RAH", 10, false);
        repository.save(card);
        assertEquals(4, repository.size());
    }
}
package edu.ucsd.cse110.successorator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import edu.ucsd.cse110.successorator.util.DateSubject;

public class DateSubjectTest {

    private DateSubject dateSubject;
    private Date initialDate;

    @Before
    public void setUp() {
        dateSubject = new DateSubject();
        initialDate = new Date();
        dateSubject.setItem(initialDate);
    }

    @Test
    public void testSetDate() {
        Date newDate = new Date(initialDate.getTime() + 1000 * 60 * 60 * 24); // Adds one day
        dateSubject.setItem(newDate);
        assertEquals(newDate, dateSubject.getItem());
    }

    @Test
    public void testGetDate() {
        assertEquals(initialDate, dateSubject.getItem());
    }

    @Test
    public void testAdvanceDate() {
        dateSubject.advanceDate();
        Date expectedDate = new Date(initialDate.getTime() + 1000 * 60 * 60 * 24);
        assertEquals(expectedDate, dateSubject.getItem());
    }
}

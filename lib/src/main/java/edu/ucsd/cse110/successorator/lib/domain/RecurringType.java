package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.util.Observer;

public interface RecurringType extends Observer<Date> {
    Boolean checkIfToday();
}

package edu.ucsd.cse110.successorator.util;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class FocusModeSubject extends SimpleSubject<Task.Context> {
    public FocusModeSubject() {
        setItem(null);
    }

    public boolean isActivate() {
        return getItem() != null;
    }
}

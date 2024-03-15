package edu.ucsd.cse110.successorator.util;

import edu.ucsd.cse110.successorator.lib.domain.Contexts;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class FocusModeSubject extends SimpleSubject<Contexts.Context> {
    public FocusModeSubject() {
        setItem(null);
    }

    public boolean isActivate() {
        return getItem() != null;
    }
}

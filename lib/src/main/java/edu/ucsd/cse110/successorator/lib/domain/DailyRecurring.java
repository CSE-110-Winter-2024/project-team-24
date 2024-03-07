package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

import java.util.Date;

public class DailyRecurring implements RecurringType {
    @Override
    public Boolean checkIfToday() {
        return true;
    }

    @Override
    public void onChanged(@Nullable Date value) {

    }
}

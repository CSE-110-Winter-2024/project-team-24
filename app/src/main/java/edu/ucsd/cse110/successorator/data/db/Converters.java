package edu.ucsd.cse110.successorator.data.db;

import androidx.room.TypeConverter;

import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.views.IView;

public class Converters {
    @TypeConverter
    public static RecurringType toRecurringType(String value) {
        if (value == null) {
            return null;
        }
        return RecurringType.valueOf(value);
    }

    @TypeConverter
    public static String fromRecurringType(RecurringType recurringType) {
        if (recurringType == null) {
            return null;
        }
        return recurringType.toString();
    }

    @TypeConverter
    public static IView toIView(String value) {
        if (value == null) {
            return null;
        }
        return IView.valueOf(value);
    }

    @TypeConverter
    public static String fromIView(IView view) {
        if (view == null) {
            return null;
        }
        return view.toString();
    }
}

package edu.ucsd.cse110.successorator.data.db;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import edu.ucsd.cse110.successorator.lib.domain.RecurringType;

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
}

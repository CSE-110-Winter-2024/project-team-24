package edu.ucsd.cse110.successorator.data.db;

import androidx.room.TypeConverter;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.recurring.RecurringType;


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
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

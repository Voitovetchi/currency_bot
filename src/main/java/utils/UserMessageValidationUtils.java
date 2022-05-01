package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class UserMessageValidationUtils {

    public static boolean dateIsValid(String date) {
        try {
            LocalDate.parse(date, new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .toFormatter());
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}

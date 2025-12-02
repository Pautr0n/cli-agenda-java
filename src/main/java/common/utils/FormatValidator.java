package common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormatValidator {

    public static boolean isValidLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dateStr, formatter);
            return true; // se pudo parsear correctamente
        } catch (DateTimeParseException e) {
            return false; // no es una fecha válida
        }
    }

    public static boolean isValidPriority(String priority){
        switch (priority.toUpperCase()){
            case "LOW", "MEDIUM", "HIGH" -> {return true;}
            default -> {
                return false;
            }
        }
    }
}

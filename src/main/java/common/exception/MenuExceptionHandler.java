package common.exception;

public class MenuExceptionHandler {

    public static void handle(Exception e) {
        switch (e) {
            case EntityNotFoundException entityNotFoundException ->
                    System.out.println("Task not found: " + e.getMessage());
            case ValidationException validationException -> System.out.println("Validation Error: " + e.getMessage());
            case DataAccessException dataAccessException ->
                    System.out.println("Technical error from the database: " + e.getMessage());
            case ServiceException serviceException -> System.out.println("Unexpected Error: " + e.getMessage());
            default -> System.out.println("Unknown error: " + e.getMessage());
        }
    }

}

package common.exception;

public class MenuExceptionHandler {

    public static void handle(Exception e) {
        if (e instanceof EntityNotFoundException) {
            System.out.println("Task not found: " + e.getMessage());
        } else if (e instanceof ValidationException) {
            System.out.println("Validation Error: " + e.getMessage());
        } else if (e instanceof DataAccessException) {
            System.out.println("Technical error from the database: " + e.getMessage());
        } else if (e instanceof ServiceException) {
            System.out.println("Unexpected Error: " + e.getMessage());
        } else {
            System.out.println("Unknown error: " + e.getMessage());
        }
    }

}

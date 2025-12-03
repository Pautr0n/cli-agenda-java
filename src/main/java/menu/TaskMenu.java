package menu;

import common.exception.*;
import common.observer.ExpirableObserver;
import common.publisher.ExpirableService;
import common.utils.FormatValidator;
import task.dto.TaskDTO;
import task.dto.TaskIdDTO;
import task.dto.TaskOutputDTO;
import task.dto.TaskUpdateDTO;
import task.publisher.TaskChecker;
import task.service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static common.utils.PrintMenus.*;

public class TaskMenu implements ExpirableObserver<TaskOutputDTO> {

    private final Scanner scanner;
    private final TaskService taskService;
    private final ExpirableService<TaskOutputDTO> expirableService;
    private final TaskChecker taskChecker;

    private static int expiredCount = 0;
    private List<TaskOutputDTO> lastExpired = List.of();


    public TaskMenu(Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;

        this.expirableService = new ExpirableService<>(taskService::getAllTasks);
        this.taskChecker = new TaskChecker();
        this.expirableService.addObserver(this);


    }

    public void start() {
        int option = -1;

        while (option != 0) {
            expirableService.notifyExpired(taskChecker);
            showTaskMenu();

            while (!scanner.hasNextInt()) {
                System.out.print("Introduce a valid option: ");
                scanner.nextLine();
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createTask();
                case 2 -> listTasks();
                case 3 -> getTaskById();
                case 4 -> markTaskCompleted();
                case 5 -> updateTask();
                case 6 -> deleteTask();
                case 7 -> listExpiredTasks();
                case 0 -> System.out.println("Going back to main menu...");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // Crear tarea (construye el DTO y llama al metodo de taskservice)
    private void createTask() {

        System.out.println("\n***CREA TU TAREA***");
        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Content: ");
        String content = scanner.nextLine();

        String expirationDate;
        while(true){

            System.out.print("Expiration date (YYYY-MM-DD): ");
            expirationDate = scanner.nextLine();
            try {
                LocalDate.parse(expirationDate);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Debe ser YYYY-MM-DD.");
            }

        }

        String priorityText;

        while(true){
            System.out.print("Prioridad (LOW, MEDIUM, HIGH): ");
            priorityText = scanner.nextLine().toUpperCase();
            if (!priorityText.equals("LOW") && !priorityText.equals("MEDIUM") && !priorityText.equals("HIGH")) {
                System.out.println("Invalid priority. Usa LOW, MEDIUM o HIGH.");
            } else{
                break;
            }
        }

        TaskDTO dto = new TaskDTO(title, content, expirationDate, priorityText);

        try {
            TaskOutputDTO dtoOutput = taskService.createTask(dto);
            printMenuCreateTask(dtoOutput);
        }  catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }

    private void listTasks() {
        int option = -1;
        while (option != 0) {

            printMenuListTask();

            while (!scanner.hasNextInt()) {
                System.out.print("Introduce a valid option: ");
                scanner.nextLine();
            }

            option = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            try {
                List<TaskOutputDTO> listTasks = List.of();
                switch (option) {
                    case 1 -> listTasks = taskService.getAllTasks();
                    case 2, 3 -> listTasks = taskService.getTasksByStatus(option);
                    case 0 -> System.out.println("Going back to main menu.");
                    default -> {
                        System.out.println("Invalid option.");
                        continue;
                    }
                }
                printTaskList(listTasks);
            }  catch (Exception e) {
                MenuExceptionHandler.handle(e);
            }
        }

    }

    private void getTaskById() {
        System.out.print("Introduce el ID de la tarea: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Introduce a valid option: ");
            scanner.nextLine();
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        TaskIdDTO dto = new TaskIdDTO(id);

        try {
            TaskOutputDTO dtoOutput = taskService.getTaskById(dto);
            printMenuCreateTask(dtoOutput);
        } catch (ValidationException e){
            MenuExceptionHandler.handle(e);
        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }


    private void markTaskCompleted() {
        System.out.print("ID de la tarea completada: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        TaskIdDTO dto = new TaskIdDTO(id);

        try {
            TaskOutputDTO dtoOutput = taskService.markTaskCompleted(dto);
            printMarkTask(dtoOutput);

        }  catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }

    }


    private void updateTask() {
        System.out.print("ID of the TASK to be updated: ");
        while(!scanner.hasNextInt()){
            System.out.print("Invalid ID, try again: ");
            scanner.nextLine();
        }
        int id = scanner.nextInt();
        scanner.nextLine();


        String title = null;
        String content = null;
        String expirationDate = null;
        String priorityText = null;

        int option = -1;

        while (option != 0) {

            printMenuUpdateTask();

            while (!scanner.hasNextInt()) {
                System.out.print("Introduce un número válido: ");
                scanner.nextLine();
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("New Title: ");
                    title = scanner.nextLine();
                }
                case 2 -> {
                    System.out.print("New Content: ");
                    content = scanner.nextLine();
                }
                case 3 -> {
                    boolean checkDate = false;
                    while(checkDate == false){
                        System.out.print("New Expiration Date (YYYY-MM-DD): ");
                        expirationDate = scanner.nextLine();
                        checkDate = FormatValidator.isValidLocalDate(expirationDate);
                        if(!checkDate) System.out.println("Date Format not valid. Try again.");
                    }
                }
                case 4 -> {
                    boolean checkPriority = false;
                    while(checkPriority == false){
                        System.out.print("New priority (LOW, MEDIUM, HIGH): ");
                        priorityText = scanner.nextLine();
                        checkPriority = FormatValidator.isValidPriority(priorityText);
                        if(!checkPriority) System.out.println(priorityText + " is not a valid priority. Try again.");
                    }

                }
                case 0 -> System.out.println("Exiting Update Menu!");
                default -> System.out.println("Invalid Option.");
            }
        }

        TaskUpdateDTO dto = new TaskUpdateDTO(id, title, content, expirationDate, priorityText);

        try {
            TaskOutputDTO dtoOutput = taskService.updateTask(dto);
            printMenuTaskUpdated(dtoOutput);
        }  catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }

    }


    private void deleteTask() {


        System.out.print("ID de la tarea a eliminar: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Introduce a valid integer number: ");
            scanner.nextLine();
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        TaskIdDTO dto = new TaskIdDTO(id);

        try {
            TaskOutputDTO dtoOutput = taskService.getTaskById(dto);
            printMenuCreateTask(dtoOutput);

            while(true){
                System.out.println("Estas seguro que quieres eliminar la tarea, (S/N)");
                String confirmation = scanner.nextLine().toUpperCase();
                switch (confirmation){
                    case "S"->{
                        taskService.deleteTask(dto);
                        printDeleteTask(id);
                        return;
                    }
                    case "N"-> {
                        System.out.println("Aborting delete task");
                        return;
                    }
                    default -> System.out.println("Invalid Option.");
                }
            }


        }  catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }

    private void listExpiredTasks() {
        if(lastExpired.isEmpty()){
            System.out.println("No hay tareas caducadas.");
        }else{
            System.out.println("*** TAREAS CADUCADAS (" + lastExpired.size() + ") ***");
            lastExpired.forEach(t->System.out.println("⚠️ Tarea caducada: " + t.title() + " (ID: " + t.id() + ")"));
        }
    }

    public static int getExpiredCount(){
        return expiredCount;
    }


    @Override
    public void onExpired(List<TaskOutputDTO> expiredEntities) {
        expiredCount = expiredEntities.size();
        this.lastExpired = expiredEntities;

    }


}

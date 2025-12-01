package menu;

import common.utils.PrintMenus;
import task.dto.TaskDTO;
import task.dto.TaskIdDTO;
import task.dto.TaskOutputDTO;
import task.dto.TaskUpdateDTO;
import task.service.TaskService;

import java.util.List;
import java.util.Scanner;

import static common.utils.PrintMenus.*;

public class TaskMenu {

    private final Scanner scanner;
    private final TaskService taskService;

    public TaskMenu(Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
    }

    public void start() {
        int option = -1;

        while (option != 0) {

            PrintMenus.showTaskMenu();

            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, introduce un número válido: ");
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
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    // Crear tarea (construye el DTO y llama al metodo de taskservice)
    private void createTask() {

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Contenido: ");
        String content = scanner.nextLine();

        System.out.print("Fecha de la Tarea (YYYY-MM-DD): ");
        String expirationDate = scanner.nextLine();

        System.out.print("Prioridad (LOW, MEDIUM, HIGH): ");
        String priorityText = scanner.nextLine();

        TaskDTO dto = new TaskDTO(title, content, expirationDate, priorityText);

       TaskOutputDTO dtoOutput = taskService.createTask(dto);
        printMenuCreateTask(dtoOutput);


    }


    private void listTasks() {
      printMenuListTask();

        while (!scanner.hasNextInt()) {
            System.out.print("Introduce un número válido: ");
            scanner.nextLine();
        }

        int option = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        try {
            List<TaskOutputDTO> listTasks;

            switch (option) {
                case 1:
                    listTasks = taskService.getAllTasks();
                    printTaskList(listTasks);
                    break;
                case 2:
                    listTasks = taskService.getTasksByStatus(option);
                    printTaskList(listTasks);
                    break;
                case 3:
                    listTasks = taskService.getTasksByStatus(option);
                    printTaskList(listTasks);
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    /*
    private void listTasks() {
        try {
            taskService.getAllTasks();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //LISTAR tareas completadas
    //añadido no existia (revisar si cuadra con la nueva ServiceTask de Andres.
    private void completedTask() {
        try {
            taskService.getCompletedTasks();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    //LISTAR tareas no completadas

    private void notCompletedTask(){
        try {
            taskService.getPendingTasks();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
*/

    private void getTaskById() {
        System.out.print("Introduce el ID de la tarea: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        TaskIdDTO dto = new TaskIdDTO(id);

        try {
          TaskOutputDTO dtoOutput =  taskService.getTaskById(dto);
          printMenuCreateTask(dtoOutput);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // modificado revisar con taskService de andres.
    // variable inicializadas vacias (puede ser null?) para la logica en taskservice

    private void updateTask() {
        System.out.print("ID de la tarea para hacer update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String title = null;
        String content = null;
        String expirationDate = null;
        String priorityText = null;

        int option = -1;

        while (option != 0) {

            printMenuUpdate();

            while (!scanner.hasNextInt()) {
                System.out.print("Introduce un número válido: ");
                scanner.nextLine();
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Nuevo título: ");
                    title = scanner.nextLine();
                }
                case 2 -> {
                    System.out.print("Nuevo contenido: ");
                    content = scanner.nextLine();
                }
                case 3 -> {
                    System.out.print("Nueva fecha (YYYY-MM-DD): ");
                    expirationDate = scanner.nextLine();
                }
                case 4 -> {
                    System.out.print("Nueva prioridad (LOW, MEDIUM, HIGH): ");
                    priorityText = scanner.nextLine();
                }
                case 0 -> System.out.println("Saliendo del menú de update!");
                default -> System.out.println("Opción no válida.");
            }
        }

        TaskUpdateDTO dto = new TaskUpdateDTO(id, title, content, expirationDate, priorityText);

        try {
           TaskOutputDTO dtoOutput = taskService.updateTask(dto);
           printMenuCreateUpdateTask(dtoOutput);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void deleteTask() {
        System.out.print("ID de la tarea a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        TaskIdDTO dto = new TaskIdDTO(id);

        try {
            // task service no devuelve nada en este caso.
            taskService.deleteTask(dto);
            printDeleteTask(id);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

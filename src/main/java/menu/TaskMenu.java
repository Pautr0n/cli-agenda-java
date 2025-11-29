package menu;

import common.utils.PrintMenus;
import task.dto.TaskDTO;
import task.dto.TaskIdDTO;
import task.dto.TaskUpdateDTO;
import task.service.TaskService;

import java.util.Scanner;


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

            PrintMenus.showTaskMenu();  // cambio a impresión en clase nueva

            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, introduce un número válido: ");
                scanner.nextLine();
            }
// añadir funcion que falta listar tarras completadas
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createTask();
                case 2 -> listTasks();
                case 3 -> getTaskById();
                case 4 -> markTaskCompleted();
                case 5 -> completedTask(); // añadido no existia revisar con Andres
                case 6 -> updateTask(); // pendiente
                case 7 -> deleteTask();
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

                taskService.createTask(dto);

    }

    private void listTasks() {
        try {
            taskService.getAllTasks();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getTaskById() {
        System.out.print("Introduce el ID de la tarea: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        TaskIdDTO dto = new TaskIdDTO(id);

        try {
            taskService.getTaskById(dto);
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
            taskService.markTaskCompleted(dto);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //LISTAR tareas competadas
    //añadido no existia (revisar si cuadra con la nueva ServiceTask de Andres.
    private void completedTask() {
        try {
            taskService.completedTask();
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

            System.out.println("""
                    ¿Qué contenido deseas modificar?
                    1- Título
                    2- Contenido
                    3- Fecha
                    4- Prioridad
                    0- Salir
                    """);

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
            taskService.updateTask(dto);
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
            taskService.deleteTask(dto);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

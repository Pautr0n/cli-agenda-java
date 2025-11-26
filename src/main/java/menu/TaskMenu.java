package menu;

import task.dto.TaskCreateDTO;
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

            printMenu();

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
                case 5 -> updateTask();     // pendiente
                case 6 -> deleteTask();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===== MENÚ DE TAREAS =====");
        System.out.println("1. Crear tarea");
        System.out.println("2. Listar tareas");
        System.out.println("3. Ver tarea por ID");
        System.out.println("4. Marcar tarea como completada");
        System.out.println("5. Actualizar tarea (pendiente)");
        System.out.println("6. Eliminar tarea");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }

    // Crear tarea (solo construye el DTO y muestra mensaje)
    private void createTask() {
        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Contenido: ");
        String content = scanner.nextLine();

        System.out.print("Fecha de vencimiento (YYYY-MM-DD): ");
        String expirationDate = scanner.nextLine();

        System.out.print("Prioridad (LOW, MEDIUM, HIGH): ");  //LA RECOjO COMO STRING
        String priorityText = scanner.nextLine();

        TaskCreateDTO dto = new TaskCreateDTO(title, content, expirationDate, priorityText);

        //Llamar al TaskService.createTask(dto) cuando esté implementado.
        //taskService.createTask(dto);
    }

    //Listar tareas
    private void listTasks() {
        try {
            taskService.getAllTasks();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    //Obtener tarea por id (sin DTO)
    private void getTaskById() {
        System.out.print("Introduce el ID de la tarea: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            taskService.getTaskById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void markTaskCompleted() {
        System.out.print("ID de la tarea a completar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            taskService.markTaskCompleted(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    //DEFINIR EL UPDATE ( NO ME ENTERO como va a quedar al final??)
    private void updateTask() {
        //Implementar update cuando tengáis el TaskUpdateDTO.
    }

    // Borrar tarea.
    private void deleteTask() {
        System.out.print("ID de la tarea a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            taskService.deleteTask(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }
}

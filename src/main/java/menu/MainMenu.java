package menu;

import task.service.TaskService;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final TaskService taskService;

    public MainMenu(Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
    }

    public void start() {
        int option = -1;

        while (option != 0) {

            printMainMenu();

            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, introduce un número válido: ");
                scanner.nextLine();
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1 -> {
                    TaskMenu taskMenu = new TaskMenu(scanner, taskService);
                    taskMenu.start();
                }

                case 2 -> {
                    System.out.println(" Menú de notas pendiente.");
                }

                case 3 -> {
                    System.out.println("Menú de eventos pendiente.");
                }

                case 0 -> {
                    System.out.println("Saliendo de la aplicación...");
                }

                default -> {
                    System.out.println("Opción no válida. Intenta de nuevo.");
                }
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n===== AGENDA =====");
        System.out.println("1. Gestionar tareas");
        System.out.println("2. Gestionar notas");
        System.out.println("3. Gestionar eventos");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }
}


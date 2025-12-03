package menu;

import common.utils.PrintMenus;
import event.service.EventService;
import note.service.NoteService;
import task.service.TaskService;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final TaskService taskService;
    private final NoteService noteService;
    private final EventService eventService;

    public MainMenu(Scanner scanner, TaskService taskService, NoteService noteService, EventService eventService) {
        this.scanner = scanner;
        this.taskService = taskService;
        this.noteService = noteService;
        this.eventService = eventService;
    }

    public void start() {
        int option = -1;

        while (option != 0) {

            PrintMenus.printMainMenu();

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
                    NoteMenu noteMenu = new NoteMenu(scanner, noteService);
                    noteMenu.start();
                }

                case 3 -> {
                    EventMenu eventMenu = new EventMenu(scanner,eventService);
                    eventMenu.start();
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

}


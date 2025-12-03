package menu;

import common.exception.MenuExceptionHandler;
import common.exception.ValidationException;
import common.observer.ExpirableObserver;
import common.publisher.ExpirableService;
import common.utils.FormatValidator;
import event.dto.EventDTO;
import event.dto.EventIdDTO;
import event.dto.EventOutputDTO;
import event.dto.EventUpdateDTO;
import event.publisher.EventChecker;
import event.service.EventService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static common.utils.PrintMenus.*;

public class EventMenu implements ExpirableObserver<EventOutputDTO> {

    private final Scanner scanner;
    private final EventService eventService;
    private final ExpirableService<EventOutputDTO> expirableService;
    private final EventChecker eventChecker;

    private static int expiredCount = 0;
    private List<EventOutputDTO> lastExpired = List.of();

    public EventMenu(Scanner scanner, EventService eventService) {
        this.scanner = scanner;
        this.eventService = eventService;

        this.expirableService = new ExpirableService<>((eventService::getAllEvents));
        this.eventChecker = new EventChecker();
        this.expirableService.addObserver(this);

    }

    public void start() {
        int option = -1;

        while (option != 0) {
            expirableService.notifyExpired(eventChecker);
            showEventMenu();

            while (!scanner.hasNextInt()) {
                System.out.print("Introduce a valid option: ");
                scanner.nextLine();
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createEvent();
                case 2 -> listEvents();
                case 3 -> getEventById();
                case 4 -> updateEvent();
                case 5 -> deleteEvent();
                case 6 -> listExpiredEvents();
                case 0 -> System.out.println("Going back to main menu...");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // Crear tarea (construye el DTO y llama al metodo de taskservice)
    private void createEvent() {

        System.out.println("\n***CREA TU EVENTO***");
        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Content: ");
        String content = scanner.nextLine();

        String expirationDate;
        while (true) {

            System.out.print("Expiration date (YYYY-MM-DD): ");
            expirationDate = scanner.nextLine();
            try {
                LocalDate.parse(expirationDate);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Debe ser YYYY-MM-DD.");
            }

        }

        EventDTO dto = new EventDTO(title, content, expirationDate);

        try {
            EventOutputDTO dtoOutput = eventService.createEvent(dto);
            printMenuCreateEvent(dtoOutput);
        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }

    private void listEvents() {

        try {
            List<EventOutputDTO> eventList = eventService.getAllEvents();

            printEventList(eventList.stream()
                    .filter(e -> e.expirationDate().isEqual(LocalDate.now()) || e.expirationDate()
                            .isAfter(LocalDate.now())).toList());
        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }


    private void listExpiredEvents() {
        List<EventOutputDTO> expiredNow = expirableService.notifyExpired(eventChecker);

        if (expiredNow.isEmpty()) {
            System.out.println("No hay eventos caducados.");
        } else {
            System.out.println("*** EVENTOS CADUCADOS (" + expiredNow.size() + ") ***");
            expiredNow.forEach(t ->
                    System.out.println("⚠️ Evento caducado: " + t.title() + " (ID: " + t.id() + ")")
            );
        }
    }

    private void getEventById() {
        System.out.print("Introduce el ID del evento: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Introduce a valid option: ");
            scanner.nextLine();
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        EventIdDTO dto = new EventIdDTO(id);

        try {
            EventOutputDTO dtoOutput = eventService.getEventById(dto);
            printMenuCreateEvent(dtoOutput);
        } catch (ValidationException e) {
            MenuExceptionHandler.handle(e);
        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }


    private void updateEvent() {
        System.out.print("ID of the EVENT to be updated: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid ID, try again: ");
            scanner.nextLine();
        }
        int id = scanner.nextInt();
        scanner.nextLine();


        String title = null;
        String content = null;
        String expirationDate = null;

        int option = -1;

        while (option != 0) {

            printMenuUpdateEvent();

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
                    while (checkDate == false) {
                        System.out.print("New Expiration Date (YYYY-MM-DD): ");
                        expirationDate = scanner.nextLine();
                        checkDate = FormatValidator.isValidLocalDate(expirationDate);
                        if (!checkDate) System.out.println("Date Format not valid. Try again.");
                    }
                }
                case 0 -> System.out.println("Exiting Update Menu!");
                default -> System.out.println("Invalid Option.");
            }
        }

        EventUpdateDTO dto = new EventUpdateDTO(id, title, content, expirationDate);

        try {
            EventOutputDTO dtoOutput = eventService.updateEvent(dto);
            printMenuEventUpdated(dtoOutput);
        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }

    }


    private void deleteEvent() {


        System.out.print("ID del evento a eliminar: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Introduce a valid integer number: ");
            scanner.nextLine();
        }

        int id = scanner.nextInt();
        scanner.nextLine();


        EventIdDTO dto = new EventIdDTO(id);

        try {
            EventOutputDTO dtoOutput = eventService.getEventById(dto);
            printMenuCreateEvent(dtoOutput);

            while (true) {
                System.out.println("Estas seguro que quieres eliminar el evento, (S/N)");
                String confirmation = scanner.nextLine().toUpperCase();
                switch (confirmation) {
                    case "S" -> {
                        eventService.deleteEvent(dto);
                        printDeleteEvent(id);
                        return;
                    }
                    case "N" -> {
                        System.out.println("Aborting delete event");
                        return;
                    }
                    default -> System.out.println("Invalid Option.");
                }
            }


        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }

    public static int getExpiredCount(){
        return expiredCount;
    }

    @Override
    public void onExpired(List<EventOutputDTO> expiredEntities) {
        expiredCount = expiredEntities.size();
        this.lastExpired = expiredEntities;
    }
}

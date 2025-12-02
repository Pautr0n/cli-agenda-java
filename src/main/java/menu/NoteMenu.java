package menu;

import common.exception.MenuExceptionHandler;
import common.exception.ValidationException;
import common.utils.PrintMenus;
import note.dto.NoteDTO;
import note.dto.NoteIdDTO;
import note.dto.NoteOutputDTO;
import note.dto.NoteUpdateDTO;
import note.service.NoteService;


import java.util.List;
import java.util.Scanner;

import static common.utils.PrintMenus.*;

public class NoteMenu {

    private final Scanner scanner;
    private final NoteService noteService;

    public NoteMenu(Scanner scanner, NoteService noteService) {
        this.scanner = scanner;
        this.noteService = noteService;
    }

    public void start() {
        int option = -1;

        while (option != 0) {
            PrintMenus.showNoteMenu();

            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, introduce un número válido: ");
                scanner.nextLine();
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createNote();
                case 2 -> listNotes();
                case 3 -> getNoteById();
                case 4 -> updateNote();
                case 5 -> deleteNote();
                case 0 -> System.out.println("Going back to main menú...");
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void createNote() {

        System.out.println("\n***CREA TU NOTA***");
        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Content: ");
        String content = scanner.nextLine();

        NoteDTO dto = new NoteDTO(title, content);

        try {
            NoteOutputDTO dtoOutput = noteService.createNote(dto);
            printMenuCreateNote(dtoOutput);
        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }

    private void listNotes() {

        List<NoteOutputDTO> notesList;
        try {
            notesList = noteService.getAllNotes();
            printNoteList(notesList);
        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }

    private void getNoteById() {
        System.out.print("Introduce el ID de la nota: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Introduce a valid option: ");
            scanner.nextLine();
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        NoteIdDTO dto = new NoteIdDTO(id);

        try {
            NoteOutputDTO dtoOutput = noteService.getNoteById(dto);
            printMenuCreateNote(dtoOutput);
        } catch (ValidationException e){
            MenuExceptionHandler.handle(e);
        } catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }

    private void updateNote() {
        System.out.print("ID of the NOTE to be updated: ");
        while(!scanner.hasNextInt()){
            System.out.print("Invalid ID, try again: ");
            scanner.nextLine();
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        String title = null;
        String content = null;

        int option = -1;

        while (option != 0) {

            printMenuUpdateNote();

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
                case 0 -> System.out.println("Exiting Update Menu!");
                default -> System.out.println("Invalid Option.");
            }
        }

        NoteUpdateDTO dto = new NoteUpdateDTO(id, title, content);

        try {
            NoteOutputDTO dtoOutput = noteService.updateNote(dto);
            printMenuNoteUpdated(dtoOutput);
        }  catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }

    }

    private void deleteNote() {


        System.out.print("ID de la nota a eliminar: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Introduce a valid integer number: ");
            scanner.nextLine();
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        NoteIdDTO dto = new NoteIdDTO(id);

        try {
            NoteOutputDTO dtoOutput = noteService.getNoteById(dto);
            printMenuCreateNote(dtoOutput);

            while(true){
                System.out.println("Estas seguro que quieres eliminar la nota, (S/N)");
                String confirmation = scanner.nextLine().toUpperCase();
                switch (confirmation){
                    case "S"->{
                        noteService.deleteNote(dto);
                        printDeleteNote(id);
                    }
                    case "N"-> {
                        System.out.println("Aborting delete note");
                        return;
                    }
                    default -> System.out.println("Invalid Option.");
                }
            }

        }  catch (Exception e) {
            MenuExceptionHandler.handle(e);
        }
    }

}

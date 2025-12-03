package application;
import common.dao.GenericDAO;
import event.model.Event;
import event.repository.EventRepository;
import event.service.EventService;
import infrastructure.sql.connection.DBConnection;
import infrastructure.sql.dao.MySQLEventDAOAdapter;
import infrastructure.sql.dao.MySQLNoteDAOAdapter;
import infrastructure.sql.dao.MySQLTaskDAOAdapter;
import menu.MainMenu;
import note.model.Note;
import note.repository.NoteRepository;
import note.service.NoteService;
import task.model.Task;
import task.repository.TaskRepository;
import task.service.TaskService;
import java.sql.Connection;
import java.util.Scanner;


public class MainApp {

    private Scanner scanner;

    private TaskRepository taskRepository;
    private TaskService taskService;

    private NoteRepository noteRepository;
    private NoteService noteService;

    private EventRepository eventRepository;
    private EventService eventService;

    private MainMenu mainMenu;

    //inicializar dependencias1
    private void init() {
        scanner = new Scanner(System.in);

        // 1. Obtener conexión SQL
        Connection connection = DBConnection.getConnection();

        // 2. Crear DAO concreto usando la conexión
        GenericDAO<Task> taskDao = new MySQLTaskDAOAdapter(connection);
        GenericDAO<Note> noteDao = new MySQLNoteDAOAdapter(connection);
        GenericDAO<Event> eventDao = new MySQLEventDAOAdapter(connection);

        // 3. Crear repositorio pasando el DAO
        taskRepository = new TaskRepository(taskDao);
        noteRepository = new NoteRepository(noteDao);
        eventRepository = new EventRepository(eventDao);

        // 4. Crear servicio pasando el repositorio
        taskService = new TaskService(taskRepository);
        noteService = new NoteService(noteRepository);
        eventService = new EventService(eventRepository);

        // 5. Crear el menú principal
        mainMenu = new MainMenu(scanner, taskService, noteService, eventService);

    }

    public void run() {
        mainMenu.start();
    }

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.init();
        app.run();
    }
}



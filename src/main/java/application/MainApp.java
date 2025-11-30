package application;
import common.dao.GenericDAO;
import infrastructure.sql.connection.DBConnection;
import infrastructure.sql.dao.MySQLTaskDAOAdapter;
import menu.MainMenu;
import task.model.Task;
import task.repository.TaskRepository;
import task.service.TaskService;
import java.sql.Connection;
import java.util.Scanner;


public class MainApp {

    private Scanner scanner;
    private TaskRepository taskRepository;
    private TaskService taskService;
    private MainMenu mainMenu;

    //inicializar dependencias
    private void init() {
        scanner = new Scanner(System.in);

        // 1. Obtener conexión SQL
        Connection connection = DBConnection.getConnection();

        // 2. Crear DAO concreto usando la conexión
        GenericDAO<Task> taskDao = new MySQLTaskDAOAdapter(connection);

        // 3. Crear repositorio pasando el DAO
        taskRepository = new TaskRepository(taskDao);

        // 4. Crear servicio pasando el repositorio
        taskService = new TaskService(taskRepository);

        // 5. Crear el menú principal
        mainMenu = new MainMenu(scanner, taskService);

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



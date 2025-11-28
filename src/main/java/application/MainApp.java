package application;
import common.dao.GenericDAO;
import menu.MainMenu;
import task.model.Task;
import task.repository.TaskRepository;
import task.service.TaskService;
import java.util.Scanner;

public class MainApp {

    private Scanner scanner;
    private TaskRepository taskRepository;
    private TaskService taskService;
    private MainMenu mainMenu;

    //inicializar dependencias
    private void init() {
        scanner = new Scanner(System.in);

/*
        GenericDAO<Task> taskDao = new GenericDAO<>();
        taskRepository = new TaskRepository(taskDao);
        taskService = new TaskService(taskRepository);
        mainMenu = new MainMenu(scanner, taskService);
  */

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



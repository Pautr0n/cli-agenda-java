package application;
import menu.MainMenu;
import task.model.Task;
import task.repository.TaskRepository;
import task.service.TaskService;
import common.dao.GenericDAO;
import java.util.Scanner;



public class MainApp {

    static Scanner scanner = new Scanner(System.in);
    static TaskRepository taskRepository = new TaskRepository(GenericDAO < Task > taskDao);
    static TaskService taskService = new TaskService(taskRepository);
    static MainMenu mainMenu = new MainMenu(scanner, taskService);



    /*private static runProgram(){
    }*/

    public static void main(String[] args) {
        MainMenu menu = new MainMenu(scanner,taskService);
        menu.start();
    }
}


package application;
import menu.MainMenu;


//EMN ESTA CLASE SE DEBEN INSTANCIAS POR PRIMERA VEZ EL TASK SERVICE, TASK REPOSITORY ETC?

public class MainApp {

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.start();
    }
}



/*
public class MainApp {
    public static void main(String[] args) {
        // Crear el repositorio primero
        TaskRepository taskRepository = new TaskRepository();

        // Crear el servicio pasando el repositorio
        TaskService taskService = new TaskService(taskRepository);

        // Crear el menú principal pasando el scanner y el servicio
        Scanner scanner = new Scanner(System.in);

        MainMenu mainMenu = new MainMenu(scanner, taskService);

        // Iniciar el menú principal
        mainMenu.start();
    }
}

 */

//Al crear las dependencias en el MainApp y pasarlas a las demás clases,
// mantienes cada clase enfocada en su responsabilidad y evitas mezclar lógica de infraestructura con la lógica de presentación.
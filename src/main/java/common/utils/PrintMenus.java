package common.utils;

import task.dto.TaskOutputDTO;

import java.time.LocalDate;
import java.util.List;

public class PrintMenus {

    public static void showTaskMenu() {
        System.out.println("\n ***** MENÚ DE TAREAS ****");
        System.out.println("1. Crear tarea");
        System.out.println("2. Listar tareas");
        System.out.println("3. Ver tarea por ID");
        System.out.println("4. Marcar tarea como completada");
        System.out.println("5. Actualizar tarea");
        System.out.println("6. Eliminar tarea");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: \n");
    }

    public static void showNoteMenu(){
        System.out.println("\n ***** MENÚ DE NOTAS ****");
        System.out.println("1. Crear nota");
        System.out.println("2. Listar notas");
        System.out.println("3. Ver nota por ID");
        System.out.println("4. Actualizar nota");
        System.out.println("5. Eliminar nota");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: \n");
    }


    public static void printMainMenu() {

        System.out.println(" \n ||#############################");
        System.out.println(" ||#                           #");
        System.out.println(" ||#        "+ LocalDate.now()+ "         #");
        System.out.println(" ||#  ***     AGENDA    ***    #");
        System.out.println(" ||#                           #");
        System.out.println(" ||#     Elige una opción:     #");
        System.out.println(" ||#   1. Gestionar tareas     #");
        System.out.println(" ||#   2. Gestionar notas      #");
        System.out.println(" ||#   3. Gestionar eventos    #");
        System.out.println(" ||#   0. Salir                #");
        System.out.println(" ||#                           #");
        System.out.println(" ||#                           #");
        System.out.println(" ||#      Developers Team      #");
        System.out.println(" ||#           P-A-J           #");
        System.out.println(" ||#                           #");
        System.out.println(" ||#############################\n");
    }

    /*****************************************************************/

    //MENU CREATE TASK

    public static void printMenuCreateTask(TaskOutputDTO dto) {

        System.out.println(" __________________________________________________");
        System.out.println("| ID: " + dto.id());
        System.out.println("| Título: " + dto.title());
        System.out.println("| Contenido: " + dto.content());
        System.out.println("| Fecha: " + dto.expirationDate());
        System.out.println("| Prioridad: " + dto.priority());
        System.out.println("| Estado: " + dto.doneStatus());
        System.out.println("|___________________________________________________");

    }

    public static void printMenuCreateNote(NoteOutputDTO dto){
        System.out.println(" __________________________________________________");
        System.out.println("| ID: " + dto.id());
        System.out.println("| Título: " + dto.title());
        System.out.println("| Contenido: " + dto.content());
        System.out.println("|___________________________________________________");
    }


    //MENU INTERNO LIST TASK
    public static void printMenuListTask (){

        System.out.println("\n--- LISTAR TAREAS ---");
        System.out.println("1. Todas");
        System.out.println("2. Pendientes");
        System.out.println("3. Completadas");
        System.out.println("0. Volver\n");
        System.out.print("Selecciona una opción: ");

    }

    //IMPRESION DE  ELECCION LIST TASK
    public static void printTaskList(List<TaskOutputDTO> tasks) {

        if (tasks.isEmpty()) {
            System.out.println("No hay tareas para mostrar.");
            return;
        }
        System.out.println("\n***LISTADO DE TAREAS***");
        for (TaskOutputDTO dto : tasks) {
            System.out.println("---------------------------------->");
            System.out.println("ID: " + dto.id());
            System.out.println("Título: " + dto.title());

        }
    }

    public static void printNoteList(List<NoteOutputDTO> notes){
        if (notes.isEmpty()) {
            System.out.println("No hay notas para mostrar.");
            return;
        }
        System.out.println("\n***LISTADO DE NOTAS***");
        for (NoteOutputDTO dto : notes) {
            System.out.println("---------------------------------->");
            System.out.println("ID: " + dto.id());
            System.out.println("Título: " + dto.title());

        }
    }


    //MENSAJE TASCA MARCADA COMO COMPLETADA
    public static void printMarkTask(TaskOutputDTO dto){
        System.out.println(" _______________________________");
        System.out.println("| Tarea con ID: " + dto.id());
        System.out.println("| " + dto.title());
        System.out.println("| Marcada como completada!");
        System.out.println("|_______________________________");

    }


    //MENU INTERNO UPDATE TASK

    public static void printMenuUpdateTask(){

        System.out.println("\n| ¿Qué contenido deseas modificar?");
        System.out.println("| 1- Título");
        System.out.println("| 2- Contenido");
        System.out.println("| 3- Fecha");
        System.out.println("| 4- Prioridad");
        System.out.println("| 0- Salir y aceptar cambios.");

    }

    public static void printMenuUpdateNote(){
        System.out.println("\n| ¿Qué contenido deseas modificar?");
        System.out.println("| 1- Título");
        System.out.println("| 2- Contenido");
        System.out.println("| 0- Salir y aceptar cambios");
    }


    // IMPRESION UPDATE TASK
    public static void printMenuTaskUpdated(TaskOutputDTO dto) {
        System.out.println("***TAREA ACTUALIZADA***");
        System.out.println(" __________________________________________________");
        System.out.println("| ID: " + dto.id());
        System.out.println("| Título: " + dto.title());
        System.out.println("| Contenido: " + dto.content());
        System.out.println("| Fecha: " + dto.expirationDate());
        System.out.println("| Prioridad: " + dto.priority());
        System.out.println("| Estado: " + dto.doneStatus());
        System.out.println("|___________________________________________________");


    }

    public static void printMenuNoteUpdated(NoteOutputDTO dto){
        System.out.println("***NOTA ACTUALIZADA***");
        System.out.println(" __________________________________________________");
        System.out.println("| ID: " + dto.id());
        System.out.println("| Título: " + dto.title());
        System.out.println("| Contenido: " + dto.content());
        System.out.println("|___________________________________________________");

    }

    //IMPRESIÓN DELETE TASK

    //MENSAJE TASCA MARCADA COMO ELIMINADA
    public static void printDeleteTask(int id){
        System.out.println(" ___________________________________________________");
        System.out.println("| Tarea con ID: " + id + " Eliminada correctamente!");
        System.out.println("|___________________________________________________");

    }

    public static void printDeleteNote(int id){
        System.out.println(" ___________________________________________________");
        System.out.println("| Nota con ID: " + id + " Eliminada correctamente!");
        System.out.println("|___________________________________________________");
    }

}

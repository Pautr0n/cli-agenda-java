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
        System.out.print("Elige una opción: ");
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
        System.out.println(dto.id());
        System.out.println(dto.title());
        System.out.println(dto.content());
        System.out.println(dto.expirationDate());
        System.out.println(dto.priority());
        System.out.println(dto.doneStatus());

    }

    //FALTA LIST TASK

    //MENU INTERNO LIST TASK
    public static void printMenuListTask (){

        System.out.println("--- LISTAR TAREAS ---");
        System.out.println("1. Todas");
        System.out.println("2. Pendientes");
        System.out.println("3. Completadas");
        System.out.print("Selecciona una opción: ");

    }

    //IMPRESION DE  ELECCION LIST TASK
    public static void printTaskList(List<TaskOutputDTO> tasks) {

        if (tasks.isEmpty()) {
            System.out.println("No hay tareas para mostrar.");
            return;
        }

        for (TaskOutputDTO dto : tasks) {
            System.out.println("ID: " + dto.id());
            System.out.println("Título: " + dto.title());
            System.out.println("------------------------");
        }
    }




    //MENSAJE TASCA MARCADA COMO COMPLETADA
    public static void printMarkTask(TaskOutputDTO dto){
        System.out.println("Tarea con ID: "+dto.id()+"\n"+
                dto.title()+"\n"+
                "Marcada como completada!");
    }


    //MENU INTERNO UPDATE TASK

    public static void printMenuUpdate (){

        System.out.println("""
                    ¿Qué contenido deseas modificar?
                    1- Título
                    2- Contenido
                    3- Fecha
                    4- Prioridad
                    0- Ejecutar cambio / Salir
                    """);

    }



    // IMPRESION UPDATE TASK
    public static void printMenuCreateUpdateTask(TaskOutputDTO dto) {
        System.out.println("***TAREA ACTUALIZADA***");
        System.out.println("ID: "+dto.id());
        System.out.println("Titulo: "+dto.title());
        System.out.println("Contenido: "+dto.content());
        System.out.println("Fecha: "+ (dto.expirationDate()));
        System.out.println("Prioridad: "+dto.priority());
        System.out.println("Estatus: "+ dto.doneStatus());

    }

    //IMPRESIÓN DELETE TASK

    //MENSAJE TASCA MARCADA COMO ELIMINADA
    public static void printDeleteTask(int id){
        System.out.println("Tarea con ID: "+id+" Eliminada correctamente!");
    }





}

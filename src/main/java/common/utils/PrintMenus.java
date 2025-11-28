package common.utils;

import java.time.LocalDate;

public class PrintMenus {

    public static void showTaskMenu() {
        System.out.println("\n ***** MENÚ DE TAREAS ****");
        System.out.println("1. Crear tarea");
        System.out.println("2. Listar tareas");
        System.out.println("3. Ver tarea por ID");
        System.out.println("4. Marcar tarea como completada");
        System.out.println("5. Listar tareas completadas");
        System.out.println("6. Actualizar tarea (pendiente)");
        System.out.println("7. Eliminar tarea");
        System.out.println("8. Volver");
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



}

package task.service;
import task.enums.PriorityType;
import task.model.Task;

import java.time.LocalDate;


public class AppMain {
    public static void main(String[] args) {


        Task task1 = new Task ("Hacer la compra","Lechuga,huevos,carne,leche,madalenas,un pollo.", LocalDate.of(2025,11,28));
        System.out.println(task1.toString());

        Task task2 = new Task ("Cine con Amigos", "Comprar entradas, llamar a Juan y planear cena después", LocalDate.of(2025,11,30),PriorityType.HIGH);
        System.out.println(task2);

        Task task3 = new Task ("", "Comprar entradas, llamar a Juan y planear cena después", LocalDate.of(2025,11,30),PriorityType.HIGH);
        System.out.println(task2);

    }

}

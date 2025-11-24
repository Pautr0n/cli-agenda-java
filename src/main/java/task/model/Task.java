package task.model;

import task.enums.DoneType;
import task.enums.PriorityType;

import java.time.LocalDate;

public class Task {

    private Integer id;
    private String title;
    private String content;
    private LocalDate expirationDate;
    private LocalDate creationDate;
    private PriorityType priority;
    private DoneType doneStatus;

    public Task(String title, String content, LocalDate expirationDate) {

        if (title == null || title.isBlank()) {   // maravilla el isblank para que nadie ingrese epsacios vacios.
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("El contenido no puede estar vacío");
        }

        this.title = title;
        this.content = content;
        this.expirationDate = expirationDate;
        this.creationDate = LocalDate.now();
        this.priority = PriorityType.MEDIUM;
        this.doneStatus = DoneType.NOTDONE;
    }


    public Task(String title, String content, LocalDate expirationDate, PriorityType priority) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("El contenido no puede estar vacío");
        }

        this.title = title;
        this.content = content;
        this.expirationDate = expirationDate;
        this.creationDate = LocalDate.now();
        this.priority = (priority != null) ? priority : PriorityType.MEDIUM;
        this.doneStatus = DoneType.NOTDONE;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public void setDoneStatus(DoneType doneStatus) {
        this.doneStatus = doneStatus;
    }


    @Override
    public String toString() {
        return "\n***" + title.toUpperCase() + "***\n"
                +"-"+ content + "\n" +
                "-Día:" + expirationDate +"\n"+
                "-Prioridad: " + priority + "\n"+
                "-Estatus:  " + doneStatus +"\n"+
                "-Tarea creada el día: " + creationDate +"\n";
    }
}



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

    public Task(String title, String content, LocalDate expirationDate, PriorityType priority) {


        this.title = title;
        this.content = content;
        this.expirationDate = expirationDate;
        this.creationDate = LocalDate.now();
        this.priority = priority;
        this.doneStatus = DoneType.NOTDONE;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public PriorityType getPriority() {
        return priority;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public DoneType getDoneStatus() {
        return doneStatus;
    }

    public void setDoneStatus(DoneType doneStatus) {
        this.doneStatus = doneStatus;
    }
}



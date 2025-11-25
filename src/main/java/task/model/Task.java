package task.model;

import task.enums.DoneType;
import task.enums.PriorityType;
import java.time.LocalDateTime;

public class Task {

    private Integer id;
    private String title;
    private String content;
    private LocalDateTime expirationDate;
    private LocalDateTime creationDate;
    private PriorityType priority;
    private DoneType doneStatus;

    public Task() {
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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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



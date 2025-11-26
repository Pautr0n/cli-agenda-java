package task.dto;

public class TaskCreateDTO {

    private final String title;
    private final String content;
    private final String expirationDate;
    private final String priority;

    public TaskCreateDTO(String title, String content, String expirationDate, String priority) {
        this.title = title;
        this.content = content;
        this.expirationDate = expirationDate;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "TaskCreateDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}

package note.model;


import java.time.LocalDateTime;

public class Note {

    private Integer id;
    private String title;
    private String content;
    private LocalDateTime creationDate;

    public Note(){

    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

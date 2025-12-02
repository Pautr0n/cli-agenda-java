package note.dto;

public record NoteUpdateDTO (
        Integer id,
        String title,
        String content
)
{ }

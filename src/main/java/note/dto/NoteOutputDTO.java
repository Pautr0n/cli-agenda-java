package note.dto;

import java.time.LocalDateTime;

public record NoteOutputDTO(
        int id,
        String title,
        String content,
        LocalDateTime creationDate
) { }

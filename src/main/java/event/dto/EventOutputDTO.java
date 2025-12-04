package event.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventOutputDTO(
        int id,
        String title,
        String content,
        LocalDateTime creationDate,
        LocalDate expirationDate
) { }

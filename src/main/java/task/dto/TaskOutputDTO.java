package task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskOutputDTO (

    int id,
    String title,
    String content,
    LocalDateTime creationDate,
    LocalDate expirationDate,
    String priority,
    String doneStatus

) {}

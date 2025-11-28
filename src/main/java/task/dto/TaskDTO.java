package task.dto;

public record TaskDTO(
        String title,
        String content,
        String expirationDate,
        String priority
) { }

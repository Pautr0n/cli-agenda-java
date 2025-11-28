package task.dto;

public record TaskUpdateDTO(
        Integer id,
        String title,
        String content,
        String expirationDate,
        String priority
) { }

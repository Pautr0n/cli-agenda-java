package event.dto;

public record EventUpdateDTO(
        Integer id,
        String title,
        String content,
        String expirationDate
) { }

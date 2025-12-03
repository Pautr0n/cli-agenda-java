package event.mapper;

import event.dto.EventDTO;
import event.dto.EventOutputDTO;
import event.dto.EventUpdateDTO;
import event.model.*;
import java.time.LocalDateTime;

public class EventDTOMapper {

    public static Event dtoToEvent(EventDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("EventDTO cannot be null");
        }

        Event event = new Event();
        event.setTitle(dto.title());
        event.setContent(dto.content());
        event.setCreationDate(dto.creationDate() != null ? dto.creationDate() : LocalDateTime.now());
        event.setExpirationDate(dto.expirationDate());
        return event;
    }

    public static Event dtoToEvent(EventUpdateDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("EventUpdateDTO cannot be null");
        }

        Event event = new Event();
        event.setId(dto.id());


        if (dto.title() != null && !dto.title().isBlank()) {
            event.setTitle(dto.title());
        }

        if (dto.content() != null && !dto.content().isBlank()) {
            event.setContent(dto.content());
        }

        return event;
    }

    public static EventOutputDTO eventToDTO(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        return new EventOutputDTO(
                event.getId(),
                event.getTitle(),
                event.getContent(),
                event.getCreationDate(),
                event.getExpirationDate()
        );
    }
}
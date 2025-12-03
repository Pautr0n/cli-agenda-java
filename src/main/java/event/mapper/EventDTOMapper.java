package event.mapper;

import event.dto.EventDTO;
import event.dto.EventOutputDTO;
import event.dto.EventUpdateDTO;
import event.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventDTOMapper {

    public static Event dtoToEvent(EventDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("EventDTO cannot be null");
        }

        Event event = new Event();
        event.setTitle(dto.title());
        event.setContent(dto.content());
        event.setCreationDate(LocalDateTime.now());
        event.setExpirationDate(LocalDate.parse(dto.expirationDate()));
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
package event.service;

import common.exception.DataAccessException;
import common.exception.EntityNotFoundException;
import common.exception.ServiceException;
import common.exception.ValidationException;
import event.dto.EventDTO;
import event.dto.EventIdDTO;
import event.dto.EventOutputDTO;
import event.dto.EventUpdateDTO;
import event.mapper.EventDTOMapper;
import event.model.Event;
import event.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Create EVENT
    public EventOutputDTO createEvent(EventDTO dto) {
        try {
            validateEventDTOCreate(dto);

            Event event = EventDTOMapper.dtoToEvent(dto);
            event = eventRepository.add(event);

            return EventDTOMapper.eventToDTO(event);

        } catch (EntityNotFoundException | DataAccessException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("EventService [createEvent]: Unexpected error creating event", e);
        }
    }

    // Read One Event
    public EventOutputDTO getEventById(EventIdDTO id) {
        try {
            validateEventId(id);
            Event event = eventRepository.getById(id.id());

            if (event == null) {
                throw new EntityNotFoundException("Event with id " + id.id() + " not found");
            }

            return EventDTOMapper.eventToDTO(event);

        } catch (EntityNotFoundException | DataAccessException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("EventService [getEventById]: Unexpected error retrieving event with id " + id.id(), e);
        }
    }

    // Read ALL EVENT
    public List<EventOutputDTO> getAllEvents() {
        try {
            return eventRepository.getAll().stream()
                    .map(EventDTOMapper::eventToDTO)
                    .toList();

        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("EventService [getAllEvents]: Unexpected error retrieving events", e);
        }
    }

    // Update Event
    public EventOutputDTO updateEvent(EventUpdateDTO dto) {
        try {
            validateEventUpdate(dto);

            Event event = eventRepository.getById(dto.id());

            if (event == null) {
                throw new EntityNotFoundException("Event with id " + dto.id() + " not found");
            }

            if (dto.title() != null && !dto.title().isBlank()) {
                event.setTitle(dto.title());
            }

            if (dto.content() != null && !dto.content().isBlank()) {
                event.setContent(dto.content());
            }

            eventRepository.update(event);

            return EventDTOMapper.eventToDTO(event);

        } catch (EntityNotFoundException | DataAccessException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("EventService [updateEvent]: Unexpected error updating event id=" + dto.id(), e);
        }
    }

    // Delete EVENT
    public void deleteEvent(EventIdDTO id) {
        try {
            validateEventId(id);
            eventRepository.remove(id.id());
        } catch (EntityNotFoundException | DataAccessException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("EventService [deleteEvent]: Unexpected error deleting event id=" + id.id(), e);
        }
    }

    //******************************************************************

    // Validations
    private void validateEventDTOCreate(EventDTO dto) {
        if (dto == null) {
            throw new ValidationException("EventDTO instance cannot be null");
        }

        if (dto.content() == null || dto.content().isBlank()) {
            throw new ValidationException("Content cannot be empty");
        }

        if (dto.title() == null || dto.title().isBlank()) {
            throw new ValidationException("Title cannot be empty");
        }

        if (dto.creationDate() == null) {
            throw new ValidationException("Creation date cannot be null");
        }

        if (dto.expirationDate() == null) {
            throw new ValidationException("Expiration date cannot be null");
        }
        //RECOMENDACION DEEP SEEK!
        // Validar que expirationDate no sea anterior a creationDate
        if (dto.expirationDate().isBefore(dto.creationDate().toLocalDate())) {
            throw new ValidationException("Expiration date cannot be before creation date");
        }

        // Opcional: validar que expirationDate no sea en el pasado
        if (dto.expirationDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Expiration date cannot be in the past");
        }
    }

    private void validateEventId(EventIdDTO id) {
        if (id == null) {
            throw new ValidationException("EventIdDTO instance cannot be null");
        }
        if (id.id() == null) {
            throw new ValidationException("Event id value cannot be null");
        }
        if (id.id() <= 0) {
            throw new ValidationException("Invalid id: " + id.id());
        }
    }

    private void validateEventUpdate(EventUpdateDTO dto) {
        if (dto == null) {
            throw new ValidationException("EventUpdateDTO instance cannot be null");
        }

        if (dto.id() <= 0) {
            throw new ValidationException("Invalid id: " + dto.id());
        }

        if ((dto.title() == null || dto.title().isBlank()) &&
                (dto.content() == null || dto.content().isBlank())) {
            throw new ValidationException("No fields provided to update");
        }
    }
}
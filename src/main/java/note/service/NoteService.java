package note.service;

import common.exception.DataAccessException;
import task.dto.TaskDTO;
import task.dto.TaskIdDTO;
import task.dto.TaskOutputDTO;
import task.dto.TaskUpdateDTO;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.mapper.TaskDTOMapper;
import task.model.Task;
import task.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;

    }


    //CRUD a preparar Variables de la nota
    //
    //Titulo
    //Texto
    //Fecha de creación

//CRUD

    // 1.Create note
    public NoteOutputDTO createNote (NoteDTO dto) {

        try {
            validateNoteDTOCreate(dto);

            Note note = NoteDTOMapper.dtoToNote(dto);
            note = taskRepository.add(note);

            return NoteDTOMapper.noteToDTO(note);

        } catch (Exception e) {
            throw new DataAccessException("Error creating note", e);
        }
    }

    // continuar a partir de aqui

    //Read One note
    public NoteOutputDTO getNoteById(NoteIdDTO id) {
        try {
            validateId(id);
            Note note = noteRepository.getById(id.id());

            if (note == null) {
                throw new IllegalArgumentException("Note with id " + id.id() + " not found");
            }

            return NoteDTOMapper.noteToDTO(note);

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving note with id: " + id.id(), e);
        }
    }

    //Read ALL Notes
    public List<NoteOutputDTO> getAllNotes() {
        try {

            return noteRepository.getAll().stream()
                    .map(NoteDTOMapper::noteToDTO)
                    .toList();

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving note from database.", e);
        }
    }


    //Update
    public NoteOutputDTO updateNote(NoteUpdateDTO dto) {
        try {

            validateNoteUpdate(dto);

             Note note = noteRepository.getById(dto.id());

            if (dto.title() != null && !dto.title().isBlank()) {
                note.setTitle(dto.title());
            }

            if (dto.content() != null && !dto.content().isBlank()) {
                note.setContent(dto.content());
            }

            noteRepository.update(note);

            return NoteDTOMapper.noteToDTO(note);

        } catch (Exception e) {
            throw new DataAccessException("Error updating note with id: " + dto.id() + " ", e);
        }
    }

    //Delete task
    public void deleteNote(NoteIdDTO id) {

        try {

            validateNote(id);

            noteRepository.remove(id.id());


        } catch (DataAccessException e) {
            throw new DataAccessException("Exception while deleting note with id: " + id.id(), e);
        }
    }

    

    //******************************************************************
/*
    //Validations
    private void validateTaskDTOCreate(TaskDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO record instance cannot be null");
        }

        if (dto.content() == null || dto.content().isBlank()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }

        if (dto.title() == null || dto.title().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (dto.expirationDate() == null || dto.expirationDate().isBlank()) {
            throw new IllegalArgumentException("Expiration date cannot be empty");
        }

        try {
            LocalDate.parse(dto.expirationDate());
        } catch (Exception e) {
            throw new IllegalArgumentException("Expiration date format must be yyyy-MM-dd", e);
        }

    }

    private void validateTaskId(TaskIdDTO id) {
        if (id == null) {
            throw new IllegalArgumentException("DTO record instance cannot be null");
        }
        if (id.id() == null) {
            throw new IllegalArgumentException("Task id value cannot be null");
        }
        if (id.id() <= 0) {
            throw new IllegalArgumentException("Invalid id: " + id.id());
        }
    }


    private void validateTaskUpdate(TaskUpdateDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO record instance cannot be null");
        }

        if (dto.id() <= 0) {
            throw new IllegalArgumentException("Invalid id.");
        }

        if ((dto.title() == null || dto.title().isBlank()) &&
                (dto.content() == null || dto.content().isBlank()) &&
                (dto.expirationDate() == null || dto.expirationDate().isBlank()) &&
                (dto.priority() == null || dto.priority().isBlank())) {


            throw new IllegalArgumentException("No fields provided to update");

        }

        if (dto.expirationDate() != null && !dto.expirationDate().isBlank()) {
            try {
                LocalDate.parse(dto.expirationDate());
            } catch (Exception e) {
                throw new IllegalArgumentException("Expiration date format must be yyyy-MM-dd");
            }
        }

    }
*/
}

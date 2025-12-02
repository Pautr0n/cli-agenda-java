package note.service;

import common.exception.DataAccessException;
import common.exception.EntityNotFoundException;
import common.exception.ValidationException;
import note.dto.NoteDTO;
import note.dto.NoteIdDTO;
import note.dto.NoteOutputDTO;
import note.dto.NoteUpdateDTO;
import note.mapper.NoteDTOMapper;
import note.model.Note;
import note.repository.NoteRepository;
import note.dto.*;
import common.exception.*;



import java.util.List;

public class NoteService {


    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;

    }

    //CRUD
    // Create NOTE
    public NoteOutputDTO createNote(NoteDTO dto) {

        try {
            validateNoteDTOCreate(dto);

            Note note = NoteDTOMapper.dtoToNote(dto);
            note = noteRepository.add(note);

            return NoteDTOMapper.noteToDTO(note);

        } catch (EntityNotFoundException | DataAccessException | ValidationException e ) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("NoteService [createNote]: Unexpected error creating note", e);
        }

    }

    //Read One NOTE

    public NoteOutputDTO getNoteById(NoteIdDTO id) {
        try {
            validateNoteId(id);
            Note note = noteRepository.getById(id.id());

            if (note == null) {
                throw new IllegalArgumentException("Note with id " + id.id() + " not found");
            }

            return NoteDTOMapper.noteToDTO(note);

        } catch (EntityNotFoundException | DataAccessException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("NoteService [getNoteById]: Unexpected error retrieving note with id " + id.id(), e);
        }

    }

    //Read ALL NOTES
    public List<NoteOutputDTO> getAllNotes() {
        try {

            return noteRepository.getAll().stream()
                    .map(NoteDTOMapper::noteToDTO)
                    .toList();

        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("NoteService [getAllNotes]: Unexpected error retrieving note", e);
        }
    }


    //Update NOTE
    public NoteOutputDTO updateNote(NoteUpdateDTO dto) {
        validateNoteUpdate(dto);

        try {
            Note note = noteRepository.getById(dto.id());

            if (note == null) {
                throw new EntityNotFoundException("Note with id " + dto.id() + " not found");
            }

            if (dto.title() != null && !dto.title().isBlank()) {
                note.setTitle(dto.title());
            }

            if (dto.content() != null && !dto.content().isBlank()) {
                note.setContent(dto.content());
            }


            noteRepository.update(note);

            return NoteDTOMapper.noteToDTO(note);

        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("NoteService [updateNote]: Unexpected error updating note id=" + dto.id(), e);
        }

    }


    //Delete NOTE
    public void deleteNote(NoteIdDTO id) {

        try {
            validateNoteId(id);
            noteRepository.remove(id.id());
        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("NoteService [deleteTask]: Unexpected error deleting note id=" + id.id(), e);
        }
    }



    //******************************************************************


    //Validations
    private void validateNoteDTOCreate(NoteDTO dto) {

        if (dto == null) {
            throw new  ValidationException("DTO record instance cannot be null");
        }

        if (dto.content() == null || dto.content().isBlank()) {
            throw new ValidationException("Content cannot be empty");
        }

        if (dto.title() == null || dto.title().isBlank()) {
            throw new ValidationException("Title cannot be empty");
        }


    }

    private void validateNoteId(NoteIdDTO id) {
        if (id == null) {
            throw new ValidationException("DTO record instance cannot be null");
        }
        if (id.id() == null) {
            throw new ValidationException("Note id value cannot be null");
        }
        if (id.id() <= 0) {
            throw new ValidationException("Invalid id: " + id.id());
        }
    }

    private void validateNoteUpdate(NoteUpdateDTO dto) {

        if (dto == null) {
            throw new ValidationException("DTO record instance cannot be null");
        }

        if (dto.id() <= 0) {
            throw new ValidationException("Invalid id.");
        }

        if ((dto.title() == null || dto.title().isBlank()) &&
                (dto.content() == null || dto.content().isBlank())) {

            throw new ValidationException("No fields provided to update");
        }

    }


}



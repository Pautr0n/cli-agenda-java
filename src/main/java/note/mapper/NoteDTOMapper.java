package note.mapper;

import note.dto.NoteDTO;
import note.dto.NoteOutputDTO;
import note.dto.NoteUpdateDTO;
import note.model.Note;
import java.time.LocalDateTime;


public class NoteDTOMapper {

    public static Note dtoToNote(NoteDTO dto) {
        Note note = new Note();

        note.setTitle(dto.title());
        note.setContent(dto.content());
        note.setCreationDate(LocalDateTime.now());
        return note;
    }

    public static Note dtoToNote(NoteUpdateDTO dto) {
        Note note = new Note();
        note.setId(dto.id());
        note.setTitle(dto.title());
        note.setContent(dto.content());

        return note;
    }

    public static NoteOutputDTO noteToDTO(Note note) {

        return new NoteOutputDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreationDate()
        );
    }
}

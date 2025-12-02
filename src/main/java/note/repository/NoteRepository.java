package note.repository;

import common.dao.GenericDAO;
import common.repository.EntityRepository;
import note.model.Note;


public class NoteRepository extends EntityRepository<Note> {

    public NoteRepository(GenericDAO<Note> noteDao){
        super(noteDao);
    }
}

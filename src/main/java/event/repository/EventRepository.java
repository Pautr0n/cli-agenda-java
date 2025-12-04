package event.repository;

import common.dao.GenericDAO;
import common.repository.EntityRepository;
import event.model.Event;

public class EventRepository extends EntityRepository<Event> {

    public EventRepository(GenericDAO<Event> eventDao){
        super(eventDao);
    }
}

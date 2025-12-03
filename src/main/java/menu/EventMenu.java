package menu;

import common.observer.ExpirableObserver;
import common.publisher.ExpirableChecker;
import common.publisher.ExpirableService;
import event.dto.EventOutputDTO;
//import event.publisher.EventChecker;
import event.service.EventService;
import task.dto.TaskOutputDTO;
import task.service.TaskService;

import java.util.List;
import java.util.Scanner;

public class EventMenu implements ExpirableObserver<EventOutputDTO> {

    private final Scanner scanner;
    private final EventService eventService;
    //private final ExpirableService<EventOutputDTO> expirableService;
    //private final EventChecker eventChecker;
    private int expiredCount = 0;

    public EventMenu(Scanner scanner, EventService eventService){
        this.scanner = scanner;
        this.eventService = eventService;
       // this.eventChecker = new EventChecker();

        //List<EventOutputDTO> events = eventService.getAllEvents();
        //this.expirableService = new ExpirableService<>(events);
        //this.expirableService.addObserver(this);
        //this.expirableService.notifyExpired(eventChecker);
    }


    @Override
    public void onExpired(List<EventOutputDTO> expiredEntities) {

    }
}

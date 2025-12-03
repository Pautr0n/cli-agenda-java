package event.publisher;

import common.publisher.ExpirableChecker;
import event.dto.EventOutputDTO;
import event.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

//public class EventChecker implements ExpirableChecker<EventOutputDTO> {
//    @Override
//    public boolean isExpired(EventOutputDTO dto) {
//        LocalDate expiration =dto.expirationDate();
//        return expiration.isBefore(LocalDate.now()) || expiration.isEqual(LocalDate.now());
//    }
//
//}

package task.publisher;

import common.publisher.ExpirableChecker;
import task.dto.TaskOutputDTO;

import java.time.LocalDate;

public class TaskChecker implements ExpirableChecker<TaskOutputDTO> {
    @Override
    public boolean isExpired(TaskOutputDTO dto) {
        LocalDate expiration =dto.expirationDate();

        if (expiration == null) {
            return false;
        }

        return expiration.isBefore(LocalDate.now()) || expiration.isEqual(LocalDate.now());
    }
}

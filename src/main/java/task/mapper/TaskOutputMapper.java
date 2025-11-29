package task.mapper;

import task.model.Task;
import task.dto.TaskOutputDTO;

public class TaskOutputMapper {

    // TO DTO
    public static TaskOutputDTO toDTO(Task task){

        String priority = task.getPriority() != null
                ? task.getPriority().name() : "MEDIUM";

        String status = task.getDoneStatus() != null
                ? task.getDoneStatus().name() : "NOTDONE";

        return new TaskOutputDTO(
            task.getId(),
            task.getTitle(),
            task.getContent(),
            task.getCreationDate(),
            task.getExpirationDate(),
            priority,
            status
        );
    }
}


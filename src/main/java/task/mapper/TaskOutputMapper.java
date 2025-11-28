package task.mapper;

import task.model.Task;

public class TaskOutputMapper {

    // TO DTO
    public static TaskOutputDTO toDTO(Task task){

        return new TaskOutputDTO(
            task.getId(),
            task.getTitle(),
            task.getContent(),
            task.getCreationDate(),
            task.getExpirationDate(),
            task.getPriority().name(),
            task.getDoneStatus().name());
    }
}


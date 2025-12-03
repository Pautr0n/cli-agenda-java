package task.mapper;

import task.dto.TaskDTO;
import task.dto.TaskOutputDTO;
import task.dto.TaskUpdateDTO;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskDTOMapper {

    public static Task dtoToTask(TaskDTO dto) {
        Task task = new Task();

        task.setTitle(dto.title());
        task.setContent(dto.content());
        task.setCreationDate(LocalDateTime.now());
        task.setExpirationDate(LocalDate.parse(dto.expirationDate()));

        PriorityType priority = PriorityType.MEDIUM;
        if (dto.priority() != null && !dto.priority().isBlank()) {
            priority = PriorityType.valueOf(dto.priority().toUpperCase());
        }

        task.setPriority(priority);

        task.setDoneStatus(DoneType.NOTDONE);

        return task;
    }

    public static Task dtoToTask(TaskUpdateDTO dto) {
        Task task = new Task();
        task.setId(dto.id());
        task.setTitle(dto.title());
        task.setContent(dto.content());
        task.setCreationDate(LocalDateTime.now());
        task.setExpirationDate(LocalDate.parse(dto.expirationDate()));

        PriorityType priority = PriorityType.MEDIUM;
        if (dto.priority() != null && !dto.priority().isBlank()) {
            priority = PriorityType.valueOf(dto.priority().toUpperCase());
        }

        task.setPriority(priority);

        task.setDoneStatus(DoneType.NOTDONE);

        return task;
    }

    public static TaskOutputDTO taskToDTO(Task task) {

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

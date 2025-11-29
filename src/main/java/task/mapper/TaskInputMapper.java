package task.mapper;

import task.dto.TaskUpdateDTO;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;
import task.dto.TaskDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskInputMapper {

    //TaskDTO to Entity
    public static Task toEntity(TaskDTO dto){
        Task task = new Task();

        task.setTitle(dto.title());
        task.setContent(dto.content());
        task.setCreationDate(LocalDateTime.now());
        task.setExpirationDate(LocalDate.parse(dto.expirationDate()));

        PriorityType priority = PriorityType.MEDIUM;
        if(dto.priority() != null && !dto.priority().isBlank()){
            priority = PriorityType.valueOf(dto.priority().toUpperCase());
        }

        task.setPriority(priority);

        task.setDoneStatus(DoneType.NOTDONE);

        return task;
    }

    // Update

    public static void applyUpdates(Task task, TaskUpdateDTO dto){

        if (dto.title() != null && !dto.title().isBlank()) {
            task.setTitle(dto.title());
        }

        if (dto.content() != null && !dto.content().isBlank()) {
            task.setContent(dto.content());
        }

        if (dto.expirationDate() != null && !dto.expirationDate().isBlank()) {
            task.setExpirationDate(LocalDate.parse(dto.expirationDate()));
        }

        if (dto.priority() != null && !dto.priority().isBlank()) {
            task.setPriority(PriorityType.valueOf(dto.priority().toUpperCase()));
        }


    }
}

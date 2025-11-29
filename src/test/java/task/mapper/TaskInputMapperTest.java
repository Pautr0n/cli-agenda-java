package task.mapper;

import org.junit.jupiter.api.Test;
import task.dto.TaskDTO;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskInputMapperTest {

    @Test
    void testToEntity_ConvertsDTOToTask(){
        TaskDTO dto = new TaskDTO(
                "Comprar pan",
                "Ir a la panaderia",
                "2025-11-28",
                "HIGH"
        );

        Task task = TaskInputMapper.toEntity(dto);

        assertEquals("Comprar pan", task.getTitle());
        assertEquals("Ir a la panaderia", task.getContent());
        assertEquals(LocalDate.of(2025, 11, 28), task.getExpirationDate());
        assertEquals(DoneType.NOTDONE, task.getDoneStatus());
        assertNotNull(task.getCreationDate(), "Creation date must be set automatically");
    }

    @Test
    void testToEntity_whenUsesDefaultPriority_thenDefaultsMedium(){
        TaskDTO dto = new TaskDTO(
                "Comprar pan",
                "Ir a la panaderia",
                "2025-11-28",
                null
        );

        Task task = TaskInputMapper.toEntity(dto);

        assertEquals(PriorityType.MEDIUM, task.getPriority());
    }

}
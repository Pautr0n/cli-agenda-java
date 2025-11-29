package task.mapper;

import org.junit.jupiter.api.Test;
import task.dto.TaskOutputDTO;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskOutputMapperTest {

    @Test
    void testToDTO_MapAllFields(){

        Task task = new Task();
        task.setId(10);
        task.setTitle("Estudiar patrones");
        task.setContent("Revisar singleton, Factory, Fluent");
        task.setCreationDate(LocalDateTime.of(2025,2,10,20,30));
        task.setExpirationDate(LocalDate.of(2025,3,5));
        task.setPriority(PriorityType.HIGH);
        task.setDoneStatus(DoneType.DONE);

        TaskOutputDTO dto = TaskOutputMapper.toDTO(task);

        assertEquals(10, dto.id());
        assertEquals("Estudiar patrones", dto.title());
        assertEquals("Revisar singleton, Factory, Fluent", dto.content());
        assertEquals(LocalDateTime.of(2025,2,10,20,30), dto.creationDate());
        assertEquals(LocalDate.of(2025,3,5), dto.expirationDate());
        assertEquals("HIGH",dto.priority());
        assertEquals("DONE", dto.doneStatus());

    }

}
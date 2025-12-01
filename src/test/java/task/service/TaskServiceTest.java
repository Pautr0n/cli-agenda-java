package task.service;

import common.exception.DataAccessException;
import infrastructure.sql.connection.DBConnection;
import infrastructure.sql.dao.MySQLTaskDAOAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.dto.TaskOutputDTO;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;
import task.repository.TaskRepository;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



class TaskServiceTest {

    private TaskService taskService;
    private TaskRepository repo;

    @BeforeEach
    void setUp() {
        // Conexión real a tu MySQL (usa db.properties)
        Connection conn = DBConnection.getConnection();
        MySQLTaskDAOAdapter dao = new MySQLTaskDAOAdapter(conn);
        repo = new TaskRepository(dao);
        taskService = new TaskService(repo);
    }

    @Test
    void testGetTasksByStatusNotDone() {

        List<TaskOutputDTO> result = taskService.getTasksByStatus(2); // NOTDONE

        // Basado en tu tabla actual: tareas con id 1, 2, 5, 6
        assertEquals(6, result.size());
        assertTrue(result.stream().anyMatch(t -> t.id() == 1 && t.title().equals("Comprar pan")));
        assertTrue(result.stream().anyMatch(t -> t.id() == 2 && t.title().equals("Estudiar Java avanzado")));
        assertTrue(result.stream().anyMatch(t -> t.id() == 5 && t.title().equals("Leer libro")));
        assertTrue(result.stream().anyMatch(t -> t.id() == 7 && t.title().equals("Leer libro")));
    }

    @Test
    void testGetTasksByStatusDone() {

        List<TaskOutputDTO> result = taskService.getTasksByStatus(3); // DONE

        // Basado en tu tabla actual: tareas con id 3 y 4
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> t.id() == 3 && t.title().equals("Hacer deporte")));
        assertTrue(result.stream().anyMatch(t -> t.id() == 9 && t.title().equals("Hecha")));
    }

    @Test
    void testGetTasksByStatusInvalidOptionThrowsException() {


        assertThrows(DataAccessException.class, () -> taskService.getTasksByStatus(99));
    }






}
package infrastructure.sql.dao;

import common.exception.DataAccessException;
import infrastructure.sql.connection.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLTaskDAOAdapterTest {
    private MySQLTaskDAOAdapter dao;

    @BeforeEach
    void setUp() {
        Connection conn = DBConnection.getConnection(); // usa db.properties
        dao = new MySQLTaskDAOAdapter(conn);
    }

    @Test
    void testFindByIdExisting() {
        Task task = dao.findById(1);
        assertNotNull(task);
        assertEquals("Comprar pan", task.getTitle());
        assertEquals(PriorityType.MEDIUM, task.getPriority());
        assertEquals(DoneType.NOTDONE, task.getDoneStatus());
    }

    @Test
    void testFindByIdNonExisting() {
        Task task = dao.findById(999);
        assertNull(task);
    }

    @Test
    void testFindAllReturnsFourTasks() {
        List<Task> tasks = dao.findAll();
        assertEquals(4, tasks.size());
    }

    @Test
    void testInsertNewTask() {
        Task newTask = new Task();
        newTask.setTitle("Leer libro");
        newTask.setContent("Leer Clean Code");
        newTask.setExpirationDate(LocalDate.of(2025, 12, 10));
        newTask.setPriority(PriorityType.HIGH);
        newTask.setDoneStatus(DoneType.NOTDONE);
        newTask.setCreationDate(LocalDateTime.now());

        dao.insert(newTask);
        Task inserted = dao.findById(newTask.getId());
        assertNotNull(inserted);
        assertEquals("Leer libro", inserted.getTitle());
    }

    @Test
    void testUpdateTask() {
        Task task = dao.findById(2);
        assertNotNull(task);
        task.setTitle("Estudiar Java avanzado");
        dao.update(task);

        Task updated = dao.findById(2);
        assertEquals("Estudiar Java avanzado", updated.getTitle());
    }

    @Test
    void testDeleteTask() {
        Task task = new Task();
        task.setTitle("Eliminarme");
        task.setContent("Tarea temporal");
        task.setExpirationDate(LocalDate.of(2025, 12, 15));
        task.setPriority(PriorityType.LOW);
        task.setDoneStatus(DoneType.NOTDONE);
        task.setCreationDate(LocalDateTime.now());

        dao.insert(task);
        int id = task.getId();
        dao.delete(id);

        Task deleted = dao.findById(id);
        assertNull(deleted);
    }

    @Test
    void testDeleteNonExistingThrowsException() {
        assertThrows(DataAccessException.class, () -> dao.delete(999));
    }

    @Test
    void testExpirationDateNullHandledCorrectly() {
        Task task = dao.findById(2);
        assertNull(task.getExpirationDate());
    }

}
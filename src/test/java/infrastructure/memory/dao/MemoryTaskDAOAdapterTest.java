package infrastructure.memory.dao;

import static org.junit.jupiter.api.Assertions.*;

import common.exception.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.model.Task;

import java.util.List;


class MemoryTaskDAOAdapterTest {
    private MemoryTaskDAOAdapter dao;

    @BeforeEach
    void setUp() {
        dao = new MemoryTaskDAOAdapter();
    }

    @Test
    void testInsertAndFindById() {
        Task task = new Task();
        task.setTitle("Test Task");
        dao.insert(task);

        Task found = dao.findById(task.getId());
        assertNotNull(found);
        assertEquals("Test Task", found.getTitle());
    }

    @Test
    void testFindAll() {
        Task t1 = new Task(); t1.setTitle("T1");
        Task t2 = new Task(); t2.setTitle("T2");
        dao.insert(t1);
        dao.insert(t2);

        List<Task> tasks = dao.findAll();
        assertEquals(2, tasks.size());
    }

    @Test
    void testUpdateExistingTask() {
        Task task = new Task(); task.setTitle("Old");
        dao.insert(task);

        task.setTitle("Updated");
        dao.update(task);

        Task updated = dao.findById(task.getId());
        assertEquals("Updated", updated.getTitle());
    }

    @Test
    void testUpdateNonExistingTaskThrowsException() {
        Task fake = new Task(); fake.setId(999);
        assertThrows(DataAccessException.class, () -> dao.update(fake));
    }

    @Test
    void testDeleteExistingTask() {
        Task task = new Task(); task.setTitle("DeleteMe");
        dao.insert(task);

        dao.delete(task.getId());
        assertNull(dao.findById(task.getId()));
    }

    @Test
    void testDeleteNonExistingTaskThrowsException() {
        assertThrows(DataAccessException.class, () -> dao.delete(999));
    }

}
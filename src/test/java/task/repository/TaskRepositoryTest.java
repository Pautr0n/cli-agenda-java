package task.repository;

import common.dao.GenericDAO;
import infrastructure.memory.dao.MemoryTaskDAOAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.enums.PriorityType;
import task.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {
    private TaskRepository repo;

    @BeforeEach
    void setUp() {
        GenericDAO<Task> dao = new MemoryTaskDAOAdapter();
        repo = new TaskRepository(dao);
    }

    @Test
    void testAddAndGetById() {
        Task task = new Task();
        task.setTitle("Repo Task");
        repo.add(task);

        Task found = repo.getById(task.getId());
        assertNotNull(found);
        assertEquals("Repo Task", found.getTitle());
    }
    @Test
    void testGetAll() {
        Task t1 = new Task(); t1.setTitle("T1");
        Task t2 = new Task(); t2.setTitle("T2");
        repo.add(t1);
        repo.add(t2);

        List<Task> tasks = repo.getAll();
        assertEquals(2, tasks.size());
    }

    @Test
    void testUpdateTask() {
        Task task = new Task(); task.setTitle("Old");
        repo.add(task);

        task.setTitle("Updated");
        repo.update(task);

        Task updated = repo.getById(task.getId());
        assertEquals("Updated", updated.getTitle());
    }

    @Test
    void testRemoveTask() {
        Task task = new Task(); task.setTitle("RemoveMe");
        repo.add(task);

        repo.remove(task.getId());
        assertNull(repo.getById(task.getId()));
    }


}
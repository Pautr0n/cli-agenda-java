package infrastructure.memory.dao;

import common.dao.GenericDAO;
import common.exception.DataAccessException;
import task.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryTaskDAOAdapter implements GenericDAO<Task> {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final File storageFile = new File("tasks.json");
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public void insert(Task entity) {
        int id = idGenerator.getAndIncrement();
        entity.setId(id);
        tasks.put(id, entity);
        saveToFile();
    }

    @Override
    public Task findById(int id) {
        return tasks.get(id);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void update(Task task) {
        if (!tasks.containsKey(task.getId())) {
            throw new DataAccessException("Task with id " + task.getId() + " not found");
        }
        tasks.put(task.getId(), task);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        if (tasks.remove(id) == null) {
            throw new DataAccessException("Task with id " + id + " not found");
        }
        saveToFile();
    }

    private void saveToFile() {
        try {
            mapper.writeValue(storageFile, tasks.values());
        } catch (IOException e) {
            throw new DataAccessException("Error saving tasks to file", e);
        }
    }

    private void loadFromFile() {
        if (storageFile.exists()) {
            try {
                Task[] loaded = mapper.readValue(storageFile, Task[].class);
                for (Task t : loaded) {
                    tasks.put(t.getId(), t);
                    idGenerator.set(Math.max(idGenerator.get(), t.getId() + 1));
                }
            } catch (IOException e) {
                throw new DataAccessException("Error loading tasks from file", e);
            }
        }
    }

}

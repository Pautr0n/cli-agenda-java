package task.repository;

import common.exception.DataAccessException;
import infrastructure.sql.dao.GenericDAO;
import task.model.Task;
import java.sql.SQLException;
import java.util.List;

public class TaskRepository {

    private final GenericDAO<Task> taskDAO;

    public TaskRepository(GenericDAO<Task> taskDAO) {
        this.taskDAO = taskDAO;
    }

    public void addTask(Task task) {
        try {
            taskDAO.insert(task);
        } catch (SQLException e) {
            throw new DataAccessException("Error inserting task", e);
        }
    }

    public Task getTask(int id) {
        try {
            return taskDAO.findById(id);
        } catch (SQLException e) {
            throw new DataAccessException("Error finding task with id " + id, e);
        }
    }

    public List<Task> getAllTasks() {
        try {
            return taskDAO.findAll();
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all tasks", e);
        }
    }

    public void updateTask(Task task) {
        try {
            taskDAO.update(task);
        } catch (SQLException e) {
            throw new DataAccessException("Error updating task with id " + task.getId(), e);
        }
    }

    public void removeTask(int id) {
        try {
            taskDAO.delete(id);
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting task with id " + id, e);
        }
    }

}

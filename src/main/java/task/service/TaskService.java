package task.service;

import common.exception.DataAccessException;
import task.dto.*;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.mapper.TaskInputMapper;
import task.mapper.TaskOutputMapper;
import task.model.Task;
import task.repository.TaskRepository;

import java.time.LocalDate;

import java.util.List;

public class TaskService {


    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }

    //CRUD

    // 1.Create


    public TaskOutputDTO createTask(TaskDTO dto) {

        try {
            validateTaskDTOCreate(dto);

            Task task = TaskInputMapper.toEntity(dto);
            taskRepository.add(task);

            return TaskOutputMapper.toDTO(task);

        } catch (Exception e) {
            throw new DataAccessException("Error creating a task", e);
        }
    }
    //Read One

    public TaskOutputDTO getTaskById(TaskIdDTO id) {
        try {
            validateTaskId(id);
            Task task = taskRepository.getById(id.id());

            if (task == null) {
                throw new IllegalArgumentException("Task with id " + id.id() + " not found");
            }

            return TaskOutputMapper.toDTO(task);

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving task with id: " + id.id(), e);
        }
    }

    //Read ALL

    public List<TaskOutputDTO> getAllTasks() {
        try {

            return taskRepository.getAll().stream()
                    .map(TaskOutputMapper::toDTO)
                    .toList();

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving task from database.", e);
        }
    }

    // Read taskCompleted

    public List<TaskOutputDTO> getCompletedTasks() {
        try {
            return taskRepository.getAll().stream()
                    .filter(t -> t.getDoneStatus() == DoneType.DONE)
                    .map(TaskOutputMapper::toDTO)
                    .toList();

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving complete tasks.", e);
        }
    }

    //NotCompletedTask

    public List<TaskOutputDTO> getPendingTasks() {
        try {

            return taskRepository.getAll().stream()
                    .filter(t -> t.getDoneStatus() == DoneType.NOTDONE)
                    .map(TaskOutputMapper::toDTO)
                    .toList();

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving uncompleted tasks", e);
        }
    }

    //completedTask

    public TaskOutputDTO markTaskCompleted(TaskIdDTO id) {
        try {

            validateTaskId(id);

            Task task = taskRepository.getById(id.id());
            if (task == null) {
                throw new IllegalArgumentException("Task with id: " + id.id() + " not found.");
            }
            task.setDoneStatus(DoneType.DONE);
            taskRepository.update(task);

            return TaskOutputMapper.toDTO(task);

        } catch (Exception e) {
            throw new DataAccessException("Error marking completed task, id=" + id.id(), e);
        }

    }


    //Update

    public TaskOutputDTO updateTask(TaskUpdateDTO dto) { //taskUpdateDTO
        try {

            validateTaskUpdate(dto);
            //PAU: El MySQLTaskDAOAdapter ya verifica si se ha updateado o no una Task
//            Task task = taskRepository.getById(dto.id());
//
//            if (task == null) {
//                throw new IllegalArgumentException("Task with id: " + dto.id() + " does not exist.");
//            }

            Task task = TaskInputMapper.toEntity(dto);

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

            //TaskInputMapper.applyUpdates(task, dto);
            taskRepository.update(task);

            //System.out.println("Task updated successfully, id: "+dto.id());

            return TaskOutputMapper.toDTO(task); //aquí estás devolviendo el mismo argumento que en la llamada al métood.

        } catch (Exception e) {
            throw new DataAccessException("Error updating task with id: " + dto.id() + " ", e);
        }
    }

    //Delete task

    public void deleteTask(TaskIdDTO id) {

        try {

            validateTaskId(id);

            //PAU: El MySQLTaskDAOAdapter ya verifica si se ha borrado o no una Task
//            Task task = taskRepository.getById(id.id());
//            if (task == null) {
//                throw new IllegalArgumentException("Task not found with id: " + id.id());
//            }

            taskRepository.remove(id.id());
            //System.out.println("Task successfully deleted");// eliminar?

        //PAU En el MySQLTaskDAOAdapter se lanza una DataAccessExceptio tanto si no existe la ID como si falla la conexión, con mensajes "e" diferentes.
        } catch (DataAccessException e) {
            throw new DataAccessException("Exception while deleting task with id: " + id.id(), e);
        }
    }


    //******************************************************************

    //Validations

    private void validateTaskDTOCreate(TaskDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO record instance cannot be null"); //PAU - Improving message information, previous: "Task cannot be null"
        }

        if (dto.content() == null || dto.content().isBlank()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }

        if (dto.title() == null || dto.title().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (dto.expirationDate() == null || dto.expirationDate().isBlank()) {
            throw new IllegalArgumentException("Expiration date cannot be empty");
        }

        try {
            LocalDate.parse(dto.expirationDate());
        } catch (Exception e) {
            throw new IllegalArgumentException("Expiration date format must be yyyy-MM-dd", e);
        }

    }

    private void validateTaskId(TaskIdDTO id) {
        if (id == null) {
            throw new IllegalArgumentException("DTO record instance cannot be null");//PAU - Improving message information, previous: "Task id cannot be null"
        }
        if (id.id() == null) {
            throw new IllegalArgumentException("Task id value cannot be null");
        }
        if (id.id() <= 0) {
            throw new IllegalArgumentException("Invalid id: " + id.id());
        }
    }

    //PAU: refactoring wrong parenthesis
    private void validateTaskUpdate(TaskUpdateDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO record instance cannot be null"); //PAU - Improving message information, previous: "DTO update cannot be null"
        }

        if (dto.id() <= 0) {
            throw new IllegalArgumentException("Invalid id.");
        }

        if ((dto.title() == null || dto.title().isBlank()) &&
                (dto.content() == null || dto.content().isBlank()) &&
                (dto.expirationDate() == null || dto.expirationDate().isBlank()) &&
                (dto.priority() == null || dto.priority().isBlank())) {


            throw new IllegalArgumentException("No fields provided to update");

        }

        if (dto.expirationDate() != null && !dto.expirationDate().isBlank()) {
            try {
                LocalDate.parse(dto.expirationDate());
            } catch (Exception e) {
                throw new IllegalArgumentException("Expiration date format must be yyyy-MM-dd");
            }
        }

    }
}



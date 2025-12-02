package task.service;


import common.exception.DataAccessException;
import common.exception.EntityNotFoundException;
import common.exception.ServiceException;
import common.exception.ValidationException;
import task.dto.TaskDTO;
import task.dto.TaskIdDTO;
import task.dto.TaskOutputDTO;
import task.dto.TaskUpdateDTO;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.mapper.TaskDTOMapper;
import task.model.Task;
import task.repository.TaskRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

            Task task = TaskDTOMapper.dtoToTask(dto);  //PAU: modificado el Mapper:
            task = taskRepository.add(task); //como ahora el método insert() devuelve la entidad con el ID la asigno a task para que cree el dto
            //Aqui modificar la clase Repository y la clase DAO para que devuelvan Task y así poder imprimir la Task con ID
            //task = taskRepository.add(task);

            return TaskDTOMapper.taskToDTO(task);

        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("TaskService [createTask]: Unexpected error creating task", e);
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

            return TaskDTOMapper.taskToDTO(task);//PAU: modificado el Mapper:

        } catch (EntityNotFoundException | DataAccessException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("TaskService [getTaskById]: Unexpected error retrieving task with id " + id.id(), e);
        }

    }

    //Read ALL
    public List<TaskOutputDTO> getAllTasks() {
        try {

            return taskRepository.getAll().stream()
                    .map(TaskDTOMapper::taskToDTO)  //PAU: modificado el Mapper:
                    .toList();

        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("TaskService [getAllTask]: Unexpected error retrieving tasks", e);
        }
    }

    public List<TaskOutputDTO> getTasksByStatus(int option) {
        DoneType doneType;
        switch (option) {
            case 2 -> doneType = DoneType.NOTDONE;
            case 3 -> doneType = DoneType.DONE;
            default -> throw new ValidationException("Option not valid");

        }

        try {
            return taskRepository.getAll().stream()
                    .filter(t -> t.getDoneStatus() == doneType)
                    .map(TaskDTOMapper::taskToDTO).toList();
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("TaskService [getTaskByStatus]: Unexpected error retrieving filtered tasks", e);
        }
    }

    //completedTask
    public TaskOutputDTO markTaskCompleted(TaskIdDTO id) {
        try {

            validateTaskId(id);

            Task task = taskRepository.getById(id.id());
            if (task == null) {
                throw new EntityNotFoundException("Task with id " + id.id() + " not found");
            }
            task.setDoneStatus(DoneType.DONE);
            taskRepository.update(task);

            return TaskDTOMapper.taskToDTO(task);

        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("TaskService [markTaskCompleted]: Unexpected error marking task completed id=" + id.id(), e);
        }
    }


    //Update
    public TaskOutputDTO updateTask(TaskUpdateDTO dto) {
        validateTaskUpdate(dto);

        try {
            Task task = taskRepository.getById(dto.id());

            if (task == null) {
                throw new EntityNotFoundException("Task with id " + dto.id() + " not found");
            }

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

            taskRepository.update(task);

            return TaskDTOMapper.taskToDTO(task);

        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("TaskService [updateTask]: Unexpected error updating task id=" + dto.id(), e);
        }

    }

    //Delete task
    public void deleteTask(TaskIdDTO id) {

        try {
            validateTaskId(id);
            taskRepository.remove(id.id());
        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("TaskService [deleteTask]: Unexpected error deleting task id=" + id.id(), e);
        }
    }


    //******************************************************************

    //Validations
    private void validateTaskDTOCreate(TaskDTO dto) {

        if (dto == null) {
            throw new  ValidationException("DTO record instance cannot be null");
        }

        if (dto.content() == null || dto.content().isBlank()) {
            throw new ValidationException("Content cannot be empty");
        }

        if (dto.title() == null || dto.title().isBlank()) {
            throw new ValidationException("Title cannot be empty");
        }

        if (dto.expirationDate() == null || dto.expirationDate().isBlank()) {
            throw new ValidationException("Expiration date cannot be empty");
        }

        try {
            LocalDate.parse(dto.expirationDate());
        } catch (DateTimeParseException e) {
            throw new ValidationException("Expiration date format must be yyyy-MM-dd", e);
        }

    }

    private void validateTaskId(TaskIdDTO id) {
        if (id == null) {
            throw new ValidationException("DTO record instance cannot be null");
        }
        if (id.id() == null) {
            throw new ValidationException("Task id value cannot be null");
        }
        if (id.id() <= 0) {
            throw new ValidationException("Invalid id: " + id.id());
        }
    }


    private void validateTaskUpdate(TaskUpdateDTO dto) {

        if (dto == null) {
            throw new ValidationException("DTO record instance cannot be null");
        }

        if (dto.id() <= 0) {
            throw new ValidationException("Invalid id.");
        }

        if ((dto.title() == null || dto.title().isBlank()) &&
                (dto.content() == null || dto.content().isBlank()) &&
                (dto.expirationDate() == null || dto.expirationDate().isBlank()) &&
                (dto.priority() == null || dto.priority().isBlank())) {

            throw new ValidationException("No fields provided to update");
        }

        if (dto.expirationDate() != null && !dto.expirationDate().isBlank()) {
            try {
                LocalDate.parse(dto.expirationDate());
            } catch (Exception e) {
                throw new ValidationException("Expiration date format must be yyyy-MM-dd");
            }
        }

    }
}



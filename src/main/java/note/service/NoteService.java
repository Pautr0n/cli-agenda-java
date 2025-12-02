package note.service;

import common.exception.DataAccessException;
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
import java.util.List;

public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;

    }


    //CRUD a preparar Variables de la nota
    //
    //Titulo
    //Texto
    //Fecha de creación

//CRUD

    // 1.Create
    public NoteOutputDTO createNote (NoteDTO dto) {

        try {
            validateNoteDTOCreate(dto);

            Note note = NoteDTOMapper.dtoToNote(dto);
            note = taskRepository.add(note);

            return NoteDTOMapper.noteToDTO(note);

        } catch (Exception e) {
            throw new DataAccessException("Error creating note", e);
        }
    }

    // continuar a partir de aqui

    //Read One
    public TaskOutputDTO getTaskById(TaskIdDTO id) {
        try {
            validateTaskId(id);
            Task task = taskRepository.getById(id.id());

            if (task == null) {
                throw new IllegalArgumentException("Task with id " + id.id() + " not found");
            }

            return TaskDTOMapper.taskToDTO(task);//PAU: modificado el Mapper:

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving task with id: " + id.id(), e);
        }
    }

    //Read ALL
    public List<TaskOutputDTO> getAllTasks() {
        try {

            return taskRepository.getAll().stream()
                    .map(TaskDTOMapper::taskToDTO)  //PAU: modificado el Mapper:
                    .toList();

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving task from database.", e);
        }
    }


    //Update
    public TaskOutputDTO updateTask(TaskUpdateDTO dto) {
        try {

            validateTaskUpdate(dto);

            Task task = taskRepository.getById(dto.id());

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

        } catch (Exception e) {
            throw new DataAccessException("Error updating task with id: " + dto.id() + " ", e);
        }
    }

    //Delete task
    public void deleteTask(TaskIdDTO id) {

        try {

            validateTaskId(id);

            taskRepository.remove(id.id());


        } catch (DataAccessException e) {
            throw new DataAccessException("Exception while deleting task with id: " + id.id(), e);
        }
    }

    

    //******************************************************************
/*
    //Validations
    private void validateTaskDTOCreate(TaskDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO record instance cannot be null");
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
            throw new IllegalArgumentException("DTO record instance cannot be null");
        }
        if (id.id() == null) {
            throw new IllegalArgumentException("Task id value cannot be null");
        }
        if (id.id() <= 0) {
            throw new IllegalArgumentException("Invalid id: " + id.id());
        }
    }


    private void validateTaskUpdate(TaskUpdateDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO record instance cannot be null");
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
*/
}

package task.service;

import common.exception.DataAccessException;
import task.dto.*;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;
import task.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



public class TaskService {


    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;

    }

    //CRUD

    // 1.Create


    public TaskDTO createTask(TaskDTO dto){

        try{
            validateTaskDTOCreate(dto);

            Task task = mapToTask(dto);
            taskRepository.add(task);

            return mapToTaskDTO(task);

        }catch(Exception e){
            throw new DataAccessException("Error creating a task",e);
        }
    }
    //Read One

    public TaskDTO getTaskById(TaskIdDTO idDto){
        try {

            Task task = taskRepository.getById(idDto.getId());

            if (task.getId() == null) {
                throw new IllegalArgumentException("Task with id " + idDto + " not found");
            }

            return mapToTaskDTO(task);

        }catch(Exception e){
            throw new DataAccessException("Error retrieving task with id: "+idDto ,e);
        }
    }

    //Read ALL

    public List<TaskDTO> getAllTasks() {
        try {

            return taskRepository.getAll().stream()
                    .map(this::mapToTaskDTO)
                    .toList();

        } catch (Exception e) {
            throw new DataAccessException("Error retrieving task from database.", e);
        }
    }

    // Read taskCompleted

    public List<TaskDTO> completedTasks(){
        try{
            return taskRepository.getAll().stream()
                    .filter(t -> t.getDoneStatus() == DoneType.DONE)
                    .map(this::mapToTaskDTO)
                    .toList();

        }catch (Exception e){
            throw new DataAccessException("Error retrieving complete tasks.");
        }
    }

    //NotCompletedTask

    public List<TaskDTO> notCompletedListTasks(){
        try{

            return taskRepository.getAll().stream()
                    .filter(t->t.getDoneStatus() == DoneType.NOTDONE)
                    .map(this::mapToTaskDTO)
                    .toList();

        }catch(Exception e){
            throw new DataAccessException("Error retrieving uncompleted tasks",e);
        }
    }

    //completedTask

    public TaskDTO markTaskCompleted(TaskIdDTO idDto)  {
        try {

            validateTaskId(idDto.getId());

            Task task = taskRepository.getById(idDto.getID());
            task.setDoneStatus(DoneType.DONE);

            taskRepository.update(task);
            System.out.println("Task completed");

            return mapToTaskDTO(task);

        }catch (Exception e){
            throw new DataAccessException("Error marking completed task, (id="+id+")",e);
        }

    }

    //Update

    public TaskDTO updateTask(TaskUpdateDTO dto) { //taskUpdateDTO
        try {

            validateTaskUpdate(dto);

            Task task = taskRepository.getById(dto.getId());
            if(task == null){
                throw new IllegalArgumentException("Task with id: "+dto.getId()+" does not exist.");
            }

            applyUpdates(task, dto);
            taskRepository.update(task);

            System.out.println("Task updated successfully, id: "+dto.getId());

            return mapToTaskDTO(task);

        }catch (Exception e){
            throw new DataAccessException("Error updating task with id: "+ dto.getId()+" ",e);
        }
    }

    //Delete task

    public void deleteTask(TaskIdDTO idDto){

        try{

            validateTaskId(idDto);
            taskRepository.remove(idDto.getId());
            System.out.println("Task successfully deleted");// eliminar?

        }catch(Exception e){
            throw new DataAccessException("Error deleting task with id: "+idDto.getId() ,e);
        }
    }



    //******************************************************************

    //Validations

    private void validateTaskDTOCreate(TaskDTO dto){

         if(dto == null){
                throw new IllegalArgumentException("Task cannot be null");
            }

           if (dto.getContent() == null || dto.getContent.isBlank()) {
               throw new IllegalArgumentException("Content cannot be empty");
           }

           if (dto.getTitle() = null || dto.getTitle.isBlank()) {
               throw new IllegalArgumentException("Title cannot be empty");
           }

           if (dto.getExpirateDate == null || dto.getExpirationDate.isBlank()) {
               throw new IllegalArgumentException("Expiration date cannot be empty");
           }

           if(dto.getExpirationDate()== null){
               throw new IllegalArgumentException("Expiration date is required");
           }

    }

    private void validateTaskId(TaskIdDTO idDto){
        if(idDto == null){
            throw new IllegalArgumentException("Task id cannot be null");
        }
        if(idDto.getId() <= 0){
            throw new IllegalArgumentException("Invalid id: " +idDto.getId());
        }

        Task task = taskRepository.getById(idDto.getId());

        if(task == null){
            throw new IllegalArgumentException("Task not found with id: "+idDto.getId());
        }

    }

    private void validateTaskUpdate(TaskUpdateDTO dto){

        if(dto == null){
            throw new IllegalArgumentException("DTO update cannot be null");
        }

        if(dto.getId() <= 0){
            throw new IllegalArgumentException("Invalid id.");
        }
        if ((dto.getTitle() == null || dto.getTitle().isBlank()) &&
            (dto.getContent() == null || dto.getContent().isBlank()) &&
            dto.getExpirationDate == null &&
            dto.getPriority == null &&
            dto.getDoneStatus == null){

           throw new IllegalArgumentException("Not fields provided to update");

        }

    }


    //******************************************************************

    //Mapper DTO -> Entity

    private Task mapToTask(TaskDTO dto){
      Task task = new Task();

      task.setTitle(dto.getTitle);
      task.setContent(dto.getContent);
      task.setCreationDate(LocalDateTime.now());

      LocalDate expireLocalDate = LocalDate.parse(dto.getExpireDate);
      task.setExpirationDate(expireLocalDate);

      PriorityType priority = dto.getPriority();
      if(priority == null){
          priority = PriorityType.MEDIUM;

      }
      task.setPriority(priority);

      task.setDoneStatus(DoneType.NOTDONE);


      return task;
    }

    private Task mapToTask(TaskDTO dto){
        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setContent(dto.getContent());
        task.setCreationDate(LocalDateTime.now());
        task.setExpirationDate(dto.getExpirationDate());

        PriorityType priority = dto.getPriority();
        if(priority == null){
            priority = PriorityType.MEDIUM;

        }
        task.setPriority(priority);

        task.setDoneStatus(DoneType.NOTDONE);

        return task;
    }


    private TaskDTO mapToTaskDTO(Task task){

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setContent(task.getContent());
        dto.setCreationDate(task.getCreationDate());
        dto.setExpirationDate(task.getExpirationDate());
        dto.Priority(task.getPriority());
        dto.setDoneStatus(task.getDoneStatus());

        return dto;
    }


    private void applyUpdates(Task task, TaskUpdateDTO dto) {

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            task.setTitle(dto.getTitle());
        }

        if (dto.getContent() != null && !dto.getContent.isBlank()) {
            task.setContent(dto.getContent);
        }

        if (dto.getExpirationDate != null) {
            task.setExpirationDate(dto.getExpirationDate());
        }

        if (dto.getPriority != null) {
            task.setPriority(dto.getPriority());
        }

        if (dto.getDoneStatus != null) {
            task.setDoneStatus(dto.getDoneStatus());


        }


    }

}

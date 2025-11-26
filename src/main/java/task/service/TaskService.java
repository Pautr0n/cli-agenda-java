package task.service;

import common.exception.DataAccessException;
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
    public void addTask(String title, String text, String expireDate, PriorityType priority){
        LocalDate expireDateLocal = LocalDate.parse(expireDate);

        if(text == null || text.isBlank()){
            throw new IllegalArgumentException("You have to enter a task.");
        }

        if(priority == null){
            priority = PriorityType.MEDIUM;

        }

        Task task = new Task();
        task.setTitle(title);
        task.setContent(text);
        task.setCreationDate(LocalDateTime.now());
        task.setExpirationDate(expireDateLocal);
        task.setPriority(priority);
        task.setDoneStatus(DoneType.NOTDONE);

        taskRepository.add(task);
        System.out.println("Task created with success");


    }

    //Read ALL
    public List<Task> getAll()  {
        try {

            return taskRepository.getAll();

        }catch (Exception e){
            throw new DataAccessException("Error retrieving task from database.",e);
        }
    }


    // Read ONE
    public Task getTaskById(int id){
        return taskRepository.getById(id);
    }

    //Update

    public void markTaskCompleted(int id)  {
        try {
            Task task = taskRepository.getById(id);

            if (task == null) {
                throw new IllegalArgumentException("The task with id " + id + " does not exists");
            }

            task.setDoneStatus(DoneType.DONE);
            taskRepository.update(task);
            System.out.println("Task completed");
        }catch (Exception e){
            throw new DataAccessException("Error marking task completed, (id="+id+")",e);
        }


    }

    public void updateTask(Task updateTask) {
        try {

            Task task = taskRepository.getById(updateTask.getId());

            if (task == null) {
                throw new IllegalArgumentException("The task with id " + updateTask.getId() + " does not exist");
            }
            if (updateTask.getContent() != null && !updateTask.getContent().isBlank()) {
                task.setContent(updateTask.getContent());

            }
            if (updateTask.getTitle() != null && !updateTask.getTitle().isBlank()) {
                task.setTitle(updateTask.getTitle());
            }

            if (updateTask.getExpirationDate() != null) {
                task.setExpirationDate(updateTask.getExpirationDate());
            }
            if (updateTask.getPriority() != null) {
                task.setPriority(updateTask.getPriority());
            }
            if(updateTask.getDoneStatus() != null){
                task.setDoneStatus(updateTask.getDoneStatus());
            }

            taskRepository.update(task);
            System.out.println("Task updated successfully, id: "+updateTask.getId());

        }catch (Exception e){
            throw new DataAccessException("Error updating task with id: "+ updateTask.getId()+" ",e);
        }
    }

    //Delete

    public void deleteTask(int id){
        try{

            taskRepository.remove(id);
            System.out.println("Task Deleted");

        }catch(Exception e){
            throw new DataAccessException("Task not found in the database",e);
        }

    }

}

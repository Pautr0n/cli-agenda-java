package task.service;

import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;
import task.repository.TaskRepository;

import java.sql.SQLDataException;
import java.sql.SQLException;
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

        taskRepository.addTask(task);
        System.out.println("Task created with success");


    }

    //Read ALL
    public List<Task> getAllTasks() throws SQLDataException {
        return taskRepository.getAllTasks();
    }


    // Read ONE
    public Task getTaskById(int id){
        return taskRepository.findById(id);
    }

    //Update

    public void markTaskCompleted(int id) throws SQLException {
        Task task = taskRepository.getTask(id);

        if (task == null){
            throw new IllegalArgumentException("The task with id "+id+" does not exists");
        }

        task.setDoneStatus(DoneType.DONE);
        taskRepository.updateTask(task);
        System.out.println("Task completed");


    }

    public void updateTask(int id, String text, String title, LocalDate expirationDate, PriorityType priority) throws SQLException{
        Task task = taskRepository.getTask(id);
        if(task == null){
            throw new IllegalArgumentException("The task with id "+id+" does not exists");
        }
        if(text != null && !text.isBlank()){
            task.setContent(text);

        }
        if (title != null && title.isBlank()){
            task.setTitle(title);
        }

        if(expirationDate != null){
            task.setExpirationDate(expirationDate);
        }
        if(priority != null){
            task.setPriority(priority);
        }

        taskRepository.updateTask(task);
    }

    //Delete

    public void deleteTask(int id)throws SQLException{
        taskRepository.removeTask(id);
        System.out.println("Task Deleted");
    }

}

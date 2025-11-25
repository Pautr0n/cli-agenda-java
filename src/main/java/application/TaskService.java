package application;

import task.repository.TaskRepository;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository taskrepository){
        this.repository = taskRepository;

    }

    //CRUD

    // 1.Create
    public void addTask(String tittle, Content text, LocalDate expireDate, PriorityType priority,Task task){

        if(text == null || text.isBlank()){
            throw new IllegalArgumentException("You have to enter a task.");
        }

        if(priority == null){
            priority = PriotiryType.MIDDLE;

        }

        Task task = new Task();
        task.setTittle(tittle);
        task.setContent(text);
        task.setCreationDate(LocalDateTime.now());
        task.setExpirationDate(LocalDateTime);
        task.setPriority(priority);
        task.setDoneStatus(DoneType doneStatus);

        taskRepository.addTask(task);
        System.out.println("Task created with success");


    }

    //Read ALL
    public List<Task> getAllTasks()throws SQLDataException {
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

        task.setDoneStatus(DoneStatus.COMPLETE);
        taskRepository.updateTask(task);
        System.out.println("Task completed");


    }

    public void updateTask(int id, Contenxt text, String tittle, LocalDateTime expirationDate, PriorityType priority)throws SQLException{
        Task task = taskRepository.getTask(id);
        if(task == null){
            throw new IllegalArgumentException("The task with id "+id+" does not exists");
        }
        if(text != null && !text.trim().isEmpty()){
            task.setContent(text);

        }
        if (titlle != null && tittle.isBlank()){
            task.setTittle(tittle);
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
        taskRepository.delete(id);
        System.out.println("Task Deleted");
    }

}

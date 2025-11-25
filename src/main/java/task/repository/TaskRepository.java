package task.repository;

import common.dao.GenericDAO;
import common.repository.EntityRepository;
import task.model.Task;

public class TaskRepository extends EntityRepository<Task> {

    public TaskRepository(GenericDAO<Task> taskDao) {
        super(taskDao);
    }

}

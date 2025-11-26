package infrastructure.memory.factory;

import common.dao.GenericDAO;
import common.factory.DAOFactory;
import infrastructure.memory.dao.MemoryTaskDAOAdapter;
import task.model.Task;

public class MemoryDAOFactory extends DAOFactory {

    @Override
    public GenericDAO<Task> getTaskDAO() {
        return new MemoryTaskDAOAdapter(); // DAO en memoria
    }

}

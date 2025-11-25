package infrastructure.sql.factory;

import common.dao.GenericDAO;
import common.factory.DAOFactory;
import infrastructure.sql.dao.MySQLTaskDAOAdapter;
import task.model.Task;

import java.sql.Connection;

public class MySQLDAOFactory extends DAOFactory {
    private final Connection connection;

    public MySQLDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public GenericDAO<Task> getTaskDAO() {
        return new MySQLTaskDAOAdapter(connection);
    }
}

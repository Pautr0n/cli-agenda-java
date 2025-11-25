package common.factory;

import common.dao.GenericDAO;
import infrastructure.memory.factory.MemoryDAOFactory;
import infrastructure.mongodb.factory.MongoDBDAOFactory;
import infrastructure.sql.factory.MySQLDAOFactory;
import task.model.Task;

import java.sql.Connection;

public abstract class DAOFactory {
    public abstract GenericDAO<Task> getTaskDAO();

    public static DAOFactory getDAOFactory(DBType type, Object connection) {
        switch (type) {
            case MYSQL:
                return new MySQLDAOFactory((Connection) connection);
            case MONGODB:
                return new MongoDBDAOFactory((MongoDatabase) connection);
            case MEMORY:
                return new MemoryDAOFactory();
            default:
                throw new IllegalArgumentException("Unsupported DB type");
        }
    }

    public enum DBType {
        MYSQL, MONGODB, MEMORY
    }

}

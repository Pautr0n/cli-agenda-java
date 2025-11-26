package infrastructure.mongodb.factory;

import com.mongodb.client.MongoDatabase;
import common.dao.GenericDAO;
import common.factory.DAOFactory;
import infrastructure.mongodb.dao.MongoDBTaskDAOAdapter;
import task.model.Task;

public class MongoDBDAOFactory extends DAOFactory {
    private final MongoDatabase database;

    public MongoDBDAOFactory(MongoDatabase database) {
        this.database = database;
    }

    @Override
    public GenericDAO<Task> getTaskDAO() {
        return new MongoDBTaskDAOAdapter(database);
    }

}

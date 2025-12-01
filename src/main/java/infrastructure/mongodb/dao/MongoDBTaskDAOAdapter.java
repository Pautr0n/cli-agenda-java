package infrastructure.mongodb.dao;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.dao.GenericDAO;
import common.exception.DataAccessException;
import org.bson.Document;
import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;

public class MongoDBTaskDAOAdapter implements GenericDAO<Task> {
    private final MongoCollection<Document> collection;

    public MongoDBTaskDAOAdapter(MongoDatabase database) {
        this.collection = database.getCollection("tasks");
    }

    @Override
    public Task insert(Task task) {
        try {
            Document doc = new Document()
                    .append("title", task.getTitle())
                    .append("content", task.getContent())
                    .append("creationDate", task.getCreationDate().toString())
                    .append("expirationDate", task.getExpirationDate() != null ? task.getExpirationDate().toString() : null)
                    .append("priority", task.getPriority().name())
                    .append("doneStatus", task.getDoneStatus().name());

            collection.insertOne(doc);
            task.setId(doc.getObjectId("_id").toHexString().hashCode()); // ejemplo simple de ID
            return task;
        } catch (MongoException e) {
            throw new DataAccessException("DAO error [update]: Error inserting task in MongoDB", e);
        }
    }

    @Override
    public Task findById(int id) {
        try {
            Document doc = collection.find(eq("_id", id)).first();
            return doc != null ? mapTask(doc) : null;
        } catch (MongoException e) {
            throw new DataAccessException("DAO error [Query]: Error finding task in MongoDB", e);
        }
    }

    @Override
    public List<Task> findAll() {
        try {
            List<Task> tasks = new ArrayList<>();
            for (Document doc : collection.find()) {
                tasks.add(mapTask(doc));
            }
            return tasks;
        } catch (MongoException e) {
            throw new DataAccessException("DAO error [Query]: Error retrieving tasks from MongoDB", e);
        }
    }

    @Override
    public void update(Task task) {
        try {
            Document updateDoc = new Document()
                    .append("title", task.getTitle())
                    .append("content", task.getContent())
                    .append("expirationDate", task.getExpirationDate() != null ? task.getExpirationDate().toString() : null)
                    .append("priority", task.getPriority().name())
                    .append("doneStatus", task.getDoneStatus().name());

            collection.updateOne(eq("_id", task.getId()), new Document("$set", updateDoc));
        } catch (MongoException e) {
            throw new DataAccessException("DAO error [update]: Error updating task in MongoDB", e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            collection.deleteOne(eq("_id", id));
        } catch (MongoException e) {
            throw new DataAccessException("Error deleting task in MongoDB", e);
        }
    }

    private Task mapTask(Document doc) {
        Task task = new Task();
        task.setId(doc.getObjectId("_id").toHexString().hashCode());
        task.setTitle(doc.getString("title"));
        task.setContent(doc.getString("content"));
        task.setCreationDate(java.time.LocalDateTime.parse(doc.getString("creationDate")));
        if (doc.getString("expirationDate") != null) {
            task.setExpirationDate(java.time.LocalDate.parse(doc.getString("expirationDate")));
        }
        task.setPriority(PriorityType.valueOf(doc.getString("priority")));
        task.setDoneStatus(DoneType.valueOf(doc.getString("doneStatus")));
        return task;
    }

}

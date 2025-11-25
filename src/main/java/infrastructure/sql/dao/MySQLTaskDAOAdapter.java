package infrastructure.sql.dao;

import task.enums.DoneType;
import task.enums.PriorityType;
import task.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLTaskDAOAdapter implements GenericDAO<Task> {
    //connection local variable must be implemented:
    private Connection connection;

    //Constructor must be provided:

    public MySQLTaskDAOAdapter(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void insert(Task entity) throws SQLException {

        String sql = "INSERT INTO tasks (title, content, creation_date, expiration_date, priority, done_status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getContent());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getCreationDate()));
            if (entity.getExpirationDate() != null) {
                statement.setDate(4, Date.valueOf(entity.getExpirationDate()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            statement.setString(5, entity.getPriority().name());
            statement.setString(6, entity.getDoneStatus().name());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                entity.setId(keys.getInt(1));
            }
        }

    }


    @Override
    public Task findById(int id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapTask(rs);
            }
        }
        return null;
    }

    @Override
    public List<Task> findAll() throws SQLException {
        String sql = "SELECT * FROM tasks";
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tasks.add(mapTask(rs));
            }
        }
        return tasks;
    }

    @Override
    public void update(Task entity) throws SQLException {
        String sql = "UPDATE tasks SET title=?, content=?, expiration_date=?, priority=?, done_status=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getContent());

            if (entity.getExpirationDate() != null) {
                statement.setDate(3, Date.valueOf(entity.getExpirationDate()));
            } else {
                statement.setNull(3, Types.DATE);
            }

            statement.setString(4, entity.getPriority().name());
            statement.setString(5, entity.getDoneStatus().name());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
        }


    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }

    }

    private Task mapTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setContent(rs.getString("content"));
        task.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());

        Date expDate = rs.getDate("expiration_date");
        if (expDate != null) {
            task.setExpirationDate(expDate.toLocalDate());
        }

        task.setPriority(PriorityType.valueOf(rs.getString("priority")));
        task.setDoneStatus(DoneType.valueOf(rs.getString("done_status")));
        return task;
    }


}

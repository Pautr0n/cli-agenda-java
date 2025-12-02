package infrastructure.sql.dao;

import common.dao.GenericDAO;
import common.exception.DataAccessException;
import note.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLNoteDAOAdapter implements GenericDAO<Note> {

    private final Connection connection;

    public MySQLNoteDAOAdapter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Note insert(Note entity) {
        try {

            String sql = "INSERT INTO note (title, content, creation_date) VALUES(?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, entity.getTitle());
                statement.setString(2, entity.getContent());
                statement.setTimestamp(3, Timestamp.valueOf(entity.getCreationDate()));
                statement.executeUpdate();

                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    entity.setId(keys.getInt(1));
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new DataAccessException("Error inserting note in database", e);
        }

    }

    @Override
    public Note findById(int id) {
        try {
            String sql = "SELECT * FROM note WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, id);

                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    return mapNote(rs);

                }
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error finding note with id: " + id, e);
        }

    }

    @Override
    public List<Note> findAll() {
        try {
            String sql = "SELECT * FROM note";
            List<Note> notes = new ArrayList<>();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    notes.add(mapNote(rs));

                }

            }
            return notes;
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all notes", e);
        }
    }

    @Override
    public void update(Note entity) {
        try {
            String sql = "UPDATE note SET title= ?, content= ? WHERE id= ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, entity.getTitle());
                statement.setString(2, entity.getContent());
                statement.setInt(3, entity.getId());

                int rows = statement.executeUpdate();
                if (rows == 0) {
                    throw new DataAccessException("No notes found with id: " + entity.getId());
                }

            }


        } catch (SQLException e) {
            throw new DataAccessException("Error updating notes with id: " + entity.getId(), e);
        }
    }

    @Override
    public void delete(int id) {
        try {

            String sql = "DELETE FROM note WHERE id= ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, id);

                int rows = statement.executeUpdate();
                if (rows == 0) {
                    throw new DataAccessException("No note found with id: " + id);
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting note with id: " + id, e);
        }
    }




    private Note mapNote(ResultSet rs) throws SQLException {

        Note note = new Note();

        note.setId(rs.getInt("id"));
        note.setTitle(rs.getString("title"));
        note.setContent(rs.getString("content"));

        Timestamp ts = rs.getTimestamp("creation_date");
        if (ts != null) {
            note.setCreationDate(ts.toLocalDateTime());
        }

        return note;
    }


}

package infrastructure.sql.dao;

import common.dao.GenericDAO;
import common.exception.DataAccessException;
import common.exception.EntityNotFoundException;
import event.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLEventDAOAdapter implements GenericDAO<Event> {

    private final Connection connection;

    public MySQLEventDAOAdapter(Connection connection){
        this.connection = connection;
    }

    @Override
    public Event insert(Event entity){

        try {
            String sql = "INSERT INTO event (title, content, creation_date, expiration_date) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, entity.getTitle());
                statement.setString(2, entity.getContent());
                statement.setTimestamp(3, Timestamp.valueOf(entity.getCreationDate()));
                if (entity.getExpirationDate() != null) {
                    statement.setDate(4, Date.valueOf(entity.getExpirationDate()));
                } else {
                    statement.setNull(4, Types.DATE);
                }
                statement.executeUpdate();

                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    entity.setId(keys.getInt(1));
                }

            }
                return entity;
            } catch (SQLException e){
            throw new DataAccessException("DAO error [update] : Error inserting event", e);
        }
    }

    @Override
    public Event findById(int id){
        try {
            String sql = "SELECT * FROM event WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, id);

                ResultSet rs = statement.executeQuery();
                if (rs.next()){
                    return mapEvent(rs);
                }
            }
            return null;

        } catch (SQLException e){
            throw new DataAccessException("DAO error [executeQuery] : Error finding event with id: "+ id, e);
        }
    }

    @Override
    public List<Event> findAll(){
        try {
            String sql = "SELECT * FROM event";
            List<Event> events = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(sql)){

                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    events.add(mapEvent(rs));
                }
            }
            return events;
        } catch (SQLException e){
            throw new DataAccessException("DAO error [executeQuery] : Error finding all events", e);
        }
    }

    @Override
    public void update(Event entity){
        try {
            String sql = "UPDATE event SET title= ?, content= ?, expiration_date=? WHERE id= ?";

            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setString(1, entity.getTitle());
                statement.setString(2, entity.getContent());

                if (entity.getExpirationDate() != null) {
                    statement.setDate(3, Date.valueOf(entity.getExpirationDate()));
                }else{
                    statement.setNull(3, Types.DATE);
                }
                statement.setInt(4,entity.getId());
                int rows = statement.executeUpdate();
                if(rows==0) throw new EntityNotFoundException("No Event found with id: "+ entity.getId());

            }

        } catch (SQLException e){
            throw new DataAccessException("DAO error [update] : Error updating event with id "+ entity.getId(), e);
        }
    }

    @Override
    public void delete(int id){

        try {
            String sql = "DELETE FROM event WHERE id= ?";
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, id);
                int rows = statement.executeUpdate();
                if(rows==0) throw new EntityNotFoundException("No event found with id "+ id);
            }
        } catch (SQLException e){
            throw new DataAccessException("DAO error [update] : Error deleting the event with id "+ id, e);
        }
    }

    private Event mapEvent(ResultSet rs) throws SQLException{
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setTitle(rs.getString("title"));
        event.setContent(rs.getString("content"));
        event.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());

        Date expDate = rs.getDate("expiration_date");
        if (expDate != null){
            event.setExpirationDate(expDate.toLocalDate());
        }

        return event;

    }

}

package infrastructure.sql.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    void insert(T entity) throws SQLException;

    T findById(int id) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(T entity) throws SQLException;

    void delete(int id) throws SQLException;
}
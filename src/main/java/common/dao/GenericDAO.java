package common.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    T insert(T entity);

    T findById(int id);

    List<T> findAll();

    void update(T entity);

    void delete(int id);
}
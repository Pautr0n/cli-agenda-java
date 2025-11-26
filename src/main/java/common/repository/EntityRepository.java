package common.repository;

import common.dao.GenericDAO;
import common.exception.DataAccessException;

import java.util.List;

public abstract class EntityRepository<T> {
    protected final GenericDAO<T> dao;

    protected EntityRepository(GenericDAO<T> dao) {
        this.dao = dao;
    }

    public void add(T entity) {
        try {
            dao.insert(entity);
        } catch (Exception e) {
            throw new DataAccessException("Error inserting entity", e);
        }
    }

    public T getById(int id) {
        try {
            return dao.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Error finding entity with id " + id, e);
        }
    }

    public List<T> getAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error retrieving entities", e);
        }
    }

    public void update(T entity) {
        try {
            dao.update(entity);
        } catch (Exception e) {
            throw new DataAccessException("Error updating entity", e);
        }
    }

    public void remove(int id) {
        try {
            dao.delete(id);
        } catch (Exception e) {
            throw new DataAccessException("Error deleting entity with id " + id, e);
        }
    }
}

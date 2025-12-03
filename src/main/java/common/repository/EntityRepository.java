package common.repository;

import common.dao.GenericDAO;
import common.exception.DataAccessException;
import common.exception.EntityNotFoundException;

import java.util.List;

public abstract class EntityRepository<T> {
    protected final GenericDAO<T> dao;

    protected EntityRepository(GenericDAO<T> dao) {
        this.dao = dao;
    }

    public T add(T entity) {
        try {
            entity = dao.insert(entity);
            return entity;
        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Repository [add]: Unexpected error inserting entity", e);
        }

    }

    public T getById(int id) {
        try {
            return dao.findById(id);
        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Repository [getById]: Unexpected error finding entity with id " + id, e);
        }
    }

    public List<T> getAll() {
        try {
            return dao.findAll();
        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Repository [getAll]: Unexpected error retrieving entities", e);
        }

    }

    public void update(T entity) {
        try {
            dao.update(entity);
        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Repository [update]: Unexpected error updating entity", e);
        }
    }

    public void remove(int id) {
        try {
            dao.delete(id);
        } catch (EntityNotFoundException | DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Repository [remove]: Unexpected error deleting entity with id " + id, e);
        }
    }
}

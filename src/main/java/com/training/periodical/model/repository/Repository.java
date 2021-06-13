package com.training.periodical.model.repository;

import com.training.periodical.model.dao.DaoException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Interface for repositories for various entities
 *
 */
public interface Repository<T> extends Serializable {
    int create(T entity) throws RepositoryException;
    Optional<T> findById(long id) throws RepositoryException;
    List<T> findAll() throws RepositoryException;
    int update(T entity) throws RepositoryException;
    int delete(long id) throws RepositoryException;
    List<T> findPage(int pageSize, int offSet) throws RepositoryException;

     RepositoryException createRepositoryException(String methodName, DaoException e);
     RepositoryException createRepositoryException(String methodName, Exception e);
}

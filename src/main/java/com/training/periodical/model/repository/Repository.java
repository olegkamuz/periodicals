package com.training.periodical.model.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<T> extends Serializable {
    int create(T entity) throws RepositoryException;
    Optional<T> findById(long id) throws RepositoryException;
    List<T> findAll() throws RepositoryException;
    int update(T entity) throws RepositoryException;
    int delete(long id) throws RepositoryException;
    List<T> findPage(int pageSize, int offSet) throws RepositoryException;
}

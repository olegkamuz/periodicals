package com.training.periodical.model.dao;

import com.training.periodical.model.mapper.Mapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

/**
 * Interface for Data Access Objects
 *
 */
public interface IDao<T> extends AutoCloseable, Serializable {
    int create(T entity) throws DaoException;

    Optional<T> findById(long id) throws DaoException;

    List<T> findAll(Mapper<T> mapper) throws DaoException;

    int update(T entity) throws DaoException;

    int delete(long id) throws DaoException;

    DaoException createDaoException(String methodName, Exception e);

    void close(ResultSet rs) throws DaoException;

}

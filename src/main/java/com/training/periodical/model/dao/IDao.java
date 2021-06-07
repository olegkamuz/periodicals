package com.training.periodical.model.dao;

import com.training.periodical.model.builder.Builder;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface IDao<T> extends AutoCloseable, Serializable {
    int create(T entity) throws DaoException;
    Optional<T> findById(long id) throws DaoException;
    List<T> findAll(Connection connection, Builder<T> builder) throws DaoException;
    int update(T entity) throws DaoException;
    int delete(long id);
}

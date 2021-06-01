package com.training.periodical.model.dao;

import com.training.periodical.model.builder.Builder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IDao<T> extends AutoCloseable {
    void create(T entity) throws DaoException;
    Optional<T> findById(String id) throws DaoException;
    List<T> findAll(Connection connection, Builder<T> builder) throws DaoException;
    int update(T entity) throws DaoException;
    void delete(int id);
}

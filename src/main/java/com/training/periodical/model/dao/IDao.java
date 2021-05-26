package com.training.periodical.model.dao;

import java.sql.SQLException;
import java.util.Optional;

public interface IDao<T> {
    Optional<T> getById(long id) throws DaoException;
}

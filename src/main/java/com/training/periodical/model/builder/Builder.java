package com.training.periodical.model.builder;

import com.training.periodical.entity.User;
import com.training.periodical.model.dao.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Defines general contract for mapping database result set rows to application entities.
 * Implementations are not supposed to move cursor of the resultSet via next() method,
 * but only extract information from the row in current cursor position.
 */
public interface Builder<T> {
    T build(ResultSet rs) throws SQLException;
}

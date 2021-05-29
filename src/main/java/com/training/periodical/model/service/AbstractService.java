package com.training.periodical.model.service;

import com.training.periodical.model.dao.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractService<T> implements Service<T>{

    protected abstract ServiceException createServiceException(String methodName, DaoException e);

    /**
     * Commits given connection.
     */
    public void commit(Connection connection) throws DaoException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    /**
     * Rollbacks given connection.
     */
    public void rollback(Connection connection) throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }
}

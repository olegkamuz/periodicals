package com.training.periodical.model.repository;

import com.training.periodical.entity.Magazine;
import com.training.periodical.model.dao.DaoException;


/**
 * Abstract class for repositories for various entities
 * @see Magazine
 *
 */
public abstract class AbstractRepository<T> implements Repository<T> {
    private static final long serialVersionUID = -3395603216979632119L;

    @Override
    public RepositoryException createRepositoryException(String methodName, DaoException e) {
        return new RepositoryException("exception in " +
                methodName +
                " method at " +
                getClass().getSimpleName(), e);
    }

    @Override
    public RepositoryException createRepositoryException(String methodName, Exception e) {
        return new RepositoryException("exception in " +
                methodName +
                " method at " +
                getClass().getSimpleName(), e);
    }
}

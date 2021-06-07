package com.training.periodical.model.repository;

import com.training.periodical.model.dao.DaoException;

public abstract class AbstractRepository<T> implements Repository<T> {
    private static final long serialVersionUID = -3395603216979632119L;

    protected abstract RepositoryException createRepositoryException(String methodName, DaoException e);
}

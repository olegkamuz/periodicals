package com.training.periodical.model.service;

import com.training.periodical.model.dao.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractService<T> implements Service<T>{
    private static final long serialVersionUID = -3395603216979632119L;

    protected abstract ServiceException createServiceException(String methodName, DaoException e);
}

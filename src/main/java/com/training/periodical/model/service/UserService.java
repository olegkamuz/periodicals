package com.training.periodical.model.service;

import com.training.periodical.entity.User;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

public class UserService implements Service{
    private static final Logger log = Logger.getLogger(UserService.class);
    private UserDao userDao;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void updateBalance(long userId, BigDecimal userBalance) throws ServiceException {
        try {
            userDao.updateBalance(userId, userBalance);
        } catch (DaoException e) {
            throw createServiceException(e);
        }
    }

    public Optional<User> getById(long id) throws ServiceException {
        try {
            return userDao.getById(id);
        } catch (DaoException e) {
            throw createServiceException(e);
        }
    }

    private ServiceException createServiceException(DaoException e) {
        return new ServiceException("exception in " +
                this.getClass().getEnclosingMethod().getName() +
                " method at " +
                this.getClass().getSimpleName() , e);
    }
}




















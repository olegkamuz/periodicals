package com.training.periodical.model.service;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.training.periodical.entity.User;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

public class UserService extends AbstractService<User> {
    private static final Logger log = Logger.getLogger(UserService.class);
    private IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public void updateBalance(long userId, BigDecimal userBalance) throws ServiceException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.updateBalance(userId, userBalance);
        } catch (DaoException e) {
            throw createServiceException("updateBalance", e);
        }
    }

    public Optional<User> findUserByLogin(String login) throws ServiceException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw createServiceException("findUserByLogin", e);
        }
    }

    @Override
    public Optional<User> findById(long id) throws ServiceException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw createServiceException("getById", e);
        }
    }

    @Override
    protected ServiceException createServiceException(
            String methodName,
            DaoException e) {
        return new ServiceException("exception in " +
                methodName + " method at " +
                this.getClass().getSimpleName(), e);
    }

}




















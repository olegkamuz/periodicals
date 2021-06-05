package com.training.periodical.model.service;

import com.training.periodical.entity.User;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserService extends AbstractService<User> {
    private static final long serialVersionUID = 8200073681069516742L;
    private static final Logger log = Logger.getLogger(UserService.class);
    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public void updateBalance(BigDecimal userBalance, long userId) throws ServiceException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.updateBalance(userBalance, userId);
        } catch (DaoException e) {
            throw createServiceException("updateBalance", e);
        }
    }

    public int update(String userId, String column, String value) throws ServiceException{
        try(UserDao userDao = daoFactory.createUserDao()) {
            return userDao.update(userId, column, value);
        } catch (DaoException e) {
           throw createServiceException("update", e);
        }
    }

    public int updateNow(User user) throws ServiceException{
        try(UserDao userDao = daoFactory.createUserDao()) {
            return userDao.updateNow(user);
        } catch (DaoException e) {
            throw createServiceException("update", e);
        }
    }

    public Optional<User> findUserByLogin(String login) throws ServiceException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw createServiceException("findUserByLogin", e);
        }
    }

    public List<User> findAll() throws ServiceException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        } catch (DaoException e) {
            throw createServiceException("findAll", e);
        }
    }

    @Override
    public int update(User entity) throws ServiceException {
        return 0;
    }

    @Override
    public int delete(long id) throws ServiceException {
        return 0;
    }

    @Override
    public int create(User user) throws ServiceException{
        try(UserDao userDao = daoFactory.createUserDao()){
            return userDao.create(user);
        } catch (DaoException e) {
            throw createServiceException("create", e);
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




















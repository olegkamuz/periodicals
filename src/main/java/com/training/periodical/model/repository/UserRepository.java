package com.training.periodical.model.repository;

import com.training.periodical.entity.User;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserRepository extends AbstractRepository<User> {
    private static final long serialVersionUID = 8200073681069516742L;
    private static final Logger log = Logger.getLogger(UserRepository.class);
    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public void updateBalance(BigDecimal userBalance, long userId) throws RepositoryException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.updateBalance(userBalance, userId);
        } catch (DaoException e) {
            throw createRepositoryException("updateBalance", e);
        }
    }

    public int update(String userId, String column, String value) throws RepositoryException {
        try(UserDao userDao = daoFactory.createUserDao()) {
            return userDao.update(userId, column, value);
        } catch (DaoException e) {
           throw createRepositoryException("update", e);
        }
    }

    public int updateNow(User user) throws RepositoryException {
        try(UserDao userDao = daoFactory.createUserDao()) {
            return userDao.updateNow(user);
        } catch (DaoException e) {
            throw createRepositoryException("update", e);
        }
    }

    public Optional<User> findUserByLogin(String login) throws RepositoryException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw createRepositoryException("findUserByLogin", e);
        }
    }

    @Override
    public List<User> findAll() throws RepositoryException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        } catch (DaoException e) {
            throw createRepositoryException("findAll", e);
        }
    }

    public List<User> findAllClients() throws RepositoryException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAllClients();
        } catch (DaoException e) {
            throw createRepositoryException("findAll", e);
        }
    }

    @Override
    public int update(User entity) throws RepositoryException {
        return 0;
    }

    @Override
    public int delete(long id) throws RepositoryException {
        return 0;
    }

    @Override
    public List<User> findPage(int pageSize, int offSet) throws RepositoryException {
        return null;
    }

    @Override
    public int create(User user) throws RepositoryException {
        try(UserDao userDao = daoFactory.createUserDao()){
            return userDao.create(user);
        } catch (DaoException e) {
            throw createRepositoryException("create", e);
        }
    }

    @Override
    public Optional<User> findById(long id) throws RepositoryException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw createRepositoryException("getById", e);
        }
    }

    @Override
    protected RepositoryException createRepositoryException(
            String methodName,
            DaoException e) {
        return new RepositoryException("exception in " +
                methodName + " method at " +
                this.getClass().getSimpleName(), e);
    }

}




















package com.training.periodical.model.repository;

import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserSubscriptionRepository extends AbstractRepository<UserSubscriptionBean> {
    private static final long serialVersionUID = 3297539402663043130L;
    private static final Logger log = Logger.getLogger(UserRepository.class);
    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public List<UserSubscriptionBean> findSubscriptionByUserId(long userId) throws RepositoryException {
        try (final UserDao USDao = daoFactory.createUserDao()) {
            return USDao.getSubscriptionsByUserId(userId);
        } catch (DaoException e) {
            throw createRepositoryException("findSubscriptionByUserId", e);
        }
    }

    @Override
    public int create(UserSubscriptionBean entity) throws RepositoryException {
        return 0;
    }

    @Override
    public Optional<UserSubscriptionBean> findById(long id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public List<UserSubscriptionBean> findAll() throws RepositoryException {
        return null;
    }

    @Override
    public int update(UserSubscriptionBean entity) throws RepositoryException {
        return 0;
    }

    @Override
    public int delete(long id) throws RepositoryException {
        return 0;
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

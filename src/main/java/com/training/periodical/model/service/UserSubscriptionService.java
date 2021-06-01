package com.training.periodical.model.service;

import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserSubscriptionService extends AbstractService<UserSubscriptionBean> {
    private static final Logger log = Logger.getLogger(UserService.class);
    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public List<UserSubscriptionBean> findSubscriptionByUserId(long userId) throws ServiceException{
        try (final UserDao USDao = daoFactory.createUserDao()) {
            return USDao.getSubscriptionsByUserId(userId);
        } catch (DaoException e) {
            throw createServiceException("findSubscriptionByUserId", e);
        }
    }

    @Override
    public Optional<UserSubscriptionBean> findById(long id) throws ServiceException {
        return Optional.empty();
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

package com.training.periodical.model.service;

import com.training.periodical.entity.Subscription;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.SubscriptionDao;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class SubscriptionService extends AbstractService<Subscription> {
    private static final Logger log = Logger.getLogger(SubscriptionService.class);
    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public void createSubscriptionPurchase(Long userId, String[] magazineIds, BigDecimal userBalance) throws ServiceException {
        try (SubscriptionDao subscriptionDao = daoFactory.createSubscriptionDao();
             Connection connection = subscriptionDao.getConnection();
             UserDao userDao = daoFactory.createUserDao(connection)) {
            subscriptionDao.createSubscription(userId, magazineIds, userDao, userBalance);
            log.info(this.getClass().getSimpleName() + " creating subscription using Daos");
        } catch (DaoException | SQLException e) {
            throw createServiceException("createSubscriptionPurchase", e);
        }
    }

    @Override
    public Optional<Subscription> findById(String id) {
        return Optional.empty();
    }

    @Override
    protected ServiceException createServiceException(String methodName, DaoException e) {
        return new ServiceException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }

    protected ServiceException createServiceException(String methodName, Exception e) {
        return new ServiceException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}




















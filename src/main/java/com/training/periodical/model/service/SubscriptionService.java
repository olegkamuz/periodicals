package com.training.periodical.model.service;

import com.training.periodical.entity.User;
import com.training.periodical.model.dao.SubscriptionDao;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SubscriptionService implements Service<User> {
    private static final Logger log = Logger.getLogger(SubscriptionService.class);
    private UserService userService;
    private SubscriptionDao subscriptionDao;


    public SubscriptionService(UserService userService, SubscriptionDao subscriptionDao) {
        this.userService = userService;
        this.subscriptionDao = subscriptionDao;
    }

    public void createSubscriptionPurchase(Long userId, List<Long> magazineIds, BigDecimal userBalance, TransactionManager transactionManager){
        try {
            subscriptionDao.createSubscription(userId, magazineIds);
            userService.updateBalance(userId, userBalance);
        } catch (SQLException e){
            transactionManager.rollbackAndClose();
            log.error(e.getMessage(), e);
        } finally {
            transactionManager.commitAndClose();
        }
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.empty();
    }
}




















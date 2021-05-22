package com.training.periodical.service;

import com.training.periodical.db.SubscriptionDao;
import com.training.periodical.db.UserDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class CreateSubscriptionService {
    private static final Logger log = Logger.getLogger(CreateSubscriptionService.class);
    private UserDao userDao;
    private SubscriptionDao subscriptionDao;
    private TransactionManager transactionManager;


    public CreateSubscriptionService(UserDao userDao, SubscriptionDao subscriptionDao, TransactionManager transactionManager) {
        this.userDao = userDao;
        this.subscriptionDao = subscriptionDao;
        this.transactionManager = transactionManager;
    }

    public void createSubscriptionPurchase(Long userId, List<Long> magazineIds, BigDecimal userBalance){
        try {
            subscriptionDao.createSubscription(userId, magazineIds);
            userDao.updateBalance(userId, userBalance);
        } catch (SQLException e){
            transactionManager.rollbackAndClose();
            log.error(e.getMessage(), e);
        } finally {
            transactionManager.commitAndClose();
        }
    }
}




















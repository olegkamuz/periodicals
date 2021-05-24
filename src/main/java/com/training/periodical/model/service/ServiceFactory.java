package com.training.periodical.model.service;

import com.training.periodical.model.dao.DaoFactory;

import java.sql.Connection;

public class ServiceFactory {
    private DaoFactory daoFactory;
    public ServiceFactory(Connection connection) {
        daoFactory = new DaoFactory(connection);
    }
    public UserService createUserService() {
        return new UserService(daoFactory.getUserDao());
    }
    public SubscriptionService createSubscriptionService() {
        return new SubscriptionService(createUserService(), daoFactory.getSubscriptionDao());
    }
}

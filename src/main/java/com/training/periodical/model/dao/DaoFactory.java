package com.training.periodical.model.dao;

import java.sql.Connection;

public class DaoFactory {
    private Connection connection;

    public DaoFactory(Connection connection) {
        this.connection = connection;
    }

    public SubscriptionDao getSubscriptionDao(){
        return new SubscriptionDao(connection);
    }

    public UserDao getUserDao(){
        return new UserDao(connection);
    }
}

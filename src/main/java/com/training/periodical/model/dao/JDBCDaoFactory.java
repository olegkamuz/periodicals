package com.training.periodical.model.dao;

import com.training.periodical.model.builder.MagazineBuilder;
import com.training.periodical.model.builder.SubscriptionBuilder;
import com.training.periodical.model.builder.ThemeBuilder;
import com.training.periodical.model.builder.UserBuilder;

import java.sql.Connection;

public class JDBCDaoFactory extends AbstractDaoFactory{

    private DBManager dbManager = DBManager.getInstance();

    @Override
    public UserDao createUserDao() {
        return new UserDao(dbManager.getConnection(), new UserBuilder());
    }

    public UserDao createUserDao(Connection connection) {
        return new UserDao(connection, new UserBuilder());
    }

    @Override
    public SubscriptionDao createSubscriptionDao() {
        return new SubscriptionDao(dbManager.getConnection(), new SubscriptionBuilder());
    }

    @Override
    public MagazineDao createMagazineDao() {
        return new MagazineDao(dbManager.getConnection(), new MagazineBuilder());
    }

    @Override
    public ThemeDao createThemeDao(){
        return new ThemeDao(dbManager.getConnection(), new ThemeBuilder());
    }
}
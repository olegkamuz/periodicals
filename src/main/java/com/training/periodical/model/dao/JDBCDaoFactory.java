package com.training.periodical.model.dao;

import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.Subscription;
import com.training.periodical.entity.Theme;
import com.training.periodical.entity.User;
import com.training.periodical.model.builder.MagazineBuilder;
import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.mapper.MagazineMapper;
import com.training.periodical.model.mapper.SubscriptionMapper;
import com.training.periodical.model.mapper.ThemeMapper;
import com.training.periodical.model.mapper.UserMapper;
import com.training.periodical.model.mapper.UserSubscriptionsMapper;

import java.sql.Connection;

/**
 * Factory of JDBC Data access objects for various entities
 * with injected dependencies
 * @see Magazine
 * @see User
 * @see Subscription
 * @see Theme
 *
 */
public class JDBCDaoFactory extends AbstractDaoFactory{
    private static final long serialVersionUID = 9081081887394751631L;
    private DBManager dbManager = DBManager.getInstance();

    @Override
    public UserDao createUserDao() {
        return new UserDao(dbManager.getConnection(), new UserMapper(), new UserSubscriptionsMapper(), new UserBuilder());
    }

    @Override
    public UserDao createUserDao(Connection connection) {
        return new UserDao(connection, new UserMapper(), new UserBuilder());
    }

    @Override
    public SubscriptionDao createSubscriptionDao() {
        return new SubscriptionDao(dbManager.getConnection(), new SubscriptionMapper());
    }

    @Override
    public MagazineDao createMagazineDao() {
        return new MagazineDao(dbManager.getConnection(), new MagazineMapper(), new MagazineBuilder());
    }

    @Override
    public ThemeDao createThemeDao(){
        return new ThemeDao(dbManager.getConnection(), new ThemeMapper());
    }
}

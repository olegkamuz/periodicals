package com.training.periodical.model.dao;

import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.Subscription;
import com.training.periodical.entity.Theme;
import com.training.periodical.model.builder.SubscriptionBuilder;
import com.training.periodical.model.dao.query.SubscriptionQuery;
import com.training.periodical.model.dao.query.ThemeQuery;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for Order entity and UserOrderBean bean.
 */
public class SubscriptionDao extends AbstractDao<Subscription> {

    private static final Logger log = Logger.getLogger(UserDao.class);
    private final SubscriptionBuilder builder;
    private final Connection connection;

    public SubscriptionDao(Connection connection, SubscriptionBuilder subscriptionBuilder) {
        this.connection = connection;
        builder = subscriptionBuilder;
        tableName = "subscription";
    }

    public void create(Subscription subscription) {}
    public int update(Subscription subscription) throws DaoException{
        return 0;
    }
    public void delete(int id){}

    public List<Subscription> findAll() throws DaoException {
        return findAll(connection, builder);
    }

    public void createSubscription(Long userId, String[] magazineIds, UserDao userDao, BigDecimal userBalance) throws DaoException {
        try {
            for (String magazineId : magazineIds) {
                String[] parameters = {String.valueOf(userId), magazineId};
                executeUpdate(connection,SubscriptionQuery.SQL__INSERT_SUBSCRIPTION, parameters);
            }
            userDao.updateUser(connection, userId, userBalance);
        } catch (DaoException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            commit(connection);
        }
    }

    @Override
    public Optional<Subscription> findById(String id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        return this.connection;
    }

    public String getTableName() {
        return tableName;
    }
}














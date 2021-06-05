package com.training.periodical.model.dao;

import com.training.periodical.entity.Subscription;
import com.training.periodical.model.builder.SubscriptionBuilder;
import com.training.periodical.model.dao.query.SubscriptionQuery;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for Order entity and UserOrderBean bean.
 */
public class SubscriptionDao extends AbstractDao<Subscription> {
    private static final long serialVersionUID = -6448380123363743542L;
    private static final Logger log = Logger.getLogger(UserDao.class);
    private final SubscriptionBuilder builder;
    private final Connection connection;

    public SubscriptionDao(Connection connection, SubscriptionBuilder subscriptionBuilder) {
        this.connection = connection;
        builder = subscriptionBuilder;
        tableName = "subscription";
    }

    @Override
    public int create(Subscription subscription) {
        return 0;
    }

    @Override
    public int update(Subscription subscription) throws DaoException {
        return 0;
    }

    @Override
    public int delete(long id) {
        return 0;
    }

    public List<Subscription> findAll() throws DaoException {
        return findAll(connection, builder);
    }

    public int countByCompositeKey(long userId, long magazineId) throws DaoException{
        ResultSet rs = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SubscriptionQuery.SQL__COUNT_BY_COMPOSITE_KEY)) {
            Object[] parameters = {userId, magazineId};
            prepareStatement(preparedStatement, parameters);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            close(rs);
        }
        return 0;
    }

    public void createSubscription(Long userId, List<String> magazineIds, UserDao userDao, BigDecimal userBalance) throws DaoException {
        try {
            for (String magazineId : magazineIds) {
                Object[] parameters = {userId, magazineId};
                executeUpdate(connection, SubscriptionQuery.SQL__INSERT_SUBSCRIPTION, parameters);
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
    public Optional<Subscription> findById(long id) throws DaoException {
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

    public Connection getConnection() {
        return this.connection;
    }

    public String getTableName() {
        return tableName;
    }

    private static void close(ResultSet rs) throws DaoException {
        if (rs == null) return;
        try {
            rs.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected DaoException createDaoException(String methodName, Exception e) {
        return new DaoException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}














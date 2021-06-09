package com.training.periodical.model.dao;

import com.training.periodical.entity.Subscription;
import com.training.periodical.model.dao.query.SubscriptionQuery;
import com.training.periodical.model.mapper.SubscriptionMapper;

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
    private final SubscriptionMapper mapper;

    public SubscriptionDao(Connection connection, SubscriptionMapper subscriptionMapper) {
        this.connection = connection;
        mapper = subscriptionMapper;
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
        return findAll(mapper);
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
            connection.setAutoCommit(false);
            for (String magazineId : magazineIds) {
                executeUpdate( SubscriptionQuery.SQL__INSERT_SUBSCRIPTION, userId, magazineId);
            }
            userDao.updateUser(userId, userBalance);
        } catch (DaoException | SQLException e) {
            rollback();
            throw new DaoException(e);
        } finally {
            commit();
            setAutocommitTrue();
        }
    }

    private void setAutocommitTrue() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("exception at setAutocommitTrue at SubscriptionDao", e);
        }
    }

    @Override
    public Optional<Subscription> findById(long id) throws DaoException {
        return Optional.empty();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public String getTableName() {
        return tableName;
    }

}














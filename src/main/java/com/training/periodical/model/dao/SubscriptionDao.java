package com.training.periodical.model.dao;

import com.training.periodical.entity.Subscription;
import com.training.periodical.model.builder.SubscriptionBuilder;
import com.training.periodical.model.builder.UserBuilder;
import org.apache.log4j.Logger;

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

    public SubscriptionDao(Connection connection) {
        super(connection);
    }

    public void createSubscription(Long userId, List<Long> magazineIds) throws DaoException {
        for (long magazineId : magazineIds) {
            String[] parameters = {String.valueOf(userId), String.valueOf(magazineId)};
            executeUpdate(SubscriptionQuery.SQL__INSERT_SUBSCRIPTION, parameters);
        }
    }

    @Override
    public Optional getById(long id) throws DaoException {
        return Optional.empty();
    }
}














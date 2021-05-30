package com.training.periodical.model.dao;

import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.User;
import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.builder.UserSubscriptionsBuilder;
import com.training.periodical.model.dao.query.UserQuery;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Data access object for User entity.
 */
public class UserSubscriptionDao extends AbstractDao<UserSubscriptionBean> {
    private static final Logger log = Logger.getLogger(UserSubscriptionDao.class);
    private UserBuilder userBuilder;
    private UserSubscriptionsBuilder USBuilder;
    private final Connection connection;

    public UserSubscriptionDao(Connection connection, UserBuilder userBuilder, UserSubscriptionsBuilder usBuilder) {
        this.connection = connection;
        this.userBuilder = userBuilder;
        this.USBuilder = usBuilder;
        tableName = "user";
    }

    public List<UserSubscriptionBean> getSubscriptionsByUserId(long userId) throws DaoException {
        String[] parameters = {String.valueOf(userId)};
        try {
            return executeQuery(connection, UserQuery.SQL__FIND_SUBSCRIPTIONS_WHERE_USER_ID, USBuilder, parameters);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<UserSubscriptionBean> findAll() throws DaoException {
        return findAll(connection, userBuilder);
    }

    public void create(User user) throws DaoException {
        String[] parameters = {
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        };
        executeUpdate(connection, UserQuery.SQL__CREATE_USER, parameters); // get id from result
    }
    public void update(User user){}
    public void delete(int id){}

    @Override
    public void create(UserSubscriptionBean entity) throws DaoException {

    }

    /**
     * Returns a user with the given identifier.
     *
     * @param id User identifier.
     * @return User entity.
     */
    @Override
    public Optional<UserSubscriptionBean> findById(long id) throws DaoException {
        try {
            return executeSingleResponseQuery(connection, UserQuery.SQL__FIND_USER_BY_ID, USBuilder, String.valueOf(id));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(UserSubscriptionBean entity) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


package com.training.periodical.model.dao;

import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.entity.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.dao.query.UserQuery;
import com.training.periodical.model.dao.query.UserSubscriptionBeanQuery;
import com.training.periodical.model.mapper.UserMapper;
import com.training.periodical.model.mapper.UserSubscriptionsMapper;

/**
 * Data access object for User entity.
 * @see User
 */
public class UserDao extends AbstractDao<User> {
    private static final long serialVersionUID = 8896652975777115207L;
    private final UserMapper userMapper;
    private UserSubscriptionsMapper userSubscriptionsMapper;
    private final UserBuilder userBuilder;

    public UserDao(Connection connection, UserMapper userMapper, UserSubscriptionsMapper userSubscriptionsMapper, UserBuilder userBuilder) {
        this.userBuilder = userBuilder;
        this.connection = connection;
        this.userMapper = userMapper;
        this.userSubscriptionsMapper = userSubscriptionsMapper;
        tableName = "user";
    }

    public UserDao(Connection connection, UserMapper userMapper, UserBuilder userBuilder) {
        this.userBuilder = userBuilder;
        this.connection = connection;
        this.userMapper = userMapper;
        tableName = "user";
    }

    public List<User> findAll() throws DaoException {
        return findAll(userMapper);
    }

    public List<User> findAllClients() throws DaoException {
        Object[] parameters = {};
        return executeQuery(UserQuery.SQL__FIND_ALL_CLIENTS, userMapper, parameters);
    }

    public List<UserSubscriptionBean> getSubscriptionsByUserId(long userId) throws DaoException {
        Object[] parameters = {String.valueOf(userId)};
        try {
            return executeBeanQuery(parameters);
        } catch (SQLException e) {
            throw createDaoException("getSubscriptionsByUserId", e);
        }
    }

    protected List<UserSubscriptionBean> executeBeanQuery(Object... parameters) throws SQLException {
        ResultSet resultSet = null;
        List<UserSubscriptionBean> entity = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UserSubscriptionBeanQuery.SQL__FIND_SUBSCRIPTIONS_WHERE_USER_ID)) {
            prepareStatement(preparedStatement, parameters);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserSubscriptionBean build = userSubscriptionsMapper.build(resultSet);
                entity.add(build);
            }
            return entity;
        } finally {
            close(resultSet);
        }
    }

    public int create(User user) throws DaoException {
        Object[] parameters = userBuilder.unBuildStrippedUser(user);
        return executeUpdate(UserQuery.SQL__CREATE_USER, parameters);
    }

    public int update(String userId, String column, String value) throws DaoException {
        String query = UserQuery.getUpdateColumnQuery(userId, column);
        Object[] parameters = {value, userId};
        return executeUpdate(query, parameters);
    }

    @Override
    public int update(User user) throws DaoException {
        String query = UserQuery.SQL__UPDATE_USER;
        Object[] parameters = userBuilder.unBuild(user);
        return executeUpdate(query, parameters);
    }

    @Override
    public int delete(long id) {
        return 0;
    }

    /**
     * Returns a user with the given identifier.
     *
     * @param id User identifier.
     * @return User entity.
     */
    @Override
    public Optional<User> findById(long id) throws DaoException {
        return executeSingleResponseQuery(UserQuery.SQL__FIND_USER_BY_ID, userMapper, id);
    }

    /**
     * Returns a user with the given login.
     *
     * @param login User login.
     * @return User entity.
     */
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return executeSingleResponseQuery(UserQuery.SQL__FIND_USER_BY_LOGIN, userMapper, login);
    }

    public void updateUser(long userId, BigDecimal userBalance) throws DaoException {
        Object[] parameters = {String.valueOf(userBalance), String.valueOf(userId)};
        executeUpdate(UserQuery.SQL__UPDATE_BALANCE_WHERE_ID, parameters);
    }

    public void updateBalance(BigDecimal balance, long userId) throws DaoException {
        Object[] parameters = {balance, userId};
        executeUpdate(UserQuery.SQL__UPDATE_BALANCE_WHERE_ID, parameters);
    }
}


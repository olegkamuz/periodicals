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
import com.training.periodical.model.builder.UserSubscriptionsBuilder;
import com.training.periodical.model.dao.query.Query;
import com.training.periodical.model.dao.query.UserQuery;
import com.training.periodical.model.dao.query.UserSubscriptionBeanQuery;
import org.apache.log4j.Logger;

/**
 * Data access object for User entity.
 */
public class UserDao extends AbstractDao<User> {
    private static final long serialVersionUID = 8896652975777115207L;
    private static final Logger log = Logger.getLogger(UserDao.class);
    private UserBuilder userBuilder;
    private UserSubscriptionsBuilder USBuilder;
    private final Connection connection;

    public UserDao(Connection connection, UserBuilder userBuilder, UserSubscriptionsBuilder usBuilder) {
        this.connection = connection;
        this.userBuilder = userBuilder;
        USBuilder = usBuilder;
        tableName = "user";
    }

    public UserDao(Connection connection, UserBuilder userBuilder) {
        this.connection = connection;
        this.userBuilder = userBuilder;
        tableName = "user";
    }

    public List<User> findAll() throws DaoException {
        return findAll(connection, userBuilder);
    }

    public List<User> findAllClients() throws DaoException {
        try {
            Object[] parameters = {};
            return executeQuery(connection, UserQuery.SQL__FIND_ALL_CLIENTS, userBuilder, parameters);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    public List<UserSubscriptionBean> getSubscriptionsByUserId(long userId) throws DaoException {
        Object[] parameters = {String.valueOf(userId)};
        try {
            return executeBeanQuery(parameters);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected List<UserSubscriptionBean> executeBeanQuery(Object... parameters) throws SQLException {
        ResultSet resultSet;
        List<UserSubscriptionBean> entity = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(UserSubscriptionBeanQuery.SQL__FIND_SUBSCRIPTIONS_WHERE_USER_ID);
        prepareStatement(preparedStatement, parameters);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserSubscriptionBean build = USBuilder.build(resultSet);
            entity.add(build);
        }
        return entity;
    }

    public int create(User user) throws DaoException {
        Object[] parameters = userBuilder.unBuildStrippedUser(user);
        return executeUpdateNow(connection, UserQuery.SQL__CREATE_USER, parameters);
    }

    public int update(String userId, String column, String value) throws DaoException {
        String query = UserQuery.getUpdateColumnQuery(userId, column);
        Object[] parameters = {value, userId};
        return executeUpdate(connection, query, parameters);
    }

    public int updateNow(User user) throws DaoException {
        String query = UserQuery.SQL__UPDATE_USER;
        Object[] parameters = userBuilder.unBuild(user);
        return executeUpdateNow(connection, query, parameters);
    }

    @Override
    public int update(User user) throws DaoException {
        return 0;
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
        try {
            return executeSingleResponseQuery(connection, UserQuery.SQL__FIND_USER_BY_ID, userBuilder, id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Returns a user with the given login.
     *
     * @param login User login.
     * @return User entity.
     */
    public Optional<User> findUserByLogin(String login) throws DaoException {
        try {
            String[] parameters = {login};
            return executeSingleResponseQuery(connection, UserQuery.SQL__FIND_USER_BY_LOGIN, userBuilder, parameters);
        } catch (SQLException ex) {
            rollback(connection);
            throw new DaoException(ex);
        } finally {
            commit(connection);
        }
    }

    public void updateUser(long userId, BigDecimal userBalance) throws DaoException {
        Object[] parameters = {String.valueOf(userBalance), String.valueOf(userId)};
        executeUpdate(connection, UserQuery.SQL__UPDATE_BALANCE_WHERE_ID, parameters);
    }

    public void updateUser(Connection connection, long userId, BigDecimal userBalance) throws DaoException {
        Object[] parameters = {String.valueOf(userBalance), String.valueOf(userId)};
        executeUpdate(connection, UserQuery.SQL__UPDATE_BALANCE_WHERE_ID, parameters);
    }

    public void updateBalance(BigDecimal balance, long userId) throws DaoException {
        Object[] parameters = {balance, userId};
        executeUpdateNow(connection, UserQuery.SQL__UPDATE_BALANCE_WHERE_ID, parameters);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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


package com.training.periodical.model.dao;

import com.training.periodical.model.builder.Builder;
import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.dao.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements IDao<T> {
    private static final long serialVersionUID = 6242682689824341676L;
    protected String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public List<T> findAll(Connection connection, Builder<T> builder) throws DaoException {
        try {
            Object[] parameters = {};
            return executeQuery(connection, Query.findAllFromTable(tableName), builder, parameters);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }


    /**
     * Commits given connection.
     */
    public void commit(Connection connection) throws DaoException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    /**
     * Rollbacks given connection.
     */
    public void rollback(Connection connection) throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    public List<T> findAll(Connection connection, Builder<T> builder, int limit, int offset) throws DaoException {
        try {
            Object[] parameters = {limit, offset};
            return executeQuery(connection, MagazineQuery.SQL__FIND_MAGAZINE_PAGE, builder, parameters);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }


    protected List<T> executeQuery(Connection connection, String query, Builder<T> builder, Object... parameters) throws SQLException {
        ResultSet resultSet;
        List<T> entity = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        prepareStatement(preparedStatement, parameters);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            T build = builder.build(resultSet);
            entity.add(build);
        }
        return entity;
    }


    protected int executeUpdate(Connection connection, String query, Object... parameters) throws DaoException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            prepareStatement(preparedStatement, parameters);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);

        }
    }

    protected int executeUpdateNow(Connection connection, String query, Object... parameters) throws DaoException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            prepareStatement(preparedStatement, parameters);
            int result = preparedStatement.executeUpdate();
            connection.commit();
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected Optional<T> executeSingleResponseQuery(Connection connection, String query, Builder<T> builder, Object... parameters) throws SQLException {
        List<T> list = executeQuery(connection, query, builder, parameters);
        if (list.size() == 1) {
            return Optional.of(list.get(0));
        } else {
            return Optional.empty();
        }
    }

    protected void prepareStatement(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 1; i < parameters.length + 1; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
    }

    protected abstract DaoException createDaoException(String methodName, Exception e);
}

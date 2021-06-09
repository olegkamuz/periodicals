package com.training.periodical.model.dao;

import com.training.periodical.model.builder.Builder;
import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.dao.query.Query;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements IDao<T> {
    private static final long serialVersionUID = 6242682689824341676L;
    private final Logger log = Logger.getLogger(getClass());
    protected String tableName;
    Connection connection;

    public String getTableName() {
        return this.tableName;
    }

    public List<T> findAll(Builder<T> builder) throws DaoException {
        try {
            Object[] parameters = {};
            return executeQuery(Query.findAllFromTable(tableName), builder, parameters);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }


    /**
     * Commits given connection.
     */
    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    /**
     * Rollbacks given connection.
     */
    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    public List<T> findAll(Builder<T> builder, int limit, int offset) throws DaoException {
        try {
            Object[] parameters = {limit, offset};
            return executeQuery(MagazineQuery.SQL__FIND_MAGAZINE_PAGE, builder, parameters);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }


    protected List<T> executeQuery(String query, Builder<T> builder, Object... parameters) throws SQLException {
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


    protected int executeUpdate(String query, Object... parameters) throws DaoException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            prepareStatement(preparedStatement, parameters);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);

        }
    }

    protected int executeUpdateNow(String query, Object... parameters) throws DaoException {
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

    protected Optional<T> executeSingleResponseQuery( String query, Builder<T> builder, Object... parameters) throws SQLException {
        List<T> list = executeQuery(query, builder, parameters);
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

    @Override
    public DaoException createDaoException(String methodName, Exception e) {
        return new DaoException("exception in " +
                methodName +
                " method at " +
                getClass().getSimpleName(), e);
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
    public void close(ResultSet rs) throws DaoException {
        if (rs == null) return;
        try {
            rs.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}












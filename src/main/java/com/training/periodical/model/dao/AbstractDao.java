package com.training.periodical.model.dao;

import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.dao.query.Query;
import com.training.periodical.model.mapper.Mapper;
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
    Logger log = Logger.getLogger(getClass());
    protected String tableName;
    Connection connection;

    public String getTableName() {
        return this.tableName;
    }

    public List<T> findAll(Mapper<T> mapper) throws DaoException {
        Object[] parameters = {};
        return executeQuery(Query.findAllFromTable(tableName), mapper, parameters);
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

    public List<T> findAll(Mapper<T> mapper, int limit, int offset) throws DaoException {
        Object[] parameters = {limit, offset};
        return executeQuery(MagazineQuery.SQL__FIND_MAGAZINE_PAGE, mapper, parameters);
    }


    protected List<T> executeQuery(String query, Mapper<T> mapper, Object... parameters) {
        ResultSet resultSet = null;
        List<T> entity = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            prepareStatement(preparedStatement, parameters);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T build = mapper.build(resultSet);
                entity.add(build);
            }
            return entity;
        } catch (SQLException e) {
            log.error("exception in executeQuery at " + getClass());
        } finally {
            close(resultSet);
        }
        return entity;
    }


    protected int executeUpdate(String query, Object... parameters) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            prepareStatement(preparedStatement, parameters);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected Optional<T> executeSingleResponseQuery(String query, Mapper<T> mapper, Object... parameters) {
        List<T> list = executeQuery(query, mapper, parameters);
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
            log.error("exception in close connection");
        }
    }

    @Override
    public void close(ResultSet rs) {
        if (rs == null) return;
        try {
            rs.close();
        } catch (SQLException e) {
            log.error("exception in close result set");
        }
    }
}












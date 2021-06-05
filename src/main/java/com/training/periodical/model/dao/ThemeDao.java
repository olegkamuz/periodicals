package com.training.periodical.model.dao;

import com.training.periodical.entity.Theme;
import com.training.periodical.model.builder.ThemeBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ThemeDao extends AbstractDao<Theme> {
    private static final long serialVersionUID = -4329084276299458939L;
    private final ThemeBuilder builder;
    private final Connection connection;

    public ThemeDao(Connection connection, ThemeBuilder themeBuilder) {
        this.connection = connection;
        this.builder = themeBuilder;
        tableName = "theme";
    }

    public int create(Theme theme) {
        return 0;
    }
    public int update(Theme theme) throws DaoException{
        return 0;
    }
    public int delete(long id){
        return 0;
    }

    public List<Theme> findAll() throws DaoException {
        return findAll(connection, builder);
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
    public Optional<Theme> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    protected DaoException createDaoException(String methodName, Exception e) {
        return new DaoException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}

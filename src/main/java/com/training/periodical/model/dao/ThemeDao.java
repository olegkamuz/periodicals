package com.training.periodical.model.dao;

import com.training.periodical.entity.Theme;
import com.training.periodical.model.builder.ThemeBuilder;
import com.training.periodical.model.dao.query.ThemeQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ThemeDao extends AbstractDao<Theme> {
    private final ThemeBuilder builder;
    private final Connection connection;

    public ThemeDao(Connection connection, ThemeBuilder themeBuilder) {
        this.connection = connection;
        this.builder = themeBuilder;
        tableName = "theme";
    }

    public void create(Theme theme) {}
    public int update(String themeId, String column, String value) throws DaoException{
        return 0;
    }
    public void delete(int id){}

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
}
